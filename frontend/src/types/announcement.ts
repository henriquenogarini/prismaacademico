export interface Announcement {
  id: string;
  title: string;
  content: string;
  authorId: string;
  authorName: string;
  targetRole?: string;
  classGroupId?: string;
  classGroupName?: string;
  createdAt: string;
  isPinned: boolean;
}
