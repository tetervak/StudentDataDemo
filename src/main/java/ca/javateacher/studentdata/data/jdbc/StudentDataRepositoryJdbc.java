/* Alex Tetervak, Sheridan College, Ontario */

package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.model.StudentForm;

import java.util.List;

public interface StudentDataRepositoryJdbc {
    void insert(StudentForm form);
    StudentForm get(int id);
    List<StudentForm> getAll();
    void update(StudentForm form);
    void delete(int id);
    void deleteAll();
}
