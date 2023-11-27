CREATE TABLE IF NOT EXISTS public.m_language (
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

INSERT INTO
    public.m_language(
        lang_code,
        lang_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        'en',
        'English',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_language(
        lang_code,
        lang_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        'ja',
        '日本語',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_language(
        lang_code,
        lang_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        'zh-CN',
        '中文',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

CREATE TABLE IF NOT EXISTS public.m_currency (
    currency_id integer NOT NULL,
    currency_symbol character varying(1),
    currency_code character varying(3) NOT NULL,
    currency_name character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (currency_id)
);

INSERT INTO
    public.m_currency(
        currency_id,
        currency_symbol,
        currency_code,
        currency_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        '$',
        'USD',
        'US Dollar',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_currency(
        currency_id,
        currency_symbol,
        currency_code,
        currency_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        '¥',
        'JPY',
        '日本円',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_currency(
        currency_id,
        currency_symbol,
        currency_code,
        currency_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        '¥',
        'CNY',
        '人民币',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

CREATE TABLE IF NOT EXISTS public.m_restaurant_genre (
    genre_id integer NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (genre_id)
);

INSERT INTO
    public.m_restaurant_genre(
        genre_id,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre(
        genre_id,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre(
        genre_id,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

CREATE TABLE IF NOT EXISTS public.m_restaurant_genre_i18n (
    seq serial NOT NULL,
    genre_id integer NOT NULL,
    lang_code character varying(5) NOT NULL,
    genre_name character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (seq),
    FOREIGN KEY (genre_id) REFERENCES m_restaurant_genre(genre_id)
);

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        'en',
        'Pizza',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        'ja',
        'ピザ',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        'zh-CN',
        '披萨',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        'en',
        'Sushi',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        'ja',
        '寿司',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        'zh-CN',
        '寿司',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        'en',
        'Chinese',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        'ja',
        '中華',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_restaurant_genre_i18n(
        genre_id,
        lang_code,
        genre_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        'zh-CN',
        '中餐',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

CREATE TABLE IF NOT EXISTS public.m_pay_method (
    pay_method_id integer NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (pay_method_id)
);

INSERT INTO
    public.m_pay_method(
        pay_method_id,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method(
        pay_method_id,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method(
        pay_method_id,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

CREATE TABLE IF NOT EXISTS public.m_pay_method_i18n (
    seq serial NOT NULL,
    pay_method_id integer NOT NULL,
    lang_code character varying(5) NOT NULL,
    pay_method_name character varying(100) NOT NULL,
    is_invalid boolean NOT NULL,
    is_deleted boolean NOT NULL,
    insert_time timestamp with time zone NOT NULL,
    inserted_by character varying(100) NOT NULL,
    update_time timestamp with time zone NOT NULL,
    updated_by character varying(100) NOT NULL,
    PRIMARY KEY (pay_method_id)
);

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        'en',
        'Cash',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        'ja',
        '現金',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        1,
        'zh-CN',
        '现金',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        'en',
        'Credit Card',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        'ja',
        'クレジットカード',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        2,
        'zh-CN',
        '信用卡',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        'en',
        'PayPal',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        'ja',
        'ペイパル',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );

INSERT INTO
    public.m_pay_method_i18n(
        pay_method_id,
        lang_code,
        pay_method_name,
        is_invalid,
        is_deleted,
        insert_time,
        inserted_by,
        update_time,
        updated_by
    )
VALUES
    (
        3,
        'zh-CN',
        '贝宝',
        false,
        false,
        now(),
        'SYSTEM',
        now(),
        'SYSTEM'
    );