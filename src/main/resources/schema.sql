set mode MySQL;

DROP TABLE IF EXISTS tmp;
CREATE TABLE tmp (
    key VARCHAR(50) NOT NULL, 
    value VARCHAR(200) DEFAULT NULL, 
    desc VARCHAR(200) DEFAULT NULL, 
    PRIMARY KEY (key)
);

DROP TABLE IF EXISTS member;
CREATE TABLE `member` (
  `member_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `token` varchar(45) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `member_id_UNIQUE` (`member_id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS member_log;
CREATE TABLE `member_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(45) ,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TRIGGER member_update
BEFORE UPDATE
ON member
FOR EACH ROW
CALL "com.careline.interview.test.trigger.MemberUpdateTrigger"

