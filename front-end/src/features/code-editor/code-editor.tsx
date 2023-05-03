import React, { useRef, useState } from "react";
import Editor, { OnMount } from "@monaco-editor/react";

export const CodeEditor: React.FC = () => {
  const editorRef = useRef<any>(null);
  const [monaco, setMonaco] = useState<any>(null);
  const [selectionStartLine, setSelectionStartLine] = useState<number | null>(
    null
  );
  const [selectionEndLine, setSelectionEndLine] = useState<number | null>(null);

  const handleEditorDidMount: OnMount = (editor, monaco) => {
    editorRef.current = editor;
    setMonaco(monaco);
    editor.onDidChangeCursorSelection((e) => handleSelectionChange(e));
  };

  const handleSelectionChange = (e: any) => {
    const selection = e.selection;
    const startLineNumber = selection.startLineNumber;
    const endLineNumber = selection.endLineNumber;
    setSelectionStartLine(startLineNumber);
    setSelectionEndLine(endLineNumber);
  };

  const selectedLine = () => {
    if (monaco && selectionStartLine !== null && selectionEndLine !== null) {
      const deltaDecorations = [
        {
          range: new monaco.Range(selectionStartLine, 1, selectionEndLine, 1),
          options: { isWholeLine: true, className: "selected-line" },
        },
      ];
      editorRef.current?.deltaDecorations([], deltaDecorations);
    }
  };

  return (
    <>
      <button onClick={selectedLine}>색칠</button>
      <Editor
        height="90vh"
        defaultLanguage="javascript"
        defaultValue={`a = [1,2,3,4,5,]

for i in range(a):
    print(i)
a = [1,2,3,4,5,]`}
        onMount={handleEditorDidMount}
      />
    </>
  );
};
