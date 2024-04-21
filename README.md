# DRAGONHACK2024

## Team KraškiBrancini hack
Erik Pahor, Aljoša Koren, Klemen Škrlj

### Project Inspiration
Our idea is to help you perform home workouts while enabling friendly competition with your friends along the way. To achieve this we've built an android app that uses a camera and AI model which detects your movement while you're doing the exercise. Depending on the exercise we can track movement of different keypoints and track how many repetitions you've done. The app is essentially used to verify that you actually did your part and not just put some numbers in your usual fitness tracker. We also enable participation in competitions where you and your friends can set a goal e.g. we'll do 20 pushups each day for a month or you can just brag to them with how many reps in a minute you can do.

### Project structure
 - **androidApp**
 
    We used Kotlin and JetPack Compose for building the Android app. For detecting person movement we used MediaPipe framework and the model that is provieded there. 
 - **backend**

    Here we used expressJS server and MongoDB to build the RestAPI for our frontend app.