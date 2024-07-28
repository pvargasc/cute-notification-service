import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import { getCategories, fetchNotifications, sendNotification } from "./api";

const mock = new MockAdapter(axios);
const API_URL = "http://localhost:8080";

describe("API tests", () => {
  afterEach(() => {
    mock.reset();
  });

  it("should fetch categories successfully", async () => {
    const categories = [{ id: 1, type: "Finance" }];
    mock.onGet(`${API_URL}/categories`).reply(200, categories);

    const result = await getCategories();
    expect(result).toEqual(categories);
  });

  it("should throw an error when fetching categories fails", async () => {
    mock.onGet(`${API_URL}/categories`).reply(500);

    await expect(getCategories()).rejects.toThrow("Error fetching categories:");
  });

  it("should fetch notifications successfully", async () => {
    const notifications = [{ id: 1, message: "Test notification" }];
    mock.onGet(`${API_URL}/notifications`).reply(200, notifications);

    const result = await fetchNotifications();
    expect(result).toEqual(notifications);
  });

  it("should throw an error when fetching notifications fails", async () => {
    mock.onGet(`${API_URL}/notifications`).reply(500);

    await expect(fetchNotifications()).rejects.toThrow("Error fetching notifications:");
  });

  it("should send notification successfully", async () => {
    const notificationData = { category: "Finance", message: "Test message" };
    const responseData = { id: 1, ...notificationData };
    mock.onPost(`${API_URL}/notifications`).reply(200, responseData);

    const result = await sendNotification(notificationData);
    expect(result).toEqual(responseData);
  });

  it("should throw an error when sending notification fails", async () => {
    const notificationData = { category: "Finance", message: "Test message" };
    mock.onPost(`${API_URL}/notifications`).reply(500);

    await expect(sendNotification(notificationData)).rejects.toThrow("Error sending notification:");
  });
});
