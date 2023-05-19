import Image from "next/image";
import styled from "styled-components";

export const LogoDiv = styled.div`
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
`;

export const LogoText = styled.span`
  /* text-align: center; */
  /* margin-left: 1rem; */
  line-height: 1.75rem;
  font-size: 1.6rem;
  font-weight: 600;
`;

export const FlexContainer = styled.div`
  display: flex;
  /* overflow-x: hidden; */
  flex-flow: column nowrap;
`;

export const NavbarContainer = styled.div`
  display: flex;
  width: 100%;
  position: fixed;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;
  z-index: 10;
  background-color: var(--white-color);
  padding: 1rem 4rem 1rem 2rem;
  height: 3.3rem;
`;

export const Main = styled.main`
  flex: 1 1 0%;
  padding-top: 3.3rem;
`;

export const Footer = styled.footer`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.3rem;
  background-color: var(--placeholder-color);
  position: relative;
  margin-top: 8rem;
  height: 7rem;
`;

export const FooterFriends = styled(Image)`
  &:hover {
    transform: scale(2);
    /* transition: all ease-in-out 0.5s; */
    transform-origin: bottom right;
  }
`;
