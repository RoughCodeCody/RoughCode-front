import React from "react";

import { SwitchRoot, SwitchThumb, Label } from "./style";

import { Text } from "@/components/elements";
import { useSearchCriteriaStore } from "@/stores";

export const SwitchDemo = () => {
  const { searchCriteria, setClosedValue } = useSearchCriteriaStore();
  return (
    <form>
      <div style={{ display: "flex", alignItems: "center" }}>
        <Label className="Label" style={{ paddingRight: 15 }}>
          <Text size="1.1rem" color="font">
            {searchCriteria.closed === 1 ? "전체 게시물" : "열린 게시물"}
          </Text>
        </Label>
        <SwitchRoot
          id="airplane-mode"
          onClick={() => {
            setClosedValue(searchCriteria.closed === 1 ? 0 : 1);
          }}
        >
          <SwitchThumb className="SwitchThumb" />
        </SwitchRoot>
      </div>
    </form>
  );
};
