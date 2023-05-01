import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { FeedbackRegister, Feedbacks } from "@/components/feedback";

import { useProjectInfo } from "../api/get-project-info";
import { Notice } from "../components/notice";
import { ProjectDescription } from "../components/project-description";
import { ProjectInfo } from "../components/project-info";
import { RelatedCodes } from "../components/related-codes";
import { VersionsInfo } from "../components/versions-info";

type ProjectDetailProps = {
  projectId: string;
};

export const ProjectDetail = ({ projectId }: ProjectDetailProps) => {
  // 더미데이터
  // const feedbacks = [
  //   {
  //     user: "닉네임",
  //     isApplied: true,
  //     content: "즐겨찾기 기능이 있으면 좋을 거 같아요",
  //     isMine: false,
  //     isLiked: false,
  //     likeCnt: 13,
  //     createdAt: "23.04.17 13:40",
  //   },
  //   {
  //     user: "닉네임",
  //     isApplied: true,
  //     content: "로그아웃 할 때 확인 한 번 해 주는 게 좋을 듯",
  //     isMine: false,
  //     isLiked: false,
  //     likeCnt: 13,
  //     createdAt: "23.04.17 13:40",
  //   },
  //   {
  //     user: "이건내꺼",
  //     isApplied: false,
  //     content: "순서는 반영된 피드백, 그다음 내가 쓴 피드백, 그다음 시간 순",
  //     isMine: true,
  //     isLiked: false,
  //     likeCnt: 13,
  //     createdAt: "23.04.17 13:40",
  //   },
  //   {
  //     user: "익명12",
  //     isApplied: false,
  //     content: "버전1의 피드백",
  //     isMine: false,
  //     isLiked: false,
  //     likeCnt: 13,
  //     createdAt: "23.04.17 13:40",
  //   },
  // ];

  const { isLoading, data, isSuccess, isError } = useProjectInfo({ projectId });
  console.log(data);

  if (isLoading) return <>loading</>;

  const {
    closed,
    codeId,
    content,
    date,
    favorite,
    favoriteCnt,
    feedbackCnt,
    feedbacks,
    img,
    likeCnt,
    liked,
    notice,
    tags,
    title,
    url,
    version,
    versions,
  } = data.result;

  return (
    <FlexDiv direction="column" gap="3rem" padding="2rem 0">
      <Notice
        notice={notice}
        version={version}
        date={date}
        versions={versions}
      />

      <WhiteBoxNoshad width="65%" padding="2.25rem">
        <ProjectInfo data={data.result} />
        <VersionsInfo versions={versions} curVersionId={projectId} />
        <ProjectDescription content={content} />
        <RelatedCodes />
      </WhiteBoxNoshad>

      <FeedbackRegister type="feedback" />
      <Feedbacks feedbacks={feedbacks} />
    </FlexDiv>
  );
};

// export const getServerSideProps: GetServerSideProps<{
//   projectInfo: {};
//   projectId: string;
// }> = async ({ params }) => {
//   const projectId =
//     typeof params.["project-id"] === "string" ? params["project-id"] : "";

//   const projectInfo = await getProjectInfo({ projectId });

//   return {
//     props: {
//       projectInfo,
//       projectId,
//     },
//   };

// export const getServerSideProps: GetServerSideProps = async (ctx) => {
//   console.log("serverside", ctx);
//   return {
//     props: {
//       ctx,
//     },
//   };
// };

// };
// if (projectInfoQuery.data) return {
//   props: {
//     projectInfoQuery.data,
//   },
// };
// };

// export const getStaticProps: GetStaticProps = async (context) => {
//   const id = context.params?.id as string;
//   const queryClient = new QueryClient();

//   await queryClient.prefetchQuery(["getPokemon", id],
//     () => fetchPokemon(id)
//   );

//   return {
//     props: {
//       dehydratedState: dehydrate(queryClient)
//     }
//   };
// };

// export const getStaticPaths: GetStaticPaths = async () => {
//   return {
//     paths: [],
//     fallback: "blocking"
//   };
// };

// export const getStaticProps: GetStaticProps = async ({ params }) => {
//   const projectId =
//     typeof params?.["project-id"] === "string" ? params["project-id"] : "";

//   const projectInfo = getProjectInfo({ projectId });

//   return {
//     props: {
//       projectInfo,
//     },
//   };
// };

// // Generates `/posts/1` and `/posts/2`
// export async function getStaticPaths() {
//   return {
//     paths: [{ params: { id: "1" } }, { params: { id: "2" } }],
//     fallback: "blocking", // can also be true or 'blocking'
//   };
// }
