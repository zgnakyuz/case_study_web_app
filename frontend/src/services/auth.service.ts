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

export const getCurrentUser = async () => {
  const userStr = localStorage.getItem("user");
  if (userStr) {
    const cachedUser = JSON.parse(userStr);

    try {
      const response = await fetchUserById(cachedUser.id);
      const updatedUser = response.data;

      localStorage.setItem("user", JSON.stringify(updatedUser));

      return updatedUser;
    } catch (error) {
      console.error("Error fetching updated user data:", error);
      return cachedUser;
    }
  }

  return null;
};

export const fetchUserById = (userId: number) => {
  return axios.get(API_URL + "users/" + userId);
};
