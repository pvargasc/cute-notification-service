import axios from "axios";

const API_URL = "http://localhost:8080";

export const getCategories = async () => {
  try {
    const response = await axios.get(`${API_URL}/categories`);
    return response.data;
  } catch (error) {
    console.error("Error fetching categories:", error);
    throw error;
  }
};

export const fetchNotifications = async () => {
  try {
    const response = await axios.get(`${API_URL}/notifications`);
    return response.data;
  } catch (error) {
    console.error("Error fetching notifications:", error);
    throw error;
  }
};

export const sendNotification = async (data) => {
  try {
    const response = await axios.post(`${API_URL}/notifications`, data);
    return response.data;
  } catch (error) {
    console.error("Error sending notification:", error);
    throw error;
  }
};
