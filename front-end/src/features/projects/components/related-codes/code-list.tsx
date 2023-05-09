import { useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";

import { Btn, FlexDiv } from "@/components/elements";
import { useMyCodeList } from "@/features/user/api/get-code-list";
import { queryClient } from "@/lib/react-query";

import { CodeListItem } from "./code-list-item";

interface MyCodeListProps {
  relatedCodeIds: number[];
}

export const MyCodeList = ({ relatedCodeIds }: MyCodeListProps) => {
  const { ref, inView } = useInView();
  const { status, data, fetchNextPage } = useMyCodeList({
    params: { size: 10 },
    config: {
      getNextPageParam: (lastPage) =>
        lastPage.nextPage === -1 ? undefined : lastPage.nextPage,
    },
  });
  const [selectedCodeIds, setSelectedCodeIds] = useState(relatedCodeIds);

  // 컴포넌트 언마운트 될 때 캐싱한 데이터 삭제
  useEffect(() => {
    queryClient.removeQueries(["codeList"]);
    return () => queryClient.removeQueries(["codeList"]);
  }, []);

  // 스크롤 트리거, 마지막 item에 inView ref 달아둠
  useEffect(() => {
    if (inView) fetchNextPage();
  }, [inView]);

  console.log("selectedCodeIds", selectedCodeIds);

  const handleCodeItemClick = (codeId: number) => {
    if (selectedCodeIds.includes(codeId)) {
      setSelectedCodeIds(selectedCodeIds.filter((id) => id !== codeId));
    } else {
      setSelectedCodeIds((prev) => [...prev, codeId]);
    }
  };

  return (
    <FlexDiv width="100%" direction="column" padding="0.5rem" gap="1rem">
      {status === "loading" && <>loading...</>}
      {status === "success" &&
        data?.pages[0].list.map((codeListItem, idx, list) => (
          <CodeListItem
            codeListItem={codeListItem}
            handleCodeItemClick={handleCodeItemClick}
            selected={selectedCodeIds.includes(codeListItem.codeId)}
            key={codeListItem.codeId}
            ref={idx === list.length - 1 ? ref : null}
          />
        ))}

      <Btn text="연결하기" onClickFunc={() => {}} />
    </FlexDiv>
  );
};
