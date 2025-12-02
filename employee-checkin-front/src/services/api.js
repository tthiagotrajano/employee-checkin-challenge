import axios from "axios";

const api = axios.create({
  baseURL: "https://employee-checkin-api.onrender.com"
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

export const loginUser = async (email, password) => {
  try {
    const response = await api.post("/auth/login", { email, password });
    return response;
  } catch (error) {
    throw error;
  }
};

export const insertCheckin = async () => {
  try {
    const response = await api.post("/work/checkin");
    return response;
  } catch (error) {
    throw error;
  }
};

export const insertCheckout = async () => {
  try {
    const response = await api.post("/work/checkout");
    return response;
  } catch (error) {
    throw error;
  }
};

export const logout = async () => {
  try {
    const response = await api.post("/auth/logout");
    return response;
  } catch (error) {
    throw error;
  }
};

export const getWorkRecords = async (name = "", startDate = "", endDate = "", page = 0, size = 10) => {
  try {
    const startDateTime = startDate ? `${startDate}T00:00:00` : null;

    const endDateTime = endDate ? `${endDate}T23:59:59` : null;


    const params = {
      name: name || undefined,
      startDate: startDateTime || undefined,
      endDate: endDateTime || undefined,
      page,
      size,
    };

    const response = await api.get("/work/list", { params });
    return response.data; 
  } catch (error) {
    throw error;
  }
};

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const msg = error?.response?.data?.message || error?.message;

    console.log(msg);
    
    if (msg && (msg.includes("You are not logged in. Please log in.") || msg.includes("The given id must not be null"))) {
      window.location.href = "/";
    }
    return Promise.reject(error);
  }
);
