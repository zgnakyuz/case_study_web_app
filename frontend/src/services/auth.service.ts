import axios from "axios";

const API_URL = "http://localhost:8080/";

export const register = (username: string, email: string, password: string) => {
  return axios.post(API_URL + "registration", {
    username,
    email,
    password,
  });
};

export const login = (username: string, password: string) => {
  return axios
    .post(API_URL + "login", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

export const logout = () => {
  return new Promise<void>((resolve) => {
    localStorage.removeItem("user");
    resolve();
  });
};

export const getCurrentUser = () => {
  const userStr = localStorage.getItem("user");
  if (userStr) return JSON.parse(userStr);

  return null;
};
