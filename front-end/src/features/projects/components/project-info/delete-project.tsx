import { Dispatch, SetStateAction } from "react";

import { Btn, FlexDiv, Text } from "@/components/elements";

interface DeleteProjectProps {
  projectTitle: string;
  setModalOpen: Dispatch<SetStateAction<boolean>>;
  handleDelete: () => void;
}

export const DeleteProject = ({
  projectTitle,
  setModalOpen,
  handleDelete,
}: DeleteProjectProps) => {
  return (
    <FlexDiv direction="column" padding="1rem">
      <FlexDiv direction="column" padding="2.5rem 0" gap="0.5rem">
        <Text as="span" color="main" bold={true}>
          {projectTitle}
        </Text>
        <Text>프로젝트를 정말 삭제할까요?</Text>
      </FlexDiv>

      <FlexDiv gap="4rem" margin="2rem 0 0 0">
        <Btn
          text="취소"
          padding="0.5rem 1rem"
          onClickFunc={() => setModalOpen(false)}
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
