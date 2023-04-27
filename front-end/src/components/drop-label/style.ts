import styled from "styled-components";

export const DropLabelBox = styled.div`
  z-index: 999;
  position: relative;
  width: 100%;
  max-width: 11rem;
  max-height: 3.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid #ebebeb;
  border-radius: 8px;
  background: var(--white-color);
  &:hover {
    border: 1px solid var(--main-color);
    transition-duration: 0.2s;
    transition-timing-function: ease;
    transition-delay: 0s;
    transition-property: all;
  }
`;
export const DropOptionContainer = styled.div`
  position: absolute;
  top: 104%;
  left: 0;
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 0.5rem 0 0.5rem 0;
  background: var(--white-color);
  border: 1px solid #ebebeb;
  border-radius: 0.5rem;
  cursor: pointer;
`;
export const DropOption = styled.div`
  width: 100%;
  height: 3.5rem;
  display: flex;
  align-items: center;
  background: var(--white-color);
  padding: 0.75rem 1rem;
  &:hover {
    background: #f9fafb;
  }
`;
