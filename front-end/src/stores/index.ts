import {
  combineReducers,
  configureStore,
  PayloadAction,
  ThunkAction,
  Action,
} from "@reduxjs/toolkit";
import { createWrapper, HYDRATE } from "next-redux-wrapper";
import { authSlice } from "./slices/auth-slice";

const reducer = (state: any, action: PayloadAction<any>) => {
  return combineReducers({
    [authSlice.name]: authSlice.reducer,
  })(state, action);
};

const makeStore = () =>
  configureStore({
    reducer,
  });

const store = makeStore();

export const wrapper = createWrapper<AppStore>(makeStore, {
  debug: process.env.NODE_ENV === "development",
});
export type AppStore = ReturnType<typeof makeStore>;
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action
>;
