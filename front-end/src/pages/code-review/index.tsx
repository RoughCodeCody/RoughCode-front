import { useRouter } from "next/router";
import { useEffect } from "react";

import { Head } from "@/components/head";
import { useAsPathInfoStore } from "@/stores";
import { useSearchCriteriaStore } from "@/stores";

import { NextPage } from "next";
import { CodeList } from "@/features/code-reviews";

const CodeListPage: NextPage = () => {
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
  }, [asPath, asPathInfo.currentAsPath]);

  return (
    <>
      <Head title="코드 리뷰" description="개발새발 코드 리뷰" />
      <CodeList />
    </>
  );
};

export default CodeListPage;
