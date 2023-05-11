import { useRef, useEffect, useState } from "react";

import { DropdownArrow } from "@/components/elements";
import { FlexDiv } from "@/components/elements";

import { SearchInput } from "./style";
import { TagList } from "./tag-list";

export const TagSearchBox = () => {
  const boxRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);
  const [isOpen, setisOpen] = useState(false);
  const [tagKeyword, setTagKeyword] = useState("");
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTagKeyword(event.target.value);
  };

  // 화살표 눌러서 닫았을 때 인풋 태그에 입력된 키워드와 검색 결과 초기화
  useEffect(() => {
    if (inputRef.current) {
      if (isOpen === false) {
        inputRef.current.value = "";
        setTagKeyword("");
      }
    }
  }, [isOpen]);

  // 토글의 바깥을 눌렀을 때 토글 닫기(토글 닫으면 위의 useEffect가 실행되어 초기화 됨
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (boxRef.current && !boxRef.current.contains(event.target as Node)) {
        setisOpen(false);
      }
    };
    document.addEventListener("click", handleClickOutside);
    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, [boxRef]);

  return (
    <FlexDiv
      ref={boxRef}
      position="relative"
      width="100%"
      height="3.7rem"
      maxWidth="17.5rem"
      maxHeight="3.7rem"
      padding="0 1.1rem 0 1.1rem"
      justify="space-between"
      radius="8px"
      color="white"
      pointer={true}
      shadow={true}
    >
      <SearchInput
        ref={inputRef}
        onChange={handleChange}
        onFocus={() => setisOpen(true)}
        placeholder="태그 검색"
      />

      <DropdownArrow
        isopen={isOpen.toString()}
        size={30}
        onClick={() => {
          setisOpen(!isOpen);
        }}
      />
      {isOpen ? <TagList tagKeyword={tagKeyword} /> : <></>}
    </FlexDiv>
  );
};
