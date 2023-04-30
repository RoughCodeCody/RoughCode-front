import { useState, useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery } from "@tanstack/react-query";
import { useQueryClient } from "@tanstack/react-query";
import axios from "axios";

import { ProjectCard } from "../project-card";
import { ProjectCardGrid } from "./style";
import { FlexDiv } from "@/components/elements";

import { useSearchCriteriaStore } from "@/stores";

interface ProjectCardProps {
  projectId: number;
  version: number;
  title: string;
  date?: Date;
  likeCnt: number;
  feedbackCnt: number;
  img: string;
  tags: string[];
  introduction: string;
  closed: boolean;
}

export const ProjectList = () => {
  const queryClient = useQueryClient();
  const { ref, inView } = useInView();
  const [hasNextPage, setHasNextPage] = useState(true);

  const { searchCriteria } = useSearchCriteriaStore();
  const { sort, size, keyword, tagIdList, closed } = searchCriteria;

  const usingKeyword = keyword.length === 0 ? undefined : keyword;
  const stringTagIdList =
    tagIdList.length === 0 ? undefined : tagIdList.join(",");

  const fetchProjects = async ({ pageParam = 0 }) => {
    const res = await axios.get(
      "http://k8a306.p.ssafy.io:8081/api/v1/project?_page=" + pageParam,

      {
        params: {
          sort: sort,
          size: size,
          keyword: usingKeyword,
          tagIdList: stringTagIdList,
          closed: closed,
        },
      }
    );
    if (res.data.count <= 9) {
      setHasNextPage(false);
    }
    return res.data;
  };

  const { status, data, fetchNextPage } = useInfiniteQuery(
    ["projects"],
    fetchProjects,
    {
      getNextPageParam: (lastPage) => lastPage.result.nextPage ?? undefined,
    }
  );

  useEffect(() => {
    return () => {
      queryClient.removeQueries(["projects"]);
    };
  }, []);
  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView]);

  return (
    <FlexDiv width="100%" direction="column">
      {status === "loading" && <p>Loading...</p>}
      {status === "success" && (
        <>
          {console.log(data)}
          {data.pages.map((page) => (
            <ProjectCardGrid key={page.nextId}>
              {page.result.list.map(
                (project: ProjectCardProps, index: number) => (
                  <FlexDiv
                    key={index}
                    ref={index === 0 ? ref : undefined}
                    justify="center"
                  >
                    <ProjectCard key={project.projectId} project={project} />
                  </FlexDiv>
                )
              )}
            </ProjectCardGrid>
          ))}
        </>
      )}
    </FlexDiv>
  );
};

// 1. 우리 서버와 무한스크롤을 연동함
// 2. bulletproof의 방식을 따르진 못함
// 3. 다른 페이지 갔다가 다시 돌아오면 여전히 그 데이터가 남아있고 새로 데이터를 불러옴
// 4. 효율적인 방법인지는 모르겠지만 언마운트될 때 해당 쿼리키의 캐싱값을 제거했음
