CREATE TABLE test_user
(
    name      varchar(100) DEFAULT NULL,
    tenant_id varchar(32)  DEFAULT NULL,
    id        varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO test_user
VALUES ('1', 'test_tenant_1', '111679474268188');
INSERT INTO test_user
VALUES ('2', 'test_tenant_1', 'db11680249732619');
INSERT INTO test_user
VALUES ('3', 'test_tenant_1', 'db11680249981639');
INSERT INTO test_user
VALUES ('4', 'test_tenant_1', 'db11680250055403');
INSERT INTO test_user
VALUES ('5', 'test_tenant_1', 'db11680250395816');
INSERT INTO test_user
VALUES ('6', 'test_tenant_2', 'sb1679474362305');
INSERT INTO test_user
VALUES ('7', 'test_tenant_2', 'sb1679474453142');

CREATE TABLE test_log_2023
(
    name      varchar(100) DEFAULT NULL,
    tenant_id varchar(32)  DEFAULT NULL,
    id        varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO test_log_2023
VALUES ('20231', 'test_tenant_1', '2023111679474268188');
INSERT INTO test_log_2023
VALUES ('20232', 'test_tenant_1', '2023db11680249732619');
INSERT INTO test_log_2023
VALUES ('20233', 'test_tenant_1', '2023db11680249981639');
INSERT INTO test_log_2023
VALUES ('20234', 'test_tenant_1', '2023db11680250055403');
INSERT INTO test_log_2023
VALUES ('20235', 'test_tenant_1', '2023db11680250395816');
INSERT INTO test_log_2023
VALUES ('20236', 'test_tenant_2', '2023sb1679474362305');
INSERT INTO test_log_2023
VALUES ('20237', 'test_tenant_2', '2023sb1679474453142');

