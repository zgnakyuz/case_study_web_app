import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/";

export const getPublicContent = () => {
  return axios.get(API_URL + "all");
};

export const getAdminBoard = () => {
  return axios.get(API_URL + "admin", { headers: authHeader() });
};
