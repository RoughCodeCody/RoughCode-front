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
  box-shadow: 12px 12px 20px rgba(0, 0, 0, 0.06);
`;

const LinkCardTitle = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 11.4%;
  padding-left: 1rem;
  padding-right: 1rem;
`;

const LinkCardContent = styled.div`
  width: 100%;
  height: 15%;
  padding-left: 1rem;
`;

const LinkCardImage = styled.div`
  width: 100%;
  flex-grow: 1;
  border-radius: 35px;
  overflow: hidden;
`;

const ArrowRightContainer = styled.div`
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;
export {
  LinkCard,
  LinkCardTitle,
  LinkCardContent,
  LinkCardImage,
  ArrowRightContainer,
};
