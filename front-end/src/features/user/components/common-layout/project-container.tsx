// import axios from "axios";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import { useUserProjectList } from "../../api";
import { FlexDiv, Spinner, NotFound } from "@/components/elements";
import { ProjectCardGrid } from "@/features/projects/components/lists/style";
import { ProjectCard } from "@/features/projects/components/project-card";

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
    <FlexDiv
      height="100%"
      width="100%"
      direction="column"
      justify="start"
      gap="3rem"
    >
      {status === "loading" && <Spinner size={500} />}
      {status === "success" && data.pages[0].list.length === 0 && (
        <NotFound size={500} />
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
