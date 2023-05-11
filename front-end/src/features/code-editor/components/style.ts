import styled from "styled-components";

const EditorWrapper = styled.div<{ noShad: boolean }>`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #d9d9d9;
  border-radius: 32px;
  box-shadow: ${({ noShad }) =>
    noShad ? "none" : "40px 40px 100px rgba(0, 0, 0, 0.15)"};
`;
const EditorHeader = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding-left: 2rem;
  padding-right: 2rem;
  border-bottom: 1px solid #d9d9d9;
`;

const EditorBottom = styled.div`
  width: 100%;
  height: 3rem;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  padding-right: 2rem;
  border-top: 1px solid #d9d9d9;
`;

export { EditorWrapper, EditorHeader, EditorBottom };
