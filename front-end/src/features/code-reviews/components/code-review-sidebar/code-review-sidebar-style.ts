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

export const Heading = styled.div`
  margin-top: 1rem;
  font-size: 1.8rem;
  font-weight: bold;
`;

export const SubHeading = styled.div`
  font-size: 1.2rem;
  line-height: 1.8rem;
`;
