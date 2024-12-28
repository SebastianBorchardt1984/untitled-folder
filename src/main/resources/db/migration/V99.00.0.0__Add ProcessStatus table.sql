CREATE TABLE IF NOT EXISTS process_status(
    reference           varchar PRIMARY KEY,
    reference_date      date NOT NULL,
    status              varchar NOT NULL,
    current_task        varchar NOT NULL,
    created             timestamptz NOT NULL,
    updated             timestamptz NOT NULL,
    sub_task            varchar,
    last_successful_task varchar,
    exception_type      varchar,
    exception_reason    varchar
);