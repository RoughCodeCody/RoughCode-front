import { CodeInfo } from "../components/code-info";

interface CodeDetailProps {
  codeId: string;
}

export const CodeDetail = ({ codeId }: CodeDetailProps) => {
  console.log(codeId);
  return (
    <>
      <CodeInfo />
    </>
  );
};
