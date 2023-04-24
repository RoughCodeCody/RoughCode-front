import { FlexDiv } from "@/components/elements";
import { BottomHeader } from "@/components/elements";
import { PortfolioCard } from "../components/portfolio-card";

export const Portfolios = () => {
  return (
    <FlexDiv direction="column">
      <BottomHeader locations={["프로젝트"]} menus={["a", "b", "c"]} />
      <FlexDiv>
        <PortfolioCard />
      </FlexDiv>
    </FlexDiv>
  );
};
