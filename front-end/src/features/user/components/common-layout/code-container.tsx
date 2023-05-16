import { useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import {
  BackToTop,
  FlexDiv,
  Spinner,
  NotFound,
  Text,
} from "@/components/elements";

import { useUserCodeList } from "../../api/";
import { CodeListItem } from "@/features/code-reviews/components/code-list-item";

type CodeContainerProps = {
  endPoint: "" | "feedback" | "favorite";
};

export const CodeContainer = ({ endPoint }: CodeContainerProps) => {
  const queryClient = useQueryClient();
  const { ref, inView } = useInView();

  const { status, data, fetchNextPage } = useUserCodeList({
    endPoint,
    config: {
      getNextPageParam: (lastPage) =>
        lastPage.nextPage === -1 ? undefined : lastPage.nextPage,
    },
  });

  // 스크롤 트리거
  // 9개의 그리드 item들 중 마지막 녀석한테 inView를 달아놨음
  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView]);

  return (
    <FlexDiv
      direction="column"
      width="100%"
      paddingX="5rem"
      maxWidth="1024px"
      minWidth="720px"
      height="100%"
      justify="start"
      gap="1rem"
    >
      {status === "loading" && <Spinner size={500} />}
      {status === "success" && data.pages[0].list.length === 0 && (
        <FlexDiv direction="column">
          <NotFound size={500} />
        </FlexDiv>
      )}
      {status === "success" && (
        <>
          {data?.pages.map((page, idx) => (
            <FlexDiv direction="column" width="100%" gap="1rem" key={idx}>
              {page.list.map((codeListItem, index) => (
                <FlexDiv
                  width="100%"
                  ref={index === page.list.length - 1 ? ref : undefined}
                  key={codeListItem.codeId}
                  justify="center"
                >
                  <CodeListItem codeListItem={codeListItem} />
                </FlexDiv>
              ))}
            </FlexDiv>
          ))}
        </>
      )}
    </FlexDiv>
  );
};
