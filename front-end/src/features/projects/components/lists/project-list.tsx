import { useState, useEffect } from "react";
import { useInView } from "react-intersection-observer";
import { useInfiniteQuery } from "@tanstack/react-query";
import axios from "axios";

import { ProjectCard } from "../project-card";
import { ProjectCardGrid } from "./style";
import { FlexDiv } from "@/components/elements";

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
  const [pageNum, setPageNum] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);

  const fetchProjects = async ({ pageParam = pageNum }) => {
    const res = await axios.get(
      "http://localhost:4000/dummy?_page=" + pageParam + "&_limit=9"
    );
    if (res.data.length === 0) {
      setHasNextPage(false);
    }
    return res.data;
  };

  const {
    status,
    data,
    error,
    isFetching,
    isFetchingNextPage,
    isFetchingPreviousPage,
    fetchNextPage,
    fetchPreviousPage,
    hasPreviousPage,
  } = useInfiniteQuery(["projects"], fetchProjects, {
    onSuccess: () => {
      setPageNum(pageNum + 1);
    },
    getNextPageParam: () => pageNum,
    // getPreviousPageParam: (firstPage) => firstPage.previousId ?? undefined,
    // getNextPageParam: (lastPage) => lastPage.nextId ? undefined,
  });

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
          {data.pages.map((page) => (
            <ProjectCardGrid key={page.nextId}>
              {page.map((project: ProjectCardProps, index: number) => (
                <FlexDiv
                  key={index}
                  ref={index === 0 ? ref : undefined}
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
