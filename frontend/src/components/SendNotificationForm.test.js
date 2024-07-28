import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import SendNotificationForm from "../components/SendNotificationForm";
import { sendNotification } from "../services/api";

// Mock the sendNotification function
jest.mock("../services/api");

const mockCategories = [
  { id: 1, type: "Info" },
  { id: 2, type: "Alert" },
];

describe("SendNotificationForm", () => {
  test("renders form with categories", () => {
    render(<SendNotificationForm categories={mockCategories} onSubmit={jest.fn()} />);

    expect(screen.getByLabelText("Category")).toBeInTheDocument();
    expect(screen.getByLabelText("Message")).toBeInTheDocument();
    expect(screen.getByText("Send Notification!")).toBeInTheDocument();

    fireEvent.mouseDown(screen.getByLabelText("Category"));
    expect(screen.getByText("Info")).toBeInTheDocument();
    expect(screen.getByText("Alert")).toBeInTheDocument();
  });

  test("displays validation messages on form submission with empty fields", async () => {
    render(<SendNotificationForm categories={mockCategories} onSubmit={jest.fn()} />);

    fireEvent.click(screen.getByText("Send Notification!"));

    await waitFor(() => {
      expect(screen.getByText("Category is required")).toBeInTheDocument();
      expect(screen.getByText("Message is required")).toBeInTheDocument();
    });
  });

  test("calls sendNotification and onSubmit on form submission with valid data", async () => {
    const mockOnSubmit = jest.fn();
    sendNotification.mockResolvedValue({});

    render(<SendNotificationForm categories={mockCategories} onSubmit={mockOnSubmit} />);

    fireEvent.change(screen.getByLabelText("Category"), { target: { value: "Info" } });
    fireEvent.change(screen.getByLabelText("Message"), { target: { value: "Test message" } });

    fireEvent.click(screen.getByText("Send Notification!"));

    await waitFor(() => {
      expect(sendNotification).toHaveBeenCalledWith({ category: "Info", message: "Test message" });
      expect(mockOnSubmit).toHaveBeenCalled();
    });
  });

  test("resets form after successful submission", async () => {
    sendNotification.mockResolvedValue({});
    const mockOnSubmit = jest.fn();

    render(<SendNotificationForm categories={mockCategories} onSubmit={mockOnSubmit} />);

    fireEvent.change(screen.getByLabelText("Category"), { target: { value: "Info" } });
    fireEvent.change(screen.getByLabelText("Message"), { target: { value: "Test message" } });

    fireEvent.click(screen.getByText("Send Notification!"));

    await waitFor(() => {
      expect(screen.getByLabelText("Category").value).toBe("");
      expect(screen.getByLabelText("Message").value).toBe("");
    });
  });

  test("disables submit button when fields are empty", () => {
    render(<SendNotificationForm categories={mockCategories} onSubmit={jest.fn()} />);

    expect(screen.getByText("Send Notification!")).toBeDisabled();

    fireEvent.change(screen.getByLabelText("Category"), { target: { value: "Info" } });
    expect(screen.getByText("Send Notification!")).toBeDisabled();

    fireEvent.change(screen.getByLabelText("Message"), { target: { value: "Test message" } });
    expect(screen.getByText("Send Notification!")).toBeEnabled();
  });
});
