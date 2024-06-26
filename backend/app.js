require("dotenv").config();
var createError = require("http-errors");
var express = require("express");
var path = require("path");

var cors = require("cors");

require("./models/database"); // connect to MongoDB

// routing
var indexRouter = require("./routes/index");

var app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: false }));

app.use(
    cors({
        credentials: true,
    })
);

app.use("/api", (req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Vary", "Origin");
    res.header(
        "Access-Control-Allow-Headers",
        "Origin, X-Requested-With, Content-Type, Accept, Authorization"
    );
    res.header(
        "Access-Control-Allow-Methods",
        "POST, GET, PUT, DELETE, OPTIONS, HEAD"
    );
    next();
});

app.use("/", indexRouter);


// catch 404 and forward to error handler
app.use(function (req, res, next) {
    next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get("env") === "development" ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render("error");
});

module.exports = app;
