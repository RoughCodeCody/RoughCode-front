import React, { useEffect, useRef, useState } from "react";
import Editor, { OnMount } from "@monaco-editor/react";
import { Denk_One } from "next/font/google";

function isOverlap(a: number, b: number, c: number, d: number) {
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

  const [draggedLineNumber, setDraggedLineNumber] = useState<null[] | number[]>(
    [null, null]
  );
  const [decoIds, setDecoIds] = useState<string[]>([]);

  const handleEditorDidMount: OnMount = (editor, monaco) => {
    editorRef.current = editor;
    setMonaco(monaco);

    editor.onDidChangeCursorSelection((e) => handleSelectionChange(e));
  };

  const handleSelectionChange = (e: any) => {
    const selection = e.selection;
    const startLineNumber = selection.startLineNumber;
    const endLineNumber = selection.endLineNumber;
    setDraggedLineNumber([startLineNumber, endLineNumber]);
  };

  const colorizeSelectedLine = () => {
    if (draggedLineNumber[0] !== null && draggedLineNumber[1] !== null) {
      let deltaDecorations = [
        {
          range: new monaco.Range(
            draggedLineNumber[0],
            1,
            draggedLineNumber[1],
            1
          ),
          options: { isWholeLine: true, className: "selected-line" },
        },
      ];

      let newDecoIds = decoIds;
      decoIds.map((deco) => {
        const startLine = editorRef.current
          ?.getModel()
          ?.getDecorationRange(deco)?.startLineNumber;
        const endLine = editorRef.current
          ?.getModel()
          ?.getDecorationRange(deco)?.endLineNumber;

        if (
          draggedLineNumber[0] !== null &&
          draggedLineNumber[1] !== null &&
          isOverlap(
            draggedLineNumber[0],
            draggedLineNumber[1],
            startLine,
            endLine
          )
        ) {
          editorRef.current.removeDecorations(deco);
          newDecoIds = newDecoIds.filter((id) => id !== deco);
        }
      });

      const decoId = editorRef.current?.deltaDecorations([], deltaDecorations);
      setDecoIds([...newDecoIds, decoId]);
    }
  };

  const erase = () => {
    decoIds.map((deco) => {
      editorRef.current.removeDecorations(deco);
      setDecoIds([]);
    });
  };

  return (
    <>
      <button onClick={erase}>초기화</button>
      <button onClick={colorizeSelectedLine}>색칠</button>
      <Editor
        height="30rem"
        defaultLanguage="javascript"
        defaultValue={`a = [1,2,3,4,5,]

for i in range(a):
    print(i)
a = [1,2,3,4,5,]
a = [1,2,3,4,5,]

for i in range(a):
    print(i)
a = [1,2,3,4,5,]`}
        onMount={handleEditorDidMount}
      />
    </>
  );
};
