import { FlexDiv } from "../elements/flexdiv";
import { Text } from "../elements/text";

interface BottomHeaderProps {
  locations?: string[];
  menus?: string[];
}

export const BottomHeader = ({ locations, menus }: BottomHeaderProps) => {
  return (
    <FlexDiv width="100vw" height="78px" color="font">
      <FlexDiv
        width="100%"
        maxWidth="1280px"
        justify={menus ? "space-between" : "start"}
      >
        <FlexDiv gap="1rem">
          {locations?.map((location) => (
            <Text size="1.2rem" color="bg" bold={true} key={location}>
              {location}
            </Text>
          ))}
        </FlexDiv>

        <FlexDiv gap="4rem">
          {menus?.map((menu) => (
            <Text size="1.2rem" color="bg" bold={true} key={menu}>
              {menu}
            </Text>
          ))}
        </FlexDiv>
      </FlexDiv>
    </FlexDiv>
  );
};
