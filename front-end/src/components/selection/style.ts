import styled from "styled-components";
import { Text } from "../elements";

const SelectionWrapper = styled.div`
  position: relative;
  cursor: pointer;
`;

const SelectionList = styled.div`
  position: absolute;
  top: 1.5rem;
  right: 0;
  width: 6.5rem;
  padding: 0.7rem 1rem;
  border: 1px solid var(--font-color);
  background-color: var(--white-color);
  border-radius: 5px;
  cursor: default;
  ${({ theme }) => theme.MIXINS.flexBox("column")};
  gap: 0.5rem;
`;

const SelectionText = styled(Text).attrs(({ color }) => ({
  size: "0.9rem",
  pointer: true,
  color: color,
}))``;

export { SelectionWrapper, SelectionList, SelectionText };
