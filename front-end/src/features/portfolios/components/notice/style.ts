import * as RadixNavMenu from "@radix-ui/react-navigation-menu";
import styled from "styled-components";

const RadixNavMenuRoot = styled(RadixNavMenu.Root)`
  width: 15%;
  position: relative;
`;

const RadixNavMenuItem = styled(RadixNavMenu.Item)`
  ${({ theme }) => theme.MIXINS.flexBox()}
`;

const RadixNavMenuTrigger = styled(RadixNavMenu.Trigger)`
  all: unset;
`;

const RadixNavMenuContent = styled(RadixNavMenu.Content)`
  position: absolute;
  top: calc(3.75rem - 1px);
  left: 0;
  width: 100%;
  background-color: var(--sub-one-color);
`;

const VersionLink = styled.li`
  padding: 1rem;
  ${({ theme }) => theme.MIXINS.flexBox("row", "space-around")}

  &:hover {
    & p {
      color: var(--main-color);
    }
  }
`;

const NoticeContent = styled.div`
  width: 70%;
  ${({ theme }) => theme.MIXINS.flexBox("row", "start")};
`;

const NoticeTime = styled.div`
  width: 15%;
  line-height: 1.2rem;
  ${({ theme }) => theme.MIXINS.flexBox("column")}
`;

export {
  RadixNavMenuRoot,
  RadixNavMenuItem,
  RadixNavMenuTrigger,
  RadixNavMenuContent,
  VersionLink,
  NoticeContent,
  NoticeTime,
};
