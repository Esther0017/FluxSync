-- 用户库

CREATE DATABASE fluxsync_user;

USE fluxsync_user;

CREATE TABLE users
(
    uid      INT          NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识符',
    username VARCHAR(255) NOT NULL COMMENT '用户名',
    nickname VARCHAR(255) COMMENT '用户名称',
    password CHAR(32)     NOT NULL COMMENT '用户密码',
    regtime  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    role     ENUM('admin', 'normal')  NOT NULL DEFAULT 'normal' COMMENT '用户级别',
    PRIMARY KEY (uid),
    UNIQUE KEY uk_user_username (username)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

CREATE TABLE teams
(
    tid         INT          NOT NULL AUTO_INCREMENT COMMENT '团队唯一标识符',
    name       VARCHAR(255) NOT NULL COMMENT '团队名',
    description TEXT         NOT NULL COMMENT '团队描述',
    createtime  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (tid),
    UNIQUE KEY uk_teams_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队信息表';

CREATE TABLE teams_users
(
    uid      INT       NOT NULL COMMENT '用户id',
    tid      INT       NOT NULL COMMENT '团队id',
    trole    ENUM('creator', 'admin', 'member') NOT NULL COMMENT '团队角色',
    jointime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (uid, tid),
    KEY idx_tid (tid),
    CONSTRAINT fk_teams_users_user
        FOREIGN KEY (uid) REFERENCES users (uid)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_teams_users_team
        FOREIGN KEY (tid) REFERENCES teams (tid)
            ON DELETE CASCADE
            ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='团队成员关联表';

CREATE TABLE totp_info
(
    username    VARCHAR(255) NOT NULL COMMENT '用户名',
    totp_secret VARCHAR(32)  NOT NULL COMMENT 'totp密钥',
    totp_enable BOOLEAN DEFAULT FALSE COMMENT '是否开启totp校验',
    PRIMARY KEY (username),
    FOREIGN KEY (username) REFERENCES users (username)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='totp信息表';