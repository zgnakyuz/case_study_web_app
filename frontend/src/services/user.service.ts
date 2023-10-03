import axios from "axios";
import authHeader from "./auth-header";

const BASE_URL = "http://localhost:8080";
const API_URL = "http://localhost:8080/api/";

export const getAllProducts = () => {
  return axios.get(BASE_URL + "/products");
};

export const getAdminBoard = () => {
  return axios.get(API_URL + "admin", { headers: authHeader() });
};

export const getSelectedProduct = (productId: number) => {
  return axios.get(BASE_URL + "/products/" + productId);
};