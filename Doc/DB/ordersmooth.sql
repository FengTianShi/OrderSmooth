CREATE TABLE IF NOT EXISTS public.m_restaurant_genre
(
    genre_id integer NOT NULL,
    lang_code character varying(5) NOT NULL,
    genre_name character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,

    PRIMARY KEY (genre_id, lang_code)
);

INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (1, 'en', 'chinese', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (1, 'ja', '中華', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (1, 'zh-CN', '中餐', false, false, now(), 'SYSTEM', now(), 'SYSTEM');

INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (2, 'en', 'india food', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (2, 'ja', 'インドカレー', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (2, 'zh-CN', '印度餐', false, false, now(), 'SYSTEM', now(), 'SYSTEM');

INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (3, 'en', 'pizza', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (3, 'ja', 'ピザ', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_restaurant_genre(
	genre_id, lang_code, genre_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES (3, 'zh-CN', '披萨', false, false, now(), 'SYSTEM', now(), 'SYSTEM');

CREATE TABLE IF NOT EXISTS public.m_language
(
    lang_code character varying(5) NOT NULL,
    lang_name character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (lang_code)
);

INSERT INTO public.m_language(
	lang_code, lang_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES ('en', 'English', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_language(
	lang_code, lang_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES ('ja', '日本語', false, false, now(), 'SYSTEM', now(), 'SYSTEM');
INSERT INTO public.m_language(
	lang_code, lang_name, is_invalid, is_deleted, insert_time, inserted_by, update_time, updated_by)
	VALUES ('zh-CN', '中文', false, false, now(), 'SYSTEM', now(), 'SYSTEM');

CREATE TABLE IF NOT EXISTS public.t_owner_signin_mgt
(
    signin_id bigserial NOT NULL,
    owner_id integer NOT NULL,
    ip_address character varying(100) NOT NULL,
    device_info character varying(100) NOT NULL,
    failure_count integer NOT NULL,
    is_ban boolean NOT NULL,
    ban_start_time timestamp with time zone ,
    ban_end_time timestamp with time zone ,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (signin_id)
);

CREATE TABLE IF NOT EXISTS public.t_owner
(
    owner_id bigserial NOT NULL,
    owner_name character varying(100) NOT NULL,
    owner_email character varying(100) NOT NULL,
    owner_password character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (owner_id)
);