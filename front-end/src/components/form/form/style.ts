import styled from "styled-components";

export const StyledForm = styled.form`
  margin-top: 3rem;
  width: 100%;
  & > :not([hidden]) ~ :not([hidden]) {
    margin-top: 1.5rem;
    margin-bottom: 1.5rem;
  }
`;
