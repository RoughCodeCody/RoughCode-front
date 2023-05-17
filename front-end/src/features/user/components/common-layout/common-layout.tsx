import { BottomHeader, FlexDiv, Title } from "@/components/elements";
import { ReactNode } from "react";

type CommonLayout = {
  title: string;
  description: string;
  children: ReactNode;
};

export const CommonLayout = ({
  title,
  description,
  children,
}: CommonLayout) => {
  return (
    <FlexDiv width="100%" direction="column" gap="4rem">
      <BottomHeader
        locations={["마이 페이지"]}
        menus={["프로필", "나의 게시물", "나의 리뷰", "즐겨찾기"]}
      />
      <FlexDiv direction="column" width="100%" gap="3rem">
        <FlexDiv width="100%" maxWidth="70%" justify="start">
          <Title title={title} description={description} />
        </FlexDiv>
        {children}
      </FlexDiv>
    </FlexDiv>
  );
};
