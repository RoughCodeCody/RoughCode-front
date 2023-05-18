import React, { useState } from "react";
import styled from "styled-components";

const FileInputContainer = styled.div`
  display: flex;
  flex-flow: column nowrap;
  align-items: center;
  justify-content: center;

  & img {
    width: 100%;
    display: block;
    margin-top: 3rem;
  }

  & input {
    display: none;
  }

  & label {
    display: block;
    width: 15rem;
    padding: 1.5rem;
    text-align: center;
    background: var(--main-color);
    color: #fff;
    font-size: 15px;
    font-family: "Open Sans", sans-serif;
    text-transform: Uppercase;
    font-weight: 600;
    border-radius: 5px;
    cursor: pointer;
  }
`;

export const ThumbnailField = ({ initialSrc }: { initialSrc?: string }) => {
  const [imageSrc, setImageSrc] = useState(initialSrc);
  const showPreview = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target?.files?.length && event.target?.files?.length > 0) {
      let src = URL.createObjectURL(event.target.files[0]);
      setImageSrc(src);
    }
  };

  return (
    <FileInputContainer>
      <label htmlFor="input-thumbnail">이미지 업로드</label>
      <input
        type="file"
        id="input-thumbnail"
        accept="image/*"
        onChange={showPreview}
      />
      <img src={imageSrc} id="input-thumbnail-preview" />
    </FileInputContainer>
  );
};
