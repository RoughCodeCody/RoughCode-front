import Link from "next/link";
import styled from "styled-components";
import { AiOutlinePlusCircle } from "react-icons/ai";

const WriteFloatBtnWrapper = styled(Link)`
  position: fixed;
  bottom: 5rem;
  right: 5rem;
  z-index: 1000;
  cursor: pointer;
`;

const FloatBtn = styled(AiOutlinePlusCircle)`
  font-size: 5rem;
  color: var(--main-color);
  background-color: white;
  border-radius: 50%;
`;

export { WriteFloatBtnWrapper, FloatBtn };
