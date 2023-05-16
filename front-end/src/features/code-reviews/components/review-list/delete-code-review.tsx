import { Dispatch, SetStateAction } from "react";

import { Btn, FlexDiv, Text } from "@/components/elements";

interface DeleteCodeReviewProps {
  setModalOpen: Dispatch<SetStateAction<boolean>>;
  handleDelete: () => void;
  setForceClose: Dispatch<SetStateAction<boolean>>;
}

export const DeleteCodeReview = ({
  setModalOpen,
  handleDelete,
  setForceClose,
}: DeleteCodeReviewProps) => {
  return (
    <FlexDiv direction="column" padding="1rem">
      <FlexDiv direction="column" padding="2.5rem 0" gap="0.5rem">
        <Text>이 코드 리뷰를 정말 삭제할까요?</Text>
      </FlexDiv>

      <FlexDiv gap="4rem" margin="2rem 0 0 0">
        <Btn
          text="취소"
          padding="0.5rem 1rem"
          onClickFunc={() => {
            setModalOpen(false);
            setForceClose(false);
          }}
        />
        <Btn
          text="삭제"
          bgColor="red"
          color="white"
          padding="0.5rem 1rem"
          onClickFunc={handleDelete}
        />
      </FlexDiv>
    </FlexDiv>
  );
};
