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
    stamps INTEGER DEFAULT 0,
    alarm_mode BOOLEAN DEFAULT TRUE,
    app_theme INTEGER DEFAULT 0
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
    description VARCHAR(500) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    iat_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    exp_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    menu_id INTEGER(100) NOT NULL,
    menu_count INTEGER(100) NOT NULL,
    CONSTRAINT fk_coupon_user FOREIGN KEY(user_id) REFERENCES t_user(id) ON DELETE CASCADE
);

-- t_ready 테이블 생성
CREATE TABLE t_ready (
    o_id INTEGER PRIMARY KEY,          -- t_order의 o_id와 동일 (Foreign Key)
    pick_up BOOLEAN DEFAULT FALSE,    -- 픽업 상태 (default: FALSE)
    CONSTRAINT fk_ready_order FOREIGN KEY (o_id) REFERENCES t_order(o_id) ON DELETE CASCADE
);

-- t_alarm 테이블 생성
CREATE TABLE t_alarm (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    sent_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alarm_user FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
);

-- t_coupon_template 테이블 생성
CREATE TABLE t_coupon_template (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    image VARCHAR(255),
    menu_id INT,
    menu_count INT
);

-- t_attendance 테이블 생성
CREATE TABLE t_attendance (
    user_id VARCHAR(100) NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    day INT NOT NULL CHECK (day BETWEEN 1 AND 31),
    attended BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (user_id, year, month, day),
    CONSTRAINT fk_attendance_user FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
);

-- t_chat 테이블 생성
CREATE TABLE t_chat (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    level TINYINT NOT NULL,
    plan TEXT NOT NULL,
    progress TEXT,
    alert_interval_minutes INT DEFAULT NULL,
    next_alarm_time TIMESTAMP DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE
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

-- t_product 데이터 삽입(ICED 음료)
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
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('콜드브루', 'beverage', 9000, 'coldbrew.png', '부드럽고 깔끔한 콜드브루 커피', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('콜드브루 라떼', 'beverage', 9000, 'coldbrewlatte.png', '우유를 섞어 부드럽게 즐기는 콜드브루', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('복숭아 아이스티', 'beverage', 9000, 'peachicetea.png', '달콤한 복숭아향의 아이스티', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('요거트 스무디', 'beverage', 9000, 'yogurtsmoothie.png', '신선한 과일과 요거트를 섞은 스무디', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('샤인 머스캣 에이드', 'beverage', 9000, 'shinemuscatade.png', '달콤한 샤인머스캣 맛이 나는 에이드', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('블루 레몬에이드', 'beverage', 9000, 'bluelemonade.png', '시각적으로도 아름다운 파란색 에이드', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('베리베리 스무디', 'beverage', 9000, 'berryberrysmoothie.png', '블루베리, 라즈베리, 딸기를 활용한 스무디', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('크림 스콘 라떼', 'beverage', 9000, 'creamsconelattehot.png', '스콘과 잘 어울리는 크림 라떼', 'ICED');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('토피넛 라떼', 'beverage', 9000, 'toffenutlatte.png', '달콤하고 고소한 토피넛 맛의 라떼', 'ICED');

-- t_product 데이터 삽입(HOT 음료)
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('바닐라라떼', 'beverage', 9000, 'vanilalattehot.png', '부드럽고 달콤한 바닐라 시럽이 들어간 라떼', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('헤이즐넛라떼', 'beverage', 9000, 'hazzlenutlattehot.png', '고소한 헤이즐넛 향이 나는 라떼', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('아인슈페너', 'beverage', 9000, 'einspannerhot.png', '휘핑크림이 올라간 달콤한 비엔나 커피', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('티라미수 라떼', 'beverage', 9000, 'tiramisulattehot.png', '티라미수 맛이 나는 크리미한 음료', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('녹차라떼', 'beverage', 9000, 'greentealattehot.png', '진한 말차 맛의 녹차 라떼', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('고구마라떼', 'beverage', 9000, 'sweetpotatolatte.png', '부드럽고 달콤한 고구마 음료', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('초콜릿라떼', 'beverage', 9000, 'chocolatelattehot.png', '진한 초콜릿 베이스의 핫초코 음료', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('얼그레이 티', 'beverage', 9000, 'earlgreyteahot.png', '은은한 향의 클래식 티', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('유자차', 'beverage', 9000, 'yuzuteahot.png', '상큼한 유자향의 따뜻한 티', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('레몬 진저 티', 'beverage', 9000, 'lemongingerteahot.png', '상큼하고 따뜻한 레몬과 생강 베이스의 티', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('캐모마일 티', 'beverage', 9000, 'chamomileteahot.png', '은은한 허브 향이 나는 캐모마일', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('아포가또', 'beverage', 9000, 'affogatohot.png', '바닐라 아이스크림에 에스프레소를 부어 먹는 디저트', 'HOT');

-- t_product 데이터 삽입(푸드)
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('크로와상', 'food', 9000, 'croissant.png', '버터 향이 풍부한 바삭하고 부드러운 크로와상', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('애플 파이', 'food', 9000, 'applepie.png', '따뜻한 사과와 시나몬이 어우러진 디저트 파이', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('베이글 & 크림치즈', 'food', 9000, 'bagelcreamcheese.png', '고소한 베이글에 크림치즈를 곁들인 메뉴', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('초콜릿 브라우니', 'food', 9000, 'chocolatebrownie.png', '진한 초콜릿 맛의 촉촉한 브라우니', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('바질 치킨 샌드위치', 'food', 9000, 'basilchickensandwich.png', '바질 향이 나는 닭가슴살 샌드위치', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('클래식 클럽 샌드위치', 'food', 9000, 'classicclubsandwich.png', '햄, 치즈, 토마토, 양상추로 채운 간단한 샌드위치', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('치즈 플레이트', 'food', 9000, 'cheeseplate.png', '다양한 치즈와 과일, 크래커를 함께 제공', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('포카치아', 'food', 9000, 'focaccia.png', '올리브 오일과 허브로 풍미를 더한 이탈리아 빵', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('프렌치 토스트', 'food', 9000, 'frenchtoast.png', '계란과 우유에 적셔 구운 달콤한 토스트', 'HOT');
INSERT INTO t_product (name, type, price, img, description, mode) VALUES ('스콘 & 잼', 'food', 9000, 'sconejam.png', '고소한 스콘에 잼과 버터를 곁들인 메뉴', 'HOT');

-- t_comment 데이터 삽입
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id01', 1, 5, '좋아요 고소하네요.');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id02', 1, 2, 'comment 02');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id03', 1, 3, 'comment 03');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id04', 4, 4, '깊은 맛이느껴집니다.');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id05', 5, 5, '천사의 맛.');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id06', 6, 1, 'comment 06');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id07', 7, 2, 'comment 07');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id08', 8, 3, '살짝 싱거움');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id09', 9, 4, '호출호 없을 맛');
INSERT INTO t_comment (user_id, product_id, rating, comment) VALUES ('id10', 10, 5, '누가 먹어도 맛있음');

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
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미인동', 'gumi_indong.jpg', '경상북도 구미시 인동가산로9-3, 노블레스타워 1층(황상동)', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.107860277822844, 128.41873514292232);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미인의DT', 'gumi_inuidt.jpg', '경상북도 구미시 인동북길 149(인의동)', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.09565743046543, 128.43098473779384);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미공단', 'gumi_gongdan.jpg', '경상북도 구미시 1공단로212, HALLA SIGMA VALLEY 104...', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.101684061858755, 128.38592779265716);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미광평DT', 'gumi_gwangpyeongdt.jpg', '경상북도 구미시 구미대로 188(광평동)', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.10361637149451, 128.36360282794408);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미옥계', 'gumi_okgae.jpg', '경상북도 구미시 옥계북로20(양포동)', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.138290806168214, 128.4195495105708);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미확장단지', 'gumi_hwakjang.jpg', '경상북도 구미시 산동면 신당1로4길 19', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.156240703496046, 128.4301959592791);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미시청DT', 'gumi_shicheongdt.jpg', '경상북도 구미시 송정대로 27', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.156240703496046, 128.4301959592791);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미상모DT', 'gumi_sangmodt.jpg', '경상북도 구미시 금오대로 439', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.08225546213961, 128.35640083138827);
INSERT INTO t_shop (name, image, description, time, latitude, longitude) VALUES ('구미금오산DT', 'gumi_geumosandt.jpg', '경상북도 구미시 남통동 금오산로 205', '평일 08:00 ~ 23:00
주말 08:00 ~ 23:00', 36.12225927143425, 128.32358396526578);

-- t_event 데이터 삽입(Mock-Up 이벤트 데이터)
INSERT INTO t_event (name, image, url) VALUES ('이벤트 01', 'event_banner_01.png', 'https://www.starbucks.co.kr/index.do');
INSERT INTO t_event (name, image, url) VALUES ('이벤트 02', 'event_banner_02.png', 'https://www.starbucks.co.kr/index.do');
INSERT INTO t_event (name, image, url) VALUES ('이벤트 03', 'event_banner_03.png', 'https://www.starbucks.co.kr/index.do');
INSERT INTO t_event (name, image, url) VALUES ('이벤트 04', 'event_banner_04.png', 'https://www.starbucks.co.kr/index.do');

INSERT INTO t_coupon (user_id, name, description, image, menu_id, menu_count) VALUES('id01', '테스트용 쿠폰', '테스트용 쿠폰입니다.', 'americano_coupon.png', 1, 1);
INSERT INTO t_coupon (user_id, name, description, image, menu_id, menu_count) VALUES('id01', '테스트용 쿠폰', '테스트용 쿠폰입니다.', 'americano_coupon.png', 1, 1);
INSERT INTO t_coupon (user_id, name, description, image, menu_id, menu_count) VALUES('id02', '테스트용 쿠폰', '테스트용 쿠폰입니다.', 'americano_coupon.png', 1, 1);

SELECT * FROM t_coupon WHERE user_id = 'id01';

select 
            COALESCE(SUM(d.quantity), 0) AS total_quantity
        from t_order o 
        join t_order_detail d on o.o_id = d.order_id
        join t_product p on d.product_id = p.id
        where p.id = 11
        order by d.d_id ASC;

-- t_coupon_template 테이블에 t_product 데이터를 삽입하는 SQL
INSERT INTO t_coupon_template (name, description, image, menu_id, menu_count)
SELECT 
    name,                       -- 메뉴 이름
    description,                -- 메뉴 설명
    img,                        -- 이미지 파일 경로
    id AS menu_id,              -- 메뉴 ID
    1 AS menu_count             -- 기본 쿠폰 발행 개수 (1개)
FROM t_product;

-- 트랜잭션 커밋
COMMIT;