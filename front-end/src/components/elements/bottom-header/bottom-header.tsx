import { FlexDiv } from "../flexdiv";
import { Text } from "../text";
import Link from "next/link";
import { useRouter } from "next/router";

interface BottomHeaderProps {
  locations?: string[];
  menus?: string[];
}

export const BottomHeader = ({ locations, menus }: BottomHeaderProps) => {
  const router = useRouter();
  const routerPath = router.asPath;
  const currentPage = routerPath.includes("/mypage/profile")
    ? "프로필"
    : routerPath.includes("/mypage/mypost")
    ? "내가 쓴 글"
    : routerPath.includes("/mypage/myreview")
    ? "나의 리뷰"
    : "즐겨찾기";
  return (
    <FlexDiv width="100vw" height="78px" color="font">
      <FlexDiv
        width="80%"
        // maxWidth="1280px"
        justify={menus ? "space-between" : "start"}
      >
        <FlexDiv gap="1rem">
          {locations?.map((location) => (
            <Text size="1.2rem" color="bg" bold={true} key={location}>
              {location}
            </Text>
          ))}
        </FlexDiv>

        <FlexDiv gap="3rem">
          {menus?.map((menu) => (
            <Link
              href={
                menu === "프로필"
                  ? "/mypage/profile"
                  : menu === "내가 쓴 글"
                  ? "/mypage/mypost"
                  : menu === "나의 리뷰"
                  ? "/mypage/myreview"
                  : "/mypage/mybookmark"
              }
            >
              <Text
                pointer={true}
                size="1.2rem"
                color={currentPage === menu ? "main" : "bg"}
                bold={true}
                key={menu}
              >
                {menu}
              </Text>
            </Link>
          ))}
        </FlexDiv>
      </FlexDiv>
    </FlexDiv>
  );
};
