import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Student {

// INSTATIATE MEMBER VARIABLES
  private int mId;
  private String mLastName;
  private String mFirstName;
  private String mEnrollmentDate;
  private boolean mComplete;


// GETTERS
  public int getId() {
    return mId;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getFirstName() {
    return mFirstName;
  }

  public String getFullName() {
    return mFirstName + " " + mLastName;
  }

  public String getEnrollmentDate() {
    return mEnrollmentDate;
  }

  public boolean complete() {
    return mComplete;
  }


// CONSTRUCTOR
  public Student(String lastName, String firstName, String enrollmentDate) {
    this.mLastName = lastName;
    this.mFirstName = firstName;
    this.mEnrollmentDate = enrollmentDate;
    this.mComplete = false;
  }

  @Override
  public boolean equals(Object otherStudent) {
    if (!(otherStudent instanceof Student)) {
      return false;
    } else {
      Student newStudent = (Student) otherStudent;
      return this.getFullName().equals(newStudent.getFullName()) &&
             this.getEnrollmentDate().equals(newStudent.getEnrollmentDate());
    }
  }

  public static List<Student> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName, enrollment_date AS mEnrollmentDate FROM students";
      return con.createQuery(sql)
                .executeAndFetch(Student.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO students(last_name, first_name, enrollment_date) VALUES (:lastName, :firstName, :enrollmentDate)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("lastName", mLastName)
        .addParameter("firstName", mFirstName)
        .addParameter("enrollmentDate", mEnrollmentDate)
        .executeUpdate()
        .getKey();
    }
  }

  public static Student find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName, enrollment_date AS mEnrollmentDate FROM students WHERE id = :id";
      Student student = con.createQuery(sql)
                           .addParameter("id", id)
                           .executeAndFetchFirst(Student.class);
      return student;
    }
  }

  public void update(String lastName, String firstName, String enrollmentDate) {
    mLastName = lastName;
    mFirstName = firstName;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE students SET last_name = :lastName, first_name = :firstName, enrollment_date = :enrollmentDate WHERE id = :id";
      con.createQuery(sql)
         .addParameter("lastName", lastName)
         .addParameter("firstName", firstName)
         .addParameter("enrollmentDate", enrollmentDate)
         .addParameter("id", this.getId())
         .executeUpdate();
    }
  }
}
