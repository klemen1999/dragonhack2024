const mongoose = require("mongoose");

const leaderboardSchema = new mongoose.Schema({
    userId: { type: Number, required: true },
    exerciseId: { type: Number, required: true },
    typeId: { type: Number, required: true },
    score: { type: Number, required: true },
    createdAt: { type: Date, default: Date.now },
});

const Leaderboard = mongoose.model("Leaderboard", leaderboardSchema);

module.exports = Leaderboard;