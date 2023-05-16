import styled from "styled-components";

export const SidebarContainer = styled.div`
  border-radius: 25px;
  background-color: var(--white-color);
  padding: 1.5rem;
  width: 25%;
  min-height: 25rem;
`;

export const SidebarFallback = styled.div`
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  justify-content: center;
  height: 20rem;
  text-align: center;
`;

export const ItemContainer = styled.div`
  display: flex;
  flex-flow: column nowrap;
  gap: 1rem;
`;

export const Item = styled.div<{ isSelected: boolean }>`
  display: flex;
  flex-flow: column nowrap;
  transition: all 0.3s ease-in-out;
  border: ${(props) =>
    props.isSelected ? "1px solid var(--main-color)" : "1px solid var(--black-color)"};
  border-radius: 15px;
  box-shadow: ${(props) => {
    if (props.isSelected) return "0 0 5px var(--main-color)";
  }};
  padding: 1rem;
  width: 100%;
  gap: 1rem;
`;

export const ItemUserName = styled.div`
  display: flex;
  flex-flow: row nowrap;
  gap: 0.5rem;
`;

export const ItemContent = styled.div``;
