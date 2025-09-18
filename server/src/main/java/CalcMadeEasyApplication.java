import models.Chapters.Chapter;
import models.Courses.Course;
import models.Pages.Page;
import models.Problem.Problem;
import models.Problem.ProblemSolutionType;
import models.Sections.Section;
import models.Users.User;
import models.Users.UserProgress;
import services.ProblemServices;

class CalcMadeEasyApplication {

  public static void main(String[] args) {
    // Test to see if Page.java is working properly.
    Problem problem1 = Problem.builder()
        .description("Derivate 2x.")
        .type(ProblemSolutionType.EXPRESSION)
        .points(1)
        .solution("sin(43x^2) + cos^2(32/3) - x^22 + x^2")
        .isChallenge(false)
        .topics("Derivatives", "Power Rule")
        .build();

    Problem problem2 = Problem.builder()
        .description("Derivate 2x.")
        .type(ProblemSolutionType.EXPRESSION)
        .points(5)
        .solution("sin(43x^2) + cos^2(32/3) - x^22 + x^2")
        .isChallenge(false)
        .topics("Derivatives", "Power Rule")
        .build();

    Page page1 = Page.builder()
        .content("MATH STUFF HERE HEHEHHEHEHEHEHEHEHEHEHEE")
        .exercises(problem1)
        .build();

    Section section1 = Section.builder()
        .title("Precalc Review")
        .description("This will be a review for precalculus. In this section you will learn...")
        .pages(page1)
        .build();

    Chapter chapter1 = Chapter.builder()
        .title("Limits and Continuity")
        .description("In this chapter we will learn the fundamentals of calculus.")
        .sections(section1)
        .build();

    Course course1 = Course.builder()
        .title("Calculus I")
        .description("In this course we will learn the fundamentals of calculus")
        .chapters(chapter1)
        .build();

    User user1 = User.builder()
        .firstName("Stiviyan")
        .lastName("Dragiev")
        .email("stiviyandragiev@gmail.com")
        .profilePicUrl("kjndkjwandkjanwdka/diwaoidjoaiwdja.png")
        .courses(course1)
        .build();

    ProblemServices service = new ProblemServices();

    boolean correct = service.verifySolution(problem1, "cos^2(32/3) + sin(43x^2) - x^22 + x^2");
    boolean correct2 = service.verifySolution(problem2, "cos^2(32/3) + sin(43x^2) - x^22 + x^2");

    System.out.println(correct);

    UserProgress uProgress = new UserProgress(user1.getId(), problem1.getId());
    UserProgress uProgress2 = new UserProgress(user1.getId(), problem1.getId());

    uProgress.recordAttempt(correct, problem1);
    uProgress2.recordAttempt(correct2, problem2);

    System.out.println(uProgress.getAttempts() + " " +
        uProgress.getLastAttemptedAt() + " " +
        uProgress.getPointsEarned() + " " + uProgress.isSolved());
    System.out.println(uProgress2.getAttempts() + " " +
        uProgress2.getLastAttemptedAt() + " " +
        uProgress2.getPointsEarned() + " " + uProgress2.isSolved());

  }
}