export type Version = {
  projectId: number;
  version: number;
  date: Date;
  notice: string;
  selectedFeedbacks: {
    feedbackId: number;
    content: string; // 피드백 내용, 신고당해서 삭제된 피드백은 빈 문자열로 내보내집니다!!
  }[];
};

export type Feedback = {
  feedbackId: number;
  userId: number; // 피드백 남긴 사람 id (0이면 익명)
  userName: string; // 빈 문자열이면 익명
  content: string; // 피드백 내용, 신고당해서 삭제된 피드백은 빈 문자열로 내보내집니다!!
  like: number;
  selected: number; // 선택 받은 횟수
  liked: boolean; // 내가 좋아요 눌렀는지 여부
  date: Date;
};

export type Code = {
  codeId: number;
  title: string;
  tags: string[]; // 태그 이름들
};

export type ProjectInfoResult = {
  closed: boolean;
  code: Code[];
  content: string;
  date: Date;
  favorite: boolean;
  favoriteCnt: number;
  feedbackCnt: number;
  feedbacks: Feedback[];
  img: string;
  likeCnt: number;
  liked: boolean;
  mine: boolean;
  notice: string;
  tags: string[];
  title: string;
  url: string;
  userName: string;
  version: number;
  versions: Version[];
};
