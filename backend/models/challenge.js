const mongoose = require("mongoose");

const challengeSchema = new mongoose.Schema({
    participants: { type: [mongoose.Schema.Types.ObjectId], ref: 'User', default: [], required: false },
    exerciseType: { type: String, required: true },
    exercises: { type: [mongoose.Schema.Types.ObjectId], ref: 'Exercise', default: [], required: false },
    startTime: { type: Date, default: Date.now(), required: false },
    endTime: { type: Date, required: true },
    recurrence: { type: Number, required: true },
    description: { type: String, required: true },
});

const Challenge = mongoose.model("Challenge", challengeSchema, "Challenges");

module.exports = Challenge;