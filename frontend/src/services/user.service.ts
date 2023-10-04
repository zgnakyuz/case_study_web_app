import axios from "axios";
import authHeader from "./auth-header";

const BASE_URL = "http://localhost:8080";

export const getAllProducts = () => {
  return axios.get(BASE_URL + "/products");
};

export const getSelectedProduct = (productId: number) => {
  return axios.get(BASE_URL + "/products/" + productId);
};

export const addToStocks = (productId: number, quantity: number) => {
  return axios.put(BASE_URL + "/products/" + productId + "?quantity=" + quantity);
};

export const changeProductPrice = (productId: number, newPrice: number) => {
  return axios.put(BASE_URL + "/products/prices/" + productId + "?newPrice=" + newPrice)
}