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
}

export const DiffCodeEditor: React.FC<DiffCodeEditorProps> = ({
  headerText,
  originalCode,
  modifiedCode,
  height,
  language,
  readOnly = true,
}) => {
  const { setModifiedCode } = useCodeReviewFeedbackDataStore();

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
  };

  return (
    <EditorWrapper>
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
          <Btn
            text="확정"
            height="2rem"
            display="flex"
            align="center"
            bgColor="orange"
            onClickFunc={saveModifiedCode}
          />
        )}
      </EditorBottom>
    </EditorWrapper>
  );
};
