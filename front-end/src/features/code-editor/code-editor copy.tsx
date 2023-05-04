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

  // 드래그 시작 라인 넘버
  const [selectionStartLine, setSelectionStartLine] = useState<number | null>(
    null
  );
  // 드래그 끝 라인 넘버
  const [selectionEndLine, setSelectionEndLine] = useState<number | null>(null);

  // 드래그 된 라인 넘버쌍
  const [selectedLines, setSelectedLines] = useState([]);

  // 데코 먹인 녀석들의 ID
  const [collection, setCollection] = useState(null);

  const handleEditorDidMount: OnMount = (editor, monaco) => {
    editorRef.current = editor;
    setMonaco(monaco);
    editor.onDidChangeCursorSelection((e) => handleSelectionChange(e));
    // editor.getModel()?.getAllDecorations;
    // editor.getModel()?.getLinesDecorations;
    // editor.getModel()?.getLineDecorations;
  };

  // 드래그 된 라인 시작, 끝 숫자 state에 저장
  const handleSelectionChange = (e: any) => {
    const selection = e.selection;
    const startLineNumber = selection.startLineNumber;
    const endLineNumber = selection.endLineNumber;
    setSelectionStartLine(startLineNumber);
    setSelectionEndLine(endLineNumber);
  };

  // 색칠 기능
  // 기존 데코된 라인들과 겹치는지 확인하고
  // 겹치는 라인이 있으면 기존 라인 삭제 후 새로운 라인 등록
  const colorizeSelectedLine = () => {
    if (monaco && selectionStartLine !== null && selectionEndLine !== null) {
      // 드래그 된 라인을 미리 넣어 놓음
      let deltaDecorations = [
        {
          range: new monaco.Range(selectionStartLine, 1, selectionEndLine, 1),
          options: { isWholeLine: true, className: "selected-line" },
        },
      ];

      // 현재 드래그한 라인을 newSelectedLines에 미리 넣어 놓음
      let newSelectedLines = [[selectionStartLine, selectionEndLine]];
      // 색칠될 라인을 검증
      selectedLines.map((line) => {
        console.log(line);
        if (
          line[0] !== undefined &&
          line[1] !== undefined &&
          !isOverlap(line[0], line[1], selectionStartLine, selectionEndLine)
        ) {
          console.log("검증 과정");
          const deco = {
            range: new monaco.Range(line[0], 1, line[1], 1),
            options: { isWholeLine: true, className: "selected-line" },
          };
          // 검증된 라인은 deltaDecorations에 저장
          deltaDecorations.push(deco);
          // 검증된 라인을 newSelectedLines에 저장
          newSelectedLines.push([line[0], line[1]]);
        }
      });

      console.log(collection);
      console.log(deltaDecorations);

      // 색칠을 먹이고 그 데코ID를 useState에 저장
      setCollection(
        editorRef.current?.deltaDecorations([collection], deltaDecorations)
      );
      // 새로운 라인 리스트를 useState에 저장
      setSelectedLines(newSelectedLines);
      console.log(
        // 얘가 핵심이다
        editorRef.current.getModel()?.getOverviewRulerDecorations(collection)
      );
    }
  };

  const erase = () => {
    // console.log(editorRef.current.getDecorations());
    // editorRef.current?.deltaDecorations(collection, []);
    console.log(
      // 얘가 핵심이다
      editorRef.current?.getModel()?.getOverviewRulerDecorations(collection)
    );
    editorRef.current.removeDecorations(collection);
  };

  // useEffect(() => {
  //   console.log(selectedLines);
  // }, [selectedLines]);

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
