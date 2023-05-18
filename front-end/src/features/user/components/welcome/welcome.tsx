import { useRouter } from "next/router";

import { FlexDiv, Text } from "@/components/elements";
import { Btn } from "@/components/elements";
import { useUser } from "@/features/auth";

import { useLogout } from "../../api/";

export const Welcome = () => {
  const router = useRouter();
  const logoutQuery = useLogout();
  const userQuery = useUser();

  const name = userQuery.data?.nickname;

  return (
    <FlexDiv justify="start" width="100%" padding="0 0 0 3%" gap="1rem">
      <Text size="2.3rem">
        <Text as="span" size="2.5rem" bold={true}>
          {name}
        </Text>
        님, 안녕하세요
      </Text>
      <Btn
        text="로그아웃"
        bgColor="orange"
        onClickFunc={async () => {
          await logoutQuery.mutateAsync();
          userQuery.remove();
          router.push("/");
        }}
      />
    </FlexDiv>
  );
};
