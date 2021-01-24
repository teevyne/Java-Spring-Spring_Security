CREATE SCHEMA IF NOT EXISTS users;

CREATE TABLE IF NOT EXISTS users.users
(
    id                         uuid primary key,
    username                   VARCHAR(255) NOT NULL,
    email_address              VARCHAR(255) NOT NULL,
    password                   TEXT,
    first_name                 VARCHAR(255) NOT NULL,
    last_name                  VARCHAR(255) NOT NULL,
    roles                      VARCHAR(255) NOT NULL,
    is_account_non_expired     BOOLEAN      NOT NULL,
    is_credentials_non_expired BOOLEAN      NOT NULL,
    is_account_non_locked      BOOLEAN      NOT NULL,
    creation_time              timestamp    not null,
    update_time                timestamp    not null,
    client_id                  varchar(255) not null
);



CREATE SCHEMA IF NOT EXISTS oauth2;

CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               VARCHAR(255) PRIMARY KEY,
    resource_ids            VARCHAR(255),
    client_secret           VARCHAR(255),
    scope                   VARCHAR(255),
    authorized_grant_types  VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities             VARCHAR(255),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS oauth_access_token
(
    token_id          VARCHAR(255),
    token             BYTEA,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name         VARCHAR(255),
    client_id         VARCHAR(255),
    authentication    BYTEA,
    refresh_token     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
    token_id       VARCHAR(255),
    token          BYTEA,
    authentication BYTEA
);
--
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;

-- INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
-- VALUES ('my-trusted-client1', NULL, 'secret', 'read, write', 'password', NULL, NULL, 2, 4, 'GREAT CLIENT', NULL);
--
