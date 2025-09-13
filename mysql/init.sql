-- 用户库

CREATE DATABASE fluxsync_user;

USE fluxsync_user;

CREATE TABLE users (
    userrank  INT          NOT NULL AUTO_INCREMENT COMMENT '用户排序',
    username  VARCHAR(255) NOT NULL COMMENT '用户名',
    title VARCHAR(255) NOT NULL COMMENT '用户名称',
    password  CHAR(32)     NOT NULL COMMENT '用户密码',
    uid       INT          NOT NULL COMMENT '用户唯一标识符',
    regtime   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    role      ENUM('admin', 'normal')  NOT NULL DEFAULT 'normal' COMMENT '用户级别',
    PRIMARY KEY (userrank),
    UNIQUE KEY uk_users_uid (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

CREATE TABLE teams (
    tidrank     INT          NOT NULL AUTO_INCREMENT COMMENT '团队排序',
    title   VARCHAR(255) NOT NULL COMMENT '团队名',
    name        VARCHAR(255) NOT NULL COMMENT '团队名称',
    description TEXT         NOT NULL COMMENT '团队描述',
    createtime  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    tid         INT          NOT NULL COMMENT '团队唯一标识符',
    PRIMARY KEY (tidrank),
    UNIQUE KEY uk_teams_tid (tid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团队信息表';

CREATE TABLE teams_users (
    uid      INT       NOT NULL COMMENT '用户id',
    tid      INT       NOT NULL COMMENT '团队id',
    trole    ENUM('creator', 'admin', 'member') NOT NULL COMMENT '团队角色',
    jointime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (uid, tid),
    CONSTRAINT fk_teams_users_user
        FOREIGN KEY (uid) REFERENCES users (uid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_teams_users_team
        FOREIGN KEY (tid) REFERENCES teams (tid)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='团队成员关联表';

-- jwt 库
-- CREATE DATABASE fluxsync_jwt;
