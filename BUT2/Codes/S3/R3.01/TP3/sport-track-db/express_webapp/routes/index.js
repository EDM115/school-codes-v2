var express = require("express");
var router = express.Router();

router.get('/', function(req, res, next) {
    res.render("index", { title: "SportTrack", user: req.session.user });
});

router.get("/about", function(req, res, next) {
    var fs = require("fs");
    var lines = 0;
    var directories = ["bin", "public/stylesheets", "routes", "views", ".", ".."];
    for (var i = 0; i < directories.length; i++) {
        var files = fs.readdirSync(directories[i]);
        for (var j = 0; j < files.length; j++) {
            var file = files[j];
            if (file.endsWith(".js") || file.endsWith(".css") || file.endsWith(".jade") || file.endsWith(".json")) {
                var content = fs.readFileSync(directories[i] + '/' + file, "utf8");
                lines += content.split("\n").length;
            }
        }
    }
    return res.render("about", { title: "À propos", lines: lines });
});

module.exports = router;
