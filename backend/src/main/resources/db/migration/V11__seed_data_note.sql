-- V11: Placeholder migration - seed data is handled by DataSeeder.java at application startup.
-- This migration is intentionally empty to maintain the version sequence.
-- The DataSeeder component runs after Flyway migrations and uses BCryptPasswordEncoder
-- to correctly hash passwords for all demo users.

-- Demo users (populated by DataSeeder.java):
-- admin@prisma.com        / 123456  (ADMIN)
-- coordination@prisma.com / 123456  (COORDINATION)
-- teacher@prisma.com      / 123456  (TEACHER)
-- student@prisma.com      / 123456  (STUDENT)
-- candidate@prisma.com    / 123456  (CANDIDATE)

SELECT 1; -- no-op statement

