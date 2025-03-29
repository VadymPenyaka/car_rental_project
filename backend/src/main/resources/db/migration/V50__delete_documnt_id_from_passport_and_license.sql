alter table passport
    drop constraint fk_document_id,
    drop column document_id;

alter table driver_license
    drop column document_id;