export type ProjectsList = {
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

export type CodesList = {
  codesId: number;
  title: String;
  createdDate: Date;
  createdDate: Date;
  like: number;
  favorite: number;
  reviewCnt: number;
  tag: Array<String>;
  userName: String;
  liked: boolean;
};

export type Tag = {
  tagId: number;
  name: string;
  cnt: number;
}[];
