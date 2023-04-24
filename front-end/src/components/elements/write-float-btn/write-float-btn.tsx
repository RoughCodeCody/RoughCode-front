import { AiOutlinePlusCircle } from "react-icons/ai";
import Link from "next/link";
import { WriteFloatBtnWrapper } from "./style";

type WriteFloatBtnProps = {
  navTo: string;
};

export const WriteFloatBtn = ({ navTo }: WriteFloatBtnProps) => {
  return (
    <Link href={navTo}>
      <WriteFloatBtnWrapper color="main">
        <AiOutlinePlusCircle />
      </WriteFloatBtnWrapper>
    </Link>
  );
};
