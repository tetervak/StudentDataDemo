/* Alex Tetervak, Sheridan College, Ontario */
package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.data.StudentDataService;
import ca.javateacher.studentdata.data.jdbc.StudentDataRepositoryJdbc;
import ca.javateacher.studentdata.model.StudentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDataServiceJdbcImpl implements StudentDataService {
    
    @Autowired
    StudentDataRepositoryJdbc studentDataRepository;
    
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
