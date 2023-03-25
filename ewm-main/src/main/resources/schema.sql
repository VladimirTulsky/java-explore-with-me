CREATE TABLE IF NOT EXISTS USERS (
    ID    BIGINT generated by default as identity primary key,
    EMAIL VARCHAR(64) not null,
    NAME  VARCHAR(64) not null,
    CONSTRAINT un_email UNIQUE (EMAIL)
);

CREATE TABLE IF NOT EXISTS CATEGORIES (
    ID   BIGINT generated by default as identity primary key,
    NAME VARCHAR(60) not null,
    CONSTRAINT un_category_name UNIQUE (NAME)
)