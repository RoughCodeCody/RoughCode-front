import { TbEdit } from "react-icons/tb";
import styled from "styled-components";

const TooltopWrapper = styled.span`
  margin-left: 1rem;
  position: relative;
  cursor: pointer;

  &::before {
    position: absolute;
    top: 1.8rem;
    left: -4.3rem;
    background-color: var(--white-color);
    border: 1px solid var(--main-color);
    color: var(--main-color);
    border-radius: 5px;
    content: attr(aria-label);
    padding: 1rem;
    text-transform: none;
    transition: all 0.5s ease;
    width: 10rem;
  }

  &::after {
    position: absolute;
    top: calc(1.3rem - 1px);
    left: 0.7rem;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    border-top: 5px solid transparent;
    border-bottom: 5px solid var(--main-color);
    content: " ";
    font-size: 0;
    line-height: 0;
    margin-left: -5px;
    width: 0;
  }

  &::before,
  &::after {
    font-family: monospace;
    opacity: 0;
    pointer-events: none;
    text-align: center;
  }

  &:focus::before,
  &:focus::after,
  &:hover::before,
  &:hover::after {
    opacity: 1;
    transition: all 0.4s ease;
  }
`;

const EditNickNameIcon = styled(TbEdit)`
  font-size: 1.5rem;
  line-height: 2.5rem;
  color: var(--shad-color);
`;

export { TooltopWrapper, EditNickNameIcon };
