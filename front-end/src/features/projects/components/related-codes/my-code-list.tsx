import { useEffect, useState, Dispatch, SetStateAction } from "react";
import { useInView } from "react-intersection-observer";

import { Btn, FlexDiv, Spinner } from "@/components/elements";
import { useMyCodeList } from "@/features/user/api/get-code-list";
import { queryClient } from "@/lib/react-query";

import { useConnectCodeToProject } from "../../api";
import { CodeListItem } from "./code-list-item";

interface MyCodeListProps {
  relatedCodeIds: number[];
  projectId: string;
  setModalOpen: Dispatch<SetStateAction<boolean>>;
}

export const MyCodeList = ({
  relatedCodeIds,
  projectId,
  setModalOpen,
}: MyCodeListProps) => {
  const { ref, inView } = useInView();

  // 프로젝트에 연결할 코드 id 리스트
  const [selectedCodeIds, setSelectedCodeIds] = useState(relatedCodeIds);
  console.log("selectedCodeIds", selectedCodeIds);

  // 내 코드 목록 가져오기
  const { status, data, fetchNextPage } = useMyCodeList({
    params: { size: 10 },
    config: {
      getNextPageParam: (lastPage) =>
        lastPage.nextPage === -1 ? undefined : lastPage.nextPage,
    },
  });

  // 프로젝트에 코드 연결
  const connectCodeToProjectQuery = useConnectCodeToProject();
  const connectCodeToProject = () => {
    connectCodeToProjectQuery.mutate({
      projectId: Number(projectId),
      data: selectedCodeIds,
    });
    setModalOpen(false);
  };

  // 컴포넌트 언마운트 될 때 캐싱한 데이터 삭제
  useEffect(() => {
    queryClient.removeQueries(["codeList"]);
    return () => queryClient.removeQueries(["codeList"]);
  }, []);

  // 스크롤 트리거, 마지막 item에 inView ref 달아둠
  useEffect(() => {
    if (inView) fetchNextPage();
  }, [inView]);

  const handleCodeItemClick = (codeId: number) => {
    if (selectedCodeIds.includes(codeId)) {
      setSelectedCodeIds(selectedCodeIds.filter((id) => id !== codeId));
    } else {
      setSelectedCodeIds((prev) => [...prev, codeId]);
    }
  };

  return (
    <FlexDiv width="100%" direction="column" padding="0.5rem" gap="1rem">
      {status === "loading" && <Spinner size={500} />}
      {status === "success" &&
        data?.pages[0].list.map((codeListItem, idx, list) => (
          <FlexDiv
            width="100%"
            key={codeListItem.codeId}
            ref={idx === list.length - 1 ? ref : null}
          >
            <CodeListItem
              codeListItem={codeListItem}
              handleCodeItemClick={handleCodeItemClick}
              selected={selectedCodeIds.includes(codeListItem.codeId)}
            />
          </FlexDiv>
        ))}

      <Btn text="연결하기" onClickFunc={connectCodeToProject} />
    </FlexDiv>
  );
};
