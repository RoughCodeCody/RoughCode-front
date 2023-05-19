import { useRouter } from "next/router";
import { useEffect } from "react";

import { useAsPathInfoStore } from "@/stores";
import { useSearchCriteriaStore } from "@/stores";

import { Head } from "@/components/head";

import { Projects } from "@/features/projects";

export default function Project() {
  const { asPath } = useRouter();
  const { asPathInfo, setAsPath } = useAsPathInfoStore();
  const { reset } = useSearchCriteriaStore();

  useEffect(() => {
    if (asPathInfo.currentAsPath !== asPath) {
      setAsPath(asPath, asPathInfo.currentAsPath);
    }
    if (
      (asPathInfo.prevAsPath === "/project" &&
        asPathInfo.currentAsPath === "/code-review") ||
      (asPathInfo.prevAsPath === "/code-review" &&
        asPathInfo.currentAsPath === "/project")
    ) {
      reset();
    }
    console.log(asPath);
    console.log(asPathInfo.currentAsPath);
  }, [asPath, asPathInfo.currentAsPath]);

  return (
    <>
      <Head title="프로젝트" description="개발새발 코드 리뷰" />
      <Projects />
    </>
  );
}
