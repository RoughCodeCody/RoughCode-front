import { useState } from "react";
import { SelectionList, SelectionText, SelectionWrapper } from "./style";
import { TbDotsVertical } from "react-icons/tb";
import { Text } from "../elements";

type SelectionProps = {
  isMine: boolean;
  handleDeleteFunc?: () => void;
};

export const Selection = ({ isMine, handleDeleteFunc }: SelectionProps) => {
  const [isOpen, setisOpen] = useState<boolean>(false);

  return (
    <SelectionWrapper>
      <TbDotsVertical onClick={() => setisOpen((prev) => !prev)} />
      {isOpen && (
        <SelectionList>
          {isMine ? (
            <>
              <SelectionText>수정하기</SelectionText>
              <SelectionText onClick={handleDeleteFunc}>삭제하기</SelectionText>
            </>
          ) : (
            <SelectionText color="red">신고하기</SelectionText>
          )}
        </SelectionList>
      )}
    </SelectionWrapper>
  );
};
