import { ReactNode, useRef, useState, MouseEvent } from "react";
import { DropdownArrow } from "../dropdown-arrow";
import { FlexDiv } from "../flexdiv";
import { Text } from "../text";
import { Btn } from "../btn";
import {
  AccordionContainer,
  AccordionHead,
  AccordionContentWrapper,
  Content,
} from "./style";

type AccordionProps = {
  title: string;
  hasBtn: boolean;
  btnText?: string;
  btnClickFunc?: () => void;
  children: ReactNode;
};

export const Accordion = ({
  title,
  hasBtn,
  btnText,
  btnClickFunc,
  children,
}: AccordionProps) => {
  const parentRef = useRef<HTMLDivElement>(null);
  const childRef = useRef<HTMLDivElement>(null);
  const [isOpen, setisOpen] = useState<boolean>(true);

  // 아코디언 접기/펼치기 핸들러 함수
  const handleOpenState = (e: MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    if (!parentRef.current || !childRef.current) return;

    if (parentRef.current.clientHeight > 0)
      parentRef.current.style.height = "0";
    else parentRef.current.style.height = `${childRef.current.clientHeight}px`;

    setisOpen((prev) => !prev);
  };

  return (
    <AccordionContainer>
      <AccordionHead>
        <FlexDiv>
          <Text padding="0 1rem 0 0" size="1.2rem">
            {title}
          </Text>
          {hasBtn && (
            <Btn text={btnText} fontSize="0.85rem" onClickFunc={btnClickFunc} />
          )}
        </FlexDiv>
        <FlexDiv pointer={true} onClick={(e) => handleOpenState(e)}>
          <Text size="0.9rem" padding="0 0.2rem" pointer={true}>
            {isOpen ? "접기" : "더보기"}
          </Text>
          <DropdownArrow size={16} isopen={isOpen.toString()} />
        </FlexDiv>
      </AccordionHead>

      <AccordionContentWrapper ref={parentRef}>
        <Content ref={childRef}>{children}</Content>
      </AccordionContentWrapper>
    </AccordionContainer>
  );
};
