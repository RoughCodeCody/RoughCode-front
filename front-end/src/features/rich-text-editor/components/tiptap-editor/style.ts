import styled from "styled-components";

export const Editor = styled.div<{ minHeight: string }>`
  display: flex;
  flex-direction: column;
  border: 3px solid #0d0d0d;
  border-radius: 0.75rem;
  background-color: #fff;
  max-height: 26rem;
  color: #0d0d0d;

  & .ProseMirror {
    outline: 2px solid transparent;
    outline-offset: 2px;
  }
  & .ProseMirror > * + * {
    margin-top: 0.75em;
  }
  & .ProseMirror ul,
  & .ProseMirror ol {
    padding: 0 1rem;
  }
  & .ProseMirror h1,
  & .ProseMirror h2,
  & .ProseMirror h3,
  & .ProseMirror h4,
  & .ProseMirror h5,
  & .ProseMirror h6 {
    line-height: 1.1;
  }
  & .ProseMirror code {
    background-color: rgba(97, 97, 97, 0.1);
    color: #616161;
  }
  & .ProseMirror pre {
    border-radius: 0.5rem;
    background: #0d0d0d;
    padding: 0.75rem 1rem;
    color: #fff;
    font-family: "JetBrainsMono", monospace;
  }
  & .ProseMirror pre code {
    background: none;
    padding: 0;
    color: inherit;
    font-size: 0.8rem;
  }
  & .ProseMirror mark {
    background-color: #faf594;
  }
  & .ProseMirror img {
    max-width: 100%;
    height: auto;
  }
  & .ProseMirror hr {
    margin: 1rem 0;
  }
  & .ProseMirror blockquote {
    border-left: 2px solid rgba(13, 13, 13, 0.1);
    padding-left: 1rem;
  }
  & .ProseMirror hr {
    margin: 2rem 0;
    border: none;
    border-top: 2px solid rgba(13, 13, 13, 0.1);
  }
  & .ProseMirror ul[data-type="taskList"] {
    padding: 0;
    list-style: none;
  }
  & .ProseMirror ul[data-type="taskList"] li {
    display: flex;
    align-items: center;
  }
  & .ProseMirror ul[data-type="taskList"] li > label {
    flex: 0 0 auto;
    margin-right: 0.5rem;
    user-select: none;
  }
  & .ProseMirror ul[data-type="taskList"] li > div {
    flex: 1 1 auto;
  }
  & .editor__header {
    display: flex;
    flex: 0 0 auto;
    flex-wrap: wrap;
    align-items: center;
    border-bottom: 3px solid #0d0d0d;
    border-top-left-radius: 0.25rem;
    border-top-right-radius: 0.25rem;
    background: #0d0d0d;
    padding: 0.25rem;
  }
  & .editor__content {
    flex: 1 1 auto;
    padding: 1.25rem 1rem;
    min-height: ${(props) => props.minHeight};
    overflow-x: hidden;
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
  }
`;
