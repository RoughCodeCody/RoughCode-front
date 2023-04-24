import { BottomHeader } from "@/components/bottom-header";
import { FlexDiv } from "@/components/elements";
import { Title } from "@/components/title";
import { CodeListItem } from "../components/code-list-item";
import { WriteFloatBtn } from "@/components/write-float-btn";

export const CodeList = () => {
  // 더미데이터
  const codelist = [
    {
      codesId: 1,
      title: "제목 블라블라 코드 좀 봐주세요",
      createdDate: new Date(),
      modifiedDate: new Date(),
      like: 10,
      favorite: 4,
      reviewCnt: 5,
      tag: ["TypeScript"],
      userName: "닉네임",
    },
    {
      codesId: 1,
      title: "제목 블라블라 코드 좀 봐주세요",
      createdDate: new Date(),
      modifiedDate: new Date(),
      like: 10,
      favorite: 4,
      reviewCnt: 5,
      tag: ["TypeScript"],
      userName: "닉네임",
    },
    {
      codesId: 1,
      title: "제목 블라블라 코드 좀 봐주세요",
      createdDate: new Date(),
      modifiedDate: new Date(),
      like: 10,
      favorite: 4,
      reviewCnt: 5,
      tag: ["TypeScript"],
      userName: "닉네임",
    },
    {
      codesId: 1,
      title: "제목 블라블라 코드 좀 봐주세요",
      createdDate: new Date(),
      modifiedDate: new Date(),
      like: 10,
      favorite: 4,
      reviewCnt: 5,
      tag: ["TypeScript"],
      userName: "닉네임",
    },
  ];

  return (
    <>
      <FlexDiv direction="column" width="100%">
        <BottomHeader locations={["코드 리뷰"]} />
        <FlexDiv direction="column" width="70%" gap="1rem">
          <Title title="코드 리뷰" description="코드를 보고 리뷰해보세요" />
          {codelist?.map((codeListItem, idx) => (
            <CodeListItem codeListItem={codeListItem} key={idx} />
          ))}
        </FlexDiv>
      </FlexDiv>
      <WriteFloatBtn navTo="/code-reviews/create" />
    </>
  );
};
