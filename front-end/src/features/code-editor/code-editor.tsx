import React, { useRef, useState, useEffect } from "react";

import { DiffEditor } from "@monaco-editor/react";
import Editor from "@monaco-editor/react";
// 어떤 것을 prop 받을 지 정해야 합니다.
export const CodeEditor = () => {
  // 한글 포함된 Base64 문자열
  // 이 부분에 받아온 content 넣으면 됨
  const editorRef = useRef(null);
  const [selectionStartLine, setSelectionStartLine] = useState(null);
  const [selectionEndLine, setSelectionEndLine] = useState(null);

  function handleEditorDidMount(editor, monaco) {
    editorRef.current = editor;
    editor.onDidChangeCursorSelection(handleSelectionChange);
  }

  function handleSelectionChange(event) {
    const selection = event.selection;
    const startLineNumber = selection.startLineNumber;
    const endLineNumber = selection.endLineNumber;
    console.log(startLineNumber);
    console.log(endLineNumber);
  }

  return (
    <>
      <Editor
        height="90vh"
        defaultLanguage="javascript"
        defaultValue={`
         a = [1,2,3,4,5,]

         for i in range(a):
             print(i)
         a = [1,2,3,4,5,]
         `}
        onMount={handleEditorDidMount}
      />
    </>
  );
};
