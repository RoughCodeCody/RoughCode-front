import styled, { css } from "styled-components";
import { FiChevronDown } from "react-icons/fi";

export const DropdownArrow = styled(FiChevronDown).attrs(({ size }) => ({
  fontSize: size,
}))<{ isOpen: boolean; size: number }>`
  ${({ isOpen }) => css`
    cursor: pointer;
    transform: ${isOpen ? "rotate(180deg)" : "rotate(0)"};
    transition: all 0.3s ease-in-out;
  `}
`;
