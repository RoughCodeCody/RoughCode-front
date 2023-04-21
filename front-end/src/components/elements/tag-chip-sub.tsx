import styled from "styled-components";

const TagChipSubWrapper = styled.div`
  background-color: var(--sub-one-color);
  padding: 0.2rem 0.7rem;
  border-radius: 1rem;
  margin-right: 0.5rem;
  height: 1.5rem;
`;

type TagChipSubProps = {
  tag: string;
};

export const TagChipSub = ({ tag }: TagChipSubProps) => {
  return <TagChipSubWrapper>{tag}</TagChipSubWrapper>;
};
