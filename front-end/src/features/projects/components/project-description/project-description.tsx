import { Accordion } from "@/components/elements";

type ProjectDescriptionProps = {
  content: string;
};

export const ProjectDescription = ({ content }: ProjectDescriptionProps) => {
  return (
    <Accordion title="프로젝트 상세" hasBtn={false}>
      <div>{content}</div>
    </Accordion>
  );
};
