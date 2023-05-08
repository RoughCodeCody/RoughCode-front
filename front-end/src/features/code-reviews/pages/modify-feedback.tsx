import React, { useRef } from "react";

import { FlexDiv } from "@/components/elements";
import { Title } from "@/components/elements";
import { CodeEditor } from "@/features/code-editor";
import { DiffCodeEditor } from "@/features/code-editor";

export const ModifyFeedback = () => {
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
        <FlexDiv width="100%" height="100%">
          <CodeEditor
            // 코드 라인 선택 기능 버튼 여부
            headerText="피드백할 라인을 선택해 주세요"
            lineSelection={true}
            height="20rem"
            language="javascript"
            originalCode={
              "IyDtm4Trs7TtgqQgCiMg7Jyg7J287ISxICYg7LWc7IaM7ISxIOunjOyhsQpmcm9tIGl0ZXJ0b29scyBpbXBvcnQgY29tYmluYXRpb25zCgpkZWYgc29sdXRpb24ocmVsYXRpb24pOgogICAgCiAgICByb3cgPSBsZW4ocmVsYXRpb24pCiAgICBjb2wgPSBsZW4ocmVsYXRpb25bMF0pCiAgICAKICAgICMg6rCA64ql7ZWcIOyGjeyEseydmCDrqqjrk6Ag7J24642x7IqkIOyhsO2VqQogICAgY2FzZSA9IFtdCiAgICBmb3IgaSBpbiByYW5nZSgxLCBjb2wrMSk6CiAgICAgICAgY2FzZS5leHRlbmQoY29tYmluYXRpb25zKHJhbmdlKGNvbCksIGkpKQo="
            }
          />
        </FlexDiv>
        <FlexDiv width="100%" height="100%">
          <DiffCodeEditor
            headerText="코드를 수정해 주세요"
            height="20rem"
            language="javascript"
            readOnly={false}
            originalCode={
              "IyDtm4Trs7TtgqQgCiMg7Jyg7J287ISxICYg7LWc7IaM7ISxIOunjOyhsQpmcm9tIGl0ZXJ0b29scyBpbXBvcnQgY29tYmluYXRpb25zCgpkZWYgc29sdXRpb24ocmVsYXRpb24pOgogICAgCiAgICByb3cgPSBsZW4ocmVsYXRpb24pCiAgICBjb2wgPSBsZW4ocmVsYXRpb25bMF0pCiAgICAKICAgICMg6rCA64ql7ZWcIOyGjeyEseydmCDrqqjrk6Ag7J24642x7IqkIOyhsO2VqQogICAgY2FzZSA9IFtdCiAgICBmb3IgaSBpbiByYW5nZSgxLCBjb2wrMSk6CiAgICAgICAgY2FzZS5leHRlbmQoY29tYmluYXRpb25zKHJhbmdlKGNvbCksIGkpKQo="
            }
          />
        </FlexDiv>
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
