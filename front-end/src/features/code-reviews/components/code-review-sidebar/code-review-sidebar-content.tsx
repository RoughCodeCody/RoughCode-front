import { BsPerson } from "react-icons/bs";
import { useEffect } from "react";
import { useRouter } from "next/router";

import { useCodeReviewsForCodeUpdate } from "../../api/get-code-reviews-for-code-update";
import { useCodeReviewsForCodeUpdateSelection } from "../../stores/code-review-for-code-update-selection";
import {
  ItemContainer,
  Item,
  ItemUserName,
  ItemContent,
} from "./code-review-sidebar-content-style";
import {
  SidebarFallback,
  Heading,
  SubHeading,
} from "./code-review-sidebar-style";

type CodeReviewSidebarContentProps = {
  codeId: string;
  versionUp: string;
};

export const CodeReviewSidebarContent = ({
  codeId,
  versionUp,
}: CodeReviewSidebarContentProps) => {
  // server states
  const codeReviewsForCodeUpdateQuery = useCodeReviewsForCodeUpdate({
    codeId,
    versionUp: versionUp,
    config: { cacheTime: 0 },
  });

  // client states
  const {
    selectedCodeReviewId,
    toggleCodeReviewForCodeUpdateSelection,
    resetCodeReviewForCodeUpdateSelection,
  } = useCodeReviewsForCodeUpdateSelection();

  // reset project feedback selection state on route change
  const dynamicRoute = useRouter().asPath;
  useEffect(
    () => resetCodeReviewForCodeUpdateSelection(),
    [dynamicRoute, resetCodeReviewForCodeUpdateSelection]
  );

  // fill initial selection status on each project feedback
  useEffect(() => {
    codeReviewsForCodeUpdateQuery.data?.forEach((codeReview) => {
      toggleCodeReviewForCodeUpdateSelection(codeReview.reviewId);
    });
  }, [
    codeReviewsForCodeUpdateQuery.data,
    toggleCodeReviewForCodeUpdateSelection,
  ]);

  // isLoading
  if (codeReviewsForCodeUpdateQuery.isLoading) {
    return <>Loading...</>;
  }

  // isError
  if (codeReviewsForCodeUpdateQuery.isError) {
    return <>Error!</>;
  }

  // server data & click function definition
  const codeReviewsForCodeUpdate = codeReviewsForCodeUpdateQuery.data;
  const handleClick = (codeReviewId: number) => {
    toggleCodeReviewForCodeUpdateSelection(codeReviewId);
  };

  if (codeReviewsForCodeUpdateQuery.data.length === 0) {
    return (
      <SidebarFallback>코드 리뷰가 있으면 이곳에 나타날 거에요</SidebarFallback>
    );
  }

  return (
    <ItemContainer>
      <Heading>코드 리뷰 목록</Heading>
      <SubHeading>
        아래 항목을 클릭해서 코드 리뷰를 반영했다는 표시를 남길 수 있어요.
      </SubHeading>
      {codeReviewsForCodeUpdate.map((codeReview, index) => (
        <Item
          key={`code-review-for-code-update-${codeId}-${index}`}
          onClick={() => handleClick(codeReview.reviewId)}
          isSelected={selectedCodeReviewId.includes(codeReview.reviewId)}
        >
          <ItemUserName>
            <BsPerson color="var(--font-color)" />
            {codeReview.userName}
          </ItemUserName>
          <ItemContent>
            <div
              dangerouslySetInnerHTML={{ __html: codeReview.content }}
              style={{ width: "100%" }}
            ></div>
          </ItemContent>
        </Item>
      ))}
    </ItemContainer>
  );
};
