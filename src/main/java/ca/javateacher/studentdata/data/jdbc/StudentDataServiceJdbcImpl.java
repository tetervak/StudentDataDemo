/* Alex Tetervak, Sheridan College, Ontario */
package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.data.StudentDataService;
import ca.javateacher.studentdata.model.StudentForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDataServiceJdbcImpl implements StudentDataService {

    StudentDataRepositoryJdbc studentDataRepository;

    public StudentDataServiceJdbcImpl(StudentDataRepositoryJdbc repository){
        this.studentDataRepository = repository;
    }
    
    public void insertStudentForm(StudentForm form){
        studentDataRepository.insert(form);
    }
    
    public StudentForm getStudentForm(int id){
        return studentDataRepository.get(id);
    }
    
    public List<StudentForm> getAllStudentForms(){
        return studentDataRepository.getAll();
    }
    
    public void updateStudentForm(StudentForm form){
        studentDataRepository.update(form);
    }
    
    public void deleteStudentForm(int id){
       studentDataRepository.delete(id);
    }
    
    public void deleteAllStudentForms(){
        studentDataRepository.deleteAll();
    }
}
