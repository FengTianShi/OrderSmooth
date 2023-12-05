DROP TABLE IF EXISTS public.t_printer_i18n CASCADE;

DROP TABLE IF EXISTS public.t_printer CASCADE;

DROP TABLE IF EXISTS public.t_restaurant_opening_hours CASCADE;

DROP TABLE IF EXISTS public.t_restaurant_pay_method CASCADE;

DROP TABLE IF EXISTS public.t_restaurant_image CASCADE;

DROP TABLE IF EXISTS public.t_restaurant_i18n CASCADE;

DROP TABLE IF EXISTS public.t_restaurant CASCADE;

DROP TABLE IF EXISTS public.t_owner_signin_mgt CASCADE;

DROP TABLE IF EXISTS public.t_owner CASCADE;

CREATE TABLE IF NOT EXISTS public.t_owner (
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

CREATE TABLE IF NOT EXISTS public.t_owner_signin_mgt (
    seq bigserial NOT NULL,
    owner_id bigint NOT NULL,
    ip_address character varying(100) NOT NULL,
    device_info character varying(100) NOT NULL,
    failure_count integer NOT NULL,
    is_ban boolean NOT NULL,
    ban_start_time timestamp with time zone,
    ban_end_time timestamp with time zone,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (owner_id) REFERENCES t_owner(owner_id)
);

CREATE TABLE IF NOT EXISTS public.t_restaurant (
    restaurant_id bigserial NOT NULL,
    owner_id bigint NOT NULL,
    genre_id integer NOT NULL,
    restaurant_logo_address text,
    restaurant_tel character varying(11) NOT NULL,
    restaurant_postal_code character varying(10) NOT NULL,
    restaurant_longitude double precision,
    restaurant_latitude double precision,
    restaurant_service_distance integer,
    currency_id integer NOT NULL,
    default_service_fee integer NOT NULL,
    default_tax integer NOT NULL,
    is_display_tax boolean NOT NULL,
    is_display_service_fee boolean NOT NULL,
    wifi_ssid character varying(100),
    wifi_password character varying(100),
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (restaurant_id),
    FOREIGN KEY (owner_id) REFERENCES t_owner(owner_id),
    FOREIGN KEY (genre_id) REFERENCES m_restaurant_genre(genre_id),
    FOREIGN KEY (currency_id) REFERENCES m_currency(currency_id)
);

CREATE TABLE IF NOT EXISTS public.t_restaurant_i18n (
    seq bigserial NOT NULL,
    restaurant_id bigint NOT NULL,
    lang_code character varying(5) NOT NULL,
    restaurant_name character varying(100) NOT NULL,
    restaurant_address character varying(500) NOT NULL,
    restaurant_description character varying(2000) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (restaurant_id) REFERENCES t_restaurant(restaurant_id),
    FOREIGN KEY (lang_code) REFERENCES m_language(lang_code)
);

CREATE TABLE IF NOT EXISTS public.t_restaurant_image (
    seq bigserial NOT NULL,
    restaurant_id bigint NOT NULL,
    image_address text NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (restaurant_id) REFERENCES t_restaurant(restaurant_id)
);

CREATE TABLE IF NOT EXISTS public.t_restaurant_pay_method (
    seq bigserial NOT NULL,
    restaurant_id bigint NOT NULL,
    pay_method_id integer NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (restaurant_id) REFERENCES t_restaurant(restaurant_id),
    FOREIGN KEY (pay_method_id) REFERENCES m_pay_method(pay_method_id)
);

CREATE TABLE IF NOT EXISTS public.t_restaurant_opening_hours (
    seq bigserial NOT NULL,
    restaurant_id bigint NOT NULL,
    day_in_week_id integer NOT NULL,
    open_time time without time zone NOT NULL,
    close_time time without time zone NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (restaurant_id) REFERENCES t_restaurant(restaurant_id),
    FOREIGN KEY (day_in_week_id) REFERENCES m_day_in_week(day_in_week_id)
);

CREATE TABLE IF NOT EXISTS public.t_printer (
    printer_id bigserial NOT NULL,
    restaurant_id bigint NOT NULL,
    ip_address character varying(100) NOT NULL,
    mac_address character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (printer_id),
    FOREIGN KEY (restaurant_id) REFERENCES t_restaurant(restaurant_id)
);

CREATE TABLE IF NOT EXISTS public.t_printer_i18n (
    seq bigserial NOT NULL,
    printer_id bigint NOT NULL,
    lang_code character varying(5) NOT NULL,
    printer_name character varying(100) NOT NULL,
    printer_description character varying(1000) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (printer_id) REFERENCES t_printer(printer_id),
    FOREIGN KEY (lang_code) REFERENCES m_language(lang_code)
);