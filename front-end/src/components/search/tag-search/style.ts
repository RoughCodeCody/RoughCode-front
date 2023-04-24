import styled, { css } from "styled-components";

export const SearchInput = styled.input<{
  padding?: string;
}>`
  ${({ padding }) => css`
    font-size: 1.1rem;
    width: 100%;
    height: 58px;
    max-width: 160px;
    max-height: 58px;
    padding: ${padding ? padding : 0};
    display: flex;
    align-items: center;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.05);
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
