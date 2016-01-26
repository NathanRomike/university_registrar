import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Student> students = Student.all();
      model.put("students", students);
      model.put("template", "templates/students.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/students", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String lastName = request.queryParams("last-name");
      String firstName = request.queryParams("first-name");
      String enrollmentDate = request.queryParams("enrollment-date");
      Student newStudent = new Student(lastName, firstName, enrollmentDate);
      newStudent.save();
      response.redirect("/students");
      return null;
    });

  }
}
