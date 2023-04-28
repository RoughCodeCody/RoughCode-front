import styled, { css } from "styled-components";

export const ThumbnailContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60%;
  width: 100%;
  padding: 0 1.2rem 2rem 1.2rem;
`;

export const ImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  position: relative;
  overflow: hidden;
  border-radius: 10px;
`;

export const ProjectCardDescription = styled.div`
  display: inline-block;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;

  /* 여러 줄 자르기 추가 스타일 */
  white-space: normal;
  line-height: 1.2;
  height: 3.6em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
`;

export const TagContainer = styled.div`
  display: flex;
  justify-content: start;
  align-items: center;
  width: 100%;
  overflow-x: scroll;
  ::-webkit-scrollbar {
    display: none;
  }
`;
