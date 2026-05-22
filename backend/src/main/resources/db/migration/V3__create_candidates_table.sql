-- V3: Create candidates table
CREATE TABLE candidates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    selection_process_id UUID NOT NULL REFERENCES selection_processes(id) ON DELETE RESTRICT,
    full_name VARCHAR(150) NOT NULL,
    document_number VARCHAR(20) NOT NULL,
    birth_date DATE,
    phone VARCHAR(20),
    email VARCHAR(150),
    school_name VARCHAR(200),
    school_year VARCHAR(50),
    public_school BOOLEAN,
    income_per_capita NUMERIC(10, 2),
    observation TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'SUBMITTED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_candidate_process_document UNIQUE (selection_process_id, document_number)
);

CREATE INDEX idx_candidates_status ON candidates(status);
CREATE INDEX idx_candidates_selection_process ON candidates(selection_process_id);
CREATE INDEX idx_candidates_document ON candidates(document_number);

