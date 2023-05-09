import { NextPage, GetServerSideProps } from "next";
import { CodeDetail } from "@/features/code-reviews";

const CodeDetailPage: NextPage<{ codeId: string }> = ({ codeId }) => {
  return <CodeDetail codeId={codeId} />;
};

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  return {
    props: {
      codeId: params?.["code-id"],
    },
  };
};

export default CodeDetailPage;
