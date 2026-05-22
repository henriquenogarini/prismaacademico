-- V9: Create exams and exam_results tables
CREATE TABLE exams (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    class_group_id UUID NOT NULL REFERENCES class_groups(id) ON DELETE RESTRICT,
    title VARCHAR(200) NOT NULL,
    exam_date DATE,
    total_score NUMERIC(5, 2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE exam_results (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    exam_id UUID NOT NULL REFERENCES exams(id) ON DELETE CASCADE,
    student_id UUID NOT NULL REFERENCES students(id) ON DELETE CASCADE,
    subject_id UUID NOT NULL REFERENCES subjects(id) ON DELETE RESTRICT,
    score NUMERIC(5, 2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_exam_result UNIQUE (exam_id, student_id, subject_id)
);

CREATE INDEX idx_exams_class ON exams(class_group_id);
CREATE INDEX idx_exam_results_exam ON exam_results(exam_id);
CREATE INDEX idx_exam_results_student ON exam_results(student_id);

