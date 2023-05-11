import React, { useEffect, useRef } from "react";
import { FaRegLightbulb } from "react-icons/fa";

import { EditorWrapper, EditorHeader, EditorBottom } from "./style";
import { FlexDiv, Text, Btn } from "@/components/elements";
import { DiffEditor } from "@monaco-editor/react";
import { useCodeReviewFeedbackDataStore } from "@/stores/code-review-feedback";

interface DiffCodeEditorProps {
  headerText: string;
  originalCode: string;
  modifiedCode: string;
  height: string;
  language: string;
  readOnly?: boolean;
  noShad?: boolean;
}

export const DiffCodeEditor: React.FC<DiffCodeEditorProps> = ({
  headerText,
  originalCode,
  modifiedCode,
  height,
  language,
  readOnly = true,
  noShad = false,
}) => {
  const { CodeReviewFeedbackData, setModifiedCode, setIsCompleted } =
    useCodeReviewFeedbackDataStore();

  const diffEditorRef = useRef<any>(null);

  function handleEditorDidMount(editor: any, monaco: any) {
    diffEditorRef.current = editor;
    if (readOnly) {
      editor.updateOptions({ readOnly: true });
    }
  }

  const { Buffer } = require("buffer");

  const decodeBase64ToUTF8 = (originalCode: string) => {
    const padding = "=".repeat((4 - (originalCode.length % 4)) % 4);
    const base64 = (originalCode + padding)
      .replace(/-/g, "+")
      .replace(/_/g, "/");
    const rawData = Buffer.from(base64, "base64");
    const utf8Decoder = new TextDecoder("utf-8");
    return utf8Decoder.decode(rawData);
  };

  const encodeUTF8ToBase64 = (code: string) => {
    const utf8Encoder = new TextEncoder();
    const data = utf8Encoder.encode(code);
    const base64 = Buffer.from(data).toString("base64");
    return base64.replace(/=/g, "").replace(/\+/g, "-").replace(/\//g, "_");
  };
  const decodedOriginalString = decodeBase64ToUTF8(originalCode);
  const decodedmodifiedString = decodeBase64ToUTF8(modifiedCode);

  const saveModifiedCode = () => {
    const modifiedCode = diffEditorRef?.current?.getModifiedEditor().getValue();
    const encodedCode = encodeUTF8ToBase64(modifiedCode);
    setModifiedCode(encodedCode);
    setIsCompleted("diffEditor");
  };

  useEffect(() => {
    if (CodeReviewFeedbackData.isCompleted.diffEditor) {
      diffEditorRef.current?.updateOptions({ readOnly: true });
    } else {
      diffEditorRef.current?.updateOptions({ readOnly: false });
    }
  }, [CodeReviewFeedbackData.isCompleted.diffEditor]);

  return (
    <EditorWrapper noShad={noShad}>
      <EditorHeader>
        <FlexDiv>
          <FaRegLightbulb />
          <Text padding="0 0 0 0.5rem">{headerText}</Text>
        </FlexDiv>
      </EditorHeader>
      <DiffEditor
        height={height}
        language={language}
        original={decodedOriginalString}
        modified={decodedmodifiedString}
        onMount={handleEditorDidMount}
      />
      <EditorBottom>
        {readOnly ? (
          <></>
        ) : (
          <FlexDiv gap="1rem">
            {CodeReviewFeedbackData.isCompleted.diffEditor ? (
              <Text>코드 수정이 완료되었습니다</Text>
            ) : (
              <></>
            )}
            <Btn
              text={
                CodeReviewFeedbackData.isCompleted.diffEditor ? "변경" : "완료"
              }
              height="2rem"
              display="flex"
              align="center"
              bgColor={
                CodeReviewFeedbackData.isCompleted.diffEditor
                  ? "main"
                  : "orange"
              }
              onClickFunc={saveModifiedCode}
            />
          </FlexDiv>
        )}
      </EditorBottom>
    </EditorWrapper>
  );
};
