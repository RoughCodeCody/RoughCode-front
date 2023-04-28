export type ProjectList = {
  projectId: number;
  version: number;
  title: String;
  date: Date;
  likeCnt: number;
  feedbackCnt: number;
  img: String;
  tags: Array<String>;
  introduction: String;
  closed: Boolean;
};

export type CodeList = {
  codesId: number;
  title: String;
  createdDate: Date;
  modifiedDate: Date;
  like: number;
  favorite: number;
  reviewCnt: number;
  tag: Array<String>;
  userName: String;
  liked: boolean;
};
