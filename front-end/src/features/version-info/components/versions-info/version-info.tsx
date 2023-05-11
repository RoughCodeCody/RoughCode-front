import { FlexDiv, Nickname, Text, WhiteBoxShad } from "@/components/elements";

import { VersionInfoWrapper } from "./style";
import { useRouter } from "next/router";

type VersionInfoProps = {
  version: number;
  title: string;
  current: boolean;
  id: number;
  type: "project" | "code-review";
  feedbacks:
    | { feedbackId: number; content: string }[]
    | { reviewId: number; userName: string; content: string }[];
};

// 프로젝트 버전인지 여부를 판별
function isProject(arg: any): arg is { feedbackId: number; content: string }[] {
  return arg?.[0].feedbackId !== undefined;
}

// 코드 버전인지 여부를 판별
function isCode(
  arg: any
): arg is { reviewId: number; userName: string; content: string }[] {
  return arg?.[0].reviewId !== undefined;
}

export const VersionInfo = ({
  version,
  title,
  current,
  id,
  type,
  feedbacks,
}: VersionInfoProps) => {
  const router = useRouter();

  return (
    <VersionInfoWrapper
      bgColor={current ? "sub-one" : "white"}
      pointer={current ? false : true}
      onClick={() => {
        if (!current) router.push(`/${type}/${id}`);
      }}
    >
      <FlexDiv width="5%" pointer={current ? false : true}>
        <Text
          as="span"
          color={current ? "main" : "font"}
          bold={current}
          pointer={current ? false : true}
        >
          {`V${version}`}
        </Text>
      </FlexDiv>
      <FlexDiv
        width="90%"
        direction="column"
        gap="0.7rem"
        pointer={current ? false : true}
      >
        <Text
          color={current ? "main" : "font"}
          bold={current}
          pointer={current ? false : true}
          style={{ alignSelf: "start", marginBottom: "0.5rem" }}
        >
          {title}
        </Text>
        {feedbacks.length !== 0 && isProject(feedbacks) ? (
          <>
            <Text size="0.8rem" bold={true}>
              반영한 피드백
            </Text>
            {feedbacks.map(({ feedbackId, content }) => (
              <WhiteBoxShad radius="0" padding="0.8rem" key={feedbackId}>
                <Text size="0.9rem">{content}</Text>
              </WhiteBoxShad>
            ))}
          </>
        ) : feedbacks.length !== 0 && isCode(feedbacks) ? (
          <>
            <Text>반영한 코드 리뷰</Text>
            {feedbacks.map(({ reviewId, userName, content }) => (
              <WhiteBoxShad key={reviewId}>
                <Nickname nickname={userName} />
                <Text size="0.9rem">{content}</Text>
              </WhiteBoxShad>
            ))}
          </>
        ) : (
          <></>
        )}
      </FlexDiv>
    </VersionInfoWrapper>
  );
};
