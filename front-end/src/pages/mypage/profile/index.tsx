import { NextPage } from "next";

import { Profile } from "@/features/user";
import { Head } from "@/components/head";

const ProfilePage: NextPage = () => {
  return (
    <>
      <Head title="프로필 | 마이 페이지" description="개발새발 코드 리뷰" />
      <Profile />
    </>
  );
};

export default ProfilePage;
