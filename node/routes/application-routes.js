const express = require("express");
const router = express.Router();

const { verifyAuthenticated } = require("../middleware/auth-middleware.js");
const articleDao = require("../modules/article-dao.js");
const notifyDao = require("../modules/notification-dao.js");
const analyticsDao = require("../modules/analytics-dao.js");
const { authenticate } = require("passport");
const userDao = require("../modules/user-dao.js");

router.post("/api/user", async function (req, res) {
    
    
    const users = await userDao.retrieveAllUsers();
    res.json(users);
});
router.delete("/api/user/:id", async function (req, res) {
    
   
    try {
        await userDao.deleteUser(req.params.id);
        res.status(204).end();
    } catch (err) {
        res.status(401).send({ message: 'Failed to delete user' });
    }
});



// Whenever we navigate to /home, verify that we're authenticated. If we are, render the home view.
router.get("/", async function (req, res) {
    const user = res.locals.user;
    let articles = await articleDao.retrieveAllArticles();
    if(user){
        const notifications = await notifyDao.retrieveNumberByUserId(user.user_id);
        res.locals.notifications = notifications[0];
    }

    //top 5 popular articles
    const top5Articles = await analyticsDao.getTop5PopularArticlesFromAll();
    res.locals.top5Article = top5Articles;

    //unread notication
    res.render("home", { articles, user,  });
});

router.get("/newNotifiesSummary",verifyAuthenticated , async function (req, res) {
    if(res.locals.user){
        const data = await notifyDao.retrieveNumberByUserId(res.locals.user.user_id)
        res.json(data[0]);
    }

});

router.get("/unreadNotifications", verifyAuthenticated, async function (req, res) {
    if(res.locals.user){
        const unreadNotifications = await notifyDao.retrieveUnreadNotificationsByUserId(res.locals.user.user_id);
        res.json(unreadNotifications);
    }

});


router.get("/notification/:id", verifyAuthenticated , async function (req, res) {
    const userid = res.locals.user.user_id; 
    const noId = req.params.id;
    if(res.locals.user){
        await notifyDao.updateNotificationStatus(noId, userid)
    }
    
});


module.exports = router;