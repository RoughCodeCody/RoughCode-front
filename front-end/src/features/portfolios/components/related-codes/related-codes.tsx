import { Accordion } from "@/components/accordion";
import { TagChipSub } from "@/components/elements";
import { MiniFeedbackItem } from "../mini-feedback-item";

export const RelatedCodes = () => {
  // 더미데이터
  const codeCnt = 1;
  const isMine = true;
  const codes = [
    {
      user: "닉네임",
      content:
        "깃허브 로그인 할 때 이런 식으로 리다이렉트 컴포넌트 이용하면 될까요?",
      tags: ["TypeScript"],
    },
  ];

  return (
    <Accordion
      title={`이 프로젝트와 연결된 코드 리뷰  ${codeCnt}개`}
      hasBtn={isMine}
      btnText="+ 새 코드 연결"
    >
      {codes.length !== 0 &&
        codes.map(({ user, content, tags }) => (
          <MiniFeedbackItem user={user} content={content} tags={tags} />
        ))}
    </Accordion>
  );
};
