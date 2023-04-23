import styled from "styled-components";

const FeedbackItemWrapper = styled.div`
  width: 100%;
  background-color: var(--white-color);
  box-shadow: 4px 4px 4px var(--shad-color);
  padding: 0.7rem;
  padding-left: 1rem;
  ${({ theme }) => theme.MIXINS.flexBox("row", "start", "start")}
  gap: 1rem;
`;

export { FeedbackItemWrapper };
