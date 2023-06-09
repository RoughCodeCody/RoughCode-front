import type { AppProps } from "next/app";
import { ThemeProvider } from "styled-components";

import { MainLayout } from "@/components/layout";
import { AppProvider } from "@/providers/app";
import GlobalStyles from "@/styles/global-styles";
import { theme } from "@/styles/theme";

import "../features/code-editor/editor.css";

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <ThemeProvider theme={theme}>
        <GlobalStyles />
        <AppProvider>
          <MainLayout>
            <Component {...pageProps} />
          </MainLayout>
        </AppProvider>
      </ThemeProvider>
    </>
  );
}
