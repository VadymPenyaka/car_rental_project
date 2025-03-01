CREATE TABLE documents (
                           id VARCHAR(36) PRIMARY KEY,
                           type VARCHAR(50) NOT NULL,
                           file_path VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE passports (
                          id VARCHAR(36) PRIMARY KEY,
                          date_of_birth DATE NOT NULL,
                          document_number VARCHAR(9) UNIQUE NOT NULL,
                          issued_by VARCHAR(100) NOT NULL,
                          expiration_date DATE NOT NULL,
                          tax_identification_number VARCHAR(12),
                          document_id VARCHAR(36),
                          CONSTRAINT fk_document_id
                              FOREIGN KEY (document_id)
                                  REFERENCES documents(id)
);

CREATE TABLE driver_licenses (
                                 id VARCHAR(36) PRIMARY KEY,
                                 category VARCHAR(20) NOT NULL,
                                 issue_date DATE NOT NULL,
                                 expiration_date DATE NOT NULL,
                                 issued_by VARCHAR(100) NOT NULL,
                                 series VARCHAR(5) NOT NULL,
                                 document_number VARCHAR(10) UNIQUE NOT NULL,
                                 document_id VARCHAR(36),
                                 CONSTRAINT fk_document_id_driver_license
                                     FOREIGN KEY (document_id)
                                         REFERENCES documents(id)
);
