const mongoose = require("mongoose");

const challengeSchema = new mongoose.Schema({
    challengeId: { type: Number, required: true },
    exerciseId: { type: Number, required: true },
    typeId: { type: Number, required: true },
    startDate: { type: Date, required: true },
    endDate: { type: Date, required: true },
    userIds: { type: [Number], required: true },
    samples: { type: [String], required: true },
});

const Challenge = mongoose.model("Challenge", challengeSchema);

module.exports = Challenge;