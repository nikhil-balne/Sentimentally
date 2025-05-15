ALTER TABLE brand
DROP COLUMN description;

INSERT INTO brand (id, name, url)
VALUES
    ('MC', 'Marriott Hotels & Resorts', 'https://www.marriott.com');

INSERT INTO brand (id, name, url)
VALUES
    ('CY', 'Courtyard', 'https://www.marriott.com');

INSERT INTO brand (id, name, url)
VALUES
    ('FP', 'Four Points by Sheraton', NULL);

INSERT INTO brand (id, name, url)
VALUES
    ('RI', 'Residence Inn', NULL);

-- Insert for Brand Table (Element Hotels)
INSERT INTO brand (id, name, url)
VALUES
    ('EL', 'Element Hotels', NULL);


-- Texas
INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('ABIRI', 'Residence Inn Abilene', '32.47322, -99.692066', 'RI', 'Texas', 'Abilene', 'https://cache.marriott.com/content/dam/marriotts7prod/ABIRI/abiri-pool-0037-hor-clsc.jpg', 'adminsentiment@yopmail.com', NULL, NULL, 0);

INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('AMACY', 'Courtyard Amarillo West/Medical Center', '35.187936, -101.930333', 'CY', 'Texas', 'Amarillo', 'https://cache.marriott.com/is/image/marriotts7prod/cy-amacy-cy-amacy-1-king-14841-42623:Classic-Hor', 'adminfeedback@yopmail.com', NULL, NULL, 0);

-- Miami
INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('MIAEK', 'Element Miami Brickell', '25.76683285949398, -80.19610447381754', 'EL', 'Florida', 'Miami', 'https://cache.marriott.com/is/image/marriotts7prod/el-miaek-rooftop-pool-27110:Classic-Hor', 'sentimentallyy@yopmail.com', NULL, NULL, 0);


-- Illinois
INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('CHICM', 'Courtyard Chicago Midway Airport', '41.772836, -87.743697', 'CY', 'Illinois', 'Chicago', 'https://cache.marriott.com/is/image/marriotts7prod/cy-cy-lobby-pod-29210:Feature-Hor', 'adminsentiment@yopmail.com', NULL, NULL, 0);

INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('CHIBF', 'Four Points by Sheraton Buffalo Grove', '42.154003, -87.953685', 'FP', 'Illinois', 'Buffalo Grove', 'https://cache.marriott.com/is/image/marriotts7prod/fp-main-001-hor-clsc:Feature-Hor', 'adminfeedback@yopmail.com', NULL, NULL, 0);

-- California
INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('LAXAH', 'Anaheim Marriott', '33.799393, -117.918601', 'MC', 'California', 'Anaheim', 'https://cache.marriott.com/is/image/marriotts7prod/mc-laxah-outdoor-pool-and-nf-95054-71358:Classic-Hor', 'sentimentallyy@yopmail.com', NULL, NULL, 0);

INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('SFOBG', 'San Francisco Airport Marriott Waterfront', '37.602398, -122.370918', 'MC', 'California', 'San Francisco', 'https://cache.marriott.com/is/image/marriotts7prod/mc-sfobg-san-jose-exterior-30501:Classic-Hor', 'adminsentiment@yopmail.com', NULL, NULL, 0);

-- New York
INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('JFKCY', 'Courtyard New York JFK Airport', '40.6413, -73.7781', 'CY', 'New York', 'Queens',
     'https://cache.marriott.com/marriottassets/marriott/BR/br-main01-0001-hor-feat.jpg',
     'adminfeedback@yopmail.com', NULL, NULL, 0);

INSERT INTO property
(id, name, coordinates, brand_id, state, district, image_url, admin_email, incident_summary, feedback_summary, feedback_rating)
VALUES
    ('NYCMQ', 'New York Marriott Marquis', '40.758855, -73.985869', 'MC', 'New York', 'Manhattan',
     'https://cache.marriott.com/is/image/marriotts7prod/nycmq-exterior-0166:Classic-Hor',
     'sentimentallyy@yopmail.com', NULL, NULL, 0);