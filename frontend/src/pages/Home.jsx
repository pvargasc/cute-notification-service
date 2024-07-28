import React, { useEffect, useState } from "react";
import { Container, Grid, Paper, Typography, Box } from "@mui/material";
import SendNotificationForm from "../components/SendNotificationForm";
import NotificationTable from "../components/NotificationTable";
import { getCategories, fetchNotifications } from "../services/api";

const Home = () => {
  const [notifications, setNotifications] = useState([]);
  const [categories, setCategories] = useState([]);

  const getNotifications = async () => {
    try {
      const data = await fetchNotifications();
      setNotifications(data);
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  };

  const fetchCategories = async () => {
    try {
      const categoriesData = await getCategories();
      setCategories(categoriesData);
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  useEffect(() => {
    getNotifications();
    fetchCategories();
  }, []);

  return (
    <Container maxWidth="xl" style={{ marginTop: "20px" }}>
      <Grid container spacing={3}>
        <Grid item xs={12} md={4}>
          <Paper elevation={3} style={{ padding: "20px" }}>
            <Typography variant="h5" gutterBottom>
              Send Notification
            </Typography>
            <SendNotificationForm
              categories={categories}
              onSubmit={getNotifications}
            />
          </Paper>
        </Grid>
        <Grid item xs={12} md={8}>
          <Paper elevation={3} style={{ padding: "20px" }}>
            <Typography variant="h5" gutterBottom>
              Notifications
            </Typography>
            <NotificationTable notifications={notifications} />
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Home;
