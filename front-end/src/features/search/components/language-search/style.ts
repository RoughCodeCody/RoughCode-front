import styled, { css } from "styled-components";

export const SearchInput = styled.input<{
  padding?: string;
}>`
  ${({ padding }) => css`
    font-size: 1.1rem;
    width: 100%;
    height: 100%;
    padding: ${padding ? padding : 0};
    display: flex;
    align-items: center;
    border: none;
    border-radius: 8px;
    ::placeholder {
      color: "var(--placeholder-color)";
    }
    &:focus {
      outline: none;
    }
  `}
`;

export const LanguageSelectContainer = styled.div`
  z-index: 999;
  overflow-x: hidden;
  overflow-y: scroll;
  position: absolute;
  top: 100%;
  width: 100%;
  left: 0rem;
  max-width: 17.5rem;
  height: fit-content;
  max-height: 30rem;
  border-radius: 8px;
  border: 0.0625rem solid #dfdfdf;
`;
