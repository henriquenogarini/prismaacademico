-- V2: Create selection_processes table
CREATE TABLE selection_processes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    year INTEGER NOT NULL,
    semester INTEGER NOT NULL,
    start_date DATE,
    end_date DATE,
    vacancies INTEGER,
    description TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_selection_processes_status ON selection_processes(status);
CREATE INDEX idx_selection_processes_year_semester ON selection_processes(year, semester);

