-- Create table
create table TWT_COUNTRY
(
  id                INTEGER not null,
  name              VARCHAR(250) not null,
  sys_delstate      INTEGER default 0 not null,
  sys_created       TIMESTAMP(6),
  sys_last_modified TIMESTAMP(6)
);
create table TWT_POST
(
  id                INTEGER not null,
  user_id           INTEGER not null,
  text              VARCHAR(500) not null,
  sys_delstate      INTEGER default 0 not null,
  sys_created       TIMESTAMP(6),
  sys_last_modified TIMESTAMP(6)
);
create table TWT_SEX
(
  id                INTEGER not null,
  name              VARCHAR(100) not null,
  sys_delstate      INTEGER default 0 not null,
  sys_last_modified TIMESTAMP(6),
  sys_created       TIMESTAMP(6)
);
create table TWT_SUBSCRIPTION
(
  id                    INTEGER not null,
  user_id               INTEGER not null,
  subscribed_on_user_id INTEGER not null,
  sys_delstate          INTEGER default 0 not null,
  sys_last_modified     TIMESTAMP(6),
  sys_created           TIMESTAMP(6)
);
create table TWT_USER
(
  id                INTEGER not null,
  first_name        VARCHAR(50),
  patronymic        VARCHAR(50),
  last_name         VARCHAR(150),
  login             VARCHAR(25) not null,
  password          VARCHAR(25) not null,
  sex_id            INTEGER,
  country_id        INTEGER,
  birth_date        TIMESTAMP(6),
  sys_delstate      INTEGER default 0 not null,
  sys_last_modified TIMESTAMP(6),
  sys_created       TIMESTAMP(6)
);
alter table TWT_USER
  add constraint TWT_USER_LOGIN_UQ unique (LOGIN);

-- Sequences

-- Create sequence 
create sequence TWT_COUNTRY_SEQ
start with 1
increment by 1;

-- Create sequence 
create sequence TWT_POST_SEQ
start with 21
increment by 1;

-- Create sequence 
create sequence TWT_SEX_SEQ
start with 21
increment by 1;

-- Create sequence 
create sequence TWT_SUBSCRIBE_SEQ
start with 1
increment by 1;

-- Create sequence 
create sequence TWT_SUBSCRIPTION_SEQ
start with 21
increment by 1;


-- Create sequence 
create sequence TWT_USER_SEQ
start with 21
increment by 1;





-- Triggers

create trigger TWT_COUNTRY_ID_BI before insert on twt_country referencing NEW AS newrow FOR EACH ROW SET newrow.id = NEXT VALUE FOR TWT_COUNTRY_SEQ, newrow.sys_created = sysdate, newrow.sys_last_modified = sysdate;

create trigger TWT_POST_ID_BI before insert on twt_post referencing NEW AS newrow FOR EACH ROW SET newrow.id = NEXT VALUE FOR TWT_COUNTRY_SEQ, newrow.sys_created = sysdate, newrow.sys_last_modified = sysdate;

create trigger TWT_SEX_ID_BI  before insert on twt_sex  referencing NEW AS newrow FOR EACH ROW SET newrow.id = NEXT VALUE FOR TWT_COUNTRY_SEQ, newrow.sys_created = sysdate, newrow.sys_last_modified = sysdate;

create trigger TWT_SUBSCRIPTION_ID_BI before insert on twt_SUBSCRIPTION referencing NEW AS newrow FOR EACH ROW SET newrow.id = NEXT VALUE FOR TWT_COUNTRY_SEQ, newrow.sys_created = sysdate, newrow.sys_last_modified = sysdate; 

create trigger TWT_USER_ID_BI before insert on twt_user  referencing NEW AS newrow FOR EACH ROW SET newrow.id = NEXT VALUE FOR TWT_COUNTRY_SEQ, newrow.sys_created = sysdate, newrow.sys_last_modified = sysdate;
