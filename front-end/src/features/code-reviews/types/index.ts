type SelectedReviews = {
  reviewId: number;
  userName: string; // 작성자 닉네임
  content: string; // 작성 내용
};

export type CodeVersion = {
  codeId: number;
  version: number;
  selectedReviews: SelectedReviews[];
};

type ReReviews = {
  reReviewId: number; // 코드 리리뷰(리뷰에 대한 리뷰) id
  userId: number; // 코드 리리뷰 작성자 id (0이면 익명)
  userName: string; // 리리뷰 남긴 사람 닉네임(빈 문자열이면 익명)
  liked: boolean; // 내가 좋아요 눌렀는지 여부
  likeCnt: number; // 코드 리리뷰 좋아요 수
  content: string; // 리리뷰 내용
  createdDate: Date; // 리리뷰 작성 시간
  modifiedDate: Date; // 리리뷰 수정 시간
};

export type Review = {
  reviewId: number; // 리뷰 id
  userId: number; // 리뷰 남긴 사람 id (0이면 익명)
  userName: string; // 리뷰 남긴 사람 닉네임(빈 문자열이면 익명)
  codeContent: string; // 리뷰 내용(코드)
  content: string; // 리뷰 내용(상세설명)
  language: string; // 코드 리뷰 언어
  lineNumbers: number[][]; // 코드 선택 부분 [[1, 2], [5, 8]..] [[시작, 끝], [시작, 끝]...]
  likeCnt: number; // 좋아요 수
  selected: number; // 선택 받은 횟수
  liked: boolean; // 내가 좋아요 눌렀는지 여부
  date: Date; // 리뷰 정보 작성(수정) 시간
  reReviews: ReReviews[];
};

export type CodeInfoResult = {
  codeId: number; // 코드 id
  title: string; // 코드 제목
  version: number; // 코드 버전
  date: Date; // 코드 정보 작성(수정) 시간
  likeCnt: number; // 좋아요 수
  reviewCnt: number; // 리뷰 수
  favoriteCnt: number; // 즐겨찾기 수
  githubUrl: string; // 코드를 불러올 github URL
  tags: string[]; // 등록한 태그 이름들
  userId: number; // 작성자 id
  userName: string; // 작성자 이름
  mine: boolean; // 내 코드인지 여부
  projectId: number; // 연결된 프로젝트 id(없을 경우 null)
  projectTitle: string; // 연결된 프로젝트 제목(없을 경우 null)
  content: string; // 코드 상세 설명
  liked: boolean; // 내가 좋아요 눌렀는지 여부
  favorite: boolean; // 내가 즐겨찾기 눌렀는지 여부
  versions: CodeVersion[];
  reviews: Review[];
};

type _links = {
  self: string;
  git: string;
  html: string;
};

export type Code = {
  name: string;
  path: string;
  sha: string;
  size: number;
  url: string;
  html_url: string;
  git_url: string;
  download_url: string;
  type: string;
  content: string;
  encoding: string;
  _links: _links;
};

type reReview = {
  reReviewId: number; // 코드 리리뷰 아이디
  userId: number; // 코드 리리뷰 작성자 id (0이면 익명)
  userName: string; // 리리뷰 남긴 사람 닉네임(빈 문자열이면 익명)
  likeCnt: number; // 코드 리리뷰 좋아요 수
  liked: boolean; // 좋아요 누른 여부
  content: string; // 코드 리리뷰 내용
  createdDate: Date; // 코드 리리뷰 작성 날짜
  modifiedDate: Date; // 코드 리리뷰 수정 날짜
};

export type codeForFeedbackModify = {
  codeId: number; // 코드 id
  version: number; // 코드 버전
  title: string; // 코드 제목
  likeCnt: number; // 좋아요 수
  liked: boolean; // 내가 좋아요 눌렀는지 여부
  favoriteCnt: number; // 즐겨찾기 수
  favorite: boolean; // 내가 즐겨찾기 눌렀는지 여부
  tags: string[]; // 등록한 태그 이름들
  userName: string; // 작성자 이름
  projectTitle: string; // 연결된 프로젝트 제목(없을 경우 null)
  projectId: number; // 연결된 프로젝트 id(없을 경우 null)
};

export type CodeFeedbackInfoResult = {
  githubUrl: string;
  reviewId: number; // 코드 리뷰 아이디
  userId: number; // 코드 리뷰 작성자 id (0이면 익명)
  userName: string; // 코드 리뷰 작성자 닉네임 (빈 문자열이면 익명)
  codeContent: string; // 코드 리뷰 코드 내용 (base64 인코딩된 문자열)
  content: string; // 코드 리뷰 상세 설명 내용
  lineNumbers: number[][]; // 코드 선택 부분 [[1, 2], [5, 8]..] [[시작, 끝], [시작, 끝]...]
  selected: number; // 선택 받은 횟수
  likeCnt: number; // 코드 리뷰 좋아요 수
  liked: boolean; // 코드 리뷰 좋아요 누른 여부
  date: Date; // 코드 리뷰 수정 날짜
  reReviews: reReview[];
  code: codeForFeedbackModify;
};
