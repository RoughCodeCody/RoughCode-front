import { useRouter } from "next/navigation";

import { Notification } from "../types";
import { FlexDiv, Text } from "@/components/elements";

type NoticeContentProps = {
  data: Notification;
};

export const NoticeContent = ({
  data: { alarmId, section, content, postId, userId, createdDate },
}: NoticeContentProps) => {
  const router = useRouter();
  return (
    <FlexDiv>
      <Text size="1.3rem" bold={true}>
        {content[0]}{" "}
        <Text
          as="span"
          size="1.3rem"
          bold={true}
          color="main"
          pointer={true}
          onClick={() => router.push(`/${section}/${postId}`)}
        >
          {content[1]}{" "}
          <Text as="span" size="1.3rem" bold={true}>
            {content[2]}{" "}
          </Text>
        </Text>
      </Text>
    </FlexDiv>
  );
};
