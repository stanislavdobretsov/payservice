CREATE SEQUENCE IF NOT EXISTS client_id_sequence;
CREATE SEQUENCE IF NOT EXISTS service_id_sequence;
CREATE SEQUENCE IF NOT EXISTS pay_operation_id_sequence;

CREATE TABLE IF NOT EXISTS client (
    client_id INT NOT NULL PRIMARY KEY DEFAULT nextval('client_id_sequence'),
    phone_number TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    credits NUMERIC NOT NULL CHECK(credits >= 0)
);

CREATE TABLE IF NOT EXISTS service (
    service_id INT PRIMARY KEY NOT NULL DEFAULT nextval('service_id_sequence'),
    title TEXT NOT NULL,
    min_pay_sum NUMERIC NOT NULL,
    max_pay_sum NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS pay_operation (
    pay_operation_id INT PRIMARY KEY NOT NULL DEFAULT nextval('pay_operation_id_sequence'),
    client_id INT NOT NULL references client(client_id),
    service_id INT NOT NULL references service(service_id),
    operation_time TIMESTAMP NOT NULL,
    pay_account_number TEXT NOT NULL,
    pay_sum NUMERIC NOT NULL
);

INSERT INTO service (service_id, title, min_pay_sum, max_pay_sum) VALUES (1, 'МТС', 20, 90) ON CONFLICT(service_id) DO NOTHING;
INSERT INTO service (service_id, title, min_pay_sum, max_pay_sum) VALUES (2, 'ТТК', 10, 80) ON CONFLICT(service_id) DO NOTHING;
INSERT INTO service (service_id, title, min_pay_sum, max_pay_sum) VALUES (3, 'УК Авоська', 15, 100) ON CONFLICT(service_id) DO NOTHING;