// import React, { useRef, useState } from "react";

// import { DiffEditor } from "@monaco-editor/react";
// // 어떤 것을 prop 받을 지 정해야 합니다.
// export const DiffCodeEditor = () => {
//   // 한글 포함된 Base64 문자열
//   // 이 부분에 받아온 content 넣으면 됨
//   const base64String =
"IyDtm4Trs7TtgqQgCiMg7Jyg7J287ISxICYg7LWc7IaM7ISxIOunjOyhsQpmcm9tIGl0ZXJ0b29scyBpbXBvcnQgY29tYmluYXRpb25zCgpkZWYgc29sdXRpb24ocmVsYXRpb24pOgogICAgCiAgICByb3cgPSBsZW4ocmVsYXRpb24pCiAgICBjb2wgPSBsZW4ocmVsYXRpb25bMF0pCiAgICAKICAgICMg6rCA64ql7ZWcIOyGjeyEseydmCDrqqjrk6Ag7J24642x7IqkIOyhsO2VqQogICAgY2FzZSA9IFtdCiAgICBmb3IgaSBpbiByYW5nZSgxLCBjb2wrMSk6CiAgICAgICAgY2FzZS5leHRlbmQoY29tYmluYXRpb25zKHJhbmdlKGNvbCksIGkpKQo=";

//   const diffEditorRef = useRef(null);
//   const [lan, setLan] = useState("python");

//   function handleEditorDidMount(editor, monaco) {
//     diffEditorRef.current = editor;
//   }

//   // 받아온 코드 디코딩하는 함수
//   // 반환값 : 렌더링 시 에디터에 보여줄 코드
//   const decodeBase64ToUTF8 = (base64String) => {
//     // Base64 디코딩
//     const binaryString = atob(base64String);
//     // UTF-8 디코딩
//     const bytes = new Uint8Array(binaryString.length);
//     for (let i = 0; i < binaryString.length; i++) {
//       bytes[i] = binaryString.charCodeAt(i);
//     }
//     const utf8String = new TextDecoder("utf-8").decode(bytes);
//     return utf8String;
//   };

//   // 수정한 코드 인코딩하는 함수
//   // 반환값 : 피드백 등록 시 서버에 보낼 코드
//   const encodeUTF8ToBase64 = (code) => {
//     // UTF-8 인코딩
//     const bytes = new TextEncoder().encode(code);
//     // Base64 인코딩
//     const base64String = btoa(String.fromCharCode.apply(null, bytes));
//     return base64String;
//   };

//   // 디코딩 결과를 에디터에 넣어주면 됨
//   const decodedString = decodeBase64ToUTF8(base64String);

//   const postFeedback = () => {
//     const modifiedCode = diffEditorRef?.current?.getModifiedEditor().getValue();
//     const encodedCode = encodeUTF8ToBase64(modifiedCode);
//     // 이후 encodedCode를 api요청 보내는 코드
//   };

//   return (
//     <>
//       <DiffEditor
//         width="80vw"
//         height="80vh"
//         language={lan}
//         original={decodedString}
//         modified={decodedString}
//         onMount={handleEditorDidMount}
//       />
//     </>
//   );
// };
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
        width="80vw"
        height="80vh"
        language={lan}
        original={decodedString}
        modified={decodedString}
        onMount={handleEditorDidMount}
      />
    </>
  );
};
