import React from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import {
  Button,
  TextField,
  MenuItem,
  FormControl,
  InputLabel,
  Select,
  Box
} from "@mui/material";
import { sendNotification } from "../services/api";

const SendNotificationForm = ({ categories, onSubmit }) => {
  const formik = useFormik({
    initialValues: {
      category: "",
      message: "",
    },
    validationSchema: Yup.object({
      category: Yup.string().required("Category is required"),
      message: Yup.string()
        .max(255, "Message must be 255 characters or less")
        .required("Message is required"),
    }),
    onSubmit: async (values, { resetForm }) => {
      try {
        await sendNotification(values);
        onSubmit();
        resetForm();
      } catch (error) {
        console.error(error);
      }
    },
  });

  const isSubmitDisabled = !formik.values.category || !formik.values.message;

  return (
    <Box
      component="form"
      onSubmit={formik.handleSubmit}
      sx={{ "& > :not(style)": { m: 1 } }}
    >
      <FormControl fullWidth variant="outlined">
        <InputLabel id="category-label">Category</InputLabel>
        <Select
          labelId="category-label"
          id="category"
          name="category"
          value={formik.values.category}
          onChange={formik.handleChange}
          label="Category"
        >
          <MenuItem value="" disabled>
            Select a category
          </MenuItem>
          {categories.map((cat) => (
            <MenuItem key={cat.id} value={cat.type}>
              {cat.type}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <TextField
        fullWidth
        id="message"
        name="message"
        label="Message"
        multiline
        rows={3}
        value={formik.values.message}
        onChange={formik.handleChange}
        onBlur={formik.handleBlur}
        placeholder="Write a CUTE message..."
        variant="outlined"
        inputProps={{ maxLength: 255 }}
      />

      <Button
        variant="contained"
        color="primary"
        type="submit"
        disabled={isSubmitDisabled}
      >
        Send Notification!
      </Button>
    </Box>
  );
};

export default SendNotificationForm;
