import React, { useRef } from "react";

import { CodeEditor } from "@/features/code-editor";
import { DiffCodeEditor } from "@/features/code-editor";
import Tippy from "@tippyjs/react/headless";
import { FlexDiv } from "@/components/elements";

export const ModifyFeedback = () => {
  return (
    <FlexDiv width="100vw" direction="column" gap="10rem">
      <FlexDiv width="50%" height="50%">
        <CodeEditor />
      </FlexDiv>
      <FlexDiv width="50%" height="50%">
        <DiffCodeEditor
          base64String={
            "IyDtm4Trs7TtgqQgCiMg7Jyg7J287ISxICYg7LWc7IaM7ISxIOunjOyhsQpmcm9tIGl0ZXJ0b29scyBpbXBvcnQgY29tYmluYXRpb25zCgpkZWYgc29sdXRpb24ocmVsYXRpb24pOgogICAgCiAgICByb3cgPSBsZW4ocmVsYXRpb24pCiAgICBjb2wgPSBsZW4ocmVsYXRpb25bMF0pCiAgICAKICAgICMg6rCA64ql7ZWcIOyGjeyEseydmCDrqqjrk6Ag7J24642x7IqkIOyhsO2VqQogICAgY2FzZSA9IFtdCiAgICBmb3IgaSBpbiByYW5nZSgxLCBjb2wrMSk6CiAgICAgICAgY2FzZS5leHRlbmQoY29tYmluYXRpb25zKHJhbmdlKGNvbCksIGkpKQo="
          }
        />
      </FlexDiv>
    </FlexDiv>
  );
};
