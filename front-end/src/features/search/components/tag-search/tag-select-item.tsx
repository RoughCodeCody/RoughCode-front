import styled from "styled-components";
import { useSearchCriteriaStore } from "@/stores";

const BorderLineDiv = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  align-items: center;
  padding: 1rem;
  border-bottom: 0.0625rem solid var(--shad-color);
  background-color: var(--bg-color);
  cursor: pointer;
`;

const TagText = styled.span`
  text-indent: 0.75rem;
  text-align: center;
  cursor: pointer;
`;

interface TagType {
  tagId?: number;
  name: string;
  cnt?: number;
}

export const TagSelectItem = ({ tagId, name, cnt }: TagType) => {
  const { searchCriteria, addTagId } = useSearchCriteriaStore();

  const handleClick = () => {
    if (tagId) {
      addTagId({ tagId, name });
    }
  };

  return (
    <BorderLineDiv onClick={handleClick}>
      <TagText>{name}</TagText>
    </BorderLineDiv>
  );
};
