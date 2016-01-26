import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Course {
  private int mId;
  private String mName;
  private String mCourseCode;
  private String mTermStart;
  private String mTermEnd;

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public String getCourseCode() {
    return mCourseCode;
  }

  public String getTermStart() {
    return mTermStart;
  }

  public String getTermEnd() {
    return mTermEnd;
  }

  public String getTermLength() {
    return mTermStart + "-" + mTermEnd;
  }

  public Course(String name, String courseCode, String termStart, String termEnd) {
    this.mName = name;
    this.mCourseCode = courseCode;
    this.mTermStart = termStart;
    this.mTermEnd = termEnd;
  }

  @Override
  public boolean equals(Object otherCourse) {
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) &&
             this.getCourseCode().equals(newCourse.getCourseCode()) &&
             this.getTermStart().equals(newCourse.getTermStart()) &&
             this.getTermEnd().equals(newCourse.getTermEnd());

    }
  }

  public static List<Course> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, course_code AS mCourseCode, term_start AS mTermStart, term_end AS mTermEnd FROM courses";
      return con.createQuery(sql)
                .executeAndFetch(Course.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO courses(name, course_code, term_start, term_end) VALUES (:name, :courseCode, :termStart, :termEnd)";
      mId = (int) con.createQuery(sql, true)
        .addParameter("name", mName)
        .addParameter("courseCode", mCourseCode)
        .addParameter("termStart", mTermStart)
        .addParameter("termEnd", mTermEnd)
        .executeUpdate()
        .getKey();
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, course_code AS mCourseCode, term_start AS mTermStart, term_end AS mTermEnd FROM courses WHERE id = :id";
      Course course = con.createQuery(sql)
                         .addParameter("id", id)
                         .executeAndFetchFirst(Course.class);
      return course;
    }
  }

  public void update(String name, String courseCode, String termStart, String termEnd) {
    mName = name;
    mCourseCode = courseCode;
    mTermStart = termStart;
    mTermEnd = termEnd;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE courses SET name = :name, course_code = :courseCode, term_start = :termStart, term_end = :termEnd WHERE id = :id";
      con.createQuery(sql)
         .addParameter("name", name)
         .addParameter("courseCode", courseCode)
         .addParameter("termStart", termStart)
         .addParameter("termEnd", termEnd)
         .addParameter("id", this.getId())
         .executeUpdate();
    }
  }

  public void addStudent(Student student) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO enrollments(course_id, student_id, course_completed) VALUES (:courseId, :studentId, :courseCompleted)";
      con.createQuery(sql)
         .addParameter("courseId", this.mId)
         .addParameter("studentId", student.getId())
         .addParameter("courseCompleted", student.complete())
         .executeUpdate();
    }
  }

  public ArrayList<Student> getStudents() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT student_id FROM enrollments WHERE course_id = :courseId";
      List<Integer> studentIds = con.createQuery(sql)
        .addParameter("courseId", this.mId)
        .executeAndFetch(Integer.class);

      ArrayList<Student> associatedStudents = new ArrayList<Student>();

      for (Integer studentId : studentIds) {
        String studentQuery = "SELECT id AS mId, last_name AS mLastName, first_name AS mFirstName, enrollment_date AS mEnrollmentDate FROM students WHERE id = :studentId";
        Student student = con.createQuery(studentQuery)
                             .addParameter("studentId", studentId)
                             .executeAndFetchFirst(Student.class);
        associatedStudents.add(student);
      }
      return associatedStudents;
    }
  }

}
