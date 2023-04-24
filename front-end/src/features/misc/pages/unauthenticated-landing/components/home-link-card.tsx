import Image from "next/image";

import {
  LinkCard,
  LinkCardTitle,
  LinkCardContent,
  LinkCardImage,
  ArrowRightContainer,
} from "./style";

import { FiArrowRightCircle } from "react-icons/fi";
import { Text } from "@/components/elements/text";

interface HomeLinkCardProps {
  title: string;
  content: string;
  imageUrl: string;
}

export const HomeLinkCard = ({
  title,
  content,
  imageUrl,
}: HomeLinkCardProps) => {
  return (
    <>
      <LinkCard>
        <LinkCardTitle>
          <Text size="2.3rem" bold={true}>
            {title}
          </Text>
          <ArrowRightContainer>
            <FiArrowRightCircle fontSize="2rem" color="var(--font-color)" />
          </ArrowRightContainer>
        </LinkCardTitle>
        <LinkCardContent>
          <Text size="1.1rem" bold={true} lineHeight="1.6rem">
            {content}
          </Text>
        </LinkCardContent>
        {/* <LinkCardImage>{imageUrl}</LinkCardImage> */}
        <LinkCardImage>
          <Image
            src="https://picsum.photos/400/300"
            width={400}
            height={300}
            alt={title}
          />
        </LinkCardImage>
      </LinkCard>
    </>
  );
};
