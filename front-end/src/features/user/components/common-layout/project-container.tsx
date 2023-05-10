// import axios from "axios";
import { axios } from "@/lib/axios";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import { FlexDiv } from "@/components/elements";
import { ProjectCardGrid } from "@/features/projects/components/lists/style";
import { ProjectCard } from "@/features/projects/components/project-card";
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

type ProjectContainer = {
  endPoint: "" | "feedback" | "favorite";
};

export const ProjectContainer = ({ endPoint }: ProjectContainer) => {
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
      `http://k8a306.p.ssafy.io:8080/api/v1/mypage/project/${endPoint}`,
      //   `http://localhost:8080/api/v1/mypage/project/${endPoint}`,
      {
        // params: {
        //   page: pageParam,
        //   size: 9,
        // },

        // css 테스트용
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
    console.log("res : ", res);
    return res.data.result;
  };

  const { status, data, fetchNextPage } = useInfiniteQuery(
    ["projects"],
    fetchProjects,
    {
      getNextPageParam: (lastPage) => {
        return lastPage.nextPage === -1 ? undefined : lastPage.nextPage;
        // return lastPage.data.nextPage === -1
        //   ? undefined
        //   : lastPage.data.nextPage;
      },
    }
  );

  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView]);
  return (
    <FlexDiv height="100%" width="100%" direction="column" justify="start">
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
