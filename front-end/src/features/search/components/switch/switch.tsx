import React from "react";

import { Text } from "@/components/elements";
import { useSearchCriteriaStore } from "@/stores";

import { SwitchRoot, SwitchThumb, Label } from "./style";

export const SwitchDemo = () => {
  const { searchCriteria, setClosedValue } = useSearchCriteriaStore();
  return (
    <form>
      <div style={{ display: "flex", alignItems: "center" }}>
        <Label className="Label" style={{ paddingRight: 15 }}>
          <Text size="1.1rem" color="font">
            {searchCriteria.closed === 1
              ? "닫힌 프로젝트 포함"
              : "열린 프로젝트만"}
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
