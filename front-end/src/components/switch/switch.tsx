import React from "react";
import * as Switch from "@radix-ui/react-switch";
import { SwitchRoot, SwitchThumb, Label } from "./style";

export const SwitchDemo = () => (
  <form>
    <div style={{ display: "flex", alignItems: "center" }}>
      <Label
        className="Label"
        htmlFor="airplane-mode"
        style={{ paddingRight: 15 }}
      >
        전체보기, 열린 게시물만 보기
      </Label>
      <SwitchRoot id="airplane-mode">
        <SwitchThumb className="SwitchThumb" />
      </SwitchRoot>
    </div>
  </form>
);
