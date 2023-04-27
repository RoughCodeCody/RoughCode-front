import { useEffect, useState } from "react";
import { TbArrowBarToUp } from "react-icons/tb";
import { Text } from "../text";
import { BackToTopWrapper } from "./style";

export const BackToTop = () => {
  const [showBtn, setShowBtn] = useState<boolean>(false);

  const scrollToTop = () => {
    // 브라우저인지 체크
    const isBrowser = () => typeof window !== "undefined";
    if (!isBrowser()) return;

    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  useEffect(() => {
    let scrollPosition = 0;

    const isScrollingDown = () => {
      let scrollingDown = false;
      let newScrollPosition = window.pageYOffset;

      if (newScrollPosition > scrollPosition) scrollingDown = true;
      scrollPosition = newScrollPosition;

      return scrollingDown;
    };

    const handleScroll = () => {
      if (!isScrollingDown() || window.pageYOffset === 0) setShowBtn(false);
      else setShowBtn(true);
    };

    window.addEventListener("scroll", handleScroll);
  }, []);

  return (
    <>
      {showBtn && (
        <BackToTopWrapper onClick={scrollToTop} show={showBtn}>
          <TbArrowBarToUp />
          <Text padding="0 0 0 0.3rem" pointer={true}>
            Back to top
          </Text>
        </BackToTopWrapper>
      )}
    </>
  );
};
