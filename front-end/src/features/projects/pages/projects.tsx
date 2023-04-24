import { FlexDiv } from "@/components/elements";
import { BottomHeader } from "@/components/elements";
import { ProjectCard } from "../components/project-card";

export const Projects = () => {
  return (
    <FlexDiv direction="column">
      <BottomHeader locations={["프로젝트"]} menus={["a", "b", "c"]} />
      <FlexDiv>
        <ProjectCard />
      </FlexDiv>
    </FlexDiv>
  );
};
