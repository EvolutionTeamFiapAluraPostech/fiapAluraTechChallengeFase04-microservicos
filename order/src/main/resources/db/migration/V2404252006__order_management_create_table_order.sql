create schema if not exists "order_management";

create table if not exists "order_management"."orders"
(
    "id"                       uuid                        not null default gen_random_uuid() primary key,
    "deleted"                  boolean                     not null default false,
    "version"                  bigint                      not null,
    "created_at"               timestamp without time zone null,
    "created_by"               varchar(255)                null,
    "updated_at"               timestamp without time zone null,
    "updated_by"               varchar(255)                null,
    "active"                   boolean                     not null,
    "company_id"               uuid                        not null,
    "company_name"             varchar(500)                not null,
    "company_email"            varchar(500)                not null,
    "company_doc_number"       varchar(14)                 not null,
    "company_doc_number_type"  varchar(8)                  not null,
    "company_street"           varchar(255)                not null,
    "company_number"           varchar(100)                not null,
    "company_neighborhood"     varchar(100)                not null,
    "company_city"             varchar(100)                not null,
    "company_state"            varchar(2)                  not null,
    "company_country"          varchar(100)                not null,
    "company_postal_code"      varchar(8)                  not null,
    "company_latitude"         numeric(16, 6)              null,
    "company_longitude"        numeric(16, 6)              null,
    "customer_id"              uuid                        not null,
    "customer_name"            varchar(500)                not null,
    "customer_email"           varchar(500)                not null,
    "customer_doc_number"      varchar(14)                 not null,
    "customer_doc_number_type" varchar(8)                  not null,
    "customer_street"          varchar(255)                not null,
    "customer_number"          varchar(100)                not null,
    "customer_neighborhood"    varchar(100)                not null,
    "customer_city"            varchar(100)                not null,
    "customer_state"           varchar(2)                  not null,
    "customer_country"         varchar(100)                not null,
    "customer_postal_code"     varchar(8)                  not null,
    "customer_latitude"        numeric(16, 6)              null,
    "customer_longitude"       numeric(16, 6)              null,
    "status"                   varchar(50)                 not null,
    "order_date"               timestamp                   not null
);