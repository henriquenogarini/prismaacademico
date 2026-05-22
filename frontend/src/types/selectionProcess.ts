export type SelectionProcessStatus = 'DRAFT' | 'OPEN' | 'CLOSED' | 'FINISHED';

export interface SelectionProcess {
  id: string;
  title: string;
  year: number;
  semester: number;
  startDate: string;
  endDate: string;
  vacancies: number;
  description?: string;
  status: SelectionProcessStatus;
  applicationsCount: number;
  approvedCount: number;
  createdAt: string;
}
