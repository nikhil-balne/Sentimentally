CREATE TABLE severity (
    id   INT          PRIMARY KEY,
    name VARCHAR(50)  NOT NULL
);

CREATE TABLE category (
    id   INT          PRIMARY KEY,
    name VARCHAR(50)  NOT NULL

);

CREATE TABLE brand (
    id          VARCHAR(10)   PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    description VARCHAR(255),
    url         TEXT
);

CREATE TABLE property (
    id                VARCHAR(10)   PRIMARY KEY,
    name              VARCHAR(100)  NOT NULL,
    description       VARCHAR(255),
    coordinates       VARCHAR(50)   NOT NULL,
    brand_id          VARCHAR(10)   NOT NULL,
    state             VARCHAR(255)  NOT NULL,
    district          VARCHAR(255)  NOT NULL,
    image_url         TEXT,
    admin_email       VARCHAR(255),
    incident_summary  VARCHAR(255)  NOT NULL,
    feedback_summary  VARCHAR(255)  NOT NULL,
    feedback_rating   INT           NOT NULL,

    CONSTRAINT fk_brand_id FOREIGN KEY (brand_id) REFERENCES brand(id)
);

CREATE TABLE incident (
    id             BIGINT        PRIMARY KEY,
    incident_text  TEXT          NOT NULL,
    severity_id    INT           NOT NULL,
    category_id    INT           NOT NULL,
    property_id    VARCHAR(10)   NOT NULL,
    is_resolved    BOOLEAN       DEFAULT FALSE,
    created_tsz    TIMESTAMPTZ   NOT NULL,

    CONSTRAINT fk_severity  FOREIGN KEY (severity_id)  REFERENCES severity(id),
    CONSTRAINT fk_category  FOREIGN KEY (category_id)  REFERENCES category(id),
    CONSTRAINT fk_property  FOREIGN KEY (property_id)  REFERENCES property(id)
);
CREATE TABLE input_feedback (
    id             BIGINT        PRIMARY KEY,
    feedback_text  TEXT          NOT NULL,
    rating         INT,
    property_id    VARCHAR(10)   NOT NULL,
    created_tsz    TIMESTAMPTZ   NOT NULL,

    CONSTRAINT fk_property_feedback FOREIGN KEY (property_id) REFERENCES property(id)
);

CREATE TABLE analysed_feedback (
    id             BIGINT        PRIMARY KEY,
    feedback_text  TEXT          NOT NULL,
    category_ids   INT[]         NOT NULL,
    rating         INT           NOT NULL,
    property_id    VARCHAR(10)   NOT NULL,
    created_tsz    TIMESTAMP     NOT NULL,

    CONSTRAINT fk_property_analysis FOREIGN KEY (property_id) REFERENCES property(id)
);
