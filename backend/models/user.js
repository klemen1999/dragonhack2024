const mongoose = require("mongoose");

const userSchema = new mongoose.Schema({
    userId: { type: Number, required: true },
    name: { type: String, required: true },
    activeChallenges: { type: [mongoose.Schema.Types.ObjectId], ref: 'Challenge', default: [], required: false },
    todoChallenges: { type: [mongoose.Schema.Types.ObjectId], ref: 'Challenge', default: [], required: false },
    bestscores: { type: [mongoose.Schema.Types.ObjectId], ref: 'BestScore', default: [], required: false },
});

const User = mongoose.model("User", userSchema, "Users");

module.exports = User;
