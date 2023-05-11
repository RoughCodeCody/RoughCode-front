import styled from "styled-components";

const VersionInfoWrapper = styled.div<{
  bgColor?: string;
  pointer?: boolean;
}>`
  width: 100%;
  height: auto;
  border-radius: 15px;
  padding: 1rem;
  background-color: ${({ bgColor }) => "var(--" + bgColor + "-color)"};
  border: 3px solid var(--sub-one-color);
  cursor: ${({ pointer }) => (pointer ? "pointer" : "auto")};
  ${({ theme }) => theme.MIXINS.flexBox("row", "spce-around", "start")}
  gap: 5%;
`;

export { VersionInfoWrapper };
