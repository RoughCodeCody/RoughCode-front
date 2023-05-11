import styled from "styled-components";

export const MenuButton = styled.button<{ isActive?: (() => boolean) | null }>`
  margin-right: 0.25rem;
  border: none;
  border-radius: 0.4rem;
  background-color: ${(props) => {
    if (!props.isActive) return "transparent";
    else return props.isActive() ? "#303030" : "transparent";
  }};
  cursor: pointer;
  padding: 0.25rem;
  width: 1.75rem;
  height: 1.75rem;
  color: #fff;

  &:hover {
    background-color: #303030;
  }
`;

export const Icon = styled.svg`
  width: 100%;
  height: 100%;
  fill: currentColor;
`;
