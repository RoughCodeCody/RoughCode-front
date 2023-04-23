import styled from "styled-components";

const FeedbackResisterTextarea = styled.textarea.attrs(({ placeholder }) => ({
  placeholder: placeholder,
}))`
  width: 85%;
  height: 100%;
  resize: none;
  border: none;
  outline: none;

  &::placeholder {
    color: var(--shad-color);
    font-weight: 700;
  }
  &::-webkit-input-placeholder {
    color: var(--shad-color);
    font-weight: 700;
  }
  &::-ms-input-placeholder {
    color: var(--shad-color);
    font-weight: 700;
  }
`;

export { FeedbackResisterTextarea };
