import { FlexDiv, WhiteBoxNoshad } from "@/components/elements";
import { VersionsInfo } from "@/features/version-info";

import { useCodeInfo } from "../api/get-code-info";
import { CodeInfo } from "../components/code-info";

interface CodeDetailProps {
  codeId: string;
}

export const CodeDetail = ({ codeId }: CodeDetailProps) => {
  const { status, data } = useCodeInfo(Number(codeId));

  console.log(data);

  return (
    <>
      <FlexDiv direction="column" gap="3rem" padding="2rem 0">
        {data && (
          <>
            <WhiteBoxNoshad width="65%" padding="2.25rem">
              <CodeInfo data={data} />
              <VersionsInfo
                versions={data.versions}
                curVersionId={data.codeId}
              />
            </WhiteBoxNoshad>
          </>
        )}
      </FlexDiv>
    </>
  );
};
