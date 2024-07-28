import React from "react";
import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Home from "../components/Home";
import { getCategories, fetchNotifications } from "../services/api";
import "@testing-library/jest-dom/extend-expect";

// Mock the API functions
jest.mock("../services/api");

const mockNotifications = [
  { id: 1, category: "Info", channel: "Email", userFullName: "John Doe", sentAt: "2023-01-01", message: "Test message 1" },
  { id: 2, category: "Alert", channel: "SMS", userFullName: "Jane Doe", sentAt: "2023-01-02", message: "Test message 2" },
];

const mockCategories = [
  { id: 1, type: "Info" },
  { id: 2, type: "Alert" },
];

describe("Home", () => {
  beforeEach(() => {
    getCategories.mockResolvedValue(mockCategories);
    fetchNotifications.mockResolvedValue(mockNotifications);
  });

  test("renders Home component correctly", async () => {
    render(<Home />);

    expect(screen.getByText("Send Notification")).toBeInTheDocument();
    expect(screen.getByText("Notifications")).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText("Test message 1")).toBeInTheDocument();
      expect(screen.getByText("Test message 2")).toBeInTheDocument();
    });
  });

  test("fetches notifications and categories on mount", async () => {
    render(<Home />);

    await waitFor(() => {
      expect(getCategories).toHaveBeenCalled();
      expect(fetchNotifications).toHaveBeenCalled();
    });

    await waitFor(() => {
      expect(screen.getByText("Info")).toBeInTheDocument();
      expect(screen.getByText("Alert")).toBeInTheDocument();
    });
  });

  test("handles error when fetching notifications", async () => {
    fetchNotifications.mockRejectedValueOnce(new Error("Error fetching notifications"));

    render(<Home />);

    await waitFor(() => {
      expect(screen.queryByText("Test message 1")).not.toBeInTheDocument();
      expect(screen.queryByText("Test message 2")).not.toBeInTheDocument();
    });
  });

  test("handles error when fetching categories", async () => {
    getCategories.mockRejectedValueOnce(new Error("Error fetching categories"));

    render(<Home />);

    await waitFor(() => {
      expect(screen.queryByText("Info")).not.toBeInTheDocument();
      expect(screen.queryByText("Alert")).not.toBeInTheDocument();
    });
  });

  test("calls getNotifications on form submit", async () => {
    render(<Home />);

    const sendNotificationButton = screen.getByText("Send Notification!");

    userEvent.click(sendNotificationButton);

    await waitFor(() => {
      expect(fetchNotifications).toHaveBeenCalledTimes(2); // Once on mount, and once on form submit
    });
  });
});
