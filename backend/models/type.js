const mongoose = require("mongoose");

const typeSchema = new mongoose.Schema({
    typeId: { type: Number, required: true },
    exerciseId: { type: mongoose.Schema.Types.ObjectId, ref: 'Exercise', required: true },
    bodyPart: { type: String, required: true },
    recurrenceDays: { type: Number, required: true },
});

const Type = mongoose.model("Type", typeSchema);

module.exports = Type;
