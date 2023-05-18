import { FlexDiv, WhiteBoxNoshad, WhiteBoxShad } from "@/components/elements";
import styled from "styled-components";

const ReviewListWrapper = styled(WhiteBoxNoshad)`
  position: sticky;
  top: 5.5rem;
  right: 0;
  width: 18%;
  min-width: 16rem;
  margin-right: 1%;
  padding: 1rem 0;
`;

const ReviewItemsWrapper = styled(FlexDiv)`
  max-height: 20rem;
  /* overflow-x: hidden; */
  overflow-y: auto;

  -ms-overflow-style: none;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const ReviewItemWrapper = styled(WhiteBoxShad)<{ isComplainted: boolean }>`
  position: relative;

  &::after {
    content: "";
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: ${({ isComplainted }) =>
      isComplainted ? "#000000" : "none"};
    opacity: 0.4;
  }
`;

export { ReviewListWrapper, ReviewItemsWrapper, ReviewItemWrapper };
