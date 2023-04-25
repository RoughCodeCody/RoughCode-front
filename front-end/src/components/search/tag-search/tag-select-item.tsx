import styled, { css } from "styled-components";

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

type TagType = {
  tag: string;
};

export const TagSelectItem = ({ tag }: TagType) => {
  return (
    <BorderLineDiv>
      <TagText>{tag}</TagText>
    </BorderLineDiv>
  );
};
