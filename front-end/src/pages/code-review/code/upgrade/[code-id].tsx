import { GetServerSideProps } from "next";

import { CodeUpgrade } from "@/features/code-reviews";

export default CodeUpgrade;

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  const codeId = params?.["code-id"];
  const positiveIntRegex = /^[1-9]\d*$/;

  if (typeof codeId === "string" && positiveIntRegex.test(codeId)) {
    return { props: { codeId } };
  }

  return { notFound: true };
};
