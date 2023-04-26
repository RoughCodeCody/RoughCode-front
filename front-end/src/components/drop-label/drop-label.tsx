import { useEffect, useState } from "react";

import { DropLabelBox, DropOptionContainer, DropOption } from "./style";
import { FlexDiv, Text } from "../elements";
import { DropdownArrow } from "../elements";

interface DropLabelProps {
  options: string[];
}

export const DropLabel = ({ options }: DropLabelProps) => {
  const [selectedOption, setSelectedOption] = useState("최신순");
  const [isOpened, setIsOpened] = useState(false);
  const temp = ["최신순", "좋아요순", "리뷰순"];

  useEffect(() => console.log(isOpened), [isOpened]);
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
          {options.map((option) => (
            <DropOption
              onClick={() => {
                setSelectedOption(option);
                setIsOpened(false);
              }}
            >
              <Text pointer={true}>{option}</Text>
            </DropOption>
          ))}
        </DropOptionContainer>
      ) : (
        <></>
      )}
    </DropLabelBox>
  );
};
