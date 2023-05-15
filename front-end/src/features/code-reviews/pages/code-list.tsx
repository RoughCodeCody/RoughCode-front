import { useEffect } from "react";
import { useInView } from "react-intersection-observer";

import {
  BackToTop,
  BottomHeader,
  FlexDiv,
  Title,
  WriteFloatBtn,
} from "@/components/elements";
import { DropLabel, Search } from "@/features/search";
import { useSearchCriteriaStore } from "@/stores";

import { useCodeList } from "../api";
import { CodeListItem } from "../components/code-list-item";

export const CodeList = () => {
  const { ref, inView } = useInView();

  const { searchCriteria } = useSearchCriteriaStore();
  const { sort, size, keyword, tagIdList } = searchCriteria;

  const usingKeyword = keyword.length === 0 ? "" : keyword;
  const stringTagIdList =
    tagIdList.length === 0 ? "" : tagIdList.map((tag) => tag.tagId).join(",");

  const { status, data, fetchNextPage } = useCodeList({
    params: {
      sort: sort,
      size: size,
      keyword: usingKeyword,
      tagIdList: stringTagIdList,
    },
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

  // useEffect(() => {
  //   if (keyword === "") {
  //     fetchNextPage();
  //   }
  // }, [keyword]);

  return (
    <FlexDiv direction="column" width="100%" gap="4rem">
      <BottomHeader locations={["코드 리뷰"]} />

      <FlexDiv direction="column" width="100%" maxWidth="70%" gap="3rem">
        <Title title="코드 리뷰" description="코드를 보고 리뷰해보세요" />
      </FlexDiv>
      <Search whichTag="code" />
      <FlexDiv direction="column" width="100%" maxWidth="1024px" gap="1rem">
        <FlexDiv width="100%" justify="end" gap="2rem" paddingX="5rem">
          <DropLabel
            sortOptions={["최신순", "좋아요순", "리뷰순"]}
            type="codeReview"
          />
        </FlexDiv>
        <FlexDiv direction="column" width="100%" height="100%" gap="1rem">
          {status === "loading" && <p>Loading...</p>}
          {status === "success" && data.pages[0].list.length === 0 && (
            <FlexDiv width="100%" height="50vh">
              데이터가 없어요
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
      </FlexDiv>
      <WriteFloatBtn navTo="/code-reviews/create" />
      <BackToTop />
    </FlexDiv>
  );
};
