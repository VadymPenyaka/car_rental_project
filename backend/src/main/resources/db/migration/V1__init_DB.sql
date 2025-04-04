SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.admin (
                              id character varying(36) NOT NULL,
                              "position" character varying(50),
                              department character varying(50),
                              person_id character varying(36) NOT NULL,
                              is_on_vocation boolean DEFAULT true NOT NULL,
                              location_id character varying(36)
);

CREATE TABLE public.brand (
                              name character varying(50) NOT NULL
);

CREATE TABLE public.car (
                            fuel_consumption integer NOT NULL,
                            number_of_seats integer NOT NULL,
                            id character varying(36) NOT NULL,
                            car_class character varying(50) NOT NULL,
                            location_id character varying(36) NOT NULL,
                            fuel_type character varying(50),
                            gearbox_type character varying(50),
                            drive_type character varying(50) NOT NULL,
                            engine_capacity double precision NOT NULL,
                            car_pricing_id character varying(36) NOT NULL,
                            number character varying(36) NOT NULL,
                            color character varying(36) NOT NULL,
                            vin character varying(36) NOT NULL,
                            body_type character varying(36) NOT NULL,
                            fuel_tank_capacity integer NOT NULL,
                            trunk_capacity integer NOT NULL,
                            license_category character varying(5) DEFAULT 'B'::character varying NOT NULL,
                            required_experience integer DEFAULT 1 NOT NULL,
                            model_id character varying(36)
);

CREATE TABLE public.car_maintenance (
                                        id character varying(36) NOT NULL,
                                        schedule_id character varying(36) NOT NULL,
                                        description character varying(100) NOT NULL,
                                        price double precision NOT NULL
);

CREATE TABLE public.car_order (
                                  admin_id character varying(36) NOT NULL,
                                  id character varying(36) NOT NULL,
                                  status character varying(36) NOT NULL,
                                  customer_id character varying(36) NOT NULL,
                                  total_price double precision NOT NULL,
                                  comment character varying(100),
                                  schedule_id character varying(36) NOT NULL
);

CREATE TABLE public.car_pricing (
                                    more_then_month double precision NOT NULL,
                                    pledge double precision NOT NULL,
                                    up_to_month double precision NOT NULL,
                                    up_to_ten_days double precision NOT NULL,
                                    up_to_three_days double precision NOT NULL,
                                    id character varying(36) NOT NULL
);

CREATE TABLE public.car_schedule (
                                     id character varying(36) NOT NULL,
                                     car_id character varying(36) NOT NULL,
                                     start_date date NOT NULL,
                                     end_date date NOT NULL,
                                     status character varying(36) NOT NULL
);

CREATE TABLE public.customer (
                                 id character varying(36) NOT NULL,
                                 person_id character varying(36) NOT NULL,
                                 passport_id character varying(36),
                                 driver_license_id character varying(36)
);

CREATE TABLE public.document (
                                 id character varying(36) NOT NULL,
                                 type character varying(50) NOT NULL,
                                 file_path character varying(255) NOT NULL,
                                 created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.driver_license (
                                       id character varying(36) NOT NULL,
                                       issue_date date NOT NULL,
                                       expiration_date date NOT NULL,
                                       issued_by character varying(100) NOT NULL,
                                       document_number character varying(10) NOT NULL
);

CREATE TABLE public.driver_license_category (
                                                license_id character varying(36) NOT NULL,
                                                category character varying(5) NOT NULL,
                                                issue_date date NOT NULL
);

CREATE TABLE public.location (
                                 id character varying(36) NOT NULL,
                                 location_name character varying(50) NOT NULL,
                                 region character varying(50) NOT NULL,
                                 city character varying(50) NOT NULL,
                                 address character varying(100) NOT NULL,
                                 latitude character varying(50) NOT NULL,
                                 longitude character varying(50) NOT NULL
);

CREATE TABLE public.model (
                              id character varying(36) NOT NULL,
                              brand_name character varying(50) NOT NULL,
                              model_name character varying(50) NOT NULL,
                              description character varying(256) NOT NULL,
                              year integer NOT NULL
);

CREATE TABLE public.passport (
                                 id character varying(36) NOT NULL,
                                 date_of_birth date NOT NULL,
                                 document_number character varying(255) NOT NULL,
                                 issued_by character varying(255) NOT NULL,
                                 expiration_date date NOT NULL,
                                 tax_identification_number character varying(255)
);

CREATE TABLE public.person (
                               phone_number character varying(255) NOT NULL,
                               first_name character varying(255) NOT NULL,
                               sure_name character varying(255) NOT NULL,
                               username character varying(255) NOT NULL,
                               password character varying(255) NOT NULL,
                               role character varying(36) NOT NULL,
                               id character varying(36) NOT NULL
);

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admins_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.brand
    ADD CONSTRAINT brand_pkey PRIMARY KEY (name);

ALTER TABLE ONLY public.car_order
    ADD CONSTRAINT car_orders_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.car_pricing
    ADD CONSTRAINT car_pricing_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.car_schedule
    ADD CONSTRAINT car_schedule_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.car
    ADD CONSTRAINT cars_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.car_maintenance
    ADD CONSTRAINT cars_services_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.document
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.driver_license_category
    ADD CONSTRAINT driver_license_categories_pkey PRIMARY KEY (license_id, category);

ALTER TABLE ONLY public.driver_license
    ADD CONSTRAINT driver_licenses_document_number_key UNIQUE (document_number);

ALTER TABLE ONLY public.driver_license
    ADD CONSTRAINT driver_licenses_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.location
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.passport
    ADD CONSTRAINT passports_document_number_key UNIQUE (document_number);

ALTER TABLE ONLY public.passport
    ADD CONSTRAINT passports_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.person
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.car_order
    ADD CONSTRAINT admin_id_fk FOREIGN KEY (admin_id) REFERENCES public.admin(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE ONLY public.model
    ADD CONSTRAINT brand_name_fk FOREIGN KEY (brand_name) REFERENCES public.brand(name);

ALTER TABLE ONLY public.car_schedule
    ADD CONSTRAINT car_id_fk FOREIGN KEY (car_id) REFERENCES public.car(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pricing_id_fk FOREIGN KEY (car_pricing_id) REFERENCES public.car_pricing(id);

ALTER TABLE ONLY public.car_order
    ADD CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES public.customer(id);

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fk_driver_license_id FOREIGN KEY (driver_license_id) REFERENCES public.driver_license(id);

ALTER TABLE ONLY public.driver_license_category
    ADD CONSTRAINT fk_license FOREIGN KEY (license_id) REFERENCES public.driver_license(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fk_passport_id FOREIGN KEY (passport_id) REFERENCES public.passport(id);

ALTER TABLE ONLY public.car
    ADD CONSTRAINT location_id_fk FOREIGN KEY (location_id) REFERENCES public.location(id);

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT location_id_fk FOREIGN KEY (location_id) REFERENCES public.location(id);

ALTER TABLE ONLY public.car
    ADD CONSTRAINT model_id_fk FOREIGN KEY (model_id) REFERENCES public.model(id);

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT person_id_fk FOREIGN KEY (person_id) REFERENCES public.person(id);


ALTER TABLE ONLY public.admin
    ADD CONSTRAINT person_id_fk FOREIGN KEY (person_id) REFERENCES public.person(id);


ALTER TABLE ONLY public.car_maintenance
    ADD CONSTRAINT schedule_id_fk FOREIGN KEY (schedule_id) REFERENCES public.car_schedule(id);


ALTER TABLE ONLY public.car_order
    ADD CONSTRAINT schedule_id_fk FOREIGN KEY (schedule_id) REFERENCES public.car_schedule(id);