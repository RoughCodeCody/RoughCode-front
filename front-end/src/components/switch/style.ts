import * as Switch from "@radix-ui/react-switch";
import styled from "styled-components";

export const SwitchRoot = styled(Switch.Root)`
  padding: 0;
  width: 42px;
  height: 25px;
  background-color: var(--switch-color);
  border-radius: 9999px;
  position: relative;
  box-shadow: 0 2px 10px var(--switch-color);
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);

  &[data-state="checked"] {
    background-color: var(--main-color);
  }
`;

export const SwitchThumb = styled(Switch.Thumb)`
  display: block;
  width: 21px;
  height: 21px;
  background-color: white;
  border-radius: 9999px;
  /* box-shadow: 0 2px 2px var(--main-color); */
  transition: transform 100ms;
  transform: translateX(2px);
  will-change: transform;

  &[data-state="checked"] {
    transform: translateX(19px);
  }
`;

export const Label = styled.label`
  color: var(--font-color);
  font-size: 1rem;
  line-height: 1;
`;
