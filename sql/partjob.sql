/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 5.7.19 : Database - partjob
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`partjob` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `partjob`;

/*Table structure for table `msg_info` */

DROP TABLE IF EXISTS `msg_info`;

CREATE TABLE `msg_info` (
  `id` varchar(32) NOT NULL COMMENT '消息id',
  `from_user_id` varchar(32) NOT NULL COMMENT '消息发送者id',
  `from_user_name` varchar(50) NOT NULL COMMENT '消息发送者名称',
  `to_user_id` varchar(32) NOT NULL COMMENT '消息接收者id',
  `to_user_name` varchar(50) NOT NULL COMMENT '消息接收者名称',
  `content` varchar(200) NOT NULL COMMENT '消息内容',
  `create_time` datetime NOT NULL COMMENT '消息发送时间',
  `un_read_flag` bit(1) NOT NULL COMMENT '是否已读（1 已读）',
  `msg_type` varchar(32) NOT NULL COMMENT '消息类型(1文本 2图片)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='消息表';

/*Data for the table `msg_info` */

insert  into `msg_info`(`id`,`from_user_id`,`from_user_name`,`to_user_id`,`to_user_name`,`content`,`create_time`,`un_read_flag`,`msg_type`) values 
('1638458805266210817','0d3e9db3f6384177955b124bef61bb31','luyi16','96f36fae91564e5781fc28c5e423378f','wenxv12','12','2023-03-22 16:33:14','\0','1');

/*Table structure for table `part_job` */

DROP TABLE IF EXISTS `part_job`;

CREATE TABLE `part_job` (
  `part_job_id` varchar(32) NOT NULL COMMENT '主键',
  `title` varchar(32) NOT NULL COMMENT '标题',
  `description` longtext NOT NULL COMMENT '描述',
  `category_id` int(11) NOT NULL COMMENT '兼职类别id',
  `salary` int(11) NOT NULL COMMENT '薪酬',
  `salary_category` varchar(2) DEFAULT NULL COMMENT '薪酬类别(日结、月结)',
  `is_inside_school` varchar(2) DEFAULT NULL COMMENT '校内，校外',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `del_flag` bit(1) NOT NULL COMMENT '删除标记',
  `user_id` varchar(32) NOT NULL COMMENT '创建的用户id',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论数',
  PRIMARY KEY (`part_job_id`) USING BTREE,
  KEY `title` (`title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='兼职信息表';

/*Data for the table `part_job` */

insert  into `part_job`(`part_job_id`,`title`,`description`,`category_id`,`salary`,`salary_category`,`is_inside_school`,`create_time`,`del_flag`,`user_id`,`comment_count`) values 
('02dd8ad930214e6a9604cfaf11030cc5','2213123','123123',2,123123,'月结','校内','2022-03-26 21:08:32','\0','88b90ed634254122ba0ffb52dd3fabc5',NULL),
('1b3241749d6a46748f2cb9f7006a916b','awda','wdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadawwdadaw',2,123,'日结','校内','2022-04-02 15:58:46','','96f36fae91564e5781fc28c5e423378f',9),
('5657e90886aa479797bdd2e56f24c9e8','123123','123123',2,123123,'日结','校内','2022-03-26 21:07:21','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('60bf8b7f5ab141cab8b213fcf5b1ad00','123','123123123',3,123123,'月结','校外','2022-03-27 10:39:11','','96f36fae91564e5781fc28c5e423378f',NULL),
('6ec2cd8b681f4fa986f21479771c5cf7','食堂窗口帮忙','在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333；在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333,在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333；在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333,在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333；在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333,在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333；在东区食堂一楼窗口帮忙，每天中午十点到下午两点，晚上六点到八点，薪资方面可以再谈。联系电话：111122223333,\n\n',3,500,'月结','校内','2022-03-26 18:43:26','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('70a37861e9be4bb5a11f24a041062263','帮忙翻译一篇法语文章','会法语的朋友的帮忙翻译一篇文章1',2,200,'日结','校内','2022-03-26 19:51:18','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('7209d8f2a24846c98ff425474e56a521','123','123123123',3,123123,'月结','校外','2022-03-27 10:39:00','','96f36fae91564e5781fc28c5e423378f',NULL),
('7a1aaa6d5275457cbc7ea67d1e55661f',' 2312312','123123',2,123123,'日结','校内','2022-03-27 11:58:06','','96f36fae91564e5781fc28c5e423378f',NULL),
('81f80d26247148d58f6b77fa38bd7ee9','13123123131231231312312313','1·2·12',2,123123,'日结','校内','2022-03-27 11:47:24','','96f36fae91564e5781fc28c5e423378f',NULL),
('8460beea5ff64f87a72ba2f5bc207d4f','dwadwa','adawda',2,1212,'日结','校内','2022-03-29 18:30:42','\0','88b90ed634254122ba0ffb52dd3fabc5',NULL),
('9e77df412a0d4f1891fca10317506ce5','13123123','1·2·121·2·121·2·121·2·121·2·121·2·12',2,123123,'日结','校外','2022-03-27 11:47:11','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('a26c34d881734dc59f20e2375ce08f83','dawdawda','adad',3,12,'日结','校内','2022-04-02 15:49:27','\0','0d3e9db3f6384177955b124bef61bb31',NULL),
('a926818a2c3d4769969bc2edec87f292','213','123',2,213,'日结','校内','2022-03-26 21:05:05','','96f36fae91564e5781fc28c5e423378f',NULL),
('acccfa9e894145edb35a9c2c63bd1cc3','扫大街','校内扫大街1',3,1000,'月结','校内','2022-03-23 20:04:18','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('aebdb5204dbf40c7a1d7c43c363457d8','演唱会保安','咸宁本地演唱会保安工作1',3,200,'日结','校外','2022-03-26 18:44:05','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('b64ef024029945b9af4ff5a3d50e9e18','校外餐厅帮忙','东区校外餐厅帮忙',3,2000,'月结','校外','2022-04-11 20:05:37','\0','5d5b287ae53c4450a00410a831e9be4e',1),
('d77b2ffa5ef04f75811f883d7cc67857','整理资料1','学校图书馆整理资料1',4,1000,'月结','校外','2022-03-26 16:04:32','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('e222f1c10eed4b91ababa70334998965','校外餐馆服务员','校外参观当服务员',3,1000,'月结','校外','2022-03-27 10:38:08','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('e99c9ac195504942993affab9d330057','地铁安检','寒暑假地铁站安检工作1',3,2000,'月结','校外','2022-03-26 18:44:54','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('efe2c9d84a9a4240897521ff4bda4eb3','发传单','校内发传单',1,100,'日结','校内','2022-03-23 19:54:25','\0','96f36fae91564e5781fc28c5e423378f',NULL),
('fdd84fa66fcf4deb9cc000024ed7fe71','校内发传单','校内午饭时间发传单',3,100,'日结','校内','2022-04-06 11:02:56','\0','96f36fae91564e5781fc28c5e423378f',3);

/*Table structure for table `part_job_category` */

DROP TABLE IF EXISTS `part_job_category`;

CREATE TABLE `part_job_category` (
  `category_id` varchar(32) NOT NULL COMMENT '主键',
  `category_name` varchar(32) NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='兼职类别表';

/*Data for the table `part_job_category` */

insert  into `part_job_category`(`category_id`,`category_name`) values 
('1','家教'),
('2','专业技能'),
('3','服务业'),
('4','其他');

/*Table structure for table `part_job_comment` */

DROP TABLE IF EXISTS `part_job_comment`;

CREATE TABLE `part_job_comment` (
  `part_job_comment_id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `part_job_id` varchar(32) NOT NULL COMMENT '兼职id',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `DEL_FLAG` bit(1) NOT NULL COMMENT '删除标记',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级评论id',
  `to_uid` varchar(32) DEFAULT NULL COMMENT '上级评论的用户id',
  `level` varchar(1) NOT NULL COMMENT '是够有子评论',
  PRIMARY KEY (`part_job_comment_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='兼职评论表';

/*Data for the table `part_job_comment` */

insert  into `part_job_comment`(`part_job_comment_id`,`user_id`,`part_job_id`,`content`,`create_time`,`DEL_FLAG`,`parent_id`,`to_uid`,`level`) values 
('2bcb48bb92204275820423f44758cb48','0d3e9db3f6384177955b124bef61bb31','1b3241749d6a46748f2cb9f7006a916b','hell','2022-04-03 18:29:48','\0','3ca6719279fd4a7d895dfebfce4b4bfd','96f36fae91564e5781fc28c5e423378f','2'),
('2f317931e3b74c12b83c7401f572282a','0d3e9db3f6384177955b124bef61bb31','1b3241749d6a46748f2cb9f7006a916b','大家好啊','2022-04-03 18:30:02','\0','',NULL,'1'),
('3ca6719279fd4a7d895dfebfce4b4bfd','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','nihaoaa','2022-04-03 18:27:40','\0','',NULL,'1'),
('46e1699844c44fa7acf3b7ac4ad51829','96f36fae91564e5781fc28c5e423378f','fdd84fa66fcf4deb9cc000024ed7fe71','还不错','2022-04-06 11:55:08','\0','',NULL,'1'),
('5001753a85e846578a7729786f7e8ace','eea9da225a9040c18b41d8afe3c041f2','b64ef024029945b9af4ff5a3d50e9e18','hao','2022-04-23 15:22:53','\0','',NULL,'1'),
('74ada804020b45229b6188bfa62fc7a0','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','12312313','2022-04-03 18:37:24','\0','',NULL,'1'),
('8d51194a5fbe419f91b33f6c81a1b81e','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','122','2022-04-03 18:32:56','\0','2f317931e3b74c12b83c7401f572282a','0d3e9db3f6384177955b124bef61bb31','2'),
('98121bc8a7ae4c0885de79b151d924e3','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','enennihao','2022-04-03 18:28:27','\0','3ca6719279fd4a7d895dfebfce4b4bfd','96f36fae91564e5781fc28c5e423378f','2'),
('b6ba0398464043b089874225e7f5b609','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','nihaonihao','2022-04-03 18:32:26','\0','2f317931e3b74c12b83c7401f572282a','0d3e9db3f6384177955b124bef61bb31','2'),
('b97824f9dc724c7f91add24a633e984c','88b90ed634254122ba0ffb52dd3fabc5','fdd84fa66fcf4deb9cc000024ed7fe71','感谢','2022-04-20 17:09:56','\0','46e1699844c44fa7acf3b7ac4ad51829','96f36fae91564e5781fc28c5e423378f','2'),
('c2312106c5d74a488813bd00d6dc1c31','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','hahaha','2022-04-03 18:32:45','\0','2f317931e3b74c12b83c7401f572282a','0d3e9db3f6384177955b124bef61bb31','2'),
('cc55096c4eb240a0bdd58d027aaaec5d','5d5b287ae53c4450a00410a831e9be4e','fdd84fa66fcf4deb9cc000024ed7fe71','还可以','2022-04-11 20:05:05','\0','',NULL,'1'),
('d0b1799126d14bce8b5c0f121f6a3d52','96f36fae91564e5781fc28c5e423378f','1b3241749d6a46748f2cb9f7006a916b','nihao','2022-04-03 18:28:04','\0','3ca6719279fd4a7d895dfebfce4b4bfd','96f36fae91564e5781fc28c5e423378f','2');

/*Table structure for table `session_list` */

DROP TABLE IF EXISTS `session_list`;

CREATE TABLE `session_list` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) NOT NULL COMMENT '所属用户',
  `to_user_id` varchar(32) NOT NULL COMMENT '到用户',
  `list_name` varchar(50) NOT NULL COMMENT '会话名称',
  `un_read_count` int(6) NOT NULL COMMENT '未读消息数',
  `to_user_avatar` varchar(255) DEFAULT NULL COMMENT '目标用户头像',
  `remark` varchar(32) DEFAULT NULL COMMENT '目标用户备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='会话列表';

/*Data for the table `session_list` */

insert  into `session_list`(`id`,`user_id`,`to_user_id`,`list_name`,`un_read_count`,`to_user_avatar`,`remark`) values 
('129523912aa648b09f14076ed22fa236','88b90ed634254122ba0ffb52dd3fabc5','96f36fae91564e5781fc28c5e423378f','wenxv',0,'http://localhost:8088/files/0b68af05bb8e43c3b13a192277875520','张三'),
('13cd2df685b64544b4d797645da2cbe4','5d5b287ae53c4450a00410a831e9be4e','96f36fae91564e5781fc28c5e423378f','wenxv12',1,'http://localhost:8088/files/04c3578f02394ea88409e84c2eb1a621',NULL),
('2e053a780cc148a48c5c46f23371943e','eea9da225a9040c18b41d8afe3c041f2','5d5b287ae53c4450a00410a831e9be4e','小张',0,'http://localhost:8088/files/28274a47cf394275af9d84d1246165d4',NULL),
('2fcb5d5621124a3ea36166ea5a8e1db1','96f36fae91564e5781fc28c5e423378f','88b90ed634254122ba0ffb52dd3fabc5','xiaom',0,'http://localhost:8088/files/97d7a7b1d3834865bb5ce481dcf57765','王五'),
('98ace0e42e164d43a87d8b4a981c1561','0d3e9db3f6384177955b124bef61bb31','96f36fae91564e5781fc28c5e423378f','wenxv12',0,'http://localhost:8088/files/9074b4b3fe5945ce9696ab449c57e181',NULL),
('9bdfcf44f28445f2873cc33e72c1be28','ee29d5f4e9ac4faf8b21cdfb244edc11','96f36fae91564e5781fc28c5e423378f','admin',0,'http://localhost:8088/files/0b68af05bb8e43c3b13a192277875520',NULL),
('c59ab927af7c4190bd4e84bdb249b843','96f36fae91564e5781fc28c5e423378f','ee29d5f4e9ac4faf8b21cdfb244edc11','wxx',0,'http://localhost:8088/files/97d7a7b1d3834865bb5ce481dcf57765',NULL),
('d8859e991a694a3cbe0a20a5a92c282b','5d5b287ae53c4450a00410a831e9be4e','eea9da225a9040c18b41d8afe3c041f2','赵六',0,'http://localhost:8088/files/58cf2a266f7144c9ada3c327f4c3ef70',NULL),
('dc2c3f561d6549dd9d12e0c06d453dd3','96f36fae91564e5781fc28c5e423378f','5d5b287ae53c4450a00410a831e9be4e','小张',0,'http://localhost:8088/files/28274a47cf394275af9d84d1246165d4',NULL),
('efbfdeb458204d37985a5a8ef11d57b0','96f36fae91564e5781fc28c5e423378f','0d3e9db3f6384177955b124bef61bb31','luyi16',1,'http://localhost:8088/files/704d218856a2462bbcbe6141a6d286e8',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` varchar(32) NOT NULL COMMENT '主键',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nickname` varchar(32) NOT NULL COMMENT '昵称',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(32) NOT NULL COMMENT '电子邮箱',
  `sex` varchar(2) NOT NULL COMMENT '性别',
  `enrollment_year` varchar(32) DEFAULT NULL COMMENT '入学年份',
  `del_flag` bit(1) NOT NULL COMMENT '删除标记',
  `salt` varchar(32) NOT NULL COMMENT '用于加密密码的随机盐',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像的链接',
  PRIMARY KEY (`user_id`) USING BTREE,
  KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

/*Data for the table `user` */

insert  into `user`(`user_id`,`username`,`password`,`nickname`,`phone`,`email`,`sex`,`enrollment_year`,`del_flag`,`salt`,`avatar`) values 
('0d3e9db3f6384177955b124bef61bb31','luyi1','407d93ae9fd87e66bd50118e0fd52965','luyi16',NULL,'1253088506@qq.com','男','0','\0','6oo9DW!N',''),
('1903a4f00307472fa0c47ea82ccd6492','liuyyy','5a6ab36e804b7bffddd48e069181588b','zhangs',NULL,'3047200846@qq.com','男','1971','\0','6^rC0MDL','http://localhost:8088/files/97d7a7b1d3834865bb5ce481dcf57765'),
('5d5b287ae53c4450a00410a831e9be4e','yue234','7c0bc0471eb43ee15413c1dc828c5a08','小张',NULL,'3047200846@qq.com','男','2025','\0','^M8Njhi)','http://localhost:8088/files/28274a47cf394275af9d84d1246165d4'),
('5fe2adf11a65403b982eaffc5863a549','wangwu','d0ee79fd56bb0e4073a4481b7bed909f','wangliu',NULL,'3047200846@qq.com','男','2020','\0','wt6r6CES','http://localhost:8088/files/564be1caa16a4d84bcb50b4ff4ced4cb'),
('8072e5de9d544b7a8a3269c00491c7ba','213213','312fd86925f781898bc6da5140674349','wx','123','3047200846@qq.com','女','2022','\0','YO%VpsQW','http://localhost:8088/files/97d7a7b1d3834865bb5ce481dcf57765'),
('88b90ed634254122ba0ffb52dd3fabc5','yue0620','09b5b932e2c6d498f5a540a5a71a56ea','xiaomin',NULL,'3047200846@qq.com','男','2020','\0','P0X9tWsk','http://localhost:8088/files/0b68af05bb8e43c3b13a192277875520'),
('96f36fae91564e5781fc28c5e423378f','admin','2fadc7f5c1e9a206c21b7bfc7ffa20e8','wenxv12','17635013670','3047200846@qq.com','男','2018','\0','2($fO1I5','http://localhost:8088/files/04c3578f02394ea88409e84c2eb1a621'),
('ee29d5f4e9ac4faf8b21cdfb244edc11','liuyy','595afdd06ef735a516c42c29d6df3543','wxx',NULL,'3047200846@qq.com','男','2021','\0','802b!xO1','http://localhost:8088/files/97d7a7b1d3834865bb5ce481dcf57765'),
('eea9da225a9040c18b41d8afe3c041f2','yue123','3b53cf0d26d5492d6a9c6139815b4fcd','赵六',NULL,'3047200846@qq.com','男','2024','\0','Svj8Di$k','http://localhost:8088/files/58cf2a266f7144c9ada3c327f4c3ef70');

/*Table structure for table `user_part_job_collect` */

DROP TABLE IF EXISTS `user_part_job_collect`;

CREATE TABLE `user_part_job_collect` (
  `user_part_job_collect_id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `part_job_id` varchar(32) NOT NULL COMMENT '兼职id',
  `collect_time` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`user_part_job_collect_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户兼职收藏关系表';

/*Data for the table `user_part_job_collect` */

insert  into `user_part_job_collect`(`user_part_job_collect_id`,`user_id`,`part_job_id`,`collect_time`) values 
('098ee2e4b9c84b1f8e02ed0c7ce9bfcf','96f36fae91564e5781fc28c5e423378f','acccfa9e894145edb35a9c2c63bd1cc3','2022-03-23 21:35:04'),
('27bc8eb48c1944de9c604023c703df47','96f36fae91564e5781fc28c5e423378f','5657e90886aa479797bdd2e56f24c9e8','2022-03-27 11:35:06'),
('736c51e697c74c09b7585c95281128f7','eea9da225a9040c18b41d8afe3c041f2','b64ef024029945b9af4ff5a3d50e9e18','2022-04-23 15:22:57'),
('878083b2fe094879945c8d998afcda77','96f36fae91564e5781fc28c5e423378f','efe2c9d84a9a4240897521ff4bda4eb3','2022-03-23 21:34:54'),
('95266f56777d4316816aeababc88bbf0','96f36fae91564e5781fc28c5e423378f','02dd8ad930214e6a9604cfaf11030cc5','2022-03-29 18:23:01');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
