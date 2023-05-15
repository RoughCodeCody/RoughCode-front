import { useEffect, useState } from "react";
import { z } from "zod";
import { Form, InputField } from "@/components/form";
import { Btn, FlexDiv, Text } from "@/components/elements";
import {
  useSendEmailAddress,
  useSendEmailCode,
  useDeleteEmailAuth,
} from "../../api";
import { useUser } from "@/features/auth";

type EmailFormValues = {
  email: string;
};
type CodeFormValues = {
  code: string;
};

const emailSchema = z.object({
  email: z.string().email("유효한 이메일 주소를 입력해주세요."),
});
const codeSchema = z.object({
  code: z.string(),
});

export const EmailVerification = () => {
  const [remainingTime, setRemainingTime] = useState<number>(1);
  const sendEmailAddressQuery = useSendEmailAddress();
  const sendEmailCodeQuery = useSendEmailCode();
  const deleteEmailAuthQuery = useDeleteEmailAuth();
  const userQuery = useUser();
  const onEmailSubmit = (data: EmailFormValues) => {
    sendEmailAddressQuery.mutate(data.email);
  };
  const onCodeSubmit = async (data: CodeFormValues) => {
    const params = {
      email: sendEmailAddressQuery.variables,
      code: data.code,
    };
    await sendEmailCodeQuery.mutateAsync(params);
    await userQuery.refetch();
    if (userQuery.data?.email === "") {
      setRemainingTime(0);
    }
  };

  useEffect(() => {
    let interval: NodeJS.Timeout;
    if (sendEmailAddressQuery.status === "success") {
      setRemainingTime(180);
      interval = setInterval(() => {
        setRemainingTime((prevTime) => prevTime - 1);
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [sendEmailAddressQuery.status]);

  useEffect(() => {
    if (remainingTime === 0) {
      sendEmailAddressQuery.reset();
    }
  }, [remainingTime]);

  if (userQuery.data?.email !== "") {
    return (
      <FlexDiv
        paddingX="3%"
        direction="column"
        justify="start"
        align="start"
        gap="0.5rem"
      >
        <FlexDiv gap="1rem" align="start">
          <Text size="2rem" bold={true} color="font">
            연결된 이메일
          </Text>
          <Btn
            text="인증 해제"
            bgColor="orange"
            onClickFunc={async () => {
              await deleteEmailAuthQuery.mutateAsync(); // 사용자가 데이터를 가져오기 위해 async/await 추가
              await userQuery.refetch();
              if (userQuery.data?.email === "") {
                setRemainingTime(0);
              } // userQuery 다시 요청(refetch)
            }}
          />
        </FlexDiv>
        <Text size="1.6rem" bold={true}>
          {userQuery.data?.email}
        </Text>
      </FlexDiv>
    );
  } else {
    return (
      <FlexDiv width="100%" direction="column" gap="1rem">
        <Form onSubmit={onEmailSubmit} schema={emailSchema}>
          {({ register, formState }) => (
            <FlexDiv width="100%" justify="start" gap="2rem">
              <InputField
                inputContainerWidth="50%"
                type="email"
                label="이메일을 입력해 주세요"
                isDirty={formState.dirtyFields.email}
                error={formState.errors.email}
                registration={register("email")}
              />
              {sendEmailAddressQuery.status === "loading" ? (
                <Btn text="인증코드 전송 중" />
              ) : sendEmailAddressQuery.status === "success" ? (
                <Btn
                  bgColor="orange"
                  text={`${remainingTime}초 안에 코드를 입력해주세요`}
                  disabled={true}
                />
              ) : (
                <Btn text="인증코드 전송" />
              )}
            </FlexDiv>
          )}
        </Form>
        {sendEmailAddressQuery.status === "success" && (
          <Form onSubmit={onCodeSubmit} schema={codeSchema}>
            {({ register, formState }) => (
              <FlexDiv width="100%" justify="start" gap="2rem">
                <InputField
                  inputContainerWidth="50%"
                  type="text"
                  label={
                    sendEmailCodeQuery.data === undefined
                      ? "이메일에 전송된 인증 코드를 입력해 주세요"
                      : sendEmailCodeQuery.data === "0"
                      ? "올바른 인증 코드를 입력해주세요"
                      : "인증되었습니다."
                  }
                  isDirty={formState.dirtyFields.code}
                  error={formState.errors.code}
                  registration={register("code")}
                />
                {sendEmailCodeQuery.status === "loading" ? (
                  <Btn text="전송 중" />
                ) : (
                  <Btn text="코드 입력" />
                )}
              </FlexDiv>
            )}
          </Form>
        )}
      </FlexDiv>
    );
  }
};
