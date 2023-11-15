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