import { ProjectFeedbacksSidebarContent } from "../project-feedbacks-sidebar-content";
import { SidebarContainer, SidebarFallback } from "./style";

type ProjectFeedbacksSidebarProps = {
  projectId?: string;
  versionUp: string;
};

export const ProjectFeedbacksSidebar = ({
  projectId,
  versionUp,
}: ProjectFeedbacksSidebarProps) => {
  return (
    <SidebarContainer>
      {projectId ? (
        <ProjectFeedbacksSidebarContent
          projectId={projectId}
          versionUp={versionUp}
        />
      ) : (
        <SidebarFallback>
          프로젝트 피드백이 있으면 이곳에 나타날 거에요
        </SidebarFallback>
      )}
    </SidebarContainer>
  );
};
