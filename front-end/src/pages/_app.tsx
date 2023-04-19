import type { AppProps } from "next/app";
import GlobalStyles from "@/styles/global-styles";
import { ThemeProvider } from "styled-components";
import { theme } from "@/styles/theme";
import { wrapper } from "@/stores";

function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <GlobalStyles />
      <ThemeProvider theme={theme}>
        <Component {...pageProps} />
      </ThemeProvider>
    </>
  );
}

export default wrapper.withRedux(App);
