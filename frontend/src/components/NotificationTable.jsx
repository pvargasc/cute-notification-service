import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TableSortLabel,
  TablePagination,
  TextField,
  Paper,
} from "@mui/material";
import { visuallyHidden } from "@mui/utils";

import { formatDate } from "../util/dateUtils";

const NotificationTable = ({ notifications }) => {
  const [order, setOrder] = useState("desc");
  const [orderBy, setOrderBy] = useState("sentAt");
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchText, setSearchText] = useState("");
  const [filteredData, setFilteredData] = useState(notifications);

  const columns = [
    { id: "id", label: "ID" },
    { id: "category", label: "Category" },
    { id: "channel", label: "Channel" },
    { id: "userFullName", label: "User Full Name" },
    { id: "sentAt", label: "Sent At" },
    { id: "message", label: "Message" },
  ];

  useEffect(() => {
    setFilteredData(
      notifications
        .map((notification) => ({
          ...notification,
          sentAt: formatDate(notification.sentAt),
        }))
        .filter((row) =>
          columns.some(
            (column) =>
              row[column.id]
                .toString()
                .toLowerCase()
                .indexOf(searchText.toLowerCase()) > -1
          )
        )
    );
  }, [searchText, notifications]);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleSearchChange = (event) => {
    setSearchText(event.target.value);
  };

  const sortedData = filteredData.sort((a, b) => {
    const aDate = new Date(a[orderBy]);
    const bDate = new Date(b[orderBy]);

    if (aDate < bDate) {
      return order === "asc" ? -1 : 1;
    }
    if (aDate > bDate) {
      return order === "asc" ? 1 : -1;
    }
    return 0;
  });

  const emptyRows =
    rowsPerPage - Math.min(rowsPerPage, sortedData.length - page * rowsPerPage);

  return (
    <Paper>
      <TextField
        label="Search"
        variant="outlined"
        value={searchText}
        onChange={handleSearchChange}
        style={{ margin: "20px" }}
      />
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  sortDirection={orderBy === column.id ? order : false}
                >
                  <TableSortLabel
                    active={orderBy === column.id}
                    direction={orderBy === column.id ? order : "asc"}
                    onClick={(event) => handleRequestSort(event, column.id)}
                  >
                    {column.label}
                    {orderBy === column.id ? (
                      <span style={visuallyHidden}>
                        {order === "desc"
                          ? "sorted descending"
                          : "sorted ascending"}
                      </span>
                    ) : null}
                  </TableSortLabel>
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {sortedData
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row, index) => (
                <TableRow hover role="checkbox" tabIndex={-1} key={index}>
                  {columns.map((column) => {
                    const value = row[column.id];
                    return <TableCell key={column.id}>{value}</TableCell>;
                  })}
                </TableRow>
              ))}
            {emptyRows > 0 && (
              <TableRow style={{ height: 53 * emptyRows }}>
                <TableCell colSpan={6} />
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 50]}
        component="div"
        count={filteredData.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
  );
};

export default NotificationTable;
