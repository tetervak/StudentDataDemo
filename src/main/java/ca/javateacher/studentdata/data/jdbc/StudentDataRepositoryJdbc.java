/* Alex Tetervak, Sheridan College, Ontario */

package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.model.StudentForm;

import java.util.List;

public interface StudentDataRepositoryJdbc {
    void insertStudentForm(StudentForm form);
    StudentForm getStudentForm(int id);
    List<StudentForm> getAllStudentForms();
    void updateStudentForm(StudentForm form);
    void deleteStudentForm(int id);
    void deleteAllStudentForms();
}
