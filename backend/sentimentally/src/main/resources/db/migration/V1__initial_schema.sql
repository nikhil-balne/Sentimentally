CREATE TABLE property (
    id                VARCHAR(255) PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    description       TEXT,
    coordinates       VARCHAR(255),
    state             VARCHAR(255),
    district          VARCHAR(255),
    image_url 		  TEXT,
    admin_email       VARCHAR(255)
);