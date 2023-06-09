import styled from "styled-components";

const AccordionContainer = styled.div`
  position: relative;
  border: 1px solid var(--main-color);
  border-radius: 0 0 25px 25px;
  padding: 1rem;
  margin-top: 2.5rem;
`;

const AccordionHead = styled.div`
  height: 1rem;
  ${({ theme }) => theme.MIXINS.flexBox("row", "space-between")}
`;

const AccordionContentWrapper = styled.div`
  height: 100%;
  width: 100%;
  overflow: hidden;
  transition: height 0.3s ease;
`;

const Content = styled.div`
  padding-top: 1.5rem;
  padding-bottom: 0.5rem;
`;

export { AccordionContainer, AccordionHead, AccordionContentWrapper, Content };
