import styled from "styled-components";

import { Spinner } from "@/components/elements";

const StyledDiv = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const SuspenseFallback = () => {
  return (
    <StyledDiv>
      <Spinner size={24} />
    </StyledDiv>
  );
};
