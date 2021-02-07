var express = require('express');
var router = express.Router();
var debug = require('debug')('printapp:server');
/* GET home page. */
router.get('/', function(req, res, next) {
  debug('ss');
  res.json({ user: 'tobi' });
  //res.render('index', { title: 'Express' });
});

module.exports = router;
