export type CandidateStatus =
  | 'SUBMITTED'
  | 'UNDER_REVIEW'
  | 'PENDING'
  | 'APPROVED'
  | 'REJECTED';

export interface Candidate {
  id: string;
  fullName: string;
  cpf: string;
  birthDate: string;
  phone: string;
  email: string;
  school: string;
  grade: string;
  shift: string;
  familyIncome: number;
  address: string;
  cep?: string;
  city: string;
  state: string;
  motivation?: string;
  status: CandidateStatus;
  selectionProcessId?: string;
  appliedAt: string;
  statusHistory: StatusHistoryEntry[];
  documents?: DocumentEntry[];
  coordinationNotes?: string;
}

export interface StatusHistoryEntry {
  id: string;
  status: CandidateStatus;
  changedAt: string;
  notes?: string;
}

export interface DocumentEntry {
  name: string;
  type: string;
  uploadedAt: string;
}
