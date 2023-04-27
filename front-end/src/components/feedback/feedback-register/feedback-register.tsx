import { FlexDiv, Text, WhiteBoxShad, Btn } from "@/components/elements";
import { FeedbackRegisterTextarea } from "./style";

type FeedbackRegisterProps = {
  type: "feedback" | "review";
};

export const FeedbackRegister = ({ type }: FeedbackRegisterProps) => {
  return (
    <FlexDiv direction="column" width="80%">
      <Text color="main" bold={true} padding="0.5rem 0">
        {type === "feedback"
          ? "이 프로젝트에 피드백을 남겨주세요!"
          : "이 코드 리뷰에 의견을 남겨주세요!"}
      </Text>
      <WhiteBoxShad height="10rem" shadColor="main">
        <FlexDiv
          width="100%"
          height="100%"
          justify="space-between"
          padding="1.5rem"
        >
          <FeedbackRegisterTextarea
            placeholder={
              type === "feedback"
                ? "프로젝트에 제안할 점, 개선 의견, 발견한 버그 등을 피드백으로 남겨주세요"
                : "이 코드 리뷰에 대한 건설적인 의견을 자유롭게 남겨주세요"
            }
          />
          <Btn text="등록하기" padding="2rem 1rem" />
        </FlexDiv>
      </WhiteBoxShad>
    </FlexDiv>
  );
};
