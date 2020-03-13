/* Alex Tetervak, Sheridan College, Ontario */
package ca.javateacher.studentdata.data.jdbc;

import ca.javateacher.studentdata.model.StudentForm;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        form.setId(keys.getKey().intValue());
    }

    @Override
    public StudentForm getStudentForm(int id) {
        String query = "SELECT * FROM student WHERE ID = :id";
        Map<String, Object> params = new HashMap<>();
        StudentForm form = null;
        params.put("id", id);
        try {
            form = namedParameterJdbcTemplate.queryForObject(query, params, new StudentFormMapper());
        } catch (DataAccessException e) {
            // the code above throws an exception if the record is not found
        }
        return form;
    }

    @Override
    public List<StudentForm> getAllStudentForms() {
        return jdbcTemplate.query("SELECT * FROM student", new StudentFormMapper());
    }

    @Override
    public void updateStudentForm(StudentForm form) {
        String update = "UPDATE student SET "
                + "first_name = :first_name, last_name = :last_name, "
                + "program_name = :program_name, program_year = :program_year, "
                + "program_coop = :program_coop, program_internship = :program_internship "
                + "WHERE id = :id";
        HashMap<String, Object> params = new HashMap<>();
        params.put("first_name", form.getFirstName().trim());
        params.put("last_name", form.getLastName().trim());
        params.put("program_name", form.getProgramName());
        params.put("program_year", form.getProgramYear());
        params.put("program_coop", form.isProgramCoop());
        params.put("program_internship", form.isProgramInternship());
        params.put("id",form.getId());
        namedParameterJdbcTemplate.update(update, params); 
    }

    @Override
    public void deleteStudentForm(int id) {
        String update = "DELETE FROM student WHERE id = :id";
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        namedParameterJdbcTemplate.update(update, params); 
    }

    @Override
    public void deleteAllStudentForms() {
        String update = "TRUNCATE TABLE student";
        jdbcTemplate.update(update);
    }

    private static final class StudentFormMapper implements RowMapper<StudentForm> {

        @Override
        public StudentForm mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentForm form = new StudentForm();
            form.setId(rs.getInt("id"));
            form.setFirstName(rs.getString("first_name"));
            form.setLastName(rs.getString("last_name"));
            form.setProgramName(rs.getString("program_name"));
            form.setProgramYear(rs.getInt("program_year"));
            form.setProgramCoop(rs.getBoolean("program_coop"));
            form.setProgramInternship(rs.getBoolean("program_internship"));
            return form;
        }
    }

}
