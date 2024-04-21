const mongoose = require("mongoose");

const bestScoreSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    type: { type: String, required: true },
    reps: {type: Number, required: true},
});

const BestScoreSchema = mongoose.model("BestScore", bestScoreSchema, "BestScores");

module.exports = BestScoreSchema;
