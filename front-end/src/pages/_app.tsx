import type { AppProps } from "next/app";
import GlobalStyles from "@/styles/global-styles";
import { wrapper } from "@/stores";

function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <GlobalStyles />
      <Component {...pageProps} />
    </>
  );
}

export default wrapper.withRedux(App);
