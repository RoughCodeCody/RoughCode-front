import { create } from "zustand";

type IdArrState = {
  idArr: number[];
};

type IdArrAction = {
  toggleId: (id: number) => void;
  resetId: () => void;
};

const initialState: IdArrState = {
  idArr: [],
};

export const useIdArrStore = create<IdArrState & IdArrAction>((set) => ({
  idArr: [],
  toggleId: (id) => {
    set((state) => ({
      idArr: state.idArr.includes(id)
        ? state.idArr.filter((el) => el !== id)
        : state.idArr.concat([id]),
    }));
  },
  resetId: () => {
    set(initialState);
  },
}));
