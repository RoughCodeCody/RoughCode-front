import { CommonLayout } from "../components";
import { ContentsWrapper } from "../components";
import { ProjectContainer } from "../components/common-layout/project-container";

export const MyPost = () => {
  return (
    <CommonLayout
      title="내가 쓴 글"
      description="나의 프로젝트와 코드 리뷰 요청을 확인할 수 있습니다"
    >
      <ContentsWrapper wrapperTitle="프로젝트">
        <ProjectContainer endPoint="" />
      </ContentsWrapper>
      <ContentsWrapper wrapperTitle="코드">코드 리스트 자리</ContentsWrapper>
    </CommonLayout>
  );
};
