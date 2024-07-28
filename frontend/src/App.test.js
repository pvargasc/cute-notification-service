import React from "react";
import { render } from "@testing-library/react";
import App from "../App";
import "@testing-library/jest-dom/extend-expect";

describe("App", () => {
  test("renders App component correctly", () => {
    const { container } = render(<App />);
    expect(container).toBeInTheDocument();
  });

  test("renders Home component within App", () => {
    const { getByText } = render(<App />);
    expect(getByText("Send Notification")).toBeInTheDocument();
    expect(getByText("Notifications")).toBeInTheDocument();
  });
});
