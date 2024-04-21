// populate user database with sample data
var User = require("./user");
var Challenge = require("./challenge");
var Exercise = require("./exercise");
var BestScore = require("./bestscore");
var mongoose = require("mongoose");
var db = mongoose.connection;
var users = [
  {
    userId: 1,
    name: "Alice Smith",
  },
  {
    userId: 2,
    name: "Bob Jones",
  },
  {
    userId: 3,
    name: "Charlie Brown",
  },
];

db.once("open", async () => {
  try {
    // await User.deleteMany({});
    // await Challenge.deleteMany({});
    // await Exercise.deleteMany({});
    // await BestScore.deleteMany({});
    await User.insertMany(users);
    users = await User.find({});

    var exercises = [
      {
        type: "pushup",
        userId: users[0],
        duration: 30,
        reps: 10,
      },
      {
        type: "pushup",
        userId: users[1],
        duration: 40,
        reps: 12,
      },
      {
        type: "squat",
        userId: users[2],
        duration: 50,
        reps: 15,
      },
      {
        type: "squat",
        userId: users[1],
        duration: 60,
        reps: 20,
      },
    ];
    await Exercise.insertMany(exercises);

    var challenges = [
      {
        participants: [users[0]],
        challengeId: 1,
        name: "Pushup Challenge",
        exerciseType: "pushup",
        endTime: "2023-10-20T00:00:00Z",
        description: "Do 10 pushups every day for 30 days",
        recurrence: 1,
      },
      {
        participants: [users[1]],
        challengeId: 2,
        name: "Squat Challenge",
        exerciseType: "squat",
        endTime: "2023-10-20T00:00:00Z",
        description: "Do 15 squats every week for 30 days",
        recurrence: 7,
      },
    ];
    await Challenge.insertMany(challenges);
    var challenge1 = await Challenge.findOne({ challengeId: 1 })
    await User.findOneAndUpdate({ userId: 1 }, 
    { $push: 
        { activeChallenges: challenge1, todoChallenges: challenge1 } 
    });
    var challenge2 = await Challenge.findOne({ challengeId: 2 });
    await User.findOneAndUpdate({ userId: 2 }, 
    { $push: 
        { activeChallenges: challenge2, todoChallenges: challenge2 } 
    });


    var bestscores = [
      {
        userId: users[0],
        type: "squat",
        reps: 50,
      },
      {
        userId: users[0],
        type: "squat",
        reps: 100,
      },
    ];
    await BestScore.insertMany(bestscores);

    console.log("Sample data added to database");
        
    var apiParameters = {
        server: "http://localhost:" + (process.env.PORT || 3000),
    };
    if (process.env.NODE_ENV === "production") {
    apiParameters.server = "TODO";
    }
    const axios = require("axios").create({
    baseURL: apiParameters.server,
    timeout: 5000,
    });

    axios.get("/user/2", {}).then((res) => {
        console.log(res.data);
    });

    axios.post("/exercise", { 
        userId: 3,
        type: "pushup",
        duration: 30,
        reps: 10
    }).then((res) => {
        console.log(res.data);
    }).catch((err) => {
        console.error(err);
    });

    axios.get("/exercises/2", {}).then((res) => {
        console.log("Exercises by user", res.data);
    });

    axios.get("/exercises").then((res) => {
        console.log("All exercises", res.data);
    });


  } catch (err) {
    console.error(err);
  }
});

