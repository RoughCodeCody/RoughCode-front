import { FaRegLightbulb } from "react-icons/fa";

import { FlexDiv, Text } from "@/components/elements";
import {
  EditorBottom,
  EditorHeader,
  EditorWrapper,
} from "@/features/code-editor/components/style";

interface ClickedReviewContentProps {
  content: string;
}

export const ClickedReviewContent = ({
  content,
}: ClickedReviewContentProps) => {
  return (
    <EditorWrapper noShad={true}>
      <EditorHeader>
        <FlexDiv>
          <FaRegLightbulb />
          <Text padding="0 0 0 0.5rem">코드 리뷰어가 작성한 설명입니다</Text>
        </FlexDiv>
      </EditorHeader>

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

      <EditorBottom />
    </EditorWrapper>
  );
};
