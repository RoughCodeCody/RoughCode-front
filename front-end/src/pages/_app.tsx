import type { AppProps } from "next/app";
import { ThemeProvider } from "styled-components";
<<<<<<< HEAD
=======
import { theme } from "@/styles/theme";
// import { wrapper } from "@/stores";
>>>>>>> dev-front

import { AppProvider } from "@/providers/app";
import GlobalStyles from "@/styles/global-styles";
import { theme } from "@/styles/theme";

export default function App({ Component, pageProps }: AppProps) {
export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <ThemeProvider theme={theme}>
        <GlobalStyles />
        <AppProvider>
          <Component {...pageProps} />
        </AppProvider>
        <GlobalStyles />
        <AppProvider>
          <Component {...pageProps} />
        </AppProvider>
      </ThemeProvider>
    </>
  );
}
<<<<<<< HEAD
=======

export default App;
>>>>>>> dev-front
