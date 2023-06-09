import React, { useEffect, useRef, useState } from "react";
import { FaRegLightbulb } from "react-icons/fa";

import * as monaco from "monaco-editor";
import Editor, { OnMount } from "@monaco-editor/react";

import { EditorWrapper, EditorHeader, EditorBottom } from "./style";
import { FlexDiv, Text } from "@/components/elements";
import { Btn } from "@/components/elements";
import { useCodeReviewFeedbackDataStore } from "@/stores/code-review-feedback";
import { CodeLanguage } from "@/features/code-reviews";

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
  headerText: string;
  originalCode: string;
  height: string;
  lineSelection: boolean;
  language: CodeLanguage;
  selectedLines?: number[][];
  noShad?: boolean;
}

export const CodeEditor: React.FC<CodeEditorProps> = ({
  headerText,
  originalCode,
  height,
  lineSelection,
  language,
  selectedLines,
  noShad = false,
}) => {
  const [editorKey, setEditorKey] = useState(0);
  const { CodeReviewFeedbackData, setSelectedLines, setIsCompleted } =
    useCodeReviewFeedbackDataStore();

  const editorRef = useRef<any>(null);
  const [monaco, setMonaco] = useState<any>(null);

  const [draggedLineNumber, setDraggedLineNumber] = useState<null[] | number[]>(
    [null, null]
  );
  const [decoIds, setDecoIds] = useState<string[][]>([]);

  const handleEditorDidMount: OnMount = (editor: any, monaco: any) => {
    editorRef.current = editor;
    setMonaco(monaco);
    // 코드 수정 불가능(읽기 전용)
    editor.updateOptions({ readOnly: true });

    if (selectedLines?.length !== 0) {
      let temp: string[][] = [];
      selectedLines?.forEach((line) => {
        // let deltaDecorations: monaco.editor.IModelDeltaDecoration[] = [];
        let deltaDecoration = [
          {
            range: new monaco.Range(line[0], 1, line[1], 1),
            options: {
              isWholeLine: true,
              className: "selected-line",
              overviewRuler: {
                color: "rgba(255, 179, 64, 0.7)",
                position: monaco.editor.OverviewRulerLane.Full,
              },
              minimap: {
                color: "rgba(255, 179, 64, 0.7)",
                position: monaco.editor.MinimapPosition.Inline,
              },
            },
          },
        ];
        let appliedDecos = editorRef.current?.deltaDecorations(
          [],
          deltaDecoration
        );
        temp.push(appliedDecos);
      });
      setDecoIds(temp);
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
          options: {
            isWholeLine: true,
            className: "selected-line",
            minimap: {
              color: "rgba(255, 179, 64, 0.7)",
              position: monaco.editor.MinimapPosition.Inline,
            },
            overviewRuler: {
              color: "rgba(255, 179, 64, 0.7)",
              position: monaco.editor.OverviewRulerLane.Full,
            },
          },
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
      console.log(decoIds);
      console.log(editorRef.current?.getModel()?.getDecorationRange(deco));
      editorRef.current?.removeDecorations(deco);
    });
    setDecoIds([]);
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

  const saveSelectedLines = () => {
    let selectedLines: number[][] = [];
    decoIds.map((deco) => {
      const startLine = editorRef.current
        ?.getModel()
        ?.getDecorationRange(deco)?.startLineNumber;
      const endLine = editorRef.current
        ?.getModel()
        ?.getDecorationRange(deco)?.endLineNumber;

      selectedLines.push([startLine, endLine]);
    });

    setSelectedLines(selectedLines);
    setIsCompleted("editor");
  };

  useEffect(() => {
    setEditorKey((prevKey) => prevKey + 1);
  }, [selectedLines]);

  return (
    <EditorWrapper noShad={noShad}>
      <EditorHeader>
        <FlexDiv>
          <FaRegLightbulb />
          <Text padding="0 0 0 0.5rem">{headerText}</Text>
        </FlexDiv>
        {lineSelection && !CodeReviewFeedbackData.isCompleted.editor ? (
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
        key={editorKey}
        height={height}
        defaultLanguage={language.name}
        defaultValue={decodedString}
        onMount={handleEditorDidMount}
      />

      <EditorBottom>
        {lineSelection ? (
          <FlexDiv gap="1rem">
            {CodeReviewFeedbackData.isCompleted.editor ? (
              <Text color="main">코드 라인 체크가 완료되었습니다</Text>
            ) : (
              <Text color="orange">코드 라인 체크가 완료되지 않았습니다</Text>
            )}
            <Btn
              text={CodeReviewFeedbackData.isCompleted.editor ? "변경" : "완료"}
              height="2rem"
              display="flex"
              align="center"
              bgColor={
                CodeReviewFeedbackData.isCompleted.editor ? "main" : "orange"
              }
              onClickFunc={saveSelectedLines}
            />
          </FlexDiv>
        ) : (
          <></>
        )}
      </EditorBottom>
    </EditorWrapper>
  );
};
