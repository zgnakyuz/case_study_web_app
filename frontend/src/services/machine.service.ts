import axios from "axios";
import { CoinType } from "../types/coin.type";
import authHeader from "./auth-header";

const MACHINE_URL = "http://localhost:8080/machine";

export const getMachineState = () => {
    return axios.get(MACHINE_URL);
}

export const insertCoin = (coinType: CoinType, userId: number) => {
    const coinValue = CoinType[coinType];
    return axios.put(MACHINE_URL + "/balance" + "/" + coinValue + "?userId=" + userId);
}

export const dispenseProductAndReturnChange = (productStockId: number, userId: number) => {
    return axios.put(MACHINE_URL + "/productStock" + "/" + productStockId + "?userId=" + userId);
}

export const refund = (userId: number) => {
    return axios.put(MACHINE_URL + "/balance" + "?userId=" + userId)
}

// export const getAdminBoard = () => {
//    return axios.get(MACHINE_URL + "/info", { headers: authHeader() });
//  };

export const reset = () => {
    return axios.put(MACHINE_URL + "/reset")
}

export const collectMoney = (userId: number) => {
    return axios.put(MACHINE_URL + "/cashdrawer" + "?userId=" + userId)
}