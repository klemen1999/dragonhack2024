const mongoose = require("mongoose");

const exerciseSchema = new mongoose.Schema({
    type: { type: String, required: true },
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    date: { type: Date, default: Date.now, required: false },
    duration: { type: Number, required: true },
    reps: { type: Number, required: true },
});

const Exercise = mongoose.model("Exercise", exerciseSchema, "Exercises");

module.exports = Exercise;