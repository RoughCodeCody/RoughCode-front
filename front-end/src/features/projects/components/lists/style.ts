import styled from "styled-components";

export const ProjectCardGrid = styled.div`
  width: 100%;
  max-width: 1440px;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 3rem;

  @media (max-width: 1350px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (max-width: 860px) {
    grid-template-columns: repeat(1, 1fr);
  }
`;
