import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
    // css 초기값 정의

    @font-face {
        font-family: 'Pretendard-Regular';
        src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
        font-weight: 400;
        font-style: normal;
    }
    :root {
        /* Color */
        --bg-color: #F8F9FA;
        --main-color: #319795;
        --font-color: #45474F;
        --white-color: #FFFFFF;
        --sub-one-color: #EFF8FF;
        --sub-two-color: #E2E8F0;
        --red-color: #EF0000;
        --orange-color: #F95A00;
        --shad-color: #D9D9D9;
    
        /* Navigation bar height */
        --nav-height: 65px;
      }
    
      *, *::before, *::after {
        box-sizing: border-box;
      }

  

      /* http://meyerweb.com/eric/tools/css/reset/ 
      v2.0 | 20110126
      License: none (public domain)
   */
   
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
     padding: 0;
     border: 0;
     font-size: 100%;
     font: inherit;
     vertical-align: baseline;
   }
   /* HTML5 display-role reset for older browsers */
   article, aside, details, figcaption, figure, 
   footer, header, hgroup, menu, nav, section {
     display: block;
   }
   body {
     line-height: 1;
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

    `;

export default GlobalStyles;
