import styled from "styled-components";

export const SubmitButtonWrapper = styled.div`
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
`;

export const SubmitButton = styled.input`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 8rem;
  height: 4rem;
  border: none;
  border-radius: 5px;
  background-color: var(--main-color);
  cursor: pointer;
  padding: 0.5rem;
  color: var(--white-color);
  font-size: 1rem;
`;
