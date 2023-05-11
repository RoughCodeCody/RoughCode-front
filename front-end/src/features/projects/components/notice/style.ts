import styled, { keyframes } from "styled-components";
import { Text } from "@/components/elements";

const showDropdown = keyframes`
  from { transform: translateY(-100%) }
  to { transform: translateY(0) }
`;

const VersionDropdownWrapper = styled.div`
  width: 15%;
  max-width: 11rem;
  position: relative;
`;

const DropdownContentWrapper = styled.div`
  position: absolute;
  top: calc(3.75rem - 1px);
  left: 0;
  width: 100%;
  overflow: hidden;
  z-index: 1;
`;

const DropdownContent = styled.div`
  padding: 0.5rem;
  background-color: var(--sub-one-color);
  border: 1px solid var(--main-color);
  border-top: none;
  ${({ theme }) => theme.MIXINS.flexBox("column")}
  gap: 0.5rem;
  transition: height 1s ease-in-out;

  &[data-isopen="true"] {
    animation: ${showDropdown} 0.5s ease;
  }
`;

const DropdownItem = styled.div`
  width: 100%;
  height: 5rem;
  background-color: var(--sub-one-color);
  border-radius: 0.5rem;
  cursor: pointer;
  ${({ theme }) => theme.MIXINS.flexBox("row", "space-around")}

  &:hover {
    background-color: var(--sub-two-color);
  }
`;

const NoticeTimeWrapper = styled.div`
  width: 15%;
  line-height: 1.2rem;
  ${({ theme }) => theme.MIXINS.flexBox("column")}
`;

const NoticeTime = styled(Text).attrs({
  size: "0.7rem",
  pointer: true,
})``;

const NoticeContent = styled.div`
  width: 70%;
  ${({ theme }) => theme.MIXINS.flexBox("row", "start")};
`;

export {
  VersionDropdownWrapper,
  DropdownContentWrapper,
  DropdownContent,
  DropdownItem,
  NoticeTimeWrapper,
  NoticeTime,
  NoticeContent,
};
