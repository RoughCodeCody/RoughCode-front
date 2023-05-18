import { useState } from "react";

import { Accordion, FlexDiv, Modal, Text } from "@/components/elements";

import { RelatedCode } from "../../types";
import { MiniFeedbackItem } from "../mini-feedback-item";
import { MyCodeList } from "./my-code-list";

interface RelatedCodesProps {
  codes: RelatedCode[];
  isMine: boolean;
  projectId: string;
}

export const RelatedCodes = ({
  codes,
  isMine,
  projectId,
}: RelatedCodesProps) => {
  const [codeLinkModalOpen, setCodeLinkModalOpen] = useState(false);

  return (
    <>
      {isMine || codes.length !== 0 ? (
        <Accordion
          title={`이 프로젝트와 연결된 코드 리뷰  ${codes.length}개`}
          hasBtn={isMine}
          btnText="+ 새 코드 연결"
          btnClickFunc={() => setCodeLinkModalOpen(true)}
        >
          <FlexDiv width="100%" direction="column" gap="1rem">
            {codes.map((code) => (
              <MiniFeedbackItem code={code} key={code.codeId} />
            ))}
          </FlexDiv>
        </Accordion>
      ) : (
        isMine && (
          <Accordion
            title={`이 프로젝트와 연결된 코드 리뷰  ${codes.length}개`}
            hasBtn={isMine}
            btnText="+ 새 코드 연결"
            btnClickFunc={() => setCodeLinkModalOpen(true)}
          >
            <Text>연결된 코드가 없습니다</Text>
          </Accordion>
        )
      )}

      <Modal
        headerText={"나의 코드 목록"}
        headerDescription="연결할 코드들을 선택 또는 선택 해제한 뒤 연결하기 버튼을 클릭하세요"
        width="70%"
        isOpen={codeLinkModalOpen}
        setIsOpen={setCodeLinkModalOpen}
        modalContent={
          <MyCodeList
            relatedCodeIds={codes.map((code) => code.codeId)}
            projectId={projectId}
            setModalOpen={setCodeLinkModalOpen}
          />
        }
      />
    </>
  );
};
