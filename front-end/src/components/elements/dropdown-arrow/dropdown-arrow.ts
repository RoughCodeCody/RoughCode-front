import styled, { css } from "styled-components";
import { FiChevronDown } from "react-icons/fi";

/**
 * Shows chevron svg rotating based on isopen props.
 *
 * @param {string} isopen Open state that converted from boolean to string.
 * @param {string} size Decides font size in pixel.
 */
export const DropdownArrow = styled(FiChevronDown).attrs(({ size }) => ({
  fontSize: size,
}))<{ isopen: string; size: number }>`
  ${({ isopen }) => css`
    cursor: pointer;
    transform: ${isopen === "true" ? "rotate(180deg)" : "rotate(0)"};
    transition: all 0.3s ease-in-out;
  `}
`;
