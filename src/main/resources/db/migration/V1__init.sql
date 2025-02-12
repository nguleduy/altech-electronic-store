-- Table: products
CREATE TABLE products
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Table: promotions
CREATE TABLE promotions
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type        VARCHAR(50) UNIQUE NOT NULL,
    description TEXT               NOT NULL
);

-- Table: customers
CREATE TABLE customers
(
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    phone   VARCHAR(255),
    address TEXT         NOT NULL
);

-- Table: discounts
CREATE TABLE discounts
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id   BIGINT NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    promotion_id BIGINT NOT NULL REFERENCES promotions (id) ON DELETE CASCADE
);

-- Table: baskets
CREATE TABLE baskets
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL REFERENCES products (id) ON DELETE CASCADE,
    quantity    INT    NOT NULL
);
