-- 开启事务
START TRANSACTION;

-- 插入用户数据
INSERT INTO users (username, password, uid, role)
VALUES
    ('张三',   MD5('123456'), 1001, 'normal'), -- 123
    ('李四',   MD5('123456'), 1002, 'admin'),  -- 1234
    ('王五',   MD5('123456'), 1003, 'normal'), -- test
    ('赵六',   MD5('123456'), 1004, 'admin'),  -- admin
    ('钱七',   MD5('123456'), 1005, 'normal'); -- password

-- 插入团队数据
INSERT INTO teams (name, description, tid)
VALUES
    ('技术部',   '负责研发与维护核心系统', 2001),
    ('市场部',   '负责公司市场推广与品牌建设', 2002),
    ('运营部',   '负责日常运营与用户服务', 2003);

-- 插入团队-用户关联数据
INSERT INTO teams_users (uid, tid, trole)
VALUES
    (1001, 2001, 'creator'),
    (1002, 2001, 'admin'),
    (1003, 2001, 'member'),
    (1004, 2002, 'creator'),
    (1005, 2003, 'creator'),
    (1001, 2003, 'member');

-- 提交事务
COMMIT;
