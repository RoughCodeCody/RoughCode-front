import { Notification } from "../types";
import { FlexDiv, Text } from "@/components/elements";

type NoticeContentProps = {
  data: Notification;
};

export const NoticeContent = ({
  data: { alarmId, section, content, postId, userId, createdDate },
}: NoticeContentProps) => {
  return (
    <FlexDiv>
      <Text size="1.3rem" bold={true}>
        {content[0]}{" "}
        <Text as="span" size="1.3rem" bold={true} color="main" pointer={true}>
          {/* {isProject ? "프로젝트 " : "코드리뷰 "} */}
          {content[1]}{" "}
          <Text as="span" size="1.3rem" bold={true}>
            {/* {title} ver{version}{" "} */}
            {content[2]}{" "}
          </Text>
        </Text>
      </Text>
    </FlexDiv>
  );
};
