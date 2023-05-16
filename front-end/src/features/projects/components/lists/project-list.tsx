import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import { useProjectList } from "../../api";
import { ProjectCard } from "../project-card";
import { ProjectCardGrid } from "./style";

import { FlexDiv, NotFound, Spinner } from "@/components/elements";
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
  const { ref, inView } = useInView();

  const { searchCriteria } = useSearchCriteriaStore();
  const { sort, size, keyword, tagIdList, closed } = searchCriteria;

  const usingKeyword = keyword.length === 0 ? undefined : keyword;
  const stringTagIdList =
    tagIdList.length === 0
      ? undefined
      : tagIdList.map((tag) => tag.tagId).join(",");

  const { status, data, fetchNextPage } = useProjectList({
    params: {
      sort: sort,
      size: size,
      keyword: usingKeyword,
      tagIdList: stringTagIdList,
      closed: closed,
    },
    config: {
      getNextPageParam: (lastPage) =>
        lastPage.nextPage === -1 ? undefined : lastPage.nextPage,
    },
  });

  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView]);

  // useEffect(() => {
  //   if (keyword === "") {
  //     fetchNextPage();
  //   }
  // }, [keyword]);

  return (
    <FlexDiv height="100%" width="100%" direction="column" gap="3rem">
      {status === "loading" && <Spinner size={700} />}
      {status === "success" && data.pages[0].list.length === 0 && (
        <NotFound size={700} />
      )}
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
