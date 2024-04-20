const mongoose = require("mongoose");

const exerciseSchema = new mongoose.Schema({
    exerciseId: { type: Number, required: true },
    name: { type: String, required: true },
    currMaxReps: { type: Number, required: false },
});

const Exercise = mongoose.model("Exercise", exerciseSchema, "Exercises");

module.exports = Exercise;