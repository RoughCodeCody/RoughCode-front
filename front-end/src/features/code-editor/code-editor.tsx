import React, { useEffect, useRef, useState } from "react";
import * as monaco from "monaco-editor";
import Editor, { OnMount } from "@monaco-editor/react";

import { FaRegLightbulb } from "react-icons/fa";

import { EditorWrapper, EditorHeader, EditorBottom } from "./style";
import { FlexDiv, Text } from "@/components/elements";
import { Btn } from "@/components/elements";

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

interface CodeEditorProps {
  coloredLines: number[][];
  setColoredLines: React.Dispatch<React.SetStateAction<number[][]>>;
  headerText: string;
  originalCode: string;
  height: string;
  lineSelection: boolean;
  language: string;
  selectedLines?: number[][];
}

export const CodeEditor: React.FC<CodeEditorProps> = ({
  coloredLines,
  setColoredLines,
  headerText,
  originalCode,
  height,
  lineSelection,
  language,
  selectedLines,
}) => {
  const editorRef = useRef<any>(null);
  const [monaco, setMonaco] = useState<any>(null);

  const [draggedLineNumber, setDraggedLineNumber] = useState<null[] | number[]>(
    [null, null]
  );
  const [decoIds, setDecoIds] = useState<string[]>([]);

  const handleEditorDidMount: OnMount = (editor: any, monaco: any) => {
    editorRef.current = editor;
    setMonaco(monaco);
    // 코드 수정 불가능(읽기 전용)
    editor.updateOptions({ readOnly: true });

    if (selectedLines?.length !== 0) {
      let deltaDecorations: monaco.editor.IModelDeltaDecoration[] = [];
      selectedLines?.forEach((line) => {
        deltaDecorations.push({
          range: new monaco.Range(line[0], 1, line[1], 1),
          options: { isWholeLine: true, className: "selected-line" },
        });
      });
      editorRef.current?.deltaDecorations([], deltaDecorations);
    }

    editor.onDidChangeCursorSelection((e: any) => handleSelectionChange(e));
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
          setColoredLines(
            coloredLines.filter((item) => {
              console.log(item[0]);
              console.log(item[1]);
              console.log(startLine);
              console.log(endLine);
              item[0] !== parseInt(startLine) && item[1] !== parseInt(endLine);
            })
          );
        }
      });

      const decoId = editorRef.current?.deltaDecorations([], deltaDecorations);
      setColoredLines([
        ...coloredLines,
        [draggedLineNumber[0], draggedLineNumber[1]],
      ]);
      setDecoIds([...newDecoIds, decoId]);
      console.log(decoIds);
    }
  };

  const erase = () => {
    decoIds.map((deco) => {
      editorRef.current.removeDecorations(deco);
      setDecoIds([]);
    });
  };

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

  const decodedString = decodeBase64ToUTF8(originalCode);

  useEffect(() => {}, [originalCode]);

  return (
    <EditorWrapper>
      <EditorHeader>
        <FlexDiv>
          <FaRegLightbulb />
          <Text padding="0 0 0 0.5rem">{headerText}</Text>
        </FlexDiv>
        {lineSelection ? (
          <FlexDiv gap="1rem">
            <Btn
              text="선택"
              height="1.7rem"
              display="flex"
              align="center"
              onClickFunc={colorizeSelectedLine}
            />
            <Btn
              text="초기화"
              height="1.7rem"
              display="flex"
              align="center"
              onClickFunc={erase}
            />
          </FlexDiv>
        ) : (
          <></>
        )}
      </EditorHeader>

      <Editor
        height={height}
        defaultLanguage={language}
        defaultValue={decodedString}
        onMount={handleEditorDidMount}
      />

      <EditorBottom />
    </EditorWrapper>
  );
};
