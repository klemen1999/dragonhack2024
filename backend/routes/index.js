var express = require("express");
var User = require("../models/user");
var Exercise = require("../models/exercise");
var Challenge = require("../models/challenge");
var BestScore = require("../models/bestscore");
const { route } = require("../app");

var router = express.Router();

var init_database = require("../models/init_database");

router.get("/", (req, res) => {
    // console.log("Hello world");
});

router.post("/user", async (req, res) => {
    try {
        var userData = req.body;
        // console.log("User data:", userData);
        var userId = userData.userId;
        var userExists = await User.findOne({ userId });
        if (userExists) {
            return res.status(202).send("User already exists");
        }
        var user = await User.create(userData);
        // console.log("User created:", user);
        res.status(201).send("User created successfully");
    } catch (err) {
        console.error(err);
        res.status(500).send("Error creating user");
    }
});

router.get("/user/:id", async (req, res) => {
    try {
        var userId = req.params.id;
        var user = await User.findOne({ userId });
        // console.log("User data:", user);
        res.status(200).json(user);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving user");
    }
});

router.post("/exercise", async (req, res) => {
    try {
        var exerciseData = req.body;
        var userId = exerciseData.userId;
        var user = await User.findOne({ userId });
        if (!user) {
            return res.status(404).send("User not found");
        }else{
            exerciseData.userId = user;
        }
        // console.log("Exercise data:", exerciseData);
        var exercise = await Exercise.create(exerciseData);
        // console.log("Exercise created:", exercise);

        // fetch all exercises from this user that have same type as the newly added one
        var exercisesByType = await Exercise.find({ userId: user, type: exercise.type });
        // console.log("Exercises by type:", exercisesByType);
        // sort the exercises by score in descending order
        exercisesByType.sort((a, b) => b.score - a.score);
        // console.log("Sorted exercises by type:", exercisesByType);
        // get bestscore for this type of exercise of the user
        var bestscore = await BestScore.findOne({ userId: user, type: exercise.type });
        // console.log("Bestscore:", bestscore);
        // if the new exercise has a higher score than the bestscore, update the bestscore
        if (!bestscore || exercise.score > bestscore.score) {
            var bestscore = await BestScore.findOneAndUpdate(
                { userId: user, type: exercise.type },
                { reps: exercise.reps },
                { new: true, upsert: true }
            );
            // console.log("Bestscore updated:", bestscore);
        }
        res.status(200).send("Exercise created successfully");
    } catch (err) {
        console.error(err);
        res.status(500).send("Error creating exercise");
    }
});

router.get("/exercises/:userId", async (req, res) => {
    try {
        var userId = req.params.userId;
        // console.log("User ID:", userId);
        var user = await User.findOne({ userId });
        // console.log("User data:", user);
        if (!user) {
            return res.status(404).send("User not found");
        }
        var exercise = await Exercise.find({ userId: user });
        // console.log("Exercise data:", exercise);
        res.status(200).json(exercise);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving exercise");
    }
});

router.get("/exercises", async (req, res) => {
    try {
        var exercise = await Exercise.find();
        // console.log("Exercise data:", exercise);
        res.status(200).json(exercise);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving exercise");
    }
});

router.post("/challenge", async (req, res) => {
    // console.log("Creating challenge", req.body);
    try {
        var challengeData = req.body;
        // console.log("Challenge data:", challengeData);
        var userId = challengeData.userId;
        var user = await User.findOne({ userId });
        delete challengeData.userId
        // console.log("User ID:", userId);
        challengeData.participants = [user];
        // console.log("Challenge data:", challengeData);
        var challenge = await Challenge.create(challengeData);
        // console.log("Challenge created:", challenge);

        // add challenge to user's activeChallenges and todoChallenges
        var user = await User.findOneAndUpdate(
            { userId },
            { $push: { activeChallenges: challenge, todoChallenges: challenge } },
            { new: true }
        );

        res.status(200).send("Challenge created successfully");
    } catch (err) {
        console.error(err);
        res.status(500).send("Error creating challenge");
    }
});

router.get("/challenges", async (req, res) => {
    try {
        var challenge = await Challenge.find();
        // console.log("Challenge data:", challenge);
        res.status(200).json(challenge);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving challenge");
    }
}); 

router.put("/challenge", async (req, res) => {
    try {
        var userId = req.body.userId;
        var user = await User.findOne({ userId });
        if (!user) {
            return res.status(404).send("User not found");
        }
        var challengeId = req.body.challengeId;
        // find challenge by challengeId and update its participants with user
        var challenge = await Challenge.findOneAndUpdate(
            { challengeId },
            { $push: { participants: user } },
            { new: true }
        );
        // console.log("Challenge updated:", challenge);
        
        // update user's activeChallenges and todoChallenges with this challenge
        var user = await User.findOneAndUpdate(
            { userId },
            { $push: { activeChallenges: challenge, todoChallenges: challenge } },
            { new: true }
        ); 
        // console.log("User updated:", user);
        
        res.status(200).send("Challenge updated successfully");
    } catch (err) {
        console.error(err);
        res.status(500).send("Error updating challenge");
    }
});

router.get("/challenges/:userId", async (req, res) => {
    try {
        var userId = req.params.userId;
        var user = await User.findOne({ userId });
        if (!user) {
            return res.status(404).send("User not found");
        }
        var challenges = await Challenge.find({ participants: user });
        // console.log("Challenges data:", challenges);
        res.status(200).json(challenges);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving challenges");
    }
});

router.put("/challenge/exercise", async (req, res) => {
    try {
        var challengeId = req.body.challengeId;
        var exerciseId = req.body.exerciseId;
        var userId = req.body.userId;
        // remove this challenge from user's todoChallenges
        var user = await User.findOneAndUpdate(
            { userId },
            { $pull: { todoChallenges: challengeId } },
            { new: true }
        );
        // console.log("User updated:", user);
        // find challenge by challengeId and update its exercises with exerciseId
        var challenge = await Challenge.findOneAndUpdate(
            { challengeId },
            { $push: { exercises: exerciseId } },
            { new: true }
        );
        // console.log("Challenge updated:", challenge);
        res.status(200).send("Challenge updated successfully");
    } catch (err) {
        console.error(err);
        res.status(500).send("Error updating challenge");
    }
});

router.get("/bestscores/:userId", async (req, res) => {
    try {
        var userId = req.params.userId;
        var user = await User.findOne({ userId });
        if (!user) {
            return res.status(404).send("User not found");
        }
        var bestscores = await BestScore.find({ userId: user });
        // console.log("Bestscores data:", bestscores);
        res.status(200).json(bestscores);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving bestscores");
    }
});

// for specific challenge I want to get the 3 exercises with highest amout of reps
router.get("/challenge/:challengeId", async (req, res) => {
    try {
        var challengeId = req.params.challengeId;
        var challenge = await Challenge.findOne({ challengeId });
        if (!challenge) {
            return res.status(404).send("Challenge not found");
        }
        // find exercises of this challenge
        var exercises = challenge.exercises;
        // sort the exercise by reps and return three highest ones
        exercises.sort((a, b) => b.reps - a.reps);
        var topThreeExercises = exercises.slice(0, 3);
        // console.log("Top 3 exercises:", topThreeExercises);
        res.status(200).json(topThreeExercises);
    } catch (err) {
        console.error(err);
        res.status(500).send("Error retrieving challenge");
    }
});


module.exports = router;
