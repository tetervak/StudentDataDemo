/* Alex Tetervak, Sheridan College, Ontario */
package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.model.StudentForm;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDataRepositoryJdbcImpl implements StudentDataRepositoryJdbc {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public StudentDataRepositoryJdbcImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertStudentForm(StudentForm form) {
        String update = "INSERT INTO student "
                + "(first_name, last_name, program_name, program_year, program_coop, program_internship) "
                + "VALUES "
                + "(:first_name, :last_name, :program_name, :program_year, :program_coop, :program_internship)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        GeneratedKeyHolder keys = new GeneratedKeyHolder();
        params.addValue("first_name", form.getFirstName().trim());
        params.addValue("last_name", form.getLastName().trim());
        params.addValue("program_name", form.getProgramName());
        params.addValue("program_year", form.getProgramYear());
        params.addValue("program_coop", form.isProgramCoop());
        params.addValue("program_internship", form.isProgramInternship());
        namedParameterJdbcTemplate.update(update, params, keys);
        form.setId(keys.getKey()!=null?keys.getKey().intValue():0);
    }

    @Override
    public StudentForm getStudentForm(int id) {
        String query = "SELECT * FROM student WHERE ID = :id";
        Map<String, Object> params = new HashMap<>();
        StudentForm form = null;
        params.put("id", id);
        try {
            form = namedParameterJdbcTemplate.queryForObject(query, params, new StudentFormRowMapper());
        } catch (DataAccessException e) {
            // the code above throws an exception if the record is not found
        }
        return form;
    }

    @Override
    public List<StudentForm> getAllStudentForms() {
        return jdbcTemplate.query("SELECT * FROM student", new StudentFormRowMapper());
    }

    @Override
    public void updateStudentForm(StudentForm form) {
        jdbcTemplate.update(
        "UPDATE student SET "
                + "first_name = ?, last_name = ?, "
                + "program_name = ?, program_year = ?, "
                + "program_coop = ?, program_internship = ? "
                + "WHERE id = ?",
                form.getFirstName().trim(), form.getLastName().trim(),
                form.getProgramName(), form.getProgramYear(),
                form.isProgramCoop(), form.isProgramInternship(),
                form.getId());
    }

    @Override
    public void deleteStudentForm(int id) {
        String update = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(update, id);
    }

    @Override
    public void deleteAllStudentForms() {
        String update = "TRUNCATE TABLE student";
        jdbcTemplate.update(update);
    }

}
