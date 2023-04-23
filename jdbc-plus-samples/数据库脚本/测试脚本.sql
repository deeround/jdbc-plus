
CREATE TABLE `test_user` (
  `name` varchar(100) DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  `id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测试用户表';

INSERT INTO `test_user` VALUES ('aaa','test_tenant_1','111679474268188'),('db1','test_tenant_1','db11680249732619'),('db1','test_tenant_1','db11680249981639'),('db1','test_tenant_1','db11680250055403'),('db1','test_tenant_2','db11680250395816'),('aaa','test_tenant_2','sb1679474362305'),('aaa','test_tenant_2','sb1679474453142');

