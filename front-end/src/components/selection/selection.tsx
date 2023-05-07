import { useState } from "react";
import { SelectionList, SelectionText, SelectionWrapper } from "./style";
import { TbDotsVertical } from "react-icons/tb";
import { Text } from "../elements";

type SelectionProps = {
  isMine: boolean;
  handleDeleteFunc?: () => void;
  handleComplaintFunc?: () => void;
};

// 아예 props를 객체로 표시할 텍스트랑 클릭시 실행할 함수를 전달해서
// 그 객체를 map 돌려서 표시하는 게 나을 듯

export const Selection = ({
  isMine,
  handleDeleteFunc,
  handleComplaintFunc,
}: SelectionProps) => {
  const [isOpen, setisOpen] = useState<boolean>(false);

  return (
    <SelectionWrapper>
      <TbDotsVertical onClick={() => setisOpen((prev) => !prev)} />
      {isOpen && (
        <SelectionList>
          {isMine ? (
            <>
              <SelectionText>수정하기</SelectionText>
              <SelectionText
                onClick={() => {
                  handleDeleteFunc();
                  setisOpen(false);
                }}
              >
                삭제하기
              </SelectionText>
            </>
          ) : (
            <SelectionText
              color="red"
              onClick={() => {
                handleComplaintFunc();
                setisOpen(false);
              }}
            >
              신고하기
            </SelectionText>
          )}
        </SelectionList>
      )}
    </SelectionWrapper>
  );
};
