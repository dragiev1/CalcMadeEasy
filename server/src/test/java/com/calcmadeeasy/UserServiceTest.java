package com.calcmadeeasy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.calcmadeeasy.dto.Users.UserDTO;
import com.calcmadeeasy.models.Courses.Course;
import com.calcmadeeasy.models.Users.User;
import com.calcmadeeasy.models.Users.UserCourseEnrollment;
import com.calcmadeeasy.repository.CourseRepo;
import com.calcmadeeasy.repository.UserRepo;
import com.calcmadeeasy.services.UserProgressService;
import com.calcmadeeasy.services.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired private UserService userService;
    @Autowired private UserProgressService upService;

    private User user;
    private Course course;

    @Autowired private UserRepo userRepo;
    @Autowired private CourseRepo courseRepo;

    @BeforeEach
    public void setup() {
        // Create and save course directly via repository
        course = Course.builder()
            .description("DESCRIPTION")
            .title("TITLE")
            .build();
        course = courseRepo.save(course);

        user = User.builder()
            .firstName("FIRSTNAME")
            .lastName("LASTNAME")
            .email("TEST@EXAMPLE.COM")
            .profilePicUrl("/test.png")
            .build();
        user = userRepo.save(user);
    }

    @Test
    public void testCreateUser() {
        assertTrue(userService.exists(user.getId()), "User should exist after setup, but doesn't.");
    }

    @Test
    public void testGetUser() {
        User fetchedUser = userService.getUser(user.getId());
        assertEquals(fetchedUser.getEmail(), user.getEmail());
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(userService.getAllUsers().size(), 1);
    }

    @Test
    public void testEnrollCourse() {
        UserDTO resultDTO = userService.enrollCourse(user.getId(), course.getId());
        assertEquals(1, resultDTO.getCoursesEnrolled().size(), "DTO should show 1 enrolled course");

        User updatedUser = userService.getUser(user.getId());
        assertEquals(1, updatedUser.getCoursesEnrolled().size(), "Updated user's enrolled courses is not 1");
        
        UserCourseEnrollment actualEnrollment = updatedUser.getCoursesEnrolled().iterator().next();
        assertEquals(course.getId(), actualEnrollment.getCourse().getId());
        assertEquals(user.getId(), actualEnrollment.getUser().getId());
    }

    @Test
    public void testUnenrollCourse() {
        UserDTO resultDTO = userService.enrollCourse(user.getId(), course.getId());
        assertEquals(1, resultDTO.getCoursesEnrolled().size());

        resultDTO = userService.unenrollCourse(user.getId(), course.getId());

        User updatedUser = userService.getUser(user.getId());
        assertEquals(0, updatedUser.getCoursesEnrolled().size(), "User's course list must be empty, but isn't");
        
        // Should delete user progress for that course. 
        assertTrue(upService.getProgressForUserEntity(user.getId()).isEmpty(), "All progress for this course should be removed upon unenrollment");
    }

    @Test
    public void testRemoveUser() {
        userService.removeUser(user.getId());
        assertFalse(userService.exists(user.getId()));
    }
}