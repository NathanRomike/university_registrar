import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;
import org.junit.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
    public void rootTest() {
      goTo("http://localhost:4567");
      assertThat(pageSource()).contains("Welcome to Epicodus University");
  }

// As a registrar, I want to enter a student, so I can keep track of all students enrolled at this University. I should be able to provide a name and date of enrollment.

  @Test
    public void rootPageInputForNewStudentInformation() {
      goTo("http://localhost:4567");
      fill("#last-name").with("Bowen");
      fill("#first-name").with("Midori");
      fill("#enrollment-date").with("2016-01-23");
      submit(".new-student");
      assertThat(pageSource()).contains("2016-01-23");
    }

  @Test
    public void newCourseIsAddedToCoursesPage() {
      goTo("http://localhost:4567/courses");
      fill("#course-name").with("History");
      fill("#course-code").with("101");
      fill("#course-term-start").with("2016-01-04");
      fill("#course-term-end").with("2016-02-05");
      submit(".new-course");
      assertThat(pageSource()).contains("History");
    }
}
