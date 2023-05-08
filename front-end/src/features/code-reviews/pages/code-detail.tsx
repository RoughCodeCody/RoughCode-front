import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { CodeInfo } from "../components/code-info";
import { useCodeInfo } from "../api/get-code-info";

interface CodeDetailProps {
  codeId: string;
}

export const CodeDetail = ({ codeId }: CodeDetailProps) => {
  const codeInfoQuery = useCodeInfo(Number(codeId));

  console.log(codeInfoQuery.data);

  return (
    <>
      <FlexDiv direction="column" gap="3rem" padding="2rem 0">
        <WhiteBoxNoshad width="65%" padding="2.25rem">
          {/* <CodeInfo /> */}
        </WhiteBoxNoshad>
      </FlexDiv>
    </>
  );
};
