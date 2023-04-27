import { AiOutlinePlusCircle } from "react-icons/ai";
import { WriteFloatBtnWrapper } from "./style";

type WriteFloatBtnProps = {
  navTo: string;
};

export const WriteFloatBtn = ({ navTo }: WriteFloatBtnProps) => {
  return (
    <WriteFloatBtnWrapper href={navTo} color="main">
      <AiOutlinePlusCircle />
    </WriteFloatBtnWrapper>
  );
};
