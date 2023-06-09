export type ProjectFeedback = {
  feedbackId: number;
  userId: number; // 피드백 남긴 사람 id (0이면 익명)
  userName: string; // 빈 문자열이면 익명
  content: string; // 피드백 내용, 신고당해서 삭제된 피드백은 빈 문자열로 내보내집니다!!
  like: number;
  selected: number; // 선택 받은 횟수
  liked: boolean; // 내가 좋아요 눌렀는지 여부
  date: Date;
};

export type ProjectInfoResult = {
  closed: boolean;
  code: RelatedCode[];
  content: string;
  date: Date;
  favorite: boolean;
  favoriteCnt: number;
  feedbackCnt: number;
  feedbacks: ProjectFeedback[];
  img: string;
  introduction: string;
  likeCnt: number;
  liked: boolean;
  mine: boolean;
  notice: string;
  tags: {
    tagId: number;
    name: string;
    cnt: number;
  }[];
  title: string;
  url: string;
  userName: string;
  version: number;
  versions: ProjectVersion[];
};

export type ProjectUpdateValues = {
  title: string;
  notice: string;
  introduction: string;
  content: string;
  url: string;
  projectId: number;
  selectedTagsId: number[] | null;
  selectedFeedbacksId: number[] | null;
};

export type ProjectVersion = {
  projectId: number;
  version: number;
  date: Date;
  notice: string;
  selectedFeedbacks: {
    feedbackId: number;
    content: string; // 피드백 내용, 신고당해서 삭제된 피드백은 빈 문자열로 내보내집니다!!
  }[];
};

export type RelatedCode = {
  codeId: number;
  title: string;
  tags: string[]; // 태그 이름들
};
