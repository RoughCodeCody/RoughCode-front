import { encodeHTML } from "./utils";
// import dogAndDuck from "@/assets/dog-duck.png";
import dogAndDuck from "@/assets/dog-duck.svg";

export const StatCard3 = () => {
  // 더미데이터
  const name = "개발새발";
  const title = encodeHTML(`${name}의 스탯카드!`);
  const feedbackCnt = encodeHTML(`${3}`);
  const codeReviewCnt = encodeHTML(`${3}`);
  const includedFeedbackCnt = encodeHTML(`${3}`);
  const includedCodeReviewCnt = encodeHTML(`${3}`);
  const projectRefactorCnt = encodeHTML(`${3}`);
  const codeRefactorCnt = encodeHTML(`${3}`);

  return `
    <svg viewBox="0 0 500 216" xmlns="http://www.w3.org/2000/svg">
    <style>  
      .title {
        font: 800 15px 'Segoe UI', Ubuntu, Sans-Serif;
        fill: #319795;
        animation: fadeInAnimation 0.8s ease-in-out forwards;
      }
      .content {
        font: 800 10px 'Segoe UI', Ubuntu, Sans-Serif;
        fill: #45474F;
        animation: fadeInAnimation 0.8s ease-in-out forwards;
      }
    </style>
    <rect
        data-testid="card-bg"
        x="1%"
        y="0.5%"
        rx="15"
        height="99%"
        stroke="#319795"
        width="98%"
        fill="#ffffff"
      />

      <rect
      data-testid="card-bg"
      x="7%"
      y="20%"
      rx="5"
      height="70%"
      width="86%"
      fill="#EFF8FF"
    /> 

    <image href="@/assets/dog-duck.svg" width="10%" height="5%" />
    <text x="7%" y="13%" class="title">${title}</text>

    <text x="14%" y="32%" class="content">1. 프로젝트 피드백 횟수:</text>
    <text x="14%" y="42%" class="content">2. 코드 리뷰 횟수:</text>
    <text x="14%" y="52%" class="content">3. 반영된 프로젝트 피드백 수:</text>
    <text x="14%" y="62%" class="content">4. 반영된 코드 리뷰 수:</text>
    <text x="14%" y="72%" class="content">5. 프로젝트 리팩토링 횟수:</text>
    <text x="14%" y="82%" class="content">6. 코드 리팩토링 횟수:</text>

    <text x="64%" y="32%" class="content">${feedbackCnt}</text>
    <text x="64%" y="42%" class="content">${codeReviewCnt}</text>
    <text x="64%" y="52%" class="content">${includedFeedbackCnt}</text>
    <text x="64%" y="62%" class="content">${includedCodeReviewCnt}</text>
    <text x="64%" y="72%" class="content">${projectRefactorCnt}</text>
    <text x="64%" y="82%" class="content">${codeRefactorCnt}</text>

  </svg>
    `;
};
