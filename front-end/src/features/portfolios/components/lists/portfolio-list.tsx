import { useState, useEffect } from "react";
import { useInView } from "react-intersection-observer";
import {
  useInfiniteQuery,
  QueryClient,
  QueryClientProvider,
} from "@tanstack/react-query";
import axios from "axios";

import { PortfolioCard } from "../portfolio-card";
import { PortfolioCardGrid } from "./style";
import { FlexDiv } from "@/components/elements";

interface PortfolioCardProps {
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

export const PortfolioList = () => {
  const { ref, inView } = useInView();
  const [pageNum, setPageNum] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);

  const fetchProjects = async ({ pageParam = pageNum }) => {
    const res = await axios.get(
      "http://localhost:4000/dummy?_page=" + pageParam + "&_limit=9"
    );
    console.log(res.data);
    if (res.data.length === 0) {
      setHasNextPage(false);
      console.log(hasNextPage);
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
      console.log(pageNum);
    },
    getNextPageParam: () => pageNum,
    // getPreviousPageParam: (firstPage) => firstPage.previousId ?? undefined,
    // getNextPageParam: (lastPage) => lastPage.nextId ? undefined,
  });

  console.log(data);
  console.log(status);

  useEffect(() => {
    console.log(inView);
    if (inView && hasNextPage) {
      console.log(hasNextPage);
      fetchNextPage();
    }
  }, [inView]);

  return (
    <FlexDiv direction="column" gap="1rem">
      {status === "loading" && <p>Loading...</p>}
      {status === "success" && (
        <>
          {data.pages.map((page) => (
            <PortfolioCardGrid key={page.nextId}>
              {page.map((project: PortfolioCardProps, index: number) =>
                index === 0 ? (
                  <div ref={ref}>
                    <PortfolioCard
                      key={project.projectId}
                      projectId={project.projectId}
                      version={project.version}
                      title={project.title}
                      likeCnt={project.likeCnt}
                      feedbackCnt={project.feedbackCnt}
                      img={project.img}
                      tags={project.tags}
                      introduction={project.introduction}
                      closed={project.closed}
                    />
                  </div>
                ) : (
                  <PortfolioCard
                    ref={undefined}
                    key={project.projectId}
                    projectId={project.projectId}
                    version={project.version}
                    title={project.title}
                    likeCnt={project.likeCnt}
                    feedbackCnt={project.feedbackCnt}
                    img={project.img}
                    tags={project.tags}
                    introduction={project.introduction}
                    closed={project.closed}
                  />
                )
              )}
            </PortfolioCardGrid>
          ))}
        </>
      )}
    </FlexDiv>
  );
};
