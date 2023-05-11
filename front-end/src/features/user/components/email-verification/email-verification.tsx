import { z } from "zod";
import { Form, InputField } from "@/components/form";
import { Btn, FlexDiv } from "@/components/elements";
import { useSendEmailAddress } from "../../api";

type FormValues = {
  email: string;
};

const schema = z.object({
  email: z.string().email("유효한 이메일 주소를 입력해주세요."),
});

export const EmailVerification = () => {
  const sendEmailAddressQuery = useSendEmailAddress();
  const onSubmit = (data: FormValues) => {
    sendEmailAddressQuery.mutate(data.email);
  };

  return (
    <Form onSubmit={onSubmit} schema={schema}>
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
          <Btn text="인증코드 전송" />
        </FlexDiv>
      )}
    </Form>
  );
};
