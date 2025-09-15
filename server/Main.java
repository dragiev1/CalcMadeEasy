package server;

import server.models.Chapters.Chapter;
import server.models.Courses.Course;
import server.models.Pages.Page;
import server.models.Problem.Problem;
import server.models.Problem.ProblemSolutionType;
import server.models.Sections.Section;

class Main {

  public static void main(String[] args) {
    // Test to see if Page.java is working properly.
    Problem problem1 = Problem.builder()
        .description("Derivate 2x.")
        .type(ProblemSolutionType.NUMERICAL)
        .points(1)
        .solutionPath("Solutions/Derivatives/2.md")
        .isChallenge(false)
        .topics("Derivatives", "Power Rule")
        .build();

    Problem problem2 = Problem.builder()
        .description("Integrate 2xdx.")
        .type(ProblemSolutionType.NUMERICAL)
        .points(2)
        .solutionPath("Solutions/Derivatives/3.md")
        .isChallenge(false)
        .topics("Integrals", "Power Rule")
        .build();

    Problem problem3 = Problem.builder()
        .description("Integrate 2x^2dx.")
        .type(ProblemSolutionType.EXPRESSION)
        .points(3)
        .solutionPath("Solutions/Derivatives/2.md")
        .isChallenge(true)
        .topics("Integrals", "Power Rule")
        .build();

    Page page1 = Page.builder()
        .content("MATH STUFF HERE HEHEHHEHEHEHEHEHEHEHEHEE")
        .exercises(problem1, problem2)
        .build();
    Page page2 = Page.builder()
        .content("YAY MORE MATH!")
        .homework(problem1, problem2, problem3)
        .build();
    Page page3 = Page.builder()
        .content("more math...")
        .exercises(problem2)
        .homework(problem3)
        .build();

    Section section1 = Section.builder()
        .title("Precalc Review")
        .description("This will be a review for precalculus. In this section you will learn...")
        .build();
    Section section2 = Section.builder()
        .title("Limits and Continuity")
        .description("This will be the first chapter of Calculus!")
        .pages(page1)
        .build();
    
    Chapter chapter1 = Chapter.builder()
        .title("Limits and Continuity")
        .description("In this chapter we will learn the fundamentals of calculus.")
        .sections(section1, section2)
        .build();

    Course course1 = Course.builder()
        .title("Calculus I")
        .description("In this course we will learn the fundamentals of calculus")
        .chapters(chapter1)
        .build();

  }
}