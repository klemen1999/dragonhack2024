const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
    userId: { type: number, required: true },
});

mongoose.model("User", userSchema, "Users");
