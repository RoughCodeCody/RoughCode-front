import { useEffect, useState, Dispatch, SetStateAction } from "react";
import { useInView } from "react-intersection-observer";

import { Btn, FlexDiv, Spinner } from "@/components/elements";
import { useMyProjectList } from "@/features/user/api";

import { useConnectProjectToCode } from "../../api";
import { ProjectListItem } from "./project-list-item";

interface MyProjectListProps {
  relatedProjectId: number;
  codeId: number;
  setModalOpen: Dispatch<SetStateAction<boolean>>;
}

export const MyProjectList = ({
  relatedProjectId,
  codeId,
  setModalOpen,
}: MyProjectListProps) => {
  const { ref, inView } = useInView();

  // 프로젝트에 연결할 코드 id
  const [selectedProjectId, setSelectedProjectId] = useState(relatedProjectId);

  // 내 프로젝트 목록 가져오기
  const { status, data, fetchNextPage } = useMyProjectList({
    params: { size: 10 },
    config: {
      getNextPageParam: (lastPage) =>
        lastPage.nextPage === -1 ? undefined : lastPage.nextPage,
    },
  });

  // 코드에 프로젝트 연결
  const connectProjectToCodeQuery = useConnectProjectToCode();
  const connectProjectToCode = () => {
    connectProjectToCodeQuery.mutate({
      codeId,
      data: { projectId: selectedProjectId },
    });
    setModalOpen(false);
  };

  // 컴포넌트 언마운트 될 때 캐싱한 데이터 삭제
  // useEffect(() => {
  //   queryClient.removeQueries(["myProjectList"]);
  //   return () => queryClient.removeQueries(["myProjectList"]);
  // }, []);

  // 스크롤 트리거, 마지막 item에 inView ref 달아둠
  useEffect(() => {
    if (inView) fetchNextPage();
  }, [inView]);

  return (
    <FlexDiv width="100%" direction="column" padding="0.5rem" gap="1rem">
      {status === "loading" && <Spinner size={500} />}
      {status === "success" &&
        data?.pages[0].list.map((projectListItem, idx, list) => (
          <FlexDiv
            width="100%"
            key={projectListItem.projectId}
            ref={idx === list.length - 1 ? ref : null}
          >
            <ProjectListItem
              projectListItem={projectListItem}
              setSelectedProjectId={setSelectedProjectId}
              selected={selectedProjectId === projectListItem.projectId}
            />
          </FlexDiv>
        ))}

      <Btn text="연결하기" onClickFunc={connectProjectToCode} />
    </FlexDiv>
  );
};
