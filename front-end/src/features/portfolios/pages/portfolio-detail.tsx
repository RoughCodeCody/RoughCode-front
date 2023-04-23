import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { Notice } from "../components/notice";
import { PortfolioInfo } from "../components/portfolio-info";
import { VersionsInfo } from "../components/versions-info";
import { PortfolioDescription } from "../components/portfolio-description";
import { RelatedCodes } from "../components/related-codes";
import { FeedbackResister } from "@/components/feedback-resister";

export const PortfolioDetail = () => {
  return (
    <FlexDiv direction="column" gap="3rem">
      <Notice />

      <WhiteBoxNoshad width="65%" padding="2.25rem">
        <PortfolioInfo />
        <VersionsInfo />
        <PortfolioDescription />
        <RelatedCodes />
      </WhiteBoxNoshad>

      <FeedbackResister type="feedback" />
    </FlexDiv>
  );
};
