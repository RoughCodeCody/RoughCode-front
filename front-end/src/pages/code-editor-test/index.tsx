import React, { useRef } from "react";

import { CodeEditor } from "@/features/code-editor";
import { DiffCodeEditor } from "@/features/code-editor/diff-code-editor";
import Tippy from "@tippyjs/react/headless";
const CodeEditorTest = () => {
  return (
    <>
      <CodeEditor />
      {/* <DiffCodeEditor
        base64String={
          "IyDtm4Trs7TtgqQgCiMg7Jyg7J287ISxICYg7LWc7IaM7ISxIOunjOyhsQpmcm9tIGl0ZXJ0b29scyBpbXBvcnQgY29tYmluYXRpb25zCgpkZWYgc29sdXRpb24ocmVsYXRpb24pOgogICAgCiAgICByb3cgPSBsZW4ocmVsYXRpb24pCiAgICBjb2wgPSBsZW4ocmVsYXRpb25bMF0pCiAgICAKICAgICMg6rCA64ql7ZWcIOyGjeyEseydmCDrqqjrk6Ag7J24642x7IqkIOyhsO2VqQogICAgY2FzZSA9IFtdCiAgICBmb3IgaSBpbiByYW5nZSgxLCBjb2wrMSk6CiAgICAgICAgY2FzZS5leHRlbmQoY29tYmluYXRpb25zKHJhbmdlKGNvbCksIGkpKQo="
        }
      /> */}

      <br />
      <br />
      <br />
      <br />
      <Tippy
        render={(attrs) => (
          <div className="box" tabIndex="-1" {...attrs}>
            My tippy box
          </div>
        )}
      >
        <button>My button</button>
      </Tippy>
    </>
  );
};

export default CodeEditorTest;
