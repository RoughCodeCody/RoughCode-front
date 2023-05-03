import { useRouter } from "next/router";

import { FlexDiv, TagChipSub, Text } from "@/components/elements";

import { Code } from "../../types";
import { MiniFeedbackItemWrapper } from "./style";

type MiniFeedbackItemProps = {
  code: Code;
};

export const MiniFeedbackItem = ({
  code: { codeId, title, tags },
}: MiniFeedbackItemProps) => {
  const router = useRouter();

  return (
    <MiniFeedbackItemWrapper
      onClick={() => router.push(`/code-review/${codeId}`)}
    >
      <FlexDiv direction="column" gap="0.5rem" align="start">
        <Text>{title}</Text>
        <FlexDiv>
          {tags?.map((tag, idx) => (
            <TagChipSub tag={tag} key={idx} />
          ))}
        </FlexDiv>
      </FlexDiv>
    </MiniFeedbackItemWrapper>
  );
};
