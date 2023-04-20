import { DefaultTheme } from "styled-components";

export const theme: DefaultTheme = {
  color: {
    bg: "#F8F9FA",
    main: "#319795",
    font: "#45474F",
    white: "#FFFFFF",
    subone: "#EFF8FF",
    subtwo: "#E2E8F0",
    red: "#EF0000",
    orange: "#F95A00",
  },
  boxShadow: {
    normal: "0 0 20px 0 rgb(0 0 0 / 10%)",
    main: "0 0 20px 0 #319795",
    orange: "0 0 20px 0 #F95A00",
  },
  MIXINS: {
    // flex
    flexBox: (direction = "row", justify = "center", align = "center") => `
    display: flex;
    flex-direction: ${direction};
    justify-content: ${justify};
    align-items: ${align};
  `,

    //   // positions
    //   positionCenter: (type = "absolute") => {
    //     if (type === "absolute" || type === "fixed")
    //       return `
    //       position: ${type};
    //       left: 50%;
    //       top: 50%;
    //       transform: translate(-50%, -50%);
    //     `;
    //     return;
    //   },
    // },
  },
};

const customMediaQuery = (maxWidth: number): string =>
  `@media (max-width: ${maxWidth}px)`;

export const media = {
  custom: customMediaQuery,
  pc: customMediaQuery(1440),
  tablet: customMediaQuery(768),
  mobile: customMediaQuery(576),
};
