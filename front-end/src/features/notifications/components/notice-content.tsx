import { FlexDiv, Text } from "@/components/elements";

interface NoticeContentProps {
  writer: string;
  isProject: boolean;
  title: string;
  version: number;
}

export const NoticeContent = ({
  writer,
  isProject,
  title,
  version,
}: NoticeContentProps) => {
  return (
    <FlexDiv>
      <Text size="1.3rem" bold={true}>
        {writer} 님의{" "}
        <Text as="span" size="1.3rem" bold={true}>
          {isProject ? "프로젝트 " : "코드리뷰 "}
          <Text as="span" size="1.3rem" bold={true} color="main" pointer={true}>
            {title} ver{version}{" "}
          </Text>
        </Text>
        업데이트
      </Text>
    </FlexDiv>
  );
};
