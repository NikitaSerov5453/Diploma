SET search_path TO public;
CREATE TABLE IF NOT EXISTS "users" (
    id UUID PRIMARY KEY,
    username VARCHAR(256),
    password VARCHAR(256),
    role_id UUID NOT NULL,
    employee_id UUID,
--     email_configuration_id UUID,
    is_locked BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS "roles" (
    id UUID PRIMARY KEY,
    role_type VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS "reports" (
    id UUID PRIMARY KEY,
    report_name VARCHAR(256),
    cron_expression VARCHAR(40),
    automated_reporting UUID,
    report_creator VARCHAR(40)
);

-- CREATE TABLE IF NOT EXISTS "email_configurations" (
--     id UUID PRIMARY KEY,
--     host VARCHAR(256),
--     port VARCHAR(256),
--     username VARCHAR(256),
--     password VARCHAR(256),
--     properties_auth VARCHAR(256),
--     properties_starttls_enable VARCHAR(256),
--     report_id UUID
-- );

CREATE TABLE IF NOT EXISTS "employees" (
    id UUID PRIMARY KEY,
    name VARCHAR(40),
    last_name VARCHAR(40),
    patronymic_name VARCHAR(40)
);

CREATE TABLE IF NOT EXISTS "sql_authorisations" (
    id UUID PRIMARY KEY,
    url VARCHAR(256),
    login VARCHAR(40),
    password VARCHAR(256),
    report_id UUID
);

CREATE TABLE IF NOT EXISTS "sql_requests" (
    id UUID,
    request VARCHAR(1000),
    sql_authorisation_id UUID
);

CREATE TABLE IF NOT EXISTS "addresses" (
    id UUID,
    email VARCHAR(60),
    report_id UUID
);

CREATE TABLE IF NOT EXISTS "refresh_token" (
    id UUID,
    expiry_date TIMESTAMP,
    token VARCHAR (256),
    user_id UUID
);

ALTER TABLE reports ADD COLUMN report_creator VARCHAR(40);





