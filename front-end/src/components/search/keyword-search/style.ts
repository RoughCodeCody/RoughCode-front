import styled, { css } from "styled-components";

export const SearchBarForm = styled.form`
  background-color: white;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: center;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.05);
  border: 1px solid var(--bg-color);
  border-radius: 8px;
  overflow: hidden;
  :focus-within {
    border: 1px solid var(--main-color);
    transition-duration: 0.2s;
    transition-timing-function: ease;
    transition-delay: 0s;
    transition-property: all;
  }
`;

export const SearchInput = styled.input`
  font-size: 1.1rem;
  width: 100%;
  height: 100%;
  border: none;
  ::placeholder {
    color: "var(--placeholder-color)";
  }
  &:focus {
    outline: none;
  }
`;
export const SearchButton = styled.button`
  background-color: var(--main-color);
  color: var(--white-color);
  font-size: 1rem;
  width: 100%;
  height: 100%;
  max-width: 10rem;
  max-height: 3.7rem;
  padding-top: 1.25rem;
  padding-bottom: 1.25rem;
  padding-left: 1rem;
  padding-right: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.05);
  border-radius: 8px;
  cursor: pointer;
  transition-duration: 0.2s;
  transition-timing-function: ease;
  transition-delay: 0s;
  transition-property: all;
  &:hover {
    font-weight: bold; /* 볼드체 */
    transform: scale(0.95);
  }
`;
