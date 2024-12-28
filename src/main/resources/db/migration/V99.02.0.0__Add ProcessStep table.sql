
-- Table
CREATE TABLE IF NOT EXISTS process_step (
	id int4 NOT null PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- Laufende Nummer
	reference varchar NOT NULL, -- Referenz auf das Business-Objekt
	step_id varchar NOT NULL, -- ID des abbgeschlossenen fachlichen Schittes
	CONSTRAINT process_step_fk FOREIGN KEY (step_id) REFERENCES process_step_details(step_id) ON UPDATE CASCADE
);
CREATE INDEX IF NOT EXISTS process_steps_reference_idx ON process_step USING btree (reference);
COMMENT ON TABLE process_step IS 'Tabelle zum Speichern der zu einem Business-Objekt abgeschlossenen fachlichen Scritten';

-- Column comments
COMMENT ON COLUMN process_step.id IS 'Laufende Nummer';
COMMENT ON COLUMN process_step.reference IS 'Referenz auf das Business-Objekt';
COMMENT ON COLUMN process_step.step_id IS 'ID des abbgeschlossenen fachlichen Schittes';
