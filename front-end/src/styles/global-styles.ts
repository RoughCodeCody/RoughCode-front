import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
  @font-face {
    font-family: 'Pretendard-Regular';
    src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
    font-weight: 400;
    font-style: normal;
  }

  @font-face {
    font-family: 'NEXON Lv2 Gothic';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic.woff') format('woff');
    font-weight: normal;
    font-style: normal;
  }

  :root {
    /* Color */
    --bg-color: #F8F9FA;
    --main-color: #319795;
    --font-color: #45474F;
    --white-color: #FFFFFF;
    --black-color: #000000;
    --sub-one-color: #EFF8FF;
    --sub-two-color: #E2E8F0;
    --sub-three-color: #E9FBEF;
    --red-color: #EF0000;
    --orange-color: #F95A00;
    --shad-color: #D9D9D9;
    --banner-color: #68482F;
    --placeholder-color: #8E8E8E;
    --switch-color: #C1C1C1;
  }

  *, *::before, *::after {
    box-sizing: border-box;
  }

  html, body, div, span, applet, object, iframe,
  h1, h2, h3, h4, h5, h6, p, blockquote, pre,
  a, abbr, acronym, address, big, cite, code,
  del, dfn, em, img, ins, kbd, q, s, samp,
  small, strike, strong, sub, sup, tt, var,
  b, u, i, center,
  dl, dt, dd, ol, ul, li,
  fieldset, form, label, legend,
  table, caption, tbody, tfoot, thead, tr, th, td,
  article, aside, canvas, details, embed, 
  figure, figcaption, footer, header, hgroup, 
  menu, nav, output, ruby, section, summary,
  time, mark, audio, video {
    margin: 0;
    border: 0;
    padding: 0;
    vertical-align: baseline;
    color: var(--font-color);
    font: inherit;
    font-size: 100%;
  }

  article, aside, details, figcaption, figure, 
  footer, header, hgroup, menu, nav, section {
    display: block;
  }

  body {
    background-color: var(--bg-color);
    line-height: 1;
    font-family: 'NEXON Lv2 Gothic', 'Pretendard-Regular', Arial, Helvetica, sans-serif;
  }

  ol, ul {
    list-style: none;
  }

  blockquote, q {
    quotes: none;
  }

  blockquote:before, blockquote:after,
  q:before, q:after {
    content: '';
    content: none;
  }

  table {
    border-collapse: collapse;
    border-spacing: 0;
  }

  a {
    text-decoration: none;
  }
  
  button {
    border: none;
  }

  /* ProseMirror start */
  h1, h2, h3, h4, h5, h6, p, blockquote, pre,
  code,
  em, s,
  strong,
  ol, ul, li,
  mark {
    margin: revert;
    border: revert;
    padding: revert;
    vertical-align: revert;
    color: revert;
    font: revert;
    font-size: revert;
  }
  ol, ul {
    list-style: revert;
  }
  blockquote, q {
    quotes: revert;
  }
  blockquote:before, blockquote:after,
  q:before, q:after {
    content: revert;
  }
  /* ProseMirror end */
`;

export default GlobalStyles;
