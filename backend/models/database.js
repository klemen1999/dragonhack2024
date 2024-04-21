const mongoose = require("mongoose");

var databaseURI = "mongodb+srv://user:user@cluster0.aqdkt6e.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

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
    console.log(`Mongoose is connected to ${databaseURI}.`);
});

mongoose.connection.on("error", (error) => {
    console.log("Mongoose error while connecting: ", error);
});

mongoose.connection.on("disconnected", () => {
    console.log("Mongoose is not connected.");
});

const pravilnaUstavitev = (message, povratniKlic) => {
    mongoose.connection.close(() => {
        console.log(`Mongoose closed the connection '${message}'.`);
        povratniKlic();
    });
};

// Restart nodemon
process.once("SIGUSR2", () => {
    pravilnaUstavitev("nodemon restart", () => {
        process.kill(process.pid, "SIGUSR2");
    });
});

// Application exited
process.on("SIGINT", () => {
    pravilnaUstavitev("application exited", () => {
        process.exit(0);
    });
});

// Application exited on Heroku
process.on("SIGTERM", () => {
    pravilnaUstavitev("application exited on Heroku", () => {
        process.exit(0);
    });
});


require("./exercise");
require("./challenge");
require("./user");
require("./bestscore");