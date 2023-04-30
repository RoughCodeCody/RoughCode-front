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
    tagIdList.length === 0
      ? undefined
      : tagIdList.map((tag) => tag.tagId).join(",");

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

  // 조건이 바뀌면 query데이터 모두 삭제하고 새로운 데이터로 갈아치움
  useEffect(() => {
    queryClient.removeQueries(["projects"]);
    // 컴포넌트 언마운트 될 때 캐싱한 데이터 삭제
    return () => {
      queryClient.removeQueries(["projects"]);
    };
  }, [sort, size, usingKeyword, stringTagIdList, closed]);

  // 스크롤 트리거
  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView]);

  return (
    <FlexDiv height="100%" width="100%" direction="column">
      {status === "loading" && <p>Loading...</p>}
      {status === "success" && (
        <>
          {console.log(data)}
          {data.pages.map((page) => (
            <ProjectCardGrid key={page.result.nextPage}>
              {page.result.list.map(
                (project: ProjectCardProps, index: number) => (
                  <FlexDiv
                    key={project.projectId}
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
