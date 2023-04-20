import "styled-components";

declare module "styled-components" {
  export interface DefaultTheme {
    color: {
      bg: string;
      main: string;
      font: string;
      white: string;
      subone: string;
      subtwo: string;
      red: string;
      orange: string;
    };
    boxShadow: {
      normal: string;
      main: string;
      orange: string;
    };
  }
}
