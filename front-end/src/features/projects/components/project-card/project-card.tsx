import Link from "next/link";
import Image from "next/image";
import { useState, useRef } from "react";

import { FlexDiv } from "@/components/elements";
import { Text } from "@/components/elements";
import { TagChipSub } from "@/components/elements";
import { Count } from "@/components/elements/count";

import dog from "@/assets/dog.png";
import cryingDuck from "@/assets/crying-duck-face.png";

import {
  ThumbnailContainer,
  ImageContainer,
  ProjectCardDescription,
  TagContainer,
} from "./style";

interface ProjectCardProps {
  project: {
    projectId: number;
    version: number;
    title: string;
    date?: Date;
    likeCnt: number;
    feedbackCnt: number;
    img: string;
    tags: { tagId: number; name: string; cnt: number }[];
    introduction: string;
    closed: boolean;
  };
}
export const ProjectCard = ({
  project: {
    projectId,
    version,
    title,
    likeCnt,
    feedbackCnt,
    img,
    tags,
    introduction,
    closed,
  },
}: ProjectCardProps) => {
  const [isDown, setIsDown] = useState(false);
  const [startX, setStartX] = useState<number | null>(null);
  const [scrollLeft, setScrollLeft] = useState<number>(0);
  const [isHovered, setIsHovered] = useState(false);
  const tagContainerRef = useRef<HTMLDivElement>(null);
  const handleMouseDown = (e: React.MouseEvent<HTMLDivElement>) => {
    setIsDown(true);
    if (tagContainerRef.current) {
      setStartX(e.pageX - tagContainerRef.current.offsetLeft);
      setScrollLeft(tagContainerRef.current.scrollLeft);
    }
  };

  const handleMouseEnterCard = () => {
    setIsHovered(true);
  };

  const handleMouseLeaveCard = () => {
    setIsHovered(false);
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
    <Link href={`/project/${projectId}`}>
      <FlexDiv
        width="24rem"
        height="31rem"
        direction="column"
        color={closed ? "shad" : "white"}
        border={
          isHovered
            ? "solid 2px var(--main-color)"
            : "solid 2px var(--shad-color)"
        }
        radius="32px"
        padding="2px 0 1rem 0"
        shadow={true}
        pointer={true}
        onMouseEnter={handleMouseEnterCard}
        onMouseLeave={handleMouseLeaveCard}
      >
        <FlexDiv
          width="100%"
          height="3rem"
          justify="space-between"
          padding="0 1rem 0 2rem"
          pointer={true}
        >
          <FlexDiv>
            <Image
              src={closed ? cryingDuck : dog}
              width={30}
              height={30}
              alt="face"
            />
            <Text>{closed ? "닫힘" : "열림"}</Text>
          </FlexDiv>
          {/* 좋아요 리뷰 아이콘 */}
          <FlexDiv>
            <Count type="like" isChecked={true} cnt={likeCnt} />
            <Count type="code" isChecked={true} cnt={feedbackCnt} />
          </FlexDiv>
        </FlexDiv>
        {/* 썸네일 */}
        <ThumbnailContainer>
          <ImageContainer>
            <Image
              src={img}
              width={343.6}
              height={234.4}
              alt={"PJT"}
              object-fit="cover"
            />
          </ImageContainer>
        </ThumbnailContainer>

        {/* 분리선 */}
        <FlexDiv
          pointer={true}
          width="100%"
          height="0"
          border={
            isHovered
              ? "solid 1px var(--main-color)"
              : "solid 1px var(--shad-color)"
          }
          // border={"solid 1px var(--shad-color)"}
        ></FlexDiv>

        {/* 내용 */}
        <FlexDiv
          width="100%"
          height="35%"
          paddingX="1.8rem"
          direction="column"
          justify="center"
          align="start"
          pointer={true}
        >
          {/* 타이틀 컨테이너 */}
          <FlexDiv
            width="100%"
            paddingY="0.5rem"
            justify="space-between"
            pointer={true}
          >
            {/* 타이틀 */}
            <FlexDiv gap="0.6rem" align="end" pointer={true}>
              <Text size="1.5rem" bold={true} color="main">
                V{version}
              </Text>
              <Text size="1.3rem" bold={true} pointer={true}>
                {title}
              </Text>
            </FlexDiv>
          </FlexDiv>
          <FlexDiv
            width="100%"
            height="100%"
            direction="column"
            justify="space-between"
            align="start"
            pointer={true}
          >
            {/* 한 줄 설명 */}
            <ProjectCardDescription>{introduction}</ProjectCardDescription>
            {/* 태그들 */}
            <TagContainer
              ref={tagContainerRef}
              onMouseDown={handleMouseDown}
              onMouseLeave={handleMouseLeave}
              onMouseUp={handleMouseUp}
              onMouseMove={handleMouseMove}
              onWheel={handleWheel}
            >
              {tags.map((tag, idx) => (
                <TagChipSub tag={tag.name} key={idx} />
              ))}
            </TagContainer>
          </FlexDiv>
        </FlexDiv>
      </FlexDiv>
    </Link>
  );
};
