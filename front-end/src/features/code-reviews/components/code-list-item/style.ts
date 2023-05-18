import { WhiteBoxShad } from "@/components/elements";
import styled from "styled-components";

const CodeListItemWrapper = styled(WhiteBoxShad)`
  width: 100%;
  padding: 1.2rem 1.5rem;
  cursor: pointer;
  ${({ theme }) => theme.MIXINS.flexBox("column")}
  gap: 1rem;
  border: 1px solid transparent;

  &:hover {
    border: 1px solid var(--main-color);
  }
`;

export { CodeListItemWrapper };
