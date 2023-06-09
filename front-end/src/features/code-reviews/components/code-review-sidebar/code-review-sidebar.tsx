import { CodeReviewSidebarContent as SidebarContent } from "./code-review-sidebar-content";
import { SidebarContainer, SidebarFallback } from "./code-review-sidebar-style";

type CodeReviewSidebarProps = {
  codeId?: string;
  versionUp: string;
};

export const CodeReviewSidebar = ({
  codeId,
  versionUp,
}: CodeReviewSidebarProps) => {
  return (
    <SidebarContainer>
      {codeId ? (
        <SidebarContent codeId={codeId} versionUp={versionUp} />
      ) : (
        <SidebarFallback>
          코드 리뷰가 있으면 이곳에 나타날 거에요
        </SidebarFallback>
      )}
    </SidebarContainer>
  );
};
