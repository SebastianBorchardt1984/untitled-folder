-- Table
CREATE TABLE IF NOT EXISTS process_step_details (
	step_id varchar NOT NULL, -- ID des Prozessschrittes
	step_name varchar NULL, -- Name des Prozessschrittes
	step_desctiption varchar NULL, -- Beschreibung des Prozessschrittes
	step_vbe numeric(10, 5) NOT NULL, -- VbE-Kompensation durch Automatisierung
	CONSTRAINT process_step_details_pk PRIMARY KEY (step_id)
);

-- Column comments
COMMENT ON COLUMN process_step_details.step_id IS 'ID des Prozessschrittes';
COMMENT ON COLUMN process_step_details.step_name IS 'Name des Prozessschrittes';
COMMENT ON COLUMN process_step_details.step_desctiption IS 'Beschreibung des Prozessschrittes';
COMMENT ON COLUMN process_step_details.step_vbe IS 'VbE-Kompensation durch Automatisierung';