import axios from "axios";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import { ProjectCard } from "../project-card";
import { ProjectCardGrid } from "./style";

import { FlexDiv } from "@/components/elements";
import { useSearchCriteriaStore } from "@/stores";
import { useInfiniteQuery } from "@tanstack/react-query";
import { useQueryClient } from "@tanstack/react-query";

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

  const { searchCriteria } = useSearchCriteriaStore();
  const { sort, size, keyword, tagIdList, closed } = searchCriteria;

  const usingKeyword = keyword.length === 0 ? undefined : keyword;
  const stringTagIdList =
    tagIdList.length === 0
      ? undefined
      : tagIdList.map((tag) => tag.tagId).join(",");

  const fetchProjects = async ({ pageParam = 0 }) => {
    const res = await axios.get(
      "http://k8a306.p.ssafy.io:8080/api/v1/project",
      {
        params: {
          page: pageParam,
          sort: sort,
          size: size,
          keyword: usingKeyword,
          tagIdList: stringTagIdList,
          closed: closed,
        },
      }
    );
    return res.data.result;
  };
  useEffect(() => {
    queryClient.removeQueries(["projects"]);
    // 컴포넌트 언마운트 될 때 캐싱한 데이터 삭제
    return () => {
      queryClient.removeQueries(["projects"]);
    };
  }, [sort, size, keyword, tagIdList, closed]);

  const { status, data, fetchNextPage } = useInfiniteQuery(
    ["projects"],
    fetchProjects,
    {
      getNextPageParam: (lastPage) => {
        return lastPage.nextPage === -1 ? undefined : lastPage.nextPage;
      },
    }
  );

  // 스크롤 트리거
  // 9개의 그리드 item들 중 마지막 녀석한테 inView를 달아놨음
  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView]);

  useEffect(() => {
    if (keyword === "") {
      fetchNextPage();
    }
  }, [keyword]);

  return (
    <FlexDiv height="100%" width="100%" direction="column" gap="3rem">
      {status === "loading" && <p>Loading...</p>}
      {status === "success" && (
        <>
          {data.pages.map((page, idx) => (
            <ProjectCardGrid key={idx}>
              {page.list.map((project: ProjectCardProps, index: number) => (
                <FlexDiv
                  key={project.projectId}
                  ref={index === page.list.length - 1 ? ref : undefined}
                  justify="center"
                >
                  <ProjectCard key={project.projectId} project={project} />
                </FlexDiv>
              ))}
            </ProjectCardGrid>
          ))}
        </>
      )}
    </FlexDiv>
  );
};
