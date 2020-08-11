/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/6/7 11:52:33                            */
/*==============================================================*/
drop database  if exists Chat_Online_System;

create database Chat_Online_System DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

use Chat_Online_System;

drop table if exists User;

drop table if exists friend;

drop table if exists message;

drop table if exists tag;

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   user_id              varchar(15) not null,
   user_nickname        varchar(30) not null,
   user_password        varchar(30) not null,
   user_avatar          varchar(50) not null,
   user_gender          varchar(10),   
   user_birthday        date,
   user_home            varchar(50),
   user_sign            varchar(100),
   primary key (user_id)
);

/*==============================================================*/
/* Table: friend                                                */
/*==============================================================*/
create table friend
(
   friend_a_id          varchar(15) not null,
   friend_b_id          varchar(15) not null,
   friend_remark        varchar(30) not null,
   friend_stat          int not null,
   primary key (friend_a_id,friend_b_id)
);

/*==============================================================*/
/* Table: message                                               */
/*==============================================================*/
create table message
(
   message_id           int not null auto_increment,
   message_sender_id    varchar(15) not null,
   message_receiver_id  varchar(15) not null,
   message_time         datetime not null,
   message_content      varchar(1024) not null,
   message_stat         int not null,
   primary key (message_id)
);

/*==============================================================*/
/* Table: tag                                                   */
/*==============================================================*/
create table tag
(
   tag_id               int not null auto_increment,
   tag_sender_id        varchar(15) not null,
   tag_receiver_id      varchar(15) not null,
   tag_content          varchar(50) not null,
   primary key (tag_id)
);

alter table friend add constraint FK_friend foreign key (friend_a_id)
      references User (user_id) on delete restrict on update restrict;

alter table friend add constraint FK_friend2 foreign key (friend_b_id)
      references User (user_id) on delete restrict on update restrict;

alter table message add constraint FK_message foreign key (message_sender_id)
      references User (user_id) on delete restrict on update restrict;

alter table message add constraint FK_message2 foreign key (message_receiver_id)
      references User (user_id) on delete restrict on update restrict;

alter table tag add constraint FK_tag foreign key (tag_sender_id)
      references User (user_id) on delete restrict on update restrict;

alter table tag add constraint FK_tag2 foreign key (tag_receiver_id)
      references User (user_id) on delete restrict on update restrict;

