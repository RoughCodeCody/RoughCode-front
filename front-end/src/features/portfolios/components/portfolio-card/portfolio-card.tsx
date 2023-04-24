import { useState, useRef } from "react";

import Image from "next/image";
import { FlexDiv } from "@/components/elements";
import { Text } from "@/components/elements";
import {
  ThumbnailContainer,
  ImageContainer,
  ProjectCardDescription,
  TagContainer,
} from "./style";
import { TagChipSub } from "@/components/elements";

export const PortfolioCard = () => {
  const [isDown, setIsDown] = useState(false);
  const [startX, setStartX] = useState<number | null>(null);
  const [scrollLeft, setScrollLeft] = useState<number>(0);
  const tagContainerRef = useRef<HTMLDivElement>(null);

  const handleMouseDown = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsDown(true);
    if (tagContainerRef.current) {
      setStartX(e.pageX - tagContainerRef.current.offsetLeft);
      setScrollLeft(tagContainerRef.current.scrollLeft);
    }
  };

  const handleMouseLeave = () => {
    setIsDown(false);
  };

  const handleMouseUp = () => {
    setIsDown(false);
  };

  const handleMouseMove = (e: React.MouseEvent<HTMLDivElement>) => {
    if (!isDown) return;
    e.preventDefault();
    if (tagContainerRef.current && startX !== null) {
      const x = e.pageX - tagContainerRef.current.offsetLeft;
      const walk = x - startX;
      tagContainerRef.current.scrollLeft = scrollLeft - walk;
    }
  };

  const handleWheel = (event: React.WheelEvent<HTMLDivElement>) => {
    event.preventDefault();
    if (tagContainerRef.current) {
      tagContainerRef.current.scrollLeft += event.deltaY;
    }
  };

  return (
    <FlexDiv
      width="24rem"
      height="30rem"
      direction="column"
      border="solid 1px var(--shad-color)"
      radius="32px"
      padding="2rem 0 1rem 0"
    >
      <ThumbnailContainer>
        <ImageContainer>
          <Image
            src="https://picsum.photos/400/300"
            width={343.6}
            height={234.4}
            alt={"PJT"}
            object-fit="cover"
          />
        </ImageContainer>
      </ThumbnailContainer>

      {/* 분리선 */}
      <FlexDiv
        width="100%"
        height="0"
        border="solid 1px var(--shad-color)"
      ></FlexDiv>

      <FlexDiv
        width="100%"
        height="35%"
        paddingX="1.8rem"
        direction="column"
        justify="center"
        align="start"
      >
        <FlexDiv paddingY="0.5rem">
          <Text size="1.3rem" bold={true}>
            title
          </Text>
        </FlexDiv>
        <FlexDiv
          width="100%"
          height="100%"
          direction="column"
          justify="space-between"
          align="start"
        >
          <ProjectCardDescription>
            descriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescriptdescript
          </ProjectCardDescription>
          <TagContainer
            ref={tagContainerRef}
            onMouseDown={handleMouseDown}
            onMouseLeave={handleMouseLeave}
            onMouseUp={handleMouseUp}
            onMouseMove={handleMouseMove}
            onWheel={handleWheel}
          >
            <TagChipSub tag="Javascript" />
            <TagChipSub tag="Java" />
            <TagChipSub tag="React" />
            <TagChipSub tag="Spring" />
            <TagChipSub tag="Javascript" />
            <TagChipSub tag="Java" />
            <TagChipSub tag="React" />
            <TagChipSub tag="Spring" />
          </TagContainer>
        </FlexDiv>
      </FlexDiv>
    </FlexDiv>
  );
};
