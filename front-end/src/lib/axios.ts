import Axios from "axios";

import { API_URL } from "@/config";

export const axios = Axios.create({
  baseURL: API_URL,
  withCredentials: true,
});

axios.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    return Promise.reject(error);
  }
);
