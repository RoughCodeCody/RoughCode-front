import { FlexDiv } from "@/components/elements";
import { BottomHeader } from "@/components/elements";
import { PortfolioCard } from "../components/portfolio-card";
import { KeywordSearch } from "@/components/search/keyword-search";
import { TagSearch } from "@/components/search/tag-search";
import { Search } from "@/components/search/search";
import { Title } from "@/components/title";
import { PortfolioList } from "../components/lists";

export const Portfolios = () => {
  return (
    <FlexDiv direction="column" align="center" gap="4rem" overflow="hidden">
      <BottomHeader locations={["프로젝트"]} />
      <FlexDiv
        width="100%"
        maxWidth="1112px"
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
      <PortfolioList />
    </FlexDiv>
  );
};
