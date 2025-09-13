-- 使用目标数据库
USE
fluxsync_user;

START TRANSACTION;

-- =====================
-- 1) 用户表：users
-- =====================
INSERT INTO users (username, title, password, uid, role)
VALUES
    ('admin', '管理员', 'e10adc3949ba59abbe56e057f20f883e', 1001, 'admin'),
    ('zhangsan', '张三', 'e10adc3949ba59abbe56e057f20f883e', 1002, 'normal'),
    ('lisi', '李四', 'e10adc3949ba59abbe56e057f20f883e', 1003, 'normal'),
    ('wangwu', '王五', 'e10adc3949ba59abbe56e057f20f883e', 1004, 'normal'),
    ('zhaoliu', '赵六', 'e10adc3949ba59abbe56e057f20f883e', 1005, 'normal'),
    ('sunqi', '孙七', 'e10adc3949ba59abbe56e057f20f883e', 1006, 'normal'),
    ('liuba', '刘八', 'e10adc3949ba59abbe56e057f20f883e', 1007, 'normal'),
    ('chenjiu', '陈九', 'e10adc3949ba59abbe56e057f20f883e', 1008, 'normal');

-- =====================
-- 2) 团队表：teams
-- =====================
INSERT INTO teams (title, name, description, tid)
VALUES
    ('develop', '研发团队', '负责产品研发与技术迭代', 2001),
    ('marketing', '市场团队', '负责市场推广、品牌与增长', 2002),
    ('ops', '运维团队', '负责基础设施与稳定性保障', 2003),
    ('product', '产品团队', '负责需求分析、产品设计与规划', 2004);

-- =====================
-- 3) 团队成员关联：teams_users
--    注意：包含 admin 与 develop 的关联（creator）
-- =====================
INSERT INTO teams_users (uid, tid, trole)
VALUES
    -- develop 团队
    (1001, 2001, 'creator'),
    (1002, 2001, 'admin'),
    (1003, 2001, 'member'),
    (1004, 2001, 'member'),

    -- marketing 团队
    (1005, 2002, 'creator'),
    (1006, 2002, 'member'),

    -- ops 团队
    (1007, 2003, 'creator'),
    (1003, 2003, 'admin'),
    (1004, 2003, 'member'),

    -- product 团队
    (1008, 2004, 'creator'),
    (1002, 2004, 'member');

COMMIT;
