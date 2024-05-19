create table if not exists "logistics_management"."logistics_items"
(
    "id"                  uuid                        not null default gen_random_uuid() primary key,
    "deleted"             boolean                     not null default false,
    "version"             bigint                      not null,
    "created_at"          timestamp without time zone null,
    "created_by"          varchar(255)                null,
    "updated_at"          timestamp without time zone null,
    "updated_by"          varchar(255)                null,
    "logistics_id"        uuid                        not null,
    "order_item_id"       uuid                        not null,
    "product_id"          uuid                        not null,
    "product_sku"         varchar(50)                 not null,
    "product_description" varchar(500)                not null,
    "quantity"            numeric(16, 2)              null,
    "price"               numeric(16, 2)              null,
    "total_amount"        numeric(16, 2)              null
);

alter table "logistics_management".logistics_items
    add constraint "fk_logistics_items_logistics"
        foreign key ("logistics_id") references "logistics_management".logistics ("id");