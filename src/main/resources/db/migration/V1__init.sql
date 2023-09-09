--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)

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

DROP DATABASE IF EXISTS webstore;
--
-- Name: webstore; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE webstore WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C.UTF-8';


ALTER DATABASE webstore OWNER TO postgres;

\connect webstore

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

--
-- Name: validatepersonkey(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validatepersonkey() RETURNS trigger
    LANGUAGE plpgsql
AS $$
declare exists integer;

BEGIN
    exists = (select count(1) from fisical_person where id = NEW.person_id);
    if (exists <= 0) then
        exists = (select count(1) from legal_person where id = NEW.person_id);
        if (exists <= 0) then
            RAISE EXCEPTION 'ID and PK of person not found';
        end if;
    end if;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.validatepersonkey() OWNER TO postgres;

--
-- Name: validatesupplierpersonkey(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validatesupplierpersonkey() RETURNS trigger
    LANGUAGE plpgsql
AS $$
declare exists integer;

BEGIN
    exists = (select count(1) from fisical_person where id = NEW.supplier_person_id);
    if (exists <= 0) then
        exists = (select count(1) from legal_person where id = NEW.supplier_person_id);
        if (exists <= 0) then
            RAISE EXCEPTION 'ID and PK of supplier person not found';
        end if;
    end if;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.validatesupplierpersonkey() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.access (
                               id bigint NOT NULL,
                               description character varying(255) NOT NULL
);


ALTER TABLE public.access OWNER TO postgres;

--
-- Name: access_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.access_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.access_seq OWNER TO postgres;

--
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.address (
                                id bigint NOT NULL,
                                address_type character varying(255) NOT NULL,
                                city character varying(255) NOT NULL,
                                complement character varying(255),
                                number character varying(255) NOT NULL,
                                street character varying(255) NOT NULL,
                                zip_code character varying(255) NOT NULL,
                                person_id bigint NOT NULL,
                                CONSTRAINT address_address_type_check CHECK (((address_type)::text = ANY ((ARRAY['BILLING'::character varying, 'SHIPPING'::character varying])::text[])))
);


ALTER TABLE public.address OWNER TO postgres;

--
-- Name: bill_to_pay; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bill_to_pay (
                                    id bigint NOT NULL,
                                    description character varying(255) NOT NULL,
                                    discount_value numeric(38,2),
                                    due_date date NOT NULL,
                                    payment_date date,
                                    status character varying(255) NOT NULL,
                                    total_value numeric(38,2) NOT NULL,
                                    person_id bigint NOT NULL,
                                    supplier_person_id bigint NOT NULL,
                                    CONSTRAINT bill_to_pay_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'PAID'::character varying, 'OVERDUE'::character varying, 'CANCELED'::character varying])::text[])))
);


ALTER TABLE public.bill_to_pay OWNER TO postgres;

--
-- Name: bill_to_pay_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bill_to_pay_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bill_to_pay_seq OWNER TO postgres;

--
-- Name: bill_to_receive; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bill_to_receive (
                                        id bigint NOT NULL,
                                        description character varying(255) NOT NULL,
                                        discount_value numeric(38,2) NOT NULL,
                                        due_date date,
                                        payment_date date,
                                        status character varying(255),
                                        total_value numeric(38,2) NOT NULL,
                                        person_id bigint NOT NULL,
                                        supplier_id bigint NOT NULL,
                                        CONSTRAINT bill_to_receive_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING'::character varying, 'PAID'::character varying, 'OVERDUE'::character varying, 'CANCELED'::character varying])::text[])))
);


ALTER TABLE public.bill_to_receive OWNER TO postgres;

--
-- Name: bill_to_receive_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bill_to_receive_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bill_to_receive_seq OWNER TO postgres;

--
-- Name: default_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.default_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.default_generator OWNER TO postgres;

--
-- Name: discount_code; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discount_code (
                                      id bigint NOT NULL,
                                      code character varying(255) NOT NULL,
                                      due_date date NOT NULL,
                                      percentage_value numeric(38,2),
                                      real_value numeric(38,2)
);


ALTER TABLE public.discount_code OWNER TO postgres;

--
-- Name: discount_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.discount_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.discount_code_seq OWNER TO postgres;

--
-- Name: fisical_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fisical_person (
                                       id bigint NOT NULL,
                                       email character varying(255) NOT NULL,
                                       name character varying(255) NOT NULL,
                                       phone character varying(255) NOT NULL,
                                       birth_date date NOT NULL,
                                       cpf character varying(255) NOT NULL
);


ALTER TABLE public.fisical_person OWNER TO postgres;

--
-- Name: invoice_product_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.invoice_product_item (
                                             id bigint NOT NULL,
                                             quantity double precision NOT NULL,
                                             product_id bigint NOT NULL,
                                             purchase_invoice_id bigint NOT NULL
);


ALTER TABLE public.invoice_product_item OWNER TO postgres;

--
-- Name: invoice_product_item_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.invoice_product_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invoice_product_item_seq OWNER TO postgres;

--
-- Name: legal_person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.legal_person (
                                     id bigint NOT NULL,
                                     email character varying(255) NOT NULL,
                                     name character varying(255) NOT NULL,
                                     phone character varying(255) NOT NULL,
                                     category character varying(255),
                                     cnpj character varying(255) NOT NULL,
                                     corporate_name character varying(255) NOT NULL,
                                     fantasy_name character varying(255) NOT NULL,
                                     municipal_registration character varying(255),
                                     state_registration character varying(255) NOT NULL
);


ALTER TABLE public.legal_person OWNER TO postgres;

--
-- Name: payment_method; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payment_method (
                                       id bigint NOT NULL,
                                       description character varying(255) NOT NULL
);


ALTER TABLE public.payment_method OWNER TO postgres;

--
-- Name: payment_method_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.payment_method_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.payment_method_seq OWNER TO postgres;

--
-- Name: person_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_seq OWNER TO postgres;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
                                id bigint NOT NULL,
                                active boolean NOT NULL,
                                alert_stock_quantity boolean,
                                clicks_quantity integer,
                                depth double precision NOT NULL,
                                description text NOT NULL,
                                height double precision NOT NULL,
                                name character varying(255) NOT NULL,
                                sale_value numeric(38,2) NOT NULL,
                                stock_alert_quantity integer,
                                stock_quantity integer NOT NULL,
                                unity_type character varying(255) NOT NULL,
                                weight double precision NOT NULL,
                                width double precision NOT NULL,
                                youtube_video_url character varying(255)
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: product_brand; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_brand (
                                      id bigint NOT NULL,
                                      name_description character varying(255) NOT NULL
);


ALTER TABLE public.product_brand OWNER TO postgres;

--
-- Name: product_brand_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_brand_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_brand_seq OWNER TO postgres;

--
-- Name: product_category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_category (
                                         id bigint NOT NULL,
                                         name_description character varying(255) NOT NULL
);


ALTER TABLE public.product_category OWNER TO postgres;

--
-- Name: product_category_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_category_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_category_seq OWNER TO postgres;

--
-- Name: product_image; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_image (
                                      id bigint NOT NULL,
                                      minified_image text NOT NULL,
                                      original_image text NOT NULL,
                                      product_id bigint NOT NULL
);


ALTER TABLE public.product_image OWNER TO postgres;

--
-- Name: product_image_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_image_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_image_seq OWNER TO postgres;

--
-- Name: product_review; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_review (
                                       id bigint NOT NULL,
                                       description text NOT NULL,
                                       rating integer NOT NULL,
                                       person_id bigint NOT NULL,
                                       product_id bigint NOT NULL
);


ALTER TABLE public.product_review OWNER TO postgres;

--
-- Name: product_review_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_review_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_review_seq OWNER TO postgres;

--
-- Name: purchase_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchase_invoice (
                                         id bigint NOT NULL,
                                         description character varying(255),
                                         discount_value numeric(38,2),
                                         icms_value numeric(38,2) NOT NULL,
                                         number character varying(255) NOT NULL,
                                         puchase_date date NOT NULL,
                                         serial character varying(255) NOT NULL,
                                         total_value numeric(38,2) NOT NULL,
                                         bill_to_pay_id bigint NOT NULL,
                                         person_id bigint NOT NULL
);


ALTER TABLE public.purchase_invoice OWNER TO postgres;

--
-- Name: purchase_invoice_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchase_invoice_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchase_invoice_seq OWNER TO postgres;

--
-- Name: sell_invoice; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sell_invoice (
                                     id bigint NOT NULL,
                                     number character varying(255) NOT NULL,
                                     pdf text NOT NULL,
                                     series character varying(255) NOT NULL,
                                     type character varying(255) NOT NULL,
                                     xml text NOT NULL,
                                     sell_purchase_virtual_store_id bigint NOT NULL
);


ALTER TABLE public.sell_invoice OWNER TO postgres;

--
-- Name: sell_invoice_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sell_invoice_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sell_invoice_seq OWNER TO postgres;

--
-- Name: sell_item_store; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sell_item_store (
                                        id bigint NOT NULL,
                                        quantity double precision NOT NULL,
                                        product_id bigint NOT NULL,
                                        sell_purchase_virtual_store_id bigint NOT NULL
);


ALTER TABLE public.sell_item_store OWNER TO postgres;

--
-- Name: sell_item_store_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sell_item_store_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sell_item_store_seq OWNER TO postgres;

--
-- Name: sell_purchase_virtual_store; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sell_purchase_virtual_store (
                                                    id bigint NOT NULL,
                                                    delivery_date date NOT NULL,
                                                    delivery_day integer NOT NULL,
                                                    delivery_value numeric(38,2) NOT NULL,
                                                    discount_value numeric(38,2) NOT NULL,
                                                    sell_date date NOT NULL,
                                                    total_value numeric(38,2) NOT NULL,
                                                    billing_address_id bigint NOT NULL,
                                                    delivery_address_id bigint NOT NULL,
                                                    discount_code_id bigint NOT NULL,
                                                    payment_method_id bigint NOT NULL,
                                                    person_id bigint NOT NULL,
                                                    sell_invoice_id bigint NOT NULL
);


ALTER TABLE public.sell_purchase_virtual_store OWNER TO postgres;

--
-- Name: sell_purchase_virtual_store_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sell_purchase_virtual_store_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sell_purchase_virtual_store_seq OWNER TO postgres;

--
-- Name: tracking_status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tracking_status (
                                        id bigint NOT NULL,
                                        city character varying(255),
                                        distribution_center character varying(255),
                                        state character varying(255),
                                        status character varying(255),
                                        sell_purchase_virtual_store_id bigint NOT NULL
);


ALTER TABLE public.tracking_status OWNER TO postgres;

--
-- Name: tracking_status_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tracking_status_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tracking_status_seq OWNER TO postgres;

--
-- Name: user_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_access (
                                    user_id bigint NOT NULL,
                                    access_id bigint NOT NULL
);


ALTER TABLE public.user_access OWNER TO postgres;

--
-- Name: user_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_seq OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id bigint NOT NULL,
                              last_password_update_date date NOT NULL,
                              login character varying(255) NOT NULL,
                              password character varying(255) NOT NULL,
                              person_id bigint NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: access; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: bill_to_pay; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: bill_to_receive; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: discount_code; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: fisical_person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.fisical_person (id, email, name, phone, birth_date, cpf) VALUES (1, 'any_email@mail.com', 'Name 1', '99999999999', '2020-01-01', '99999999999');


--
-- Data for Name: invoice_product_item; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: legal_person; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: payment_method; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product (id, active, alert_stock_quantity, clicks_quantity, depth, description, height, name, sale_value, stock_alert_quantity, stock_quantity, unity_type, weight, width, youtube_video_url) VALUES (1, true, true, NULL, 10, 'Description 1', 10, 'Product 1', 10.00, 10, 10, 'UN', 10, 10, NULL);


--
-- Data for Name: product_brand; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: product_category; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: product_image; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: product_review; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product_review (id, description, rating, person_id, product_id) VALUES (1, 'Description 1', 10, 1, 1);


--
-- Data for Name: purchase_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: sell_invoice; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: sell_item_store; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: sell_purchase_virtual_store; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: tracking_status; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: user_access; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: access_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.access_seq', 1, false);


--
-- Name: bill_to_pay_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bill_to_pay_seq', 1, false);


--
-- Name: bill_to_receive_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bill_to_receive_seq', 1, false);


--
-- Name: default_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.default_generator', 1, false);


--
-- Name: discount_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.discount_code_seq', 1, false);


--
-- Name: invoice_product_item_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.invoice_product_item_seq', 1, false);


--
-- Name: payment_method_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.payment_method_seq', 1, false);


--
-- Name: person_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_seq', 1, false);


--
-- Name: product_brand_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_brand_seq', 1, false);


--
-- Name: product_category_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_category_seq', 1, false);


--
-- Name: product_image_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_image_seq', 1, false);


--
-- Name: product_review_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_review_seq', 1, false);


--
-- Name: purchase_invoice_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchase_invoice_seq', 1, false);


--
-- Name: sell_invoice_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sell_invoice_seq', 1, false);


--
-- Name: sell_item_store_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sell_item_store_seq', 1, false);


--
-- Name: sell_purchase_virtual_store_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sell_purchase_virtual_store_seq', 1, false);


--
-- Name: tracking_status_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tracking_status_seq', 1, false);


--
-- Name: user_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_seq', 1, false);


--
-- Name: access access_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.access
    ADD CONSTRAINT access_pkey PRIMARY KEY (id);


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: bill_to_pay bill_to_pay_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bill_to_pay
    ADD CONSTRAINT bill_to_pay_pkey PRIMARY KEY (id);


--
-- Name: bill_to_receive bill_to_receive_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bill_to_receive
    ADD CONSTRAINT bill_to_receive_pkey PRIMARY KEY (id);


--
-- Name: discount_code discount_code_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_code
    ADD CONSTRAINT discount_code_pkey PRIMARY KEY (id);


--
-- Name: fisical_person fisical_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fisical_person
    ADD CONSTRAINT fisical_person_pkey PRIMARY KEY (id);


--
-- Name: invoice_product_item invoice_product_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_product_item
    ADD CONSTRAINT invoice_product_item_pkey PRIMARY KEY (id);


--
-- Name: legal_person legal_person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.legal_person
    ADD CONSTRAINT legal_person_pkey PRIMARY KEY (id);


--
-- Name: payment_method payment_method_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payment_method
    ADD CONSTRAINT payment_method_pkey PRIMARY KEY (id);


--
-- Name: product_brand product_brand_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_brand
    ADD CONSTRAINT product_brand_pkey PRIMARY KEY (id);


--
-- Name: product_category product_category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);


--
-- Name: product_image product_image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_image
    ADD CONSTRAINT product_image_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: product_review product_review_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT product_review_pkey PRIMARY KEY (id);


--
-- Name: purchase_invoice purchase_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_invoice
    ADD CONSTRAINT purchase_invoice_pkey PRIMARY KEY (id);


--
-- Name: sell_invoice sell_invoice_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_invoice
    ADD CONSTRAINT sell_invoice_pkey PRIMARY KEY (id);


--
-- Name: sell_item_store sell_item_store_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_item_store
    ADD CONSTRAINT sell_item_store_pkey PRIMARY KEY (id);


--
-- Name: sell_purchase_virtual_store sell_purchase_virtual_store_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT sell_purchase_virtual_store_pkey PRIMARY KEY (id);


--
-- Name: tracking_status tracking_status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracking_status
    ADD CONSTRAINT tracking_status_pkey PRIMARY KEY (id);


--
-- Name: sell_invoice uk_aw5apltmsd4msc7h954csda78; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_invoice
    ADD CONSTRAINT uk_aw5apltmsd4msc7h954csda78 UNIQUE (sell_purchase_virtual_store_id);


--
-- Name: sell_purchase_virtual_store uk_g04gn99aycdny529x6gpngf5q; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT uk_g04gn99aycdny529x6gpngf5q UNIQUE (sell_invoice_id);


--
-- Name: user_access uk_ojlpsp4dq6pt966i85jb7i386; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT uk_ojlpsp4dq6pt966i85jb7i386 UNIQUE (access_id);


--
-- Name: sell_item_store uk_p7ca12bdo1fxwbb6vx30o59fo; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_item_store
    ADD CONSTRAINT uk_p7ca12bdo1fxwbb6vx30o59fo UNIQUE (sell_purchase_virtual_store_id);


--
-- Name: sell_item_store uk_ptig5xnl5n3dy1dq1eg3k78jc; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_item_store
    ADD CONSTRAINT uk_ptig5xnl5n3dy1dq1eg3k78jc UNIQUE (product_id);


--
-- Name: user_access unique_user_access; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT unique_user_access UNIQUE (user_id, access_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: address validatepersonkeyaddressinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaddressinsert BEFORE INSERT ON public.address FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: address validatepersonkeyaddressupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyaddressupdate BEFORE UPDATE ON public.address FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: bill_to_pay validatepersonkeybilltopayinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeybilltopayinsert BEFORE INSERT ON public.bill_to_pay FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: product_review validatepersonkeybilltopayinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeybilltopayinsert BEFORE INSERT ON public.product_review FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: bill_to_pay validatepersonkeybilltopayupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeybilltopayupdate BEFORE UPDATE ON public.bill_to_pay FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: product_review validatepersonkeybilltopayupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeybilltopayupdate BEFORE UPDATE ON public.product_review FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: bill_to_receive validatepersonkeybilltoreceiveinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeybilltoreceiveinsert BEFORE INSERT ON public.bill_to_receive FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: bill_to_receive validatepersonkeybilltoreceiveupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeybilltoreceiveupdate BEFORE UPDATE ON public.bill_to_receive FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: purchase_invoice validatepersonkeypurchaseinvoiceinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeypurchaseinvoiceinsert BEFORE INSERT ON public.purchase_invoice FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: purchase_invoice validatepersonkeypurchaseinvoiceupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeypurchaseinvoiceupdate BEFORE UPDATE ON public.purchase_invoice FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: sell_purchase_virtual_store validatepersonkeysellpurchaseitemstoreinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeysellpurchaseitemstoreinsert BEFORE INSERT ON public.sell_purchase_virtual_store FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: sell_purchase_virtual_store validatepersonkeysellpurchaseitemstoreupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeysellpurchaseitemstoreupdate BEFORE UPDATE ON public.sell_purchase_virtual_store FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: users validatepersonkeyuserinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyuserinsert BEFORE INSERT ON public.users FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: users validatepersonkeyuserupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatepersonkeyuserupdate BEFORE UPDATE ON public.users FOR EACH ROW EXECUTE FUNCTION public.validatepersonkey();


--
-- Name: bill_to_pay validatesupplierpersonkeybilltopayinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatesupplierpersonkeybilltopayinsert BEFORE INSERT ON public.bill_to_pay FOR EACH ROW EXECUTE FUNCTION public.validatesupplierpersonkey();


--
-- Name: bill_to_pay validatesupplierpersonkeybilltopayupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validatesupplierpersonkeybilltopayupdate BEFORE UPDATE ON public.bill_to_pay FOR EACH ROW EXECUTE FUNCTION public.validatesupplierpersonkey();


--
-- Name: user_access access_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT access_fk FOREIGN KEY (access_id) REFERENCES public.access(id);


--
-- Name: purchase_invoice bill_to_pay_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchase_invoice
    ADD CONSTRAINT bill_to_pay_fk FOREIGN KEY (bill_to_pay_id) REFERENCES public.bill_to_pay(id);


--
-- Name: sell_purchase_virtual_store billing_address_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT billing_address_fk FOREIGN KEY (billing_address_id) REFERENCES public.address(id);


--
-- Name: sell_purchase_virtual_store delivery_address_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT delivery_address_fk FOREIGN KEY (delivery_address_id) REFERENCES public.address(id);


--
-- Name: sell_purchase_virtual_store discount_code_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT discount_code_fk FOREIGN KEY (discount_code_id) REFERENCES public.discount_code(id);


--
-- Name: sell_purchase_virtual_store payment_method_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT payment_method_fk FOREIGN KEY (payment_method_id) REFERENCES public.payment_method(id);


--
-- Name: invoice_product_item product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_product_item
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_image product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_image
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_review product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: sell_item_store product_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_item_store
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: invoice_product_item purchase_invoice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.invoice_product_item
    ADD CONSTRAINT purchase_invoice_fk FOREIGN KEY (purchase_invoice_id) REFERENCES public.purchase_invoice(id);


--
-- Name: sell_purchase_virtual_store sell_invoice_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_purchase_virtual_store
    ADD CONSTRAINT sell_invoice_fk FOREIGN KEY (sell_invoice_id) REFERENCES public.sell_invoice(id);


--
-- Name: sell_invoice sell_purchase_virtual_store_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_invoice
    ADD CONSTRAINT sell_purchase_virtual_store_fk FOREIGN KEY (sell_purchase_virtual_store_id) REFERENCES public.sell_purchase_virtual_store(id);


--
-- Name: sell_item_store sell_purchase_virtual_store_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sell_item_store
    ADD CONSTRAINT sell_purchase_virtual_store_fk FOREIGN KEY (sell_purchase_virtual_store_id) REFERENCES public.sell_purchase_virtual_store(id);


--
-- Name: tracking_status sell_purchase_virtual_store_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tracking_status
    ADD CONSTRAINT sell_purchase_virtual_store_fk FOREIGN KEY (sell_purchase_virtual_store_id) REFERENCES public.sell_purchase_virtual_store(id);


--
-- Name: user_access user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_access
    ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

