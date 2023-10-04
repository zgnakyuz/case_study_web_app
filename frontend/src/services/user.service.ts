import axios from "axios";
import authHeader from "./auth-header";

const BASE_URL = "http://localhost:8080";

export const getAllProducts = () => {
  return axios.get(BASE_URL + "/products");
};

export const getSelectedProduct = (productId: number) => {
  return axios.get(BASE_URL + "/products/" + productId);
};