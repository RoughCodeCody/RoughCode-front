import { ProjectList } from "../components/lists";

import { FlexDiv } from "@/components/elements";
import { BottomHeader } from "@/components/elements";
import { WriteFloatBtn } from "@/components/elements";
import { BackToTop } from "@/components/elements";
import { Title } from "@/components/elements/title";
import { DropLabel, Search, SwitchDemo } from "@/features/search";

export const Projects = () => {
  const sortOptions = ["최신순", "좋아요순", "리뷰순"];

  return (
    <FlexDiv
      direction="column"
      align="center"
      justify="center"
      gap="4rem"
      overflow="hidden"
      height="100%"
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
      <Search whichTag="project" />
      <FlexDiv
        width="100%"
        height="100%"
        maxWidth="1440px"
        direction="column"
        gap="1.7rem"
      >
        <FlexDiv width="100%" justify="end" gap="2rem" paddingX="5rem">
          <SwitchDemo />
          <DropLabel sortOptions={sortOptions} type="project" />
        </FlexDiv>
        <ProjectList />
      </FlexDiv>
      <WriteFloatBtn navTo="/project/register" />
      <BackToTop />
    </FlexDiv>
  );
};
