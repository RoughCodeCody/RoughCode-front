import styled from "styled-components";

export const Input = styled.input`
  appearance: none;
  display: block;
  border: 1px solid var(--sub-one-color);
  border-radius: 0.375rem;
  background-color: var(--sub-two-color);
  padding: 1.5rem 1rem 0.25rem 1rem;
  width: 100%;
  height: 4rem;

  &:focus {
    outline: 2px solid transparent;
    outline-offset: 2px;
    border-color: var(--main-color);
  }
`;

export const InputContainer = styled.div<{
  width?: string;
}>`
  width: ${({ width }) => (width ? width : "unset")};
  display: flex;
  position: relative;
  flex-flow: column nowrap;

  &:focus-within label {
    transform: translate(0, 0.75rem) scale(0.8);
    color: var(--placeholder-color);
  }
`;

export const InputLabel = styled.label<{ isDirty: boolean | undefined }>`
  position: absolute;
  left: 1rem;
  transform: ${(props) =>
    props.isDirty
      ? "translate(0, 0.75rem) scale(0.8)"
      : "translate(0, 1.5rem) scale(1)"};
  transform-origin: top left;
  transition: 200ms cubic-bezier(0, 0, 0.2, 1) 0ms;
  line-height: 1.5rem;
  color: var(--placeholder-color);
  font-size: 1rem;
  pointer-events: none;
`;

export const InputErrorMsg = styled.div`
  line-height: 1.25rem;
  color: var(--red-color);
  font-size: 0.875rem;
  font-weight: 600;
`;
