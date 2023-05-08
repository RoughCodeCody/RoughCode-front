import React, { useEffect, useRef, useState } from "react";
import { DiffEditor } from "@monaco-editor/react";

import { FlexDiv, Text } from "@/components/elements";

import { FaRegLightbulb } from "react-icons/fa";

import { EditorWrapper, EditorHeader, EditorBottom } from "./style";

interface DiffCodeEditorProps {
  headerText: string;
  originalCode: string;
  height: string;
  language: string;
  readOnly?: boolean;
}

export const DiffCodeEditor: React.FC<DiffCodeEditorProps> = ({
  headerText,
  originalCode,
  height,
  language,
  readOnly = true,
}) => {
  const diffEditorRef = useRef<any>(null);

  function handleEditorDidMount(editor: any, monaco: any) {
    diffEditorRef.current = editor;
    if (readOnly) {
      editor.updateOptions({ readOnly: true });
    }
  }

  // const decodeBase64ToUTF8 = (originalCode: string): string => {
  //   const binaryString = atob(originalCode);
  //   const bytes = new Uint8Array(binaryString.length);
  //   for (let i = 0; i < binaryString.length; i++) {
  //     bytes[i] = binaryString.charCodeAt(i);
  //   }
  //   const utf8String = new TextDecoder("utf-8").decode(bytes);
  //   return utf8String;
  // };

  // const encodeUTF8ToBase64 = (code: string): string => {
  //   const bytes = new TextEncoder().encode(code);
  //   const originalCode = btoa(String.fromCharCode(...bytes));
  //   return originalCode;
  // };

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
  const decodedString = decodeBase64ToUTF8(originalCode);

  const postFeedback = () => {
    const modifiedCode = diffEditorRef?.current?.getModifiedEditor().getValue();
    const encodedCode = encodeUTF8ToBase64(modifiedCode);
    // 이후 encodedCode를 api요청 보내는 코드
  };

  useEffect(() => {}, [originalCode]);

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
        original={decodedString}
        modified={decodedString}
        onMount={handleEditorDidMount}
      />
      <EditorBottom />
    </EditorWrapper>
  );
};
