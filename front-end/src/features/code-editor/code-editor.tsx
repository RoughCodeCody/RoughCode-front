import React, { useEffect, useRef, useState } from "react";
import Editor, { OnMount } from "@monaco-editor/react";

function isOverlap(a, b, c, d) {
  // a와 b의 위치를 정렬합니다.
  if (a > b) {
    [a, b] = [b, a];
  }

  // c와 d의 위치를 정렬합니다.
  if (c > d) {
    [c, d] = [d, c];
  }

  // a~b 구간과 c~d 구간이 겹치는지 확인합니다.
  if (b >= c && a <= d) {
    return true; // 겹침
  } else {
    return false; // 겹치지 않음
  }
}

export const CodeEditor: React.FC = () => {
  const editorRef = useRef<any>(null);
  const [monaco, setMonaco] = useState<any>(null);
  const [selectionStartLine, setSelectionStartLine] = useState<number | null>(
    null
  );
  const [selectionEndLine, setSelectionEndLine] = useState<number | null>(null);
  const [selectedLines, setSelectedLines] = useState([]);
  const [decorationsCollection, setDecorationsCollection] = useState([]);

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

  const colorizeSelectedLine = () => {
    if (monaco && selectionStartLine !== null && selectionEndLine !== null) {
      let deltaDecorations = [
        {
          range: new monaco.Range(selectionStartLine, 1, selectionEndLine, 1),
          options: { isWholeLine: true, className: "selected-line" },
        },
      ];
      let newSelectedLines = [];
      selectedLines.map((line) => {
        if (
          !isOverlap(line[0], line[1], selectionStartLine, selectionEndLine)
        ) {
          const deco = {
            range: new monaco.Range(line[0], 1, line[1], 1),
            options: { isWholeLine: true, className: "selected-line" },
          };
          deltaDecorations.push(deco);
          newSelectedLines.push([line[0], line[1]]);
        }
      });
      newSelectedLines.push([selectionStartLine, selectionEndLine]);
      editorRef.current?.deltaDecorations([], deltaDecorations);
      setDecorationsCollection(deltaDecorations);
      setSelectedLines(newSelectedLines);
    }
  };

  const erase = () => {
    console.log(editorRef.current.getLineDecorations());
  };

  useEffect(() => {
    console.log(selectedLines);
  }, [selectedLines]);

  return (
    <>
      <button onClick={erase}>지우기</button>
      <button onClick={colorizeSelectedLine}>색칠</button>
      <Editor
        height="60vh"
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
