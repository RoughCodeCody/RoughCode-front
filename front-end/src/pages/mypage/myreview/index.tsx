import { NextPage } from "next";

import { MyPageContent } from "@/features/user";
import { Head } from "@/components/head";

const MyPostPage: NextPage = () => {
  return (
    <>
      <Head title="나의 리뷰 | 마이 페이지" description="개발새발 코드 리뷰" />

      <MyPageContent
        title={"나의 리뷰"}
        description={"나의 프로젝트와 코드 리뷰 요청을 확인할 수 있습니다"}
        // endPoint에 "" or "feedback" or "favorite"이 들어가면 됨
        endPoint={"feedback"}
      />
    </>
  );
};

export default MyPostPage;
