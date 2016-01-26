import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/university_registrar_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteEnrollmentsQuery = "DELETE FROM enrollments *;";
      String deleteDepartmentsQuery = "DELETE FROM departments *;";
      String deleteCoursesQuery = "DELETE FROM courses *;";
      String deleteStudentsQuery = "DELETE FROM students *;";
      con.createQuery(deleteEnrollmentsQuery).executeUpdate();
      con.createQuery(deleteDepartmentsQuery).executeUpdate();
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteStudentsQuery).executeUpdate();
    }
  }
}
