import { useEffect, useState } from "react";

import { DropLabelBox, DropOptionContainer, DropOption } from "./style";
import { FlexDiv, Text } from "@/components/elements";
import { DropdownArrow } from "@/components/elements";

import { useSearchCriteriaStore } from "@/stores";

interface DropLabelProps {
  sortOptions: string[];
}

export const DropLabel = ({ sortOptions }: DropLabelProps) => {
  const { searchCriteria, setSort } = useSearchCriteriaStore();

  const [selectedOption, setSelectedOption] = useState("최신순");
  const [isOpened, setIsOpened] = useState(false);

  return (
    <DropLabelBox>
      <FlexDiv
        width="100%"
        height="100%"
        justify="space-between"
        padding="1rem"
        pointer={true}
        onClick={() => setIsOpened(!isOpened)}
      >
        <Text pointer={true}>{selectedOption}</Text>
        <DropdownArrow isOpen={isOpened} size={20} />
      </FlexDiv>

      {isOpened ? (
        <DropOptionContainer>
          {sortOptions.map((sortOption) => (
            <DropOption
              key={sortOption}
              onClick={() => {
                setSort(sortOption);
                setSelectedOption(sortOption);
                setIsOpened(false);
              }}
            >
              <Text pointer={true}>{sortOption}</Text>
            </DropOption>
          ))}
        </DropOptionContainer>
      ) : (
        <></>
      )}
    </DropLabelBox>
  );
};
