import { NextPage, GetServerSideProps } from "next";
import { ProjectDetail } from "@/features/projects";
import { Head } from "@/components/head";

const ProjectDetailPage: NextPage<{ projectId: string }> = ({ projectId }) => {
  return (
    <>
      <Head title="프로젝트 상세 | 프로젝트" description="개발새발 코드 리뷰" />

      <ProjectDetail projectId={projectId} />
    </>
  );
};

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  return {
    props: {
      projectId: params?.["project-id"],
    },
  };
};

// // This function gets called at build time
// export async function getStaticPaths() {
//   // Call an external API endpoint to get posts
//   const res = await fetch('https://.../posts')
//   const posts = await res.json()

//   // Get the paths we want to pre-render based on posts
//   const paths = posts.map((post) => ({
//     params: { id: post.id },
//   }))

//   // We'll pre-render only these paths at build time.
//   // { fallback: false } means other routes should 404.
//   return { paths, fallback: false }
// }

export default ProjectDetailPage;
