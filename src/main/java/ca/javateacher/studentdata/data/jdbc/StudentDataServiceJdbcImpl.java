/* Alex Tetervak, Sheridan College, Ontario */
package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.data.StudentDataService;
import ca.javateacher.studentdata.model.StudentForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDataServiceJdbcImpl implements StudentDataService {

    StudentDataRepositoryJdbc studentDataRepository;

    public StudentDataServiceJdbcImpl(StudentDataRepositoryJdbc repository){
        this.studentDataRepository = repository;
    }
    
    public void insertStudentForm(StudentForm form){
        studentDataRepository.insertStudentForm(form);
    }
    
    public StudentForm getStudentForm(int id){
        return studentDataRepository.getStudentForm(id);
    }
    
    public List<StudentForm> getAllStudentForms(){
        return studentDataRepository.getAllStudentForms();
    }
    
    public void updateStudentForm(StudentForm form){
        studentDataRepository.updateStudentForm(form);
    }
    
    public void deleteStudentForm(int id){
       studentDataRepository.deleteStudentForm(id);
    }
    
    public void deleteAllStudentForms(){
        studentDataRepository.deleteAllStudentForms();
    }
}
