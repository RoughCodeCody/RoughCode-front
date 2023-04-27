import {
  BackToTop,
  BottomHeader,
  FlexDiv,
  Title,
  WriteFloatBtn,
} from "@/components/elements";
import { CodeListItem } from "../components/code-list-item";
import { useEffect, useRef, useState } from "react";

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
  const nextCodelist = [
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

  // 무한스크롤 테스트용 함수
  const fakeFetch = (delay = 1000) =>
    new Promise((res) => setTimeout(res, delay));

  const infiniteScrollTarget = useRef<HTMLDivElement>(null);
  const [infiniteScrollState, setInfiniteScrollState] = useState({
    currentCodelist: [...codelist],
    isLoading: false,
  });

  // IntersectionObserver에서 감지되면 code list 추가로 요청할 콜백함수
  const fetchNewCodeList = async (nextCodeList: any) => {
    setInfiniteScrollState((prev) => ({
      ...prev,
      isLoading: true,
    }));
    await fakeFetch();
    setInfiniteScrollState((prev) => ({
      currentCodelist: [...prev.currentCodelist, ...nextCodeList],
      isLoading: false,
    }));
  };

  // 기존 code list의 70% viewport가 감지되면 fetchNewCodeList 콜백함수 실행
  useEffect(() => {
    let observer: IntersectionObserver;
    if (infiniteScrollTarget) {
      observer = new IntersectionObserver(
        async ([e], observer) => {
          if (e.isIntersecting) {
            observer.unobserve(e.target);
            await fetchNewCodeList(nextCodelist);
            observer.observe(e.target);
          }
        },
        { threshold: 0.7 }
      );
      observer.observe(infiniteScrollTarget.current as Element);
    }
    return () => observer.disconnect();
  }, [infiniteScrollTarget]);

  const { currentCodelist, isLoading } = infiniteScrollState;

  return (
    <>
      <FlexDiv direction="column" width="100%">
        <BottomHeader locations={["코드 리뷰"]} />
        <FlexDiv direction="column" width="70%" gap="1rem">
          <Title title="코드 리뷰" description="코드를 보고 리뷰해보세요" />
          {infiniteScrollState.currentCodelist?.map((codeListItem, idx) => (
            <CodeListItem codeListItem={codeListItem} key={idx} />
          ))}
          <div ref={infiniteScrollTarget}>
            {isLoading && <div>Loading...</div>}
          </div>
        </FlexDiv>
      </FlexDiv>
      <WriteFloatBtn navTo="/code-reviews/create" />
      <BackToTop />
    </>
  );
};
