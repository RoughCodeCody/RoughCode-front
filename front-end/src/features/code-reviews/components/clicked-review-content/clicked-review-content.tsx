import { FlexDiv, Text, WhiteBoxShad } from "@/components/elements";

interface ClickedReviewContentProps {
  content: string;
}

export const ClickedReviewContent = ({
  content,
}: ClickedReviewContentProps) => {
  return (
    <WhiteBoxShad shadColor="main">
      <FlexDiv
        width="100%"
        justify="space-between"
        align="end"
        pointer={true}
        padding="1rem 2rem"
      >
        {!content.length ? (
          <Text color="red">신고되어 가려진 게시물입니다.</Text>
        ) : (
          <div
            dangerouslySetInnerHTML={{ __html: content }}
            style={{ width: "100%" }}
          ></div>
        )}
      </FlexDiv>
    </WhiteBoxShad>
  );
};
