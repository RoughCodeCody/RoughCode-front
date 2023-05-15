import Axios from "axios";

import { API_URL } from "@/config";

export const axios = Axios.create({
  baseURL: API_URL,
  withCredentials: true,
});

axios.interceptors.response.use(
  (response) => {
    console.log("response data: ", response.data);
    console.log("res msg: ", response.data.message);
    return response.data?.result;
  },
  (error) => {
    if (error.response?.status === 409) alert(error.response.data.message);
    return Promise.reject(error);
  }
);

export const axiosExternal = Axios.create();

axiosExternal.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    return Promise.reject(error);
  }
);
