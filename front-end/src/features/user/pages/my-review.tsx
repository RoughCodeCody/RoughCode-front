import { CommonLayout } from "../components";
import { ContentsWrapper } from "../components";
import { ProjectContainer } from "../components/common-layout/project-container";

export const MyReview = () => {
  return (
    <CommonLayout
      title="나의 리뷰"
      description="내가 남긴 리뷰를 확인할 수 있습니다"
    >
      <ContentsWrapper wrapperTitle="프로젝트">
        <ProjectContainer endPoint="feedback" />
      </ContentsWrapper>
      <ContentsWrapper wrapperTitle="코드">코드 리스트 자리</ContentsWrapper>
    </CommonLayout>
  );
};
