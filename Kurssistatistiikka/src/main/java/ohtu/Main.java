package ohtu;

import ohtu.api.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        StudiesApi api = new StudiesApi();
        List<Submission> subs = api.getSubmissions(studentNr);
        List<CourseInfo> courses = api.getCourseInfos();
        Map<String, CourseStats> courseWeekStats = getTotalStatsForCourses(api, courses);

        SubmissionStatsPrinter printer = new SubmissionStatsPrinter(System.out);
        printer.print(studentNr, subs, courses, courseWeekStats);
    }

    private static Map<String, CourseStats> getTotalStatsForCourses(StudiesApi api, List<CourseInfo> courses) {
        return courses
            .stream()
            .collect(Collectors.toMap(
                CourseInfo::getName,
                course -> api.getCourseStats(course.getName())
            ));
    }
}