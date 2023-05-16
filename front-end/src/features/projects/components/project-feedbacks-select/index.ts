import { useRouter } from "next/router";
import { BsPerson } from "react-icons/bs";
import { useEffect } from "react";
import {
  SidebarContainer,
  SidebarFallback,
  ItemContainer,
  Item,
  ItemUserName,
  ItemContent,
} from "@/components/elements";
import { useIdArrStore } from "@/stores/id-arr";
import { useProjectFeedbacksForUpdate } from "../../api/get-project-feedbacks-for-update";
import { ProjectFeedbacksSidebarContent } from "../project-feedbacks-sidebar-content";

export const ProjectFeedbacksSelect = ({ projectId }: { projectId: string }) => {
  return (
    <SidebarContainer>
      {projectId ? (
        <ProjectFeedbacksSidebarContent projectId={projectId} />
      ) : (
        <SidebarFallback>프로젝트 피드백이 있으면 이곳에 나타날 거에요</SidebarFallback>
      )}
    </SidebarContainer>
  );
};

const ProjectFeedbacksSelectContent = ({ projectId }: { projectId: string }) => {
  // server states
  const projectFeedbacksQuery = useProjectFeedbacks({ projectId });

  // client states
  const {
    selectedProjectFeedbackId,
    toggleProjectFeedbackSelection,
    resetProjectFeedbackSelection,
  } = useProjectFeedbackSelectionStore();

  // reset project feedback selection state on route change
  const dynamicRoute = useRouter().asPath;
  useEffect(() => resetProjectFeedbackSelection(), [dynamicRoute, resetProjectFeedbackSelection]);

  // fill initial selection status on each project feedback
  useEffect(() => {
    projectFeedbacksQuery.data?.forEach((projectFeedback) => {
      toggleProjectFeedbackSelection(projectFeedback.feedbackId);
    });
  }, [projectFeedbacksQuery.data, toggleProjectFeedbackSelection]);

  // isLoading
  if (projectFeedbacksQuery.isLoading) {
    return <>Loading...</>;
  }

  // isError
  if (projectFeedbacksQuery.isError) {
    return <>Error!</>;
  }

  // server data & click function definition
  const projectFeedbacks = projectFeedbacksQuery.data;
  const handleClick = (projectFeedbackId: number) => {
    toggleProjectFeedbackSelection(projectFeedbackId);
  };

  return (
    <ItemContainer>
      {projectFeedbacks.map((projectFeedback, index) => (
        <Item
          key={`project-feedback-${projectId}-${index}`}
          onClick={() => handleClick(projectFeedback.feedbackId)}
          isSelected={selectedProjectFeedbackId.includes(projectFeedback.feedbackId)}
        >
          <ItemUserName>
            <BsPerson color="var(--font-color)" />
            {projectFeedback.userName}
          </ItemUserName>
          <ItemContent>{projectFeedback.content}</ItemContent>
        </Item>
      ))}
    </ItemContainer>
  );
};
