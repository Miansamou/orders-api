create table customers(
  id uuid primary key,
  name varchar(120) not null,
  email varchar(180) unique not null,
  created_at timestamptz not null default now(),
  created_by varchar(100),
  updated_at timestamptz,
  updated_by varchar(100)
);
create table products(
  id uuid primary key,
  sku varchar(64) unique not null,
  name varchar(160) not null,
  price_cents bigint not null check (price_cents>=0),
  weight_grams int not null default 0
);
create table orders(
  id uuid primary key,
  customer_id uuid not null references customers(id),
  status varchar(32) not null,
  subtotal_cents bigint not null,
  discount_cents bigint not null,
  freight_cents bigint not null,
  total_cents bigint not null,
  created_at timestamptz not null default now()
);
create table order_items(
  id uuid primary key,
  order_id uuid not null references orders(id) on delete cascade,
  product_id uuid not null references products(id),
  quantity int not null check (quantity>0),
  unit_price_cents bigint not null,
  line_total_cents bigint not null
);
create table idempotency_keys(
  key varchar(80) primary key,
  request_hash varchar(64) not null,
  response_body jsonb,
  http_status int,
  created_at timestamptz not null default now(),
  expires_at timestamptz not null
);
create index idx_idempotency_expires on idempotency_keys(expires_at);
