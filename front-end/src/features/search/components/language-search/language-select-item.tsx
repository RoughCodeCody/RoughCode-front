import styled from "styled-components";
import { useRouter } from "next/router";

import { useSelectedLanguageStore } from "@/stores";
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

const LanguageText = styled.span`
  text-indent: 0.75rem;
  text-align: center;
  cursor: pointer;
`;

interface LanguageType {
  languageId?: number;
  name: string;
  cnt?: number;
}

export const LanguageSelectItem = ({ languageId, name, cnt }: LanguageType) => {
  const { setSelectedLanguage } = useSelectedLanguageStore();
  const { addTagId } = useSearchCriteriaStore();
  const router = useRouter();
  const endPoint = router.asPath;
  console.log(endPoint);

  const handleClick = () => {
    console.log(languageId, name);
    if (endPoint === "/code-review") {
      if (languageId) {
        addTagId({ tagId: languageId, name });
      }
    } else {
      if (languageId) {
        setSelectedLanguage([{ languageId, name }]);
      }
    }
  };

  return (
    <BorderLineDiv onClick={handleClick}>
      <LanguageText>{name}</LanguageText>
    </BorderLineDiv>
  );
};
