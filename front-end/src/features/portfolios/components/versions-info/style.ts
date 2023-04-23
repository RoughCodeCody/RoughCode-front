import styled from "styled-components";

const VersionInfoWrapper = styled.div<{
  bgColor?: string;
}>`
  width: 100%;
  height: auto;
  border-radius: 15px;
  padding: 1rem;
  background-color: ${({ bgColor }) => "var(--" + bgColor + "-color)"};
  border: 2px solid var(--sub-one-color);
  ${({ theme }) => theme.MIXINS.flexBox("row", "spce-around", "start")}
  gap: 5%;
`;

const FeedbackItem = styled.div`
  width: 100%;
  background-color: var(--white-color);
  box-shadow: 4px 4px 4px var(--shad-color);
  padding: 0.7rem;
  padding-left: 1rem;
  ${({ theme }) => theme.MIXINS.flexBox("row", "start")}
  gap: 1rem;
`;

export { VersionInfoWrapper, FeedbackItem };
