import Image, { StaticImageData } from "next/image";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { FiArrowRightCircle } from "react-icons/fi";

import {
  LinkCard,
  LinkCardTitle,
  LinkCardContent,
  LinkCardImage,
  ArrowRightContainer,
} from "./style";

import { API_URL } from "@/config";
import { Text } from "@/components/elements/text";
import { FlexDiv } from "@/components/elements";

interface HomeLinkCardProps {
  title: string;
  content: string;
  imageUrl: StaticImageData;
  endPoint: string;
}

export const HomeLinkCard = ({
  title,
  content,
  imageUrl,
  endPoint,
}: HomeLinkCardProps) => {
  const router = useRouter();
  return (
    <>
      <LinkCard>
        <LinkCardTitle>
          <Text size="2.3rem" bold={true}>
            {title}
          </Text>
          <Link
            href={
              endPoint === "login"
                ? `${API_URL}/oauth2/authorization/github?redirect_uri=${window.location.href}`
                : `/${endPoint}`
            }
          >
            <ArrowRightContainer>
              <FiArrowRightCircle fontSize="2rem" color="var(--font-color)" />
            </ArrowRightContainer>
          </Link>
        </LinkCardTitle>
        <LinkCardContent>
          <Text
            size="1.1rem"
            bold={true}
            lineHeight="1.6rem"
            whiteSpace="pre-line"
          >
            {content}
          </Text>
        </LinkCardContent>
        <FlexDiv>
          <LinkCardImage>
            <Image src={imageUrl} width={250} height={250} alt={title} />
          </LinkCardImage>
        </FlexDiv>
      </LinkCard>
    </>
  );
};
