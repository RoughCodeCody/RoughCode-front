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
    <FlexDiv direction="column" gap="2rem">
      <BottomHeader
        locations={["마이 페이지"]}
        menus={["프로필", "내가 쓴 글", "나의 리뷰", "즐겨찾기"]}
      />
      <FlexDiv direction="column" width="70%" gap="3rem">
        <Title title={title} description={description} />
        {children}
      </FlexDiv>
    </FlexDiv>
  );
};
