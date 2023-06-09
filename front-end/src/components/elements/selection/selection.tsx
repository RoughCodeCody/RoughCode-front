import { useEffect, useState } from "react";
import { TbDotsVertical } from "react-icons/tb";
import { useRef } from "react";

import { SelectionList, SelectionText, SelectionWrapper } from "./style";

/*
 * props는 객체 형태로 넘겨줄 것
 * key는 표시할 텍스트
 * value는 해당 텍스트를 클릭했을 때 실행할 함수
 */

type SelectionProps = {
  selectionList: Record<string, () => void>;
  forceClose?: boolean;
};

export const Selection = ({ selectionList, forceClose }: SelectionProps) => {
  const wrapperRef = useRef<HTMLInputElement>(null);
  const [isOpen, setisOpen] = useState(false);

  useEffect(() => {
    if (forceClose) setisOpen(false);
  }, [forceClose]);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        wrapperRef.current &&
        !wrapperRef.current.contains(event.target as Node)
      ) {
        setisOpen(false);
      }
    };
    document.addEventListener("click", handleClickOutside);
    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, [wrapperRef]);

  return (
    <SelectionWrapper ref={wrapperRef}>
      <TbDotsVertical onClick={() => setisOpen((prev) => !prev)} />

      {isOpen && (
        <SelectionList>
          {Object.entries(selectionList).map((selectionItem, idx) => (
            <SelectionText onClick={selectionItem[1]} key={idx}>
              {selectionItem[0]}
            </SelectionText>
          ))}
        </SelectionList>
      )}
    </SelectionWrapper>
  );
};
