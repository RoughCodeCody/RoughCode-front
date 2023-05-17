import { create } from "zustand";

interface AsPathStoreType {
  prevAsPath: string | undefined;
  currentAsPath: string | undefined;
}

type AsPathInfoStore = {
  asPathInfo: AsPathStoreType;
  setAsPath: (
    asPath: string | undefined,
    currentAsPath: string | undefined
  ) => void;
};

export const useAsPathInfoStore = create<AsPathInfoStore>((set) => ({
  asPathInfo: {
    prevAsPath: undefined,
    currentAsPath: undefined,
  },
  setAsPath: (asPath, currentAsPath) => {
    // 전체 프로젝트 보기, 열린 프로젝트만 보기
    set((state) => ({
      asPathInfo: {
        ...state.asPathInfo,
        currentAsPath: asPath,
        prevAsPath: currentAsPath,
      },
    }));
  },
}));
