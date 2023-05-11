import React from "react";

import { MenuButton, Icon } from "./style";

type TiptapMenuItemProps = {
  icon?: string;
  title?: string;
  action?: () => boolean;
  isActive?: (() => boolean) | null;
};

export const TiptapMenuItem = ({
  icon,
  title,
  action,
  isActive = null,
}: TiptapMenuItemProps) => (
  <MenuButton isActive={isActive} onClick={action} title={title}>
    <Icon className="remix">
      <use xlinkHref={`/remixicon.symbol.svg#ri-${icon}`} />
    </Icon>
  </MenuButton>
);
