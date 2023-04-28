import styled from "styled-components";

const StyledDiv = styled.div`
  color: rgb(239 68 68);
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const StyledH2 = styled.h2`
  font-size: 1.125rem;
  font-weight: 600;
`;

const StyledButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  border-width: 1px;
  border-color: rgb(209 213 219);
  border-radius: 0.375rem;
  font-weight: 500;
  background-color: rgb(37 99 235);
  color: rgb(255 255 255);
  font-size: 1rem;
  line-height: 1.5rem;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  padding-left: 1.5rem;
  padding-right: 1.5rem;
  margin-top: 1rem;

  &:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }

  &:hover {
    opacity: 0.8;
  }

  &:focus {
    outline: 2px solid transparent;
    outline-offset: 2px;
  }
`;

const StyledSpan = styled.span`
  margin-left: 0.5rem;
  margin-right: 0.5rem;
`;

export const ErrorFallback = () => {
  return (
    <StyledDiv role="alert">
      <StyledH2>Ooops, something went wrong :( </StyledH2>
      <StyledButton
        onClick={() => window.location.assign(window.location.origin)}
      >
        <StyledSpan>Refresh</StyledSpan>
      </StyledButton>
    </StyledDiv>
  );
};
