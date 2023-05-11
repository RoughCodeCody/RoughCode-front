import { useEffect, useRef, useState } from "react";

import { FlexDiv, Text } from "@/components/elements";
import { DropdownArrow } from "@/components/elements";
import { useSearchCriteriaStore } from "@/stores";

import { DropLabelBox, DropOptionContainer, DropOption } from "./style";

interface DropLabelProps {
  sortOptions: string[];
  type: "project" | "codeReview";
}

export const DropLabel = ({ sortOptions, type }: DropLabelProps) => {
  const boxRef = useRef<HTMLInputElement>(null);
  const [isOpened, setIsOpened] = useState(false);
  const [selectedOption, setSelectedOption] = useState("최신순");

  const { setSort } = useSearchCriteriaStore();

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (boxRef.current && !boxRef.current.contains(event.target as Node)) {
        setIsOpened(false);
      }
    };
    document.addEventListener("click", handleClickOutside);
    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, [boxRef]);

  return (
    <DropLabelBox ref={boxRef}>
      <FlexDiv
        width="100%"
        height="100%"
        justify="space-between"
        padding="1rem"
        pointer={true}
        onClick={() => setIsOpened(!isOpened)}
      >
        <Text pointer={true}>{selectedOption}</Text>
        <DropdownArrow isopen={isOpened.toString()} size={20} />
      </FlexDiv>

      {isOpened ? (
        <DropOptionContainer>
          {sortOptions.map((sortOption) => (
            <DropOption
              key={sortOption}
              onClick={() => {
                setSort(sortOption, type);
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
