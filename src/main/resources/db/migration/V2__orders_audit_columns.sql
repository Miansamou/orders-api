alter table orders add column created_by varchar(100);
alter table orders add column updated_at timestamptz;
alter table orders add column updated_by varchar(100);