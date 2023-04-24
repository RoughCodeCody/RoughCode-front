import { AiOutlinePlusCircle } from "react-icons/ai";
import { WriteFloatBtnWrapper } from "./style";
import Link from "next/link";

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
