import { GetServerSideProps } from "next";

import { ProjectUpgrade } from "@/features/projects";

export default ProjectUpgrade;

export const getServerSideProps: GetServerSideProps = async ({ params }) => {
  const projectId = params?.["project-id"];
  const positiveIntRegex = /^[1-9]\d*$/;

  if (typeof projectId === "string" && positiveIntRegex.test(projectId)) {
    return { props: { projectId } };
  }

  return { notFound: true };
};
