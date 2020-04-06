package ca.javateacher.studentdata.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDataRepositoryJpa extends JpaRepository<StudentEntity, Integer> {
}
