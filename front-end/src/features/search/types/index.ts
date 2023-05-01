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
  modifiedDate: Date;
  like: number;
  favorite: number;
  reviewCnt: number;
  tag: Array<String>;
  userName: String;
  liked: boolean;
};

type tagResult = {
  tagId: number;
  name: string;
  cnt: number;
};

export type Tag = {
  count: number;
  message: string;
  result: tagResult[];
  // tagId: number;
  // name: String;
  // cnt: number;
};
