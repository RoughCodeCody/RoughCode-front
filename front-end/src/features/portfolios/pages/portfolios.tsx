import { FlexDiv } from "@/components/elements";
import { BottomHeader } from "@/components/elements";
import { Search } from "@/components/search/search";
import { Title } from "@/components/title";
import { PortfolioList } from "../components/lists";
import { DropLabel } from "@/components/drop-label";
import { SwitchDemo } from "@/components/switch";

export const Portfolios = () => {
  return (
    <FlexDiv
      direction="column"
      align="center"
      justify="center"
      gap="4rem"
      overflow="hidden"
    >
      <BottomHeader locations={["프로젝트"]} />
      <FlexDiv
        width="100%"
        maxWidth="70rem"
        direction="column"
        align="center"
        gap="1rem"
      >
        <Title
          title="프로젝트"
          description="우리의 토이 프로젝트를 구경해 보세요"
        />
      </FlexDiv>
      <Search />
      <FlexDiv width="100%" maxWidth="1440px" direction="column" gap="1.7rem">
        <FlexDiv width="100%" justify="end" gap="2rem" paddingX="5rem">
          <SwitchDemo />
          <DropLabel options={["최신순", "좋아요순", "리뷰순"]} />
        </FlexDiv>
        <PortfolioList />
      </FlexDiv>
    </FlexDiv>
  );
};
