import styled from "styled-components";

export const LogoDiv = styled.div`
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
`;

export const LogoText = styled.span`
  margin-left: 1rem;
  line-height: 1.75rem;
  font-size: 1.25rem;
  font-weight: 600;
`;

export const FlexContainer = styled.div`
  display: flex;
  flex-flow: column nowrap;
`;

export const NavbarContainer = styled.div`
  display: flex;
  position: relative;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;
  z-index: 10;
  background-color: var(--white-color);
  padding: 1rem;
  height: 5rem;
`;

export const Main = styled.main`
  flex: 1 1 0%;
`;
