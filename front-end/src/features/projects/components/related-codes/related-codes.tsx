import { Accordion } from "@/components/elements";

import { RelatedCode } from "../../types";
import { MiniFeedbackItem } from "../mini-feedback-item";

type RelatedCodesProps = { codes: RelatedCode[] };

export const RelatedCodes = ({ codes }: RelatedCodesProps) => {
  // 더미데이터
  const codeCnt = 1;
  const isMine = true;

  return (
    <Accordion
      title={`이 프로젝트와 연결된 코드 리뷰  ${codeCnt}개`}
      hasBtn={isMine}
      btnText="+ 새 코드 연결"
    >
      {codes.length !== 0 &&
        codes.map((code) => <MiniFeedbackItem code={code} key={code.codeId} />)}
    </Accordion>
  );
};
