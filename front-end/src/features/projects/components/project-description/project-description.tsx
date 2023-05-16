import { Accordion } from "@/components/elements";

type ProjectDescriptionProps = {
  content: string;
};

export const ProjectDescription = ({ content }: ProjectDescriptionProps) => {
  return (
    <Accordion title="프로젝트 상세" hasBtn={false}>
      {!content.length ? (
        "프로젝트 상세 설명이 없습니다"
      ) : (
        <div
          dangerouslySetInnerHTML={{ __html: content }}
          style={{ width: "100%" }}
        ></div>
      )}
    </Accordion>
  );
};
