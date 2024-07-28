import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import NotificationTable from "../components/NotificationTable";

const mockNotifications = [
  {
    id: 1,
    category: "Info",
    channel: "Email",
    userFullName: "John Doe",
    sentAt: "2023-07-25T12:34:56Z",
    message: "This is a test message",
  },
  {
    id: 2,
    category: "Alert",
    channel: "SMS",
    userFullName: "Jane Smith",
    sentAt: "2023-07-26T12:34:56Z",
    message: "This is another test message",
  },
];

describe("NotificationTable", () => {
  test("renders table with notifications", () => {
    render(<NotificationTable notifications={mockNotifications} />);
    expect(screen.getByText("ID")).toBeInTheDocument();
    expect(screen.getByText("Category")).toBeInTheDocument();
    expect(screen.getByText("Channel")).toBeInTheDocument();
    expect(screen.getByText("User Full Name")).toBeInTheDocument();
    expect(screen.getByText("Sent At")).toBeInTheDocument();
    expect(screen.getByText("Message")).toBeInTheDocument();

    expect(screen.getByText("John Doe")).toBeInTheDocument();
    expect(screen.getByText("Jane Smith")).toBeInTheDocument();
  });

  test("sorts notifications by sentAt date", () => {
    render(<NotificationTable notifications={mockNotifications} />);
    
    const sentAtHeader = screen.getByText("Sent At");
    fireEvent.click(sentAtHeader);
    fireEvent.click(sentAtHeader);

    const rows = screen.getAllByRole("row");
    expect(rows[1]).toHaveTextContent("Jane Smith");
    expect(rows[2]).toHaveTextContent("John Doe");
  });

  test("paginates notifications", () => {
    const manyNotifications = Array.from({ length: 15 }, (_, index) => ({
      id: index + 1,
      category: "Info",
      channel: "Email",
      userFullName: `User ${index + 1}`,
      sentAt: `2023-07-25T12:34:56Z`,
      message: `Message ${index + 1}`,
    }));

    render(<NotificationTable notifications={manyNotifications} />);

    expect(screen.getByText("User 1")).toBeInTheDocument();
    expect(screen.queryByText("User 11")).not.toBeInTheDocument();

    fireEvent.click(screen.getByLabelText("Next page"));

    expect(screen.queryByText("User 1")).not.toBeInTheDocument();
    expect(screen.getByText("User 11")).toBeInTheDocument();
  });

  test("filters notifications by search text", () => {
    render(<NotificationTable notifications={mockNotifications} />);
    
    fireEvent.change(screen.getByLabelText("Search"), { target: { value: "Jane" } });

    expect(screen.queryByText("John Doe")).not.toBeInTheDocument();
    expect(screen.getByText("Jane Smith")).toBeInTheDocument();
  });
});
