import { CommonLayout } from "../components";
import { ContentsWrapper } from "../components";
import { ProjectContainer } from "../components/common-layout/project-container";

export const MyBookmark = () => {
  return (
    <CommonLayout
      title="즐겨찾기"
      description="내가 즐겨찾기한 게시물을 확인할 수 있습니다"
    >
      <ContentsWrapper wrapperTitle="프로젝트">
        <ProjectContainer endPoint="favorite" />
      </ContentsWrapper>
      <ContentsWrapper wrapperTitle="코드">코드 리스트 자리</ContentsWrapper>
    </CommonLayout>
  );
};
