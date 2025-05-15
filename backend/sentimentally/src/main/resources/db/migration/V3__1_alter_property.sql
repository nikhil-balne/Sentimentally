ALTER TABLE property
    ALTER COLUMN incident_summary DROP NOT NULL,
    ALTER COLUMN feedback_summary DROP NOT NULL,
    ALTER COLUMN feedback_rating DROP NOT NULL;