ALTER TABLE property
    ALTER COLUMN state DROP NOT NULL,
    ALTER COLUMN district DROP NOT NULL,
    ALTER COLUMN incident_summary DROP NOT NULL,
    ALTER COLUMN feedback_summary DROP NOT NULL,
    ALTER COLUMN feedback_rating DROP NOT NULL;

ALTER TABLE property
DROP COLUMN description;