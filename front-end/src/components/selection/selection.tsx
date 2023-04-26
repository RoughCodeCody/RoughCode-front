import { useState } from "react";
import { SelectionList, SelectionText, SelectionWrapper } from "./style";
import { TbDotsVertical } from "react-icons/tb";
import { Text } from "../elements";

type SelectionProps = {
  isMine: boolean;
};

export const Selection = ({ isMine }: SelectionProps) => {
  const [isOpen, setisOpen] = useState<boolean>(false);

  return (
    <SelectionWrapper>
      <TbDotsVertical onClick={() => setisOpen((prev) => !prev)} />
      {isOpen && (
        <SelectionList>
          {isMine ? (
            <>
              <SelectionText>수정하기</SelectionText>
              <SelectionText>삭제하기</SelectionText>
            </>
          ) : (
            <SelectionText color="red">신고하기</SelectionText>
          )}
        </SelectionList>
      )}
    </SelectionWrapper>
  );
};
