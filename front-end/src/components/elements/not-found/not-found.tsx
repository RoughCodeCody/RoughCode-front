import styled, { keyframes } from "styled-components";
import Image from "next/image";
import cryingFriends from "@/assets/crying-friends.png";

const NotFoundWrapper = styled.div<{ size: number }>`
  width: ${(props) => props.size}px;
  height: ${(props) => props.size}px;
`;

export const NotFound = ({ size }: { size: number }) => {
  return (
    <NotFoundWrapper size={size}>
      <Image src={cryingFriends} alt="spinner" width={size} height={size} />
    </NotFoundWrapper>
  );
};
