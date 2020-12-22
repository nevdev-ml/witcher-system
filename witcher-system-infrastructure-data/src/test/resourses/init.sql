--
-- testQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

-- Started on 2020-12-21 17:13:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 16738)
-- Name: authority; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.authority (
    id bigint NOT NULL,
    name character varying(50)
);


ALTER TABLE public.authority OWNER TO test;

--
-- TOC entry 201 (class 1259 OID 16726)
-- Name: authority_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.authority_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.authority_seq OWNER TO test;

--
-- TOC entry 208 (class 1259 OID 16743)
-- Name: bank; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.bank (
    id bigint NOT NULL,
    king_repository boolean
);


ALTER TABLE public.bank OWNER TO test;

--
-- TOC entry 209 (class 1259 OID 16748)
-- Name: bank_deposit; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.bank_deposit (
    bank_id bigint NOT NULL,
    deposit_id bigint NOT NULL
);


ALTER TABLE public.bank_deposit OWNER TO test;

--
-- TOC entry 202 (class 1259 OID 16728)
-- Name: bank_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.bank_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bank_seq OWNER TO test;

--
-- TOC entry 210 (class 1259 OID 16751)
-- Name: beast; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.beast (
    id bigint NOT NULL,
    beast_name integer
);


ALTER TABLE public.beast OWNER TO test;

--
-- TOC entry 203 (class 1259 OID 16730)
-- Name: beast_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.beast_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.beast_seq OWNER TO test;

--
-- TOC entry 211 (class 1259 OID 16756)
-- Name: deal; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.deal (
    completion_on timestamp without time zone,
    create_on timestamp without time zone,
    customer_id bigint,
    description character varying(255),
    done boolean,
    executor_id bigint,
    paid boolean,
    sale boolean,
    title character varying(255),
    trader boolean,
    reward_id bigint NOT NULL
);


ALTER TABLE public.deal OWNER TO test;

--
-- TOC entry 212 (class 1259 OID 16764)
-- Name: deal_user; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.deal_user (
    deal_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.deal_user OWNER TO test;

--
-- TOC entry 213 (class 1259 OID 16767)
-- Name: deal_user_bookmarked; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.deal_user_bookmarked (
    deal_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.deal_user_bookmarked OWNER TO test;

--
-- TOC entry 214 (class 1259 OID 16770)
-- Name: deposit; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.deposit (
    id bigint NOT NULL,
    balance double precision,
    type integer
);


ALTER TABLE public.deposit OWNER TO test;

--
-- TOC entry 204 (class 1259 OID 16732)
-- Name: deposit_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.deposit_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.deposit_seq OWNER TO test;

--
-- TOC entry 215 (class 1259 OID 16775)
-- Name: location; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.location (
    id bigint NOT NULL,
    region integer
);


ALTER TABLE public.location OWNER TO test;

--
-- TOC entry 205 (class 1259 OID 16734)
-- Name: location_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.location_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.location_seq OWNER TO test;

--
-- TOC entry 216 (class 1259 OID 16780)
-- Name: reward; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.reward (
    id bigint NOT NULL,
    reward double precision,
    tax double precision,
    type integer
);


ALTER TABLE public.reward OWNER TO test;

--
-- TOC entry 206 (class 1259 OID 16736)
-- Name: reward_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.reward_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.reward_seq OWNER TO test;

--
-- TOC entry 217 (class 1259 OID 16785)
-- Name: task; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.task (
    completion_on timestamp without time zone,
    create_on timestamp without time zone,
    customer_id bigint,
    done boolean,
    location_comment character varying(255),
    paid boolean,
    title character varying(255),
    witcher_id bigint,
    reward_id bigint NOT NULL,
    location_id bigint
);


ALTER TABLE public.task OWNER TO test;

--
-- TOC entry 218 (class 1259 OID 16793)
-- Name: task_beast; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.task_beast (
    task_id bigint NOT NULL,
    beast_id bigint NOT NULL
);


ALTER TABLE public.task_beast OWNER TO test;

--
-- TOC entry 219 (class 1259 OID 16796)
-- Name: task_witcher; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.task_witcher (
    task_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.task_witcher OWNER TO test;

--
-- TOC entry 220 (class 1259 OID 16799)
-- Name: task_witcher_completed; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.task_witcher_completed (
    task_id bigint NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE public.task_witcher_completed OWNER TO test;

--
-- TOC entry 200 (class 1259 OID 16552)
-- Name: user; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public."user" (
    email character varying(255),
    enabled boolean,
    first_name character varying(255),
    last_name character varying(255),
    last_password_reset_date timestamp without time zone,
    password character varying(255),
    reset_token character varying(255),
    role integer,
    username character varying(32),
    bank_id bigint NOT NULL
);


ALTER TABLE public."user" OWNER TO test;

--
-- TOC entry 221 (class 1259 OID 16802)
-- Name: user_authority; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.user_authority (
    user_id bigint NOT NULL,
    authority_id bigint NOT NULL
);


ALTER TABLE public.user_authority OWNER TO test;

--
-- TOC entry 222 (class 1259 OID 16805)
-- Name: users; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.users (
    email character varying(255),
    enabled boolean,
    first_name character varying(255),
    last_name character varying(255),
    last_password_reset_date timestamp without time zone,
    password character varying(255),
    reset_token character varying(255),
    role integer,
    username character varying(32),
    bank_id bigint NOT NULL
);


ALTER TABLE public.users OWNER TO test;

--
-- TOC entry 3113 (class 0 OID 16738)
-- Dependencies: 207
-- Data for Name: authority; Type: TABLE DATA; Schema: public; Owner: test
--

INSERT INTO public.authority (id, name) VALUES (1,'KING'),(2,'WITCHER'),(3,'USER'),(4,'VENDOR'),(5,'BLACKSMITH');

--
-- TOC entry 3114 (class 0 OID 16743)
-- Dependencies: 208
-- Data for Name: bank; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.bank (id, king_repository) VALUES (1,TRUE),(2,FALSE),(3,FALSE),(4,FALSE),(5,FALSE),(6,FALSE);

--
-- TOC entry 3115 (class 0 OID 16748)
-- Dependencies: 209
-- Data for Name: bank_deposit; Type: TABLE DATA; Schema: public; Owner: test
--

INSERT INTO public.bank_deposit (bank_id, deposit_id) VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(2,6),(3,7),(3,8),(3,9),(4,10),(4,11),(4,12),(5,13),(5,14),(5,15),(6,16),(6,17),(6,18);

--
-- TOC entry 3116 (class 0 OID 16751)
-- Dependencies: 210
-- Data for Name: beast; Type: TABLE DATA; Schema: public; Owner: test
--

INSERT INTO public.beast (id, beast_name) VALUES (1,2),(2,4),(3,5),(4,1),(5,7),(6,0);

--
-- TOC entry 3117 (class 0 OID 16756)
-- Dependencies: 211
-- Data for Name: deal; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.deal (completion_on, create_on, customer_id, description, done, executor_id, paid, sale, title, trader, reward_id) 
VALUES (NULL,'2020-12-21 13:41:47.436',5,'Покупка желудка чудовищ',FALSE,NULL,FALSE,TRUE,'Покупка желудка чудовищ',TRUE, 11),
(NULL,'2020-12-21 13:41:47.471',5,'Продажа глаз гуля',FALSE,NULL,FALSE,FALSE,'Продажа глаз гуля',TRUE,12),
(NULL,'2020-12-21 13:41:47.48',5,'Продажа шкуры чудовищ',FALSE,NULL,FALSE,FALSE,'Продажа шкуры чудовищ',TRUE,13),
(NULL,'2020-12-21 13:41:47.49',5,'Покупка мозга утопца',FALSE,NULL,FALSE,TRUE,'Покупка мозга утопца',TRUE,14),
(NULL,'2020-12-21 13:41:47.499',5,'Продажа крови гнильца',FALSE,NULL,FALSE,FALSE,'Продажа крови гнильца',TRUE,15),
(NULL,'2020-12-21 13:41:47.506',6,'Ремонт стальных мечей',FALSE,NULL,FALSE,FALSE,'Ремонт стальных мечей',FALSE,16),
(NULL,'2020-12-21 13:41:47.512',6,'Выкуп кольчужных доспех',FALSE,NULL,FALSE,TRUE,'Выкуп кольчужных доспех',FALSE,17),
(NULL,'2020-12-21 13:41:47.518',6,'Выкуп рунных мечей',FALSE,NULL,FALSE,TRUE,'Выкуп рунных мечей',FALSE,18),
(NULL,'2020-12-21 13:41:47.525',6,'Выкуп магических амулетов',FALSE,NULL,FALSE,TRUE,'Выкуп магических амулетов',FALSE,19),
(NULL,'2020-12-21 13:41:47.531',6,'Выкуп стальных мечей',FALSE,NULL,FALSE,TRUE,'Выкуп стальных мечей',FALSE,20);

--
-- TOC entry 3119 (class 0 OID 16767)
-- Dependencies: 213
-- Data for Name: deal_user_bookmarked; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.deal_user_bookmarked (deal_id, user_id) VALUES (11,2),
(11,3),
(12,2),
(12,3),
(13,2),
(13,3),
(14,2),
(14,3),
(15,2),
(15,3),
(16,2),
(16,3),
(17,2),
(17,3),
(18,2),
(18,3),
(19,2),
(19,3),
(20,2),
(20,3);

--
-- TOC entry 3120 (class 0 OID 16770)
-- Dependencies: 214
-- Data for Name: deposit; Type: TABLE DATA; Schema: public; Owner: test
--

INSERT INTO public.deposit (id, balance, type) VALUES (1,0,0),
(2,0,2),
(3,0,1),
(4,0,0),
(5,0,2),
(6,0,1),
(7,0,0),
(8,0,2),
(9,0,1),
(10,0,0),
(11,0,2),
(12,0,1),
(13,0,0),
(14,0,2),
(15,0,1),
(16,0,0),
(17,0,2),
(18,0,1);

--
-- TOC entry 3121 (class 0 OID 16775)
-- Dependencies: 215
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: test
--

INSERT INTO public.location (id, region) VALUES (1,4),(2,0),(3,3),(4,6),(5,11),(6,10);

--
-- TOC entry 3122 (class 0 OID 16780)
-- Dependencies: 216
-- Data for Name: reward; Type: TABLE DATA; Schema: public; Owner: test
--

INSERT INTO public.reward (id, reward, tax, type) VALUES (1,7650,850,1),
(2,2250,250,2),
(3,7200,800,2),
(4,6750,750,0),
(5,3150,350,1),
(6,1800,200,1),
(7,8550,950,2),
(8,3150,350,1),
(9,6750,750,1),
(10,4950,550,2),
(11,2700,300,2),
(12,4950,550,2),
(13,3150,350,0),
(14,8550,950,0),
(15,450,50,2),
(16,7200,800,0),
(17,2250,250,2),
(18,7200,800,1),
(19,1350,150,1),
(20,3600,400,1);

--
-- TOC entry 3123 (class 0 OID 16785)
-- Dependencies: 217
-- Data for Name: task; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.task (completion_on, create_on, customer_id, done, location_comment, paid, title, witcher_id, reward_id, location_id) 
VALUES (NULL,'2020-12-21 13:41:47.154',4,FALSE,'Водится в окрестностях Хенгфорса',FALSE,'Заказ на бруксу',NULL,1,1),
(NULL,'2020-12-21 13:41:47.272',4,FALSE,'Водится в окрестностях Аэдирна',FALSE,'Заказ на утопца',NULL,2,2),
(NULL,'2020-12-21 13:41:47.295',4,FALSE,'Водится в окрестностях Цинтры',FALSE,'Заказ на гуля',NULL,3,3),
(NULL,'2020-12-21 13:41:47.317',4,FALSE,'Водится в окрестностях Ковира',FALSE,'Заказ на василиска',NULL,4,4),
(NULL,'2020-12-21 13:41:47.334',4,FALSE,'Водится в окрестностях Вердена',FALSE,'Заказ на гуля',NULL,5,5),
(NULL,'2020-12-21 13:41:47.351',4,FALSE,'Водится в окрестностях Темерии',FALSE,'Заказ на кикимору',NULL,6,6),
(NULL,'2020-12-21 13:41:47.365',4,FALSE,'Водится в окрестностях Темерии',FALSE,'Заказ на бруксу',NULL,7,6),
(NULL,'2020-12-21 13:41:47.382',4,FALSE,'Водится в окрестностях Вердена',FALSE,'Заказ на альпа',NULL,8,5),
(NULL,'2020-12-21 13:41:47.402',4,FALSE,'Водится в окрестностях Темерии',FALSE,'Заказ на альпа',NULL,9,6),
(NULL,'2020-12-21 13:41:47.418',4,FALSE,'Водится в окрестностях Темерии',FALSE,'Заказ на утопца',NULL,10,6);

--
-- TOC entry 3124 (class 0 OID 16793)
-- Dependencies: 218
-- Data for Name: task_beast; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.task_beast (task_id, beast_id) VALUES (1,1),
(2,2),
(3,3),
(4,4),
(5,3),
(6,5),
(7,1),
(8,6),
(9,6),
(10,2);


--
-- TOC entry 3125 (class 0 OID 16796)
-- Dependencies: 219
-- Data for Name: task_witcher; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.task_witcher (task_id, user_id) VALUES (1,2),
(1,3),
(2,2),
(2,3),
(3,2),
(3,3),
(4,2),
(4,3),
(5,2),
(5,3),
(6,2),
(6,3),
(7,2),
(7,3),
(8,2),
(8,3),
(9,2),
(9,3),
(10,2),
(10,3);

--
-- TOC entry 3126 (class 0 OID 16799)
-- Dependencies: 220
-- Data for Name: task_witcher_completed; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.user_authority (user_id, authority_id) VALUES (1,1),
(2,2),
(3,2),
(4,3),
(5,4),
(6,5);


--
-- TOC entry 3128 (class 0 OID 16805)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: test
--
INSERT INTO public.users (email, enabled, first_name, last_name, last_password_reset_date, password, reset_token, role, username, bank_id) 
VALUES 
('foltest@gmail.com',TRUE,'Фольтест','Темерский','2020-12-21 13:41:46.69','$2a$10$bjtstvVaj.adBQKvYtiiVOcrHAXntRy/peQce6MfvDjCiVLXDJe5a',NULL,2,'king',1),
('geerkus@gmail.com',TRUE,'Геральт','Ривийский','2020-12-21 13:41:46.803','$2a$10$4W3Lh7GZnWqjTli16msGpusc3MliydVyhIarmDZTZWodgqzV32qOy',NULL,4,'witcher',2),
('witcher@gmail.com',TRUE,'Лето','Гулета','2020-12-21 13:41:46.89','$2a$10$yOIKwMlIkYuWkFFLNSqe8uf/MqmoGQLjLVosEbfv5jLbV/pEQ4xpe',NULL,4,'witcher11',3),
('vodka@gmail.com',TRUE,'Бедный','Простолюдин','2020-12-21 13:41:46.971','$2a$10$mXmbTSTNvKRO8gGvKQk8E.MxvtVCQbwH96KFAUC9TNWVSZMmTr8Z.',NULL,0,'user',4),
('trade@gmail.com',TRUE,'Жадный','Купец','2020-12-21 13:41:47.061','$2a$10$QD.5gPrZtSvGiVV40ueayuwmMPDeMu/SBT6I54fTnqhoi39ta6pL6',NULL,3,'vendor',5),
('blacksmith@gmail.com',TRUE,'Умелый','Кузнец','2020-12-21 13:41:47.144','$2a$10$aJz8nnclLe2RbhOBvzMye.RaqDkAVISY7M9daIuEgh4UXqliMxije',NULL,1,'blacksmith',6);

--
-- TOC entry 3134 (class 0 OID 0)
-- Dependencies: 201
-- Name: authority_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.authority_seq', 5, true);


--
-- TOC entry 3135 (class 0 OID 0)
-- Dependencies: 202
-- Name: bank_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.bank_seq', 6, true);


--
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 203
-- Name: beast_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.beast_seq', 6, true);


--
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 204
-- Name: deposit_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.deposit_seq', 18, true);


--
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 205
-- Name: location_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.location_seq', 6, true);


--
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 206
-- Name: reward_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.reward_seq', 20, true);


--
-- TOC entry 2933 (class 2606 OID 16742)
-- Name: authority authority_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (id);


--
-- TOC entry 2937 (class 2606 OID 16747)
-- Name: bank bank_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.bank
    ADD CONSTRAINT bank_pkey PRIMARY KEY (id);


--
-- TOC entry 2939 (class 2606 OID 16755)
-- Name: beast beast_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.beast
    ADD CONSTRAINT beast_pkey PRIMARY KEY (id);


--
-- TOC entry 2943 (class 2606 OID 16763)
-- Name: deal deal_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deal
    ADD CONSTRAINT deal_pkey PRIMARY KEY (reward_id);


--
-- TOC entry 2945 (class 2606 OID 16774)
-- Name: deposit deposit_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deposit
    ADD CONSTRAINT deposit_pkey PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 16779)
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- TOC entry 2951 (class 2606 OID 16784)
-- Name: reward reward_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT reward_pkey PRIMARY KEY (id);


--
-- TOC entry 2953 (class 2606 OID 16792)
-- Name: task task_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task
    ADD CONSTRAINT task_pkey PRIMARY KEY (reward_id);


--
-- TOC entry 2935 (class 2606 OID 16814)
-- Name: authority uk_jdeu5vgpb8k5ptsqhrvamuad2; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT uk_jdeu5vgpb8k5ptsqhrvamuad2 UNIQUE (name);


--
-- TOC entry 2941 (class 2606 OID 16816)
-- Name: beast uk_kv6vabuc8rm7gdpr27sfo7fiy; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.beast
    ADD CONSTRAINT uk_kv6vabuc8rm7gdpr27sfo7fiy UNIQUE (beast_name);


--
-- TOC entry 2949 (class 2606 OID 16818)
-- Name: location uk_m2focvxg048eptan1ldtiv991; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT uk_m2focvxg048eptan1ldtiv991 UNIQUE (region);


--
-- TOC entry 2955 (class 2606 OID 16820)
-- Name: users uk_r43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- TOC entry 2929 (class 2606 OID 16628)
-- Name: user uk_sb8bbouer5wak8vyiiy4pf2bx; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT uk_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username);


--
-- TOC entry 2931 (class 2606 OID 16559)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (bank_id);


--
-- TOC entry 2957 (class 2606 OID 16812)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (bank_id);


--
-- TOC entry 2963 (class 2606 OID 16846)
-- Name: deal_user_bookmarked fk2hx6fppaa7mu3ne38piw8bf03; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deal_user_bookmarked
    ADD CONSTRAINT fk2hx6fppaa7mu3ne38piw8bf03 FOREIGN KEY (user_id) REFERENCES public.users(bank_id);


--
-- TOC entry 2965 (class 2606 OID 16856)
-- Name: task fk3g10rvjt2p0aswg0c3ihct9fc; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task
    ADD CONSTRAINT fk3g10rvjt2p0aswg0c3ihct9fc FOREIGN KEY (location_id) REFERENCES public.location(id);


--
-- TOC entry 2960 (class 2606 OID 16831)
-- Name: deal fk4f1d4jur6l4fnlmi2jtw9kv21; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deal
    ADD CONSTRAINT fk4f1d4jur6l4fnlmi2jtw9kv21 FOREIGN KEY (reward_id) REFERENCES public.reward(id);


--
-- TOC entry 2970 (class 2606 OID 16881)
-- Name: task_witcher fk5lp6hqea7qr7p5ek196kqo32x; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task_witcher
    ADD CONSTRAINT fk5lp6hqea7qr7p5ek196kqo32x FOREIGN KEY (task_id) REFERENCES public.task(reward_id);


--
-- TOC entry 2967 (class 2606 OID 16866)
-- Name: task_beast fk74xtjuhws5mwux77ull31iw40; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task_beast
    ADD CONSTRAINT fk74xtjuhws5mwux77ull31iw40 FOREIGN KEY (beast_id) REFERENCES public.beast(id);


--
-- TOC entry 2962 (class 2606 OID 16841)
-- Name: deal_user fk7x4mcjnw64h6pn7l642xl84af; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deal_user
    ADD CONSTRAINT fk7x4mcjnw64h6pn7l642xl84af FOREIGN KEY (deal_id) REFERENCES public.deal(reward_id);


--
-- TOC entry 2972 (class 2606 OID 16891)
-- Name: task_witcher_completed fk8knb9hqrxde7bv5teym33hyas; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task_witcher_completed
    ADD CONSTRAINT fk8knb9hqrxde7bv5teym33hyas FOREIGN KEY (task_id) REFERENCES public.task(reward_id);


--
-- TOC entry 2969 (class 2606 OID 16876)
-- Name: task_witcher fk9xvebgmlsagu0bfv0nhbhk5rw; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task_witcher
    ADD CONSTRAINT fk9xvebgmlsagu0bfv0nhbhk5rw FOREIGN KEY (user_id) REFERENCES public.users(bank_id);


--
-- TOC entry 2958 (class 2606 OID 16821)
-- Name: bank_deposit fkc2oq0jca2u9lvn9xailfqehwv; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.bank_deposit
    ADD CONSTRAINT fkc2oq0jca2u9lvn9xailfqehwv FOREIGN KEY (deposit_id) REFERENCES public.deposit(id);


--
-- TOC entry 2964 (class 2606 OID 16851)
-- Name: deal_user_bookmarked fkfb1bph0jgwi0y302uvicsu092; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deal_user_bookmarked
    ADD CONSTRAINT fkfb1bph0jgwi0y302uvicsu092 FOREIGN KEY (deal_id) REFERENCES public.deal(reward_id);


--
-- TOC entry 2966 (class 2606 OID 16861)
-- Name: task fkfnt7h7anme65l8kqcu1hwgfxt; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task
    ADD CONSTRAINT fkfnt7h7anme65l8kqcu1hwgfxt FOREIGN KEY (reward_id) REFERENCES public.reward(id);


--
-- TOC entry 2959 (class 2606 OID 16826)
-- Name: bank_deposit fkgh3kqmvrffwjl3rj7vmb6qisn; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.bank_deposit
    ADD CONSTRAINT fkgh3kqmvrffwjl3rj7vmb6qisn FOREIGN KEY (bank_id) REFERENCES public.bank(id);


--
-- TOC entry 2973 (class 2606 OID 16896)
-- Name: user_authority fkgvxjs381k6f48d5d2yi11uh89; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fkgvxjs381k6f48d5d2yi11uh89 FOREIGN KEY (authority_id) REFERENCES public.authority(id);


--
-- TOC entry 2974 (class 2606 OID 16901)
-- Name: user_authority fkhi46vu7680y1hwvmnnuh4cybx; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fkhi46vu7680y1hwvmnnuh4cybx FOREIGN KEY (user_id) REFERENCES public.users(bank_id);


--
-- TOC entry 2961 (class 2606 OID 16836)
-- Name: deal_user fkhvr2w4hhgpbpvk5kdws800tia; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.deal_user
    ADD CONSTRAINT fkhvr2w4hhgpbpvk5kdws800tia FOREIGN KEY (user_id) REFERENCES public.users(bank_id);


--
-- TOC entry 2971 (class 2606 OID 16886)
-- Name: task_witcher_completed fkkuipccst6x4g4gxid5v3ekbnj; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task_witcher_completed
    ADD CONSTRAINT fkkuipccst6x4g4gxid5v3ekbnj FOREIGN KEY (user_id) REFERENCES public.users(bank_id);


--
-- TOC entry 2968 (class 2606 OID 16871)
-- Name: task_beast fklse83y0ahw4i7nwphphmeoj8x; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.task_beast
    ADD CONSTRAINT fklse83y0ahw4i7nwphphmeoj8x FOREIGN KEY (task_id) REFERENCES public.task(reward_id);


--
-- TOC entry 2975 (class 2606 OID 16906)
-- Name: users fknpxsy8sd6p6h5jhl3n5cqejyy; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fknpxsy8sd6p6h5jhl3n5cqejyy FOREIGN KEY (bank_id) REFERENCES public.bank(id);


-- Completed on 2020-12-21 17:13:13

--
-- testQL database dump complete
--

