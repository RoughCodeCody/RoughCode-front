import { useRouter } from "next/router";
import { useEffect } from "react";
import { BsPerson } from "react-icons/bs";

import { SidebarFallback } from "../project-feedbacks-sidebar/style";
import { useProjectFeedbacks } from "../../api";
import { useProjectFeedbackSelectionStore } from "../../stores";
import { ItemContainer, Item, ItemUserName, ItemContent } from "./style";

type ProjectFeedbacksSidebarContentProps = {
  projectId: string;
};

export const ProjectFeedbacksSidebarContent = ({
  projectId,
}: ProjectFeedbacksSidebarContentProps) => {
  // server states
  const projectFeedbacksQuery = useProjectFeedbacks({
    projectId: Number(projectId),
  });

  // client states
  const {
    selectedProjectFeedbackId,
    toggleProjectFeedbackSelection,
    resetProjectFeedbackSelection,
  } = useProjectFeedbackSelectionStore();

  // reset project feedback selection state on route change
  const dynamicRoute = useRouter().asPath;
  useEffect(
    () => resetProjectFeedbackSelection(),
    [dynamicRoute, resetProjectFeedbackSelection]
  );

  // fill initial selection status on each project feedback
  useEffect(() => {
    projectFeedbacksQuery.data
      ?.filter((feedback) => feedback.selected)
      .forEach((projectFeedback) => {
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

  if (projectFeedbacks.length === 0) {
    return (
      <SidebarFallback>
        프로젝트 피드백이 있으면 이곳에 나타날 거에요
      </SidebarFallback>
    );
  }
  return (
    <ItemContainer>
      {projectFeedbacks.map((projectFeedback, index) => (
        <Item
          key={`project-feedback-${projectId}-${index}`}
          onClick={() => handleClick(projectFeedback.feedbackId)}
          isSelected={selectedProjectFeedbackId.includes(
            projectFeedback.feedbackId
          )}
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
