import static org.junit.Assert.*;
import java.util.List;

import org.junit.*;


public class StudentTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Student.all().size(), 0);
  }

  @Test
  public void save_testNewStudentIsCreated_true() {
    Student newStudent = new Student("Bowen", "Midori", "2016-01-26");
    newStudent.save();
    Student savedStudent = Student.all().get(0);
    assertTrue(savedStudent.equals(newStudent));
  }

  @Test
  public void find_findsStudentInDatabase_true() {
    Student newStudent = new Student("Bowen", "Midori", "2016-01-26");
    newStudent.save();
    Student savedStudent = Student.find(newStudent.getId());
    assertTrue(newStudent.equals(savedStudent));
  }

  @Test
  public void update_updatesStudentRecord_true() {
    Student newStudent = new Student("Bowen", "Midori", "2016-01-26");
    newStudent.save();
    newStudent.update("Romike", "Nathan", "2016-01-25");
    assertEquals("Nathan Romike", newStudent.getFullName());
  }
}
