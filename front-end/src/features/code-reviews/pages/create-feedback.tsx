import React, { useRef } from "react";

import { FlexDiv, Title } from "@/components/elements";
import { CodeEditor } from "@/features/code-editor";
import { DiffCodeEditor } from "@/features/code-editor";
import { useCodeInfo } from "../api/get-code-info";
import { useCode } from "../api/get-code";
import { Text } from "@/components/elements";

type CreateFeedbackProps = {
  codeId: number;
};

export const CreateFeedback = ({ codeId }: CreateFeedbackProps) => {
  // 여기서 정보 조회하고 하위 컴포넌트에 정보를 prop줌
  const codeInfoQuery = useCodeInfo({ codeId });
  // 제대로된 api response 받으면 아래 주석 풀고
  // 임의의 githubUrl 할당 제거

  const githubUrl = codeInfoQuery.data?.githubUrl
    ? codeInfoQuery.data?.githubUrl
    : "";
  // githubUrl =
  //   "https://api.github.com/repos/LuttSpace/AutoCalen/contents/lib/pages/TagSettingPage.dart?ref=594e05aee256df9e4e826ff56ea2a8c38e9e7972";
  const codeQuery = useCode({ githubUrl });
  const originalCode = codeQuery.data?.content;
  // console.log(originalCode);

  if (codeInfoQuery.isLoading) {
    return <div></div>;
  }
  if (codeInfoQuery.isError)
    return (
      <FlexDiv>
        <Text>Not Found</Text>
      </FlexDiv>
    );
  return (
    <FlexDiv width="100vw" direction="column" gap="5rem">
      <FlexDiv
        width="100%"
        maxWidth="1080px"
        direction="column"
        gap="2rem"
        align="center"
      >
        <Title
          title="코드 수정"
          description="요청한 코드를 기반으로 수정 및 리뷰를 해주세요"
        />
        {originalCode ? (
          <>
            <FlexDiv width="100%" height="100%">
              <CodeEditor
                // 코드 라인 선택 기능 버튼 여부
                headerText="피드백할 라인들을 드래그 한 후 선택 버튼을 눌러주세요"
                lineSelection={true}
                height="30rem"
                language="javascript"
                originalCode={originalCode}
              />
            </FlexDiv>
            <FlexDiv width="100%" height="100%">
              <DiffCodeEditor
                headerText="코드를 수정해 주세요"
                height="30rem"
                readOnly={false}
                language="javascript"
                originalCode={originalCode}
              />
            </FlexDiv>
          </>
        ) : (
          // 스피너
          <></>
        )}
      </FlexDiv>
      <FlexDiv
        width="100%"
        maxWidth="1080px"
        direction="column"
        gap="2rem"
        align="center"
      >
        <Title
          title="리뷰 작성"
          description="코드에 대한 리뷰를 작성해 주세요"
        />
        {/* 여기가 md에디터 자리 */}
      </FlexDiv>
    </FlexDiv>
  );
};
