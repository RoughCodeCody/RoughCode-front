import { FlexDiv, Text, WhiteBoxShad } from "@/components/elements";

interface CodeDescriptionProps {
  content: string;
}

export const CodeDescription = ({ content }: CodeDescriptionProps) => {
  return (
    <WhiteBoxShad margin="2.5rem 0 0 0">
      <FlexDiv
        width="100%"
        justify="space-between"
        align="end"
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
