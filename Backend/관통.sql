-- 기존 데이터베이스 삭제
DROP DATABASE IF EXISTS ssafy_mobile_cafe;

-- 트랜잭션 격리 수준 설정
SELECT @@global.transaction_isolation, @@transaction_isolation;
SET @@transaction_isolation="read-committed";

-- 데이터베이스 생성 및 사용
CREATE DATABASE ssafy_mobile_cafe;
USE ssafy_mobile_cafe;

-- t_user 테이블 생성
CREATE TABLE t_user(
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    pass VARCHAR(100) NOT NULL,
    stamps INTEGER DEFAULT 0
);

-- t_product 테이블 생성
CREATE TABLE t_product(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    price INTEGER NOT NULL,
    img VARCHAR(100) NOT NULL,
    description VARCHAR(500) NOT NULL,
    mode VARCHAR(10) NOT NULL
);

-- t_order 테이블 생성
CREATE TABLE t_order(
    o_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    order_table VARCHAR(20),
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,    
    completed CHAR(1) DEFAULT 'N',
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
);

-- t_order_detail 테이블 생성
CREATE TABLE t_order_detail(
    d_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT fk_order_detail_product FOREIGN KEY (product_id) REFERENCES t_product(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_detail_order FOREIGN KEY(order_id) REFERENCES t_order(o_id) ON DELETE CASCADE
);                                                 

-- t_stamp 테이블 생성
CREATE TABLE t_stamp(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    order_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    CONSTRAINT fk_stamp_user FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_stamp_order FOREIGN KEY (order_id) REFERENCES t_order(o_id) ON DELETE CASCADE
);

-- t_comment 테이블 생성
CREATE TABLE t_comment(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    product_id INTEGER NOT NULL,
    rating FLOAT NOT NULL DEFAULT 1,
    comment VARCHAR(200),
    CONSTRAINT fk_comment_user FOREIGN KEY(user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_product FOREIGN KEY(product_id) REFERENCES t_product(id) ON DELETE CASCADE
);

-- t_fcm 테이블 생성 (새로 추가된 테이블)
CREATE TABLE t_fcm(
    user_id VARCHAR(100) NOT NULL,
    fcm_token VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, fcm_token),
    CONSTRAINT fk_fcm_user FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
);

-- t_shop 테이블 생성(매장 정보)
CREATE TABLE t_shop(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    description VARCHAR(500) NOT NULL,
    time VARCHAR(500) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

-- t_event 테이블 생성(이벤트 정보)
CREATE TABLE t_event(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    url VARCHAR(1000) NOT NULL
);

-- t_coupon 테이블 생성(쿠폰 정보)
CREATE TABLE t_coupon(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    name VARCHAR(200) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   
    price INTEGER NOT NULL,
    CONSTRAINT fk_coupon_user FOREIGN KEY(user_id) REFERENCES t_user(id) ON DELETE CASCADE
);

-- t_user 데이터 삽입
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id01', 'name01', 'pass01', 4);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id02', 'name02', 'pass02', 1);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id03', 'name03', 'pass03', 3);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id04', 'name04', 'pass04', 4);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id05', 'name05', 'pass05', 5);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id06', 'name06', 'pass06', 6);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id07', 'name07', 'pass07', 7);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id08', 'name08', 'pass08', 8);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id09', 'name09', 'pass09', 9);
INSERT INTO t_user (id, name, pass, stamps) VALUES ('id10', 'name10', 'pass10', 10);

-- t_product 데이터 삽입
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('아메리카노', 'beverage', 5000, 'americano.png', '진한 에스프레소에 시원한 정수물과 얼음을 더하여 스타벅스의 깔끔하고 강렬한 에스프레소를 가장 부드럽고 시원하게 즐길 수 있는 커피', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('카페라떼', 'beverage', 5500, 'cafelatte.png', '풍부하고 진한 에스프레소가 신선한 스팀 밀크를 만나 부드러워진 커피 위에 우유 거품을 살짝 얹은 대표적인 커피 라떼', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('카라멜 마끼야또', 'beverage', 5500, 'caramelmacchiato.png', '향긋한 바닐라 시럽과 시원한 우유에 얼음을 넣고 점을 찍듯이 에스프레소를 부은 후 벌집 모양으로 카라멜 드리즐을 올린 달콤한 커피 음료', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('카푸치노', 'beverage', 5500, 'cappuccino.png', '풍부하고 진한 에스프레소에 따뜻한 우유와 벨벳 같은 우유 거품이 1:1 비율로 어우러져 마무리된 커피 음료', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('모카라떼', 'beverage', 6000, 'mochalatte.png', '진한 초콜릿 모카 시럽과 풍부한 에스프레소를 신선한 우유 그리고 얼음과 섞어 휘핑크림으로 마무리한 음료로 진한 에스프레소와 초콜릿 맛이 어우러진 커피', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('민트라떼', 'beverage', 7000, 'mintlatte.png', '치약을 왜 마셔요?', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('화이트 모카라떼', 'beverage', 6000, 'whitemochalatte.png', '달콤하고 부드러운 화이트 초콜릿 시럽과 에스프레소를 스팀 밀크와 섞어 휘핑크림으로 마무리한 음료로 달콤함과 강렬한 에스프레소가 부드럽게 어우러진 커피', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('자몽에이드', 'beverage', 8000, 'grapefruitade.png', '맛있는 자몽 에이드', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('레몬에이드', 'beverage', 8000, 'lemonade.png', '맛있는 레몬 에이드', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('오렌지에이드', 'beverage', 8000, 'orandeade.png', '맛있는 오렌지 에이드', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('망고에이드', 'beverage', 8000, 'mangoade.png', '맛있는 망고 에이드', 'ICED');

-- t_comment 데이터 삽입
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id01', 1, 1, 'comment 01');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id02', 1, 2, 'comment 02');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id03', 1, 3, 'comment 03');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id04', 4, 4, 'comment 04');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id05', 5, 5, 'comment 05');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id06', 6, 6, 'comment 06');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id07', 7, 7, 'comment 07');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id08', 8, 8, 'comment 08');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id09', 9, 9, 'comment 09');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id10', 10, 10, 'comment 10');

-- t_order 데이터 삽입
INSERT INTO t_order (user_id, order_table) VALUES ('id01', 'order_table_01');
INSERT INTO t_order (user_id, order_table) VALUES ('id02', 'order_table_02');
INSERT INTO t_order (user_id, order_table) VALUES ('id03', 'order_table_03');
INSERT INTO t_order (user_id, order_table) VALUES ('id04', 'order_table_04');
INSERT INTO t_order (user_id, order_table) VALUES ('id05', 'order_table_05');
INSERT INTO t_order (user_id, order_table) VALUES ('id06', 'order_table_06');
INSERT INTO t_order (user_id, order_table) VALUES ('id07', 'order_table_07');
INSERT INTO t_order (user_id, order_table) VALUES ('id08', 'order_table_08');
INSERT INTO t_order (user_id, order_table) VALUES ('id09', 'order_table_09');
INSERT INTO t_order (user_id, order_table) VALUES ('id10', 'order_table_10');

-- t_order_detail 데이터 삽입
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 1, 1);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (1, 2, 3);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (2, 1, 1);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (3, 3, 3);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (4, 4, 4);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (5, 5, 5);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (6, 6, 6);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (7, 7, 7);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (8, 8, 8);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (9, 9, 9);
INSERT INTO t_order_detail (order_id, product_id, quantity) VALUES (10, 10, 10);

-- t_stamp 데이터 삽입
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id01', 1, 4);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id02', 2, 1);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id03', 3, 3);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id04', 4, 4);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id05', 5, 5);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id06', 6, 6);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id07', 7, 7);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id08', 8, 8);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id09', 9, 9);
INSERT INTO t_stamp (user_id, order_id, quantity) VALUES ('id10', 10, 10);

-- t_fcm 데이터 삽입 (가상의 FCM 토큰)
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id01', 'fcm_token_01');
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id01', 'fcm_token_02');
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id02', 'fcm_token_03');
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id03', 'fcm_token_04');
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id03', 'fcm_token_05');
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id04', 'fcm_token_06');
INSERT INTO t_fcm (user_id, fcm_token) VALUES ('id05', 'fcm_token_07');

-- t_shop 데이터 삽입(Mock-Up 매장 데이터)
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미인동', 'https://mblogthumb-phinf.pstatic.net/MjAyNDAxMThfMjUz/MDAxNzA1NTY2Mzg0Mzk2.gVwa4ygCav1gbmwGq2tWEtDvHU5ufrJVjJs-JZBIrM0g.QwyYd_P-C2LCjsTh3fEHJfAQl91scMSVaYR2gjown3Ag.JPEG.yosulpp/SE-b60fa36a-b42d-11ee-9a89-976840ec37c2.jpg?type=w800', '경상북도 구미시 인동가산로9-3, 노블레스타워 1층(황상동)', '평일 06:00 ~ 23:00
주말 07:00 ~ 23:00', 36.107860277822844, 128.41873514292232);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미인의DT', 'https://mblogthumb-phinf.pstatic.net/MjAyNDAxMDVfODIg/MDAxNzA0NDU3ODE1NjE2.3eBnacAfnkYIPrf0m1X5KrLaLfkmPak_na1ei7bZnMEg.kLKFbCAieqLlg1v80b0BWxBHfYWFCZtWA_y4oJJbrBUg.JPEG.m_4862/output_2270109392.jpg?type=w800', '경상북도 구미시 인동북길 149(인의동)', '평일 06:00 ~ 23:00
주말 07:00 ~ 23:00', 36.09565743046543, 128.43098473779384);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미공단', 'https://naverbooking-phinf.pstatic.net/20240611_120/1718104723924coCrQ_JPEG/image.jpg?type=f750_420_60_sharpen', '경상북도 구미시 1공단로212, HALLA SIGMA VALLEY 104...', '평일 06:00 ~ 23:00
주말 07:00 ~ 23:00', 36.101684061858755, 128.38592779265716);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미광평DT', 'https://naverbooking-phinf.pstatic.net/20240611_257/1718104467893XeEyG_JPEG/image.jpg?type=f750_420_60_sharpen', '경상북도 구미시 구미대로 188(광평동)', '평일 06:00 ~ 23:00
주말 07:00 ~ 23:00', 36.10361637149451, 128.36360282794408);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미옥계', 'https://mblogthumb-phinf.pstatic.net/MjAyMTA3MTRfMTEg/MDAxNjI2MjYyNzE4NDI5.AMdA_uB_i8FJNyhVzFx4pkGRyKqgzTkRRPUwbTEqflcg.wAI4J4M6MfW0_k3LKPyTGRpcii_cw-Alju6rgTGu0gog.JPEG.kilrboy89/SE-01f7a5c6-9709-4736-8f30-9f3c6d81df8a.jpg?type=w800', '경상북도 구미시 옥계북로20(양포동)', '평일 06:00 ~ 23:00
주말 07:00 ~ 23:00', 36.138290806168214, 128.4195495105708);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미확장단지', 'https://naverbooking-phinf.pstatic.net/20240611_236/1718104453700MIgDS_JPEG/image.jpg?type=f750_420_60_sharpen', '경상북도 구미시 산동면 신당1로4길 19', '평일 09:00 ~ 21:00
주말 09:00 ~ 21:00', 36.156240703496046, 128.4301959592791);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미시청DT', 'https://naverbooking-phinf.pstatic.net/20240611_31/1718104161298DQzaH_JPEG/image.jpg', '경상북도 구미시 송정대로 27', '평일 09:00 ~ 21:00
주말 09:00 ~ 21:00', 36.156240703496046, 128.4301959592791);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미상모DT', 'https://mblogthumb-phinf.pstatic.net/MjAyNDA1MDlfMzYg/MDAxNzE1MjM5MDMyNjEw.3TGO5p1aI4G3QUdCRZRIkRUTSr1skGAXGd3GLIrv4Kwg.MCoRjRujN8Y-UdhM5Hk-e0AYQIsR5z54-6hD0QGtUAAg.JPEG/20240509%EF%BC%BF161612%EF%BC%BF835.jpg?type=w800', '경상북도 구미시 금오대로 439', '평일 09:00 ~ 21:00
주말 09:00 ~ 21:00', 36.08225546213961, 128.35640083138827);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미금오산DT', 'https://blog.kakaocdn.net/dn/Kd17x/btrTRedi8j3/mxrTFcMeK8OGbz9ZE5arAk/img.jpg', '경상북도 구미시 남통동 금오산로 205', '평일 09:00 ~ 21:00
주말 09:00 ~ 21:00', 36.12225927143425, 128.32358396526578);

-- t_event 데이터 삽입(Mock-Up 이벤트 데이터)
INSERT INTO t_event (name, image, url) VALUES ('이벤트 01', 'https://img.freepik.com/free-psd/coffee-concept-banner-template_23-2148448356.jpg', 'https://www.starbucks.co.kr/index.do');
INSERT INTO t_event (name, image, url) VALUES ('이벤트 02', 'https://img.freepik.com/free-psd/coffee-shop-template-design_23-2150855323.jpg', 'https://www.starbucks.co.kr/index.do');
INSERT INTO t_event (name, image, url) VALUES ('이벤트 03', 'https://img.freepik.com/free-psd/coffee-cup-banner-template_23-2148818258.jpg?semt=ais_hybrid', 'https://www.starbucks.co.kr/index.do');

INSERT INTO t_coupon (user_id, name, image, price) VALUES('id01', '기본', '', 1000);
INSERT INTO t_coupon (user_id, name, image, price) VALUES('id01', '기본1', '', 1000);
INSERT INTO t_coupon (user_id, name, image, price) VALUES('id02', '기본1', '', 1000);

SELECT * FROM t_coupon WHERE user_id = 'id01';

UPDATE t_user SET pass = 'bb' WHERE id = 'id01';

-- 트랜잭션 커밋
COMMIT;