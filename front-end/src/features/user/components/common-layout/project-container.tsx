// import axios from "axios";
import { axios } from "@/lib/axios";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import { FlexDiv } from "@/components/elements";
import { ProjectCardGrid } from "@/features/projects/components/lists/style";
import { ProjectCard } from "@/features/projects/components/project-card";
import { useSearchCriteriaStore } from "@/stores";
import { useUserProjectList } from "../../api/get-user-project-list";

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

type ProjectContainerProps = {
  endPoint: "" | "feedback" | "favorite";
};

export const ProjectContainer = ({ endPoint }: ProjectContainerProps) => {
  const { ref, inView } = useInView();

  const { searchCriteria } = useSearchCriteriaStore();
  const { sort, size, keyword, tagIdList, closed } = searchCriteria;

  const usingKeyword = keyword.length === 0 ? undefined : keyword;
  const stringTagIdList =
    tagIdList.length === 0
      ? undefined
      : tagIdList.map((tag) => tag.tagId).join(",");

  const { status, data, fetchNextPage } = useUserProjectList({
    endPoint,
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
  return (
    <FlexDiv height="100%" width="100%" direction="column" justify="start">
      {status === "loading" && <p>Loading...</p>}
      {status === "success" && (
        <>
          {console.log(status)}
          {console.log(data)}
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
