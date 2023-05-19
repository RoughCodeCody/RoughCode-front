import { NextPage, GetServerSideProps } from "next";
import { CodeDetail } from "@/features/code-reviews";
import { Head } from "@/components/head";

const CodeDetailPage: NextPage<{ codeId: string }> = ({ codeId }) => {
  return (
    <>
      <Head title="코드 리뷰 상세" description="개발새발 코드 리뷰" />
      <CodeDetail codeId={codeId} />;
    </>
  );
};

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  return {
    props: {
      codeId: params?.["code-id"],
    },
  };
};

export default CodeDetailPage;
