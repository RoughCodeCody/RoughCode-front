import React, { useRef, useState } from "react";
import { DiffEditor } from "@monaco-editor/react";

interface DiffCodeEditorProps {
  base64String: string;
}

type Lan = "python";

export const DiffCodeEditor: React.FC<DiffCodeEditorProps> = ({
  base64String,
}) => {
  const diffEditorRef = useRef<any>(null);
  const [lan, setLan] = useState<Lan>("python");

  function handleEditorDidMount(editor: any, monaco: any) {
    diffEditorRef.current = editor;
  }

  const decodeBase64ToUTF8 = (base64String: string): string => {
    const binaryString = atob(base64String);
    const bytes = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }
    const utf8String = new TextDecoder("utf-8").decode(bytes);
    return utf8String;
  };

  const encodeUTF8ToBase64 = (code: string): string => {
    const bytes = new TextEncoder().encode(code);
    const base64String = btoa(String.fromCharCode(...bytes));
    return base64String;
  };

  const decodedString = decodeBase64ToUTF8(base64String);

  const postFeedback = () => {
    const modifiedCode = diffEditorRef?.current?.getModifiedEditor().getValue();
    const encodedCode = encodeUTF8ToBase64(modifiedCode);
    // 이후 encodedCode를 api요청 보내는 코드
  };

  return (
    <>
      <DiffEditor
        height="30rem"
        language={lan}
        original={decodedString}
        modified={decodedString}
        onMount={handleEditorDidMount}
      />
    </>
  );
};
