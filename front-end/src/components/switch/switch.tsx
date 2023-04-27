import React from "react";
import { SwitchRoot, SwitchThumb, Label } from "./style";
import { useSearchCriteriaStore } from "@/stores";
import { Text } from "../elements";

export const SwitchDemo = () => {
  const { searchCriteria, setClosedValue } = useSearchCriteriaStore();
  return (
    <form>
      <div style={{ display: "flex", alignItems: "center" }}>
        <Label className="Label" style={{ paddingRight: 15 }}>
          <Text size="1.1rem" color="font">
            {searchCriteria.closed ? "전체 게시물" : "열린 게시물"}
          </Text>
        </Label>
        <SwitchRoot
          id="airplane-mode"
          onClick={() => {
            setClosedValue(!searchCriteria.closed);
            console.log(searchCriteria);
          }}
        >
          <SwitchThumb className="SwitchThumb" />
        </SwitchRoot>
      </div>
    </form>
  );
};
