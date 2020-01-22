package ca.javateacher.studentdata.data;

import ca.javateacher.studentdata.model.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDataRepository extends JpaRepository<StudentEntity, Integer> {
}
