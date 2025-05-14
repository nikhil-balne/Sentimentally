CREATE TABLE severity (
    id   INT         PRIMARY KEY,
    code VARCHAR(50) NOT NULL
);

CREATE TABLE category (
    id          INT          PRIMARY KEY,
    code        VARCHAR(50)  NOT NULL
);

CREATE TABLE property (
    id                BIGINT       PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    description       TEXT,
    coordinates       VARCHAR(255) NOT NULL,
    state             VARCHAR(255) NOT NULL,
    district          VARCHAR(255) NOT NULL,
    image_url 		  TEXT,
    admin_email       VARCHAR(255)
);

CREATE TABLE incident (
    id             BIGINT   PRIMARY KEY,
    incident_text  TEXT     NOT NULL,
    severity_id    INT      NOT NULL,
    category_id    INT      NOT NULL,
    property_id    INT      NOT NULL,
    is_resolved    BOOLEAN  DEFAULT FALSE,

    CONSTRAINT fk_severity FOREIGN KEY (severity_id) REFERENCES severity(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id),
    CONSTRAINT fk_property FOREIGN KEY (property_id) REFERENCES property(id)
);