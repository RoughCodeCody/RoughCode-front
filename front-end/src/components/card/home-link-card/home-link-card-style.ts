import styled, { css } from "styled-components";

const LinkCard = styled.div`
  width: 23rem;
  height: 27rem;
  padding: 1.6rem;
  border-radius: 36px;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1.6rem;
`;

const LinkCardTitle = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 11.4%;
`;

const LinkCardContent = styled.div`
  width: 100%;
  height: 15%;
`;

const LinkCardImage = styled.div`
  width: 100%;
  flex-grow: 1;
`;

export { LinkCard, LinkCardTitle, LinkCardContent, LinkCardImage };
