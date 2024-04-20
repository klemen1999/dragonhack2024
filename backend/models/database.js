const mongoose = require("mongoose");

var databaseURI = "mongodb://localhost/Sweatify";

if (process.env.NODE_ENV === "main") {
    databaseURI = process.env.MONGODB_CLOUD_URI;
}

mongoose.connect(databaseURI, {
    // useNewUrlParser: true,
    // useCreateIndex: true,
    // useUnifiedTopology: true,
    // useFindAndModify: false,
});

mongoose.connection.on("connected", () => {
    console.log(`Mongoose je povezan na ${databaseURI}.`);
});

mongoose.connection.on("error", (napaka) => {
    console.log("Mongoose napaka pri povezavi: ", napaka);
});

mongoose.connection.on("disconnected", () => {
    console.log("Mongoose ni povezan.");
});

const pravilnaUstavitev = (sporocilo, povratniKlic) => {
    mongoose.connection.close(() => {
        console.log(`Mongoose je zaprl povezavo preko '${sporocilo}'.`);
        povratniKlic();
    });
};

// Ponovni zagon nodemon
process.once("SIGUSR2", () => {
    pravilnaUstavitev("nodemon ponovni zagon", () => {
        process.kill(process.pid, "SIGUSR2");
    });
});

// Izhod iz aplikacije
process.on("SIGINT", () => {
    pravilnaUstavitev("izhod iz aplikacije", () => {
        process.exit(0);
    });
});

// Izhod iz aplikacije na Heroku
process.on("SIGTERM", () => {
    pravilnaUstavitev("izhod iz aplikacije na Heroku", () => {
        process.exit(0);
    });
});
