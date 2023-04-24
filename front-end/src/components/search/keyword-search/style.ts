import styled, { css } from "styled-components";

export const SearchBarForm = styled.form`
  background-color: white;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: center;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.05);
  border-radius: 8px;
  overflow: hidden;
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
  max-width: 160px;
  max-height: 58px;
  padding-top: 20px;
  padding-bottom: 20px;
  padding-left: 16px;
  padding-right: 16px;
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
