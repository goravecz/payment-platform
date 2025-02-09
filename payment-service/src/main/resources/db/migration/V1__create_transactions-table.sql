CREATE TABLE transactions (
                             id UUID PRIMARY KEY,
                             sender_id VARCHAR(255) NOT NULL,
                             receiver_id VARCHAR(255) NOT NULL,
                             amount DECIMAL(19, 2) NOT NULL,
                             status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'SUCCESS', 'FAILURE', 'NOT_SET')),
                             failure_reason VARCHAR(20) CHECK (failure_reason IN ('INSUFFICIENT_FUNDS', 'INVALID_REQUEST', 'DUPLICATE_TRANSACTION', 'SYSTEM_ERROR', 'NOT_SET')),
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE transaction ADD CONSTRAINT unique_transaction_id UNIQUE (id);
