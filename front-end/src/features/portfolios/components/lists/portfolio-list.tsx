import internal from "stream";
import { PortfolioCard } from "../portfolio-card";
import { PortfolioCardGrid } from "./style";

interface PortfolioCardProps {
  projectId: number;
  version: number;
  title: string;
  date?: Date;
  likeCnt: number;
  feedbackCnt: number;
  img: string;
  tagId: number[];
  introduction: string;
}

export const PortfolioList = () => {
  const dummy = {
    message: "프로젝트 목록 조회 성공",
    count: 9,
    result: {
      projects: [
        {
          projectId: 1,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 2,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 3,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 4,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 5,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 6,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 7,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 8,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
        {
          projectId: 9,
          version: 4,
          title: "개발새발", // 프로젝트 이름
          likeCnt: 13,
          feedbackCnt: 16,
          img: "https://picsum.photos/400/300",
          tagId: [1, 2, 3, 4, 5],
          introduction: "개발새발 프로젝트 입니다 하하",
        },
      ],
    },
  };

  return (
    <PortfolioCardGrid>
      {dummy.result.projects.map((project: PortfolioCardProps) => (
        <PortfolioCard
          projectId={project.projectId}
          version={project.version}
          title={project.title}
          likeCnt={project.likeCnt}
          feedbackCnt={project.feedbackCnt}
          img={project.img}
          tagId={project.tagId}
          introduction={project.introduction}
        />
      ))}
    </PortfolioCardGrid>
  );
};
