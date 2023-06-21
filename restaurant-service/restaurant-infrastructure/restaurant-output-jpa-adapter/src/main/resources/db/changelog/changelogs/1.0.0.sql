-- liquibase formatted sql

-- changeset chris:1685991181562-1
create table public.restaurants
(
    id                 uuid             not null
        primary key,
    addressdisplayname varchar(255)     not null,
    addresslatitude    double precision not null,
    addresslongitude   double precision not null,
    enabled            boolean          not null,
    logo_path          varchar(255),
    name               varchar(255)     not null,
    owner_email        varchar(255)     not null,
    owner_id           uuid             not null,
    owner_username     varchar(255)     not null
);

create table public.cuisine
(
    restaurant_id uuid not null
        constraint fkqnb3woqf7oy0l4lydtc56u1hl
            references public.restaurants,
    name          varchar(255)
);

create table public.delivery_areas
(
    id      uuid not null
        primary key
        constraint fk6hapw1vlkdttvfq2k8gvrrtxv
            references public.restaurants,
    polygon geometry
);

create table public.menus
(
    id            uuid not null
        primary key,
    restaurant_id uuid
        constraint fk49thmnytvo47ttv4ggtwo9rrj
            references public.restaurants
);

create table public.revinfo
(
    rev      integer not null
        primary key,
    revtstmp bigint
);

create table public.cuisine_aud
(
    rev           integer  not null
        constraint fk705a9w6yj570r82ngij08nn5k
            references public.revinfo,
    revtype       smallint not null,
    restaurant_id uuid     not null,
    setordinal    integer  not null,
    name          varchar(255),
    primary key (rev, revtype, restaurant_id, setordinal)
);

create table public.delivery_areas_aud
(
    id      uuid    not null,
    rev     integer not null
        constraint fkgkw07cnfawk97acccx1rphqbq
            references public.revinfo,
    revtype smallint,
    polygon geometry,
    primary key (rev, id)
);

create table public.menu_jpa_entity_menu_section_jpa_entity_aud
(
    rev     integer not null
        constraint fki57b4rb7o5d9f3ic98o4mn0fi
            references public.revinfo,
    menu_id uuid    not null,
    id      uuid    not null,
    revtype smallint,
    primary key (menu_id, rev, id)
);

create table public.menus_aud
(
    id            uuid    not null,
    rev           integer not null
        constraint fkalbkggw1p8dhos6l6t3g4snnj
            references public.revinfo,
    revtype       smallint,
    restaurant_id uuid,
    primary key (rev, id)
);

create table public.menu_section_jpa_entity_product_jpa_entity_aud
(
    rev        integer not null
        constraint fk6smt3n6dw0v2g3pwti5j6oxyv
            references public.revinfo,
    section_id uuid    not null,
    id         uuid    not null,
    revtype    smallint,
    primary key (section_id, rev, id)
);

create table public.openings_aud
(
    id          uuid    not null,
    rev         integer not null
        constraint fkqysy9uu174joj5gn8v2mno687
            references public.revinfo,
    revtype     smallint,
    close_time  time,
    created_at  timestamp(6) with time zone,
    day_of_week integer,
    open_time   time,
    primary key (rev, id)
);

create table public.overrules_aud
(
    id                     uuid    not null,
    rev                    integer not null
        constraint fk6tsfi5mh8xwubnlnejy9q2j7s
            references public.revinfo,
    revtype                smallint,
    alternative_close_time time,
    alternative_open_time  time,
    closed                 boolean,
    created_at             timestamp(6) with time zone,
    day_of_week            integer,
    valid_from             date,
    valid_through          date,
    primary key (rev, id)
);

create table public.products_aud
(
    id         uuid    not null,
    rev        integer not null
        constraint fkis5p0x6t8gvib9m5ra1fiybi3
            references public.revinfo,
    revtype    smallint,
    created_at timestamp(6) with time zone,
    name       varchar(255),
    price      numeric(38, 2),
    primary key (rev, id)
);

create table public.restaurants_aud
(
    id                 uuid    not null,
    rev                integer not null
        constraint fkdiqv9ax3kctdwq5qy0trnjlkr
            references public.revinfo,
    revtype            smallint,
    addressdisplayname varchar(255),
    addresslatitude    double precision,
    addresslongitude   double precision,
    enabled            boolean,
    logo_path          varchar(255),
    name               varchar(255),
    owner_email        varchar(255),
    owner_id           uuid,
    owner_username     varchar(255),
    primary key (rev, id)
);

create table public.sections
(
    id         uuid not null
        primary key,
    created_at timestamp(6) with time zone,
    title      varchar(255),
    menu_id    uuid
        constraint fkn3g8730vxdlnjovr32scm24wa
            references public.menus
);

create table public.products
(
    id         uuid not null
        primary key,
    created_at timestamp(6) with time zone,
    name       varchar(255),
    price      numeric(38, 2),
    section_id uuid
        constraint fk6ina06wy2nvjja9v3tqqx9xs4
            references public.sections
);

create table public.sections_aud
(
    id         uuid    not null,
    rev        integer not null
        constraint fklwebqiqss3gpv1172rqpnxv3x
            references public.revinfo,
    revtype    smallint,
    created_at timestamp(6) with time zone,
    title      varchar(255),
    primary key (rev, id)
);

create table public.worktime_jpa_entity_opening_jpa_entity_aud
(
    rev         integer not null
        constraint fk1ns17gi83omscursgwhegi09m
            references public.revinfo,
    worktime_id uuid    not null,
    id          uuid    not null,
    revtype     smallint,
    primary key (rev, worktime_id, id)
);

create table public.worktime_jpa_entity_overrule_jpa_entity_aud
(
    rev         integer not null
        constraint fkb6n2reh4bi41qip5kylnpoc8y
            references public.revinfo,
    worktime_id uuid    not null,
    id          uuid    not null,
    revtype     smallint,
    primary key (rev, worktime_id, id)
);

create table public.worktimes
(
    id            uuid not null
        primary key,
    restaurant_id uuid
        constraint fkob6tf9n7rt61xo93i6giwnk2h
            references public.restaurants
);

create table public.openings
(
    id          uuid not null
        primary key,
    close_time  time,
    created_at  timestamp(6) with time zone,
    day_of_week smallint,
    open_time   time,
    worktime_id uuid
        constraint fk5584bk1128ml21nevlr95faou
            references public.worktimes
);

create table public.overrules
(
    id                     uuid not null
        primary key,
    alternative_close_time time,
    alternative_open_time  time,
    closed                 boolean,
    created_at             timestamp(6) with time zone,
    day_of_week            smallint,
    valid_from             date,
    valid_through          date,
    worktime_id            uuid
        constraint fkrca5lg95hdbsi5m3o1apgklcq
            references public.worktimes
);

create table public.worktimes_aud
(
    id            uuid    not null,
    rev           integer not null
        constraint fk33nho28ijj42a8bhy6r57yyyy
            references public.revinfo,
    revtype       smallint,
    restaurant_id uuid,
    primary key (rev, id)
);
