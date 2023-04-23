import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { Notice } from "../components/notice";
import { PortfolioInfo } from "../components/portfolio-info";
import { VersionsInfo } from "../components/versions-info";
import { PortfolioDescription } from "../components/portfolio-description";

export const PortfolioDetail = () => {
  return (
    <FlexDiv direction="column">
      <Notice />

      <WhiteBoxNoshad width="65%" padding="2.25rem">
        <PortfolioInfo />
        <VersionsInfo />
        <PortfolioDescription />
      </WhiteBoxNoshad>
    </FlexDiv>
  );
};
