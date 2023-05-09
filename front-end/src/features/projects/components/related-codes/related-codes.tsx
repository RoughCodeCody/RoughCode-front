import { useState } from "react";

import { Accordion, Modal } from "@/components/elements";

import { RelatedCode } from "../../types";
import { MiniFeedbackItem } from "../mini-feedback-item";
import { MyCodeList } from "./code-list";

interface RelatedCodesProps {
  codes: RelatedCode[];
  isMine: boolean;
}

export const RelatedCodes = ({ codes, isMine }: RelatedCodesProps) => {
  const [codeLinkModalOpen, setCodeLinkModalOpen] = useState(false);

  return (
    <>
      {codes.length !== 0 && (
        <Accordion
          title={`이 프로젝트와 연결된 코드 리뷰  ${codes.length}개`}
          hasBtn={isMine}
          btnText="+ 새 코드 연결"
          btnClickFunc={() => setCodeLinkModalOpen(true)}
        >
          {codes.map((code) => (
            <MiniFeedbackItem code={code} key={code.codeId} />
          ))}
        </Accordion>
      )}
      <button onClick={() => setCodeLinkModalOpen(true)}>click</button>

      <Modal
        headerText={"나의 코드 목록"}
        width="80%"
        isOpen={codeLinkModalOpen}
        setIsOpen={setCodeLinkModalOpen}
        modalContent={
          <MyCodeList relatedCodeIds={codes.map((code) => code.codeId)} />
        }
      />
    </>
  );
};
