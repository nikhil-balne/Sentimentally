ALTER TABLE property ALTER COLUMN incident_summary TYPE VARCHAR(1000);
ALTER TABLE property ALTER COLUMN feedback_summary TYPE VARCHAR(1000);
ALTER TABLE input_feedback ALTER COLUMN feedback_text TYPE VARCHAR(1000);
ALTER TABLE analysed_feedback ALTER COLUMN feedback_text TYPE VARCHAR(1000);