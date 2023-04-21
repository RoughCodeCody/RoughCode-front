import {
  LinkCard,
  LinkCardTitle,
  LinkCardContent,
  LinkCardImage,
} from "./home-link-card-style";

import { ArrowRightCircle } from "../../icon/arrow-right-circle";

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
          <p>{title}</p>
          <ArrowRightCircle />
        </LinkCardTitle>
        <LinkCardContent>{content}</LinkCardContent>
        <LinkCardImage>{imageUrl}</LinkCardImage>
      </LinkCard>
    </>
  );
};
