import axios from "axios";
import { CoinType } from "../types/coin.type";

const MACHINE_URL = "http://localhost:8080/machine";

export const getMachineState = () => {
    return axios.get(MACHINE_URL);
}

export const insertCoin = (coinType: CoinType, userId: number) => {
    const coinValue = CoinType[coinType];
    return axios.put(MACHINE_URL + "/balance" + "/" + coinValue + "?userId=" + userId);
}

export const dispenseProduct = (productStockId: number) => {
    return axios.put(MACHINE_URL + "/productStock" + "/" + productStockId);
}