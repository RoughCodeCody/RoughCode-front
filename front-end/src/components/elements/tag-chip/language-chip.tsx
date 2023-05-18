import { IoClose } from "react-icons/io5";

import { TagChipContainer } from "./style";
import { Text } from "../text";
import { useSelectedLanguageStore } from "@/stores";

type LanguageChipProps = {
  languageId: number;
  name: string;
};

export const LanguageChip = ({ languageId, name }: LanguageChipProps) => {
  const { reset } = useSelectedLanguageStore();

  return (
    <TagChipContainer>
      <Text size="1.2rem" color="bg" bold={true}>
        {name}
      </Text>

      <IoClose
        onClick={() => {
          reset();
        }}
        cursor="pointer"
        fontSize="1.3rem"
        color="var(--sub-two-color)"
        opacity="0.7"
      />
    </TagChipContainer>
  );
};
