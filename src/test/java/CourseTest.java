import static org.junit.Assert.*;
import java.util.List;

import org.junit.*;


public class CourseTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Course.all().size(), 0);
  }

  @Test
  public void save_testNewCourseIsCreated_true() {
    Course newCourse = new Course("Intro to Programming", 1, "2016-01-04", "2016-02-05");
    newCourse.save();
    Course savedCourse = Course.all().get(0);
    assertTrue(savedCourse.equals(newCourse));
  }

  @Test
  public void find_findsCourseInDatabase_true() {
    Course newCourse = new Course("Intro to Programming", 1, "2016-01-04", "2016-02-05");
    newCourse.save();
    Course savedCourse = Course.find(newCourse.getId());
    assertTrue(newCourse.equals(savedCourse));
  }

  @Test
  public void update_updatesCourseInfo_true() {
    Course newCourse = new Course("Intro to Programming", 1, "2016-01-04", "2016-02-05");
    newCourse.save();
    newCourse.update("Javascript", 2, "2016-02-08", "2016-03-11");
    assertEquals("Javascript", newCourse.getName());
  }

  @Test
  public void getStudents_returnsAllStudents_ArrayList() {
    Course newCourse = new Course("Intro to Programming", 1, "2016-01-04", "2016-02-05");
    newCourse.save();
    Student newStudent = new Student("Bowen", "Midori", "2016-01-26");
    newStudent.save();

    newCourse.addStudent(newStudent);
    List savedStudents = newCourse.getStudents();
    assertEquals(savedStudents.size(), 1);
  }

  @Test
  public void addStudent_addsStudentToCourse() {
    Course newCourse = new Course("Intro to Programming", 1, "2016-01-04", "2016-02-05");
    newCourse.save();
    Student newStudent = new Student("Bowen", "Midori", "2016-01-26");
    newStudent.save();
    
    newCourse.addStudent(newStudent);
    Student savedStudent = newCourse.getStudents().get(0);
    assertTrue(newStudent.equals(savedStudent));
  }


}
