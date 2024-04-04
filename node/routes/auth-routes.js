const { v4: uuid } = require("uuid");
const express = require("express");
const router = express.Router();
// const bcrypt = require('bcrypt');

// The DAO that handles CRUD operations for users.
const userDao = require("../modules/user-dao.js");
const { hashPassword } = require("../middleware/hashing-middleware.js");

// Whenever we navigate to /login, if we're already logged in, redirect to "/".
// Otherwise, render the "login" view.
router.get("/login", function (req, res) {

    if (res.locals.user) {
        res.redirect("/");
    }

    else {
        res.render("login");
    }

});

// Whenever we POST to /login, check the username and password submitted by the user.
// If they match a user in the database, give that user an authToken, save the authToken
// in a cookie, and redirect to "/". Otherwise, redirect to "/login", with a "login failed" message.
router.post("/login", async function (req, res) {

    // Get the username and password submitted in the form
    const username = req.body.username;
    const password = req.body.password;

    // Find a matching user in the database
    // const user = await userDao.retrieveUserWithCredentials(username, password);
    const user = await userDao.retrieveUserByUsername(username);




    if (user) {
        // Compare the submitted password with the hashed password in the database
        const match = bcrypt.compareSync(password, user.password);
        
        if (match) {
            // Auth success - give that user an authToken, save the token in a cookie.
            const authToken = uuid();
            user.authToken = authToken;
            await userDao.updateUser(user);
            res.cookie("authToken", authToken);
            res.locals.user = user;
            req.session.userId = user.user_id;
            res.status(204).redirect("/");  // Return a 204 status code
        } else {
            // Auth fail - password doesn't match
            res.status(401);
            res.redirect("/login")
        }
    } else {
        // Auth fail - user doesn't exist
        res.status(401);
        res.redirect("/login")
    }
});
const bcrypt = require('bcrypt');

router.post("/api/login", async function (req, res) {
    //get the username and password submitted in the form
    const {username, password} = req.body;
    // console.log(req.body);
    //find the user in the database
    const user = await userDao.retrieveUserByUsername(username);
    //if the user doesnt exist, or password is incorrect, return a 401 status code
    if(!user){
        res.status(401).json({message: "cannot find the user"});
        return;
    }
    //use bcrypt.compare to verify the password
    const match = await bcrypt.compare(password, user.password);
    console.log(match);
    if(match){
        //password match, now check if user is an admin
        console.log(match);
        if(user.is_admin){
            //user is an admin, return a 204 status code
            res.status(204).json({message: "Login successful"});
        } else {
            //user is not an admin, return a 401 status code
            res.status(401).json({message: "Incorrect username or password"});
        }
    } else {
        //password doesnt match, return a 401 status code
        res.status(401).json({message: "Incorrect username or password"});
    }
});



// Whenever we navigate to /logout, delete the authToken cookie.
// redirect to "/login", supplying a "logged out successfully" message.
router.get("/logout", function (req, res) {
    res.clearCookie("authToken");

    res.status(204).redirect("/");
    res.setToastMessage("Logged out successfully!");
   // res.redirect("/login");

});

// Account creation
router.get("/signup", function (req, res) {
    res.render("signup");
});


//check username availability
router.get("/checkUsername", async function (req, res) {
    const username = req.query.username;

    // If the request comes from our JavaScript code for username checking
    const existingUser = await userDao.retrieveUserByUsername(username);
    if (existingUser) {
        res.json({ isTaken: true, message: "The username is already taken." });
    } else {
        res.json({ isTaken: false, message: "The username is available." });
    }
});

router.post("/newAccount", async function (req, res) {

    const user = {
        username: req.body.username,
        password: req.body.password,
        name: req.body.fullName,
        date: req.body.date,
        gender: req.body.gender,
        country: req.body.country,
        description: req.body.description,
        avatarID: req.body.avatar
    };


        try {
            await userDao.createUser(user);
            res.setToastMessage("Account creation successful. Please login using your new credentials.");
            res.redirect("/login");
        }
        catch (err) {
            res.setToastMessage("That username was already taken!");
            res.redirect("/signup");
        }

    });



module.exports = router;