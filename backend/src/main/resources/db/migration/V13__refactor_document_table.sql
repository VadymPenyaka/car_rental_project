CREATE SEQUENCE document_number_seq START 1 INCREMENT 1;

ALTER TABLE document
    ADD COLUMN document_number INTEGER UNIQUE DEFAULT nextval('document_number_seq'),
    DROP COLUMN file_path;
