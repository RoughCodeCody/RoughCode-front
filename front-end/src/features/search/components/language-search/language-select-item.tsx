import styled from "styled-components";

import { useSelectedLanguageStore } from "@/stores";

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

const LanguageText = styled.span`
  text-indent: 0.75rem;
  text-align: center;
  cursor: pointer;
`;

interface LanguageType {
  tagId?: number;
  name: string;
  cnt?: number;
}

export const LanguageSelectItem = ({ tagId, name, cnt }: LanguageType) => {
  const { setSelectedLanguage } = useSelectedLanguageStore();

  const handleClick = () => {
    if (tagId) {
      setSelectedLanguage([{ tagId, name }]);
    }
  };

  return (
    <BorderLineDiv onClick={handleClick}>
      <LanguageText>{name}</LanguageText>
    </BorderLineDiv>
  );
};
