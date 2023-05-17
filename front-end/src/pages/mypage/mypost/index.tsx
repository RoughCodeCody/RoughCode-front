import { NextPage } from "next";

import { MyPageContent } from "@/features/user";

const MyPostPage: NextPage = () => {
  return (
    <MyPageContent
      title={"나의 게시물"}
      description={"나의 프로젝트와 코드 리뷰 요청을 확인할 수 있습니다"}
      // endPoint에 "" or "feedback" or "favorite"이 들어가면 됨
      endPoint={""}
    />
  );
};

export default MyPostPage;
