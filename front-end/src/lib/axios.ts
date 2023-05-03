import Axios from "axios";

import { API_URL } from "@/config";

export const axios = Axios.create({
  baseURL: API_URL,
  withCredentials: true,
});

axios.interceptors.response.use(
  (response) => {
    console.log("res msg: ", response.data.message);
    return response.data.result;
  },
  (error) => {
    return Promise.reject(error);
  }
);
