import { CommonLayout } from "../components";
import { ContentsWrapper } from "../components";
import { ProjectContainer } from "../components";
import { CodeContainer } from "../components";

type MyPageContentProps = {
  title: string;
  description: string;
  endPoint: "" | "feedback" | "favorite";
};

export const MyPageContent = ({
  title,
  description,
  endPoint,
}: MyPageContentProps) => {
  return (
    <CommonLayout title={title} description={description}>
      <ContentsWrapper wrapperTitle="프로젝트">
        <ProjectContainer endPoint={endPoint} />
      </ContentsWrapper>
      <ContentsWrapper wrapperTitle="코드">
        <CodeContainer endPoint={endPoint} />
      </ContentsWrapper>
    </CommonLayout>
  );
};
