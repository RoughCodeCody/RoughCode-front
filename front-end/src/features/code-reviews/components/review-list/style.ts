import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
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

const ReviewItemWrapper = styled(FlexDiv)`
  max-height: 20rem;
  /* overflow-x: hidden; */
  overflow-y: auto;

  -ms-overflow-style: none;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

export { ReviewListWrapper, ReviewItemWrapper };
