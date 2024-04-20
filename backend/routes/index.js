var express = require("express");
var User = require("../models/user");
var Exercise = require("../models/exercise");
var Type = require("../models/type");
var Leaderboard = require("../models/leaderboard");
var Challenge = require("../models/challenge");

var router = express.Router();

router.get("/", (req, res) => {
    console.log("Hello world");
});

router.get("/user/:id", async (req, res) => {
    try {
        var userId = req.params.id;
        var user = await User.findOne({ userId });
        console.log("User data:", user);
        res.status(200).json(user);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving user");
    }
});

router.post("/user", async (req, res) => {
    try {
        var userData = req.body;
        console.log("User data:", userData);
        var user = await User.create(userData);
        console.log("User created:", user);
        res.status(200).send("User created successfully");
    } catch (err) {
        console.error(err);
        res.status(500).send("Error creating user");
    }
});

module.exports = router;
