import styled from "styled-components";

const FeedbackRegisterTextarea = styled.textarea.attrs(({ placeholder }) => ({
  placeholder: placeholder,
}))`
  width: 85%;
  height: 100%;
  resize: none;
  border: none;
  outline: none;
  font-size: 1rem;
  line-height: 1.8rem;
  font-family: Arial, Helvetica, sans-serif;

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

export { FeedbackRegisterTextarea };
