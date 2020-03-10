INSERT INTO student
(first_name, last_name, program_name, program_year, program_coop, program_internship)
VALUES
('Harry', 'Potter', 'Computer Programmer', 1, true, false),
('Ronald', 'Weasley', 'Systems Technician', 2, false, true),
('Hermione', 'Granger', 'Systems Technology', 1, false, false),
('Draco', 'Malfoy', 'Engineering Technician', 2, true, true);

/* all these passwords are "sesame" */
INSERT INTO users
(user_login, user_password)
VALUES
('marge','$2a$10$bxGtVIu12/dXFQ8I1VrCmeFap8AXK.8EFgp.NRgaGt5no27uZd8Ty'),
('homer','$2a$10$5y39gonhJWNtUXFHi3gLaumMYLKmK/O4Jshi4/IlhryYNxhEFSNuy'),
('bart','$2a$10$WFceIBbBe2ynUC6ckJltOeI9qNgKSqGzE/PqD2BbxBHSVZyscOF8O'),
('lisa','$2a$10$/0le0donOsBt.kSva6CNNeNXRjm83m.VQeEsWHyY9ORQwJeGN/DAa');

INSERT INTO roles
(user_login, user_role)
VALUES
('marge','ROLE_ADMIN'),
('homer','ROLE_ADMIN'),
('bart','ROLE_USER'),
('lisa','ROLE_USER');
