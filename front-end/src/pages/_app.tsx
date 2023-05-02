import type { AppProps } from "next/app";
import { ThemeProvider } from "styled-components";

import { AppProvider } from "@/providers/app";
import GlobalStyles from "@/styles/global-styles";
import { theme } from "@/styles/theme";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <ThemeProvider theme={theme}>
        <GlobalStyles />
        <AppProvider>
          <Component {...pageProps} />
        </AppProvider>
      </ThemeProvider>
    </>
  );
}
