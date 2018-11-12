package ohtu;

import com.google.common.base.Joiner;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubmissionStatsPrinter {
    private final NumberFormat hoursFormat;
    private final CourseStatsCalculator courseStats;
    private final SubmissionStatsCalculator subStats;
    private final PrintStream stream;

    private Map<String, List<Submission>> courseToSubmissions;
    private List<CourseInfo> usersCourses;

    public SubmissionStatsPrinter(PrintStream stream) {
        // hours can be decimals; format so that the decimal point is only
        // showed if needed
        this.hoursFormat = new DecimalFormat("##.#");
        this.courseStats = new CourseStatsCalculator();
        this.subStats = new SubmissionStatsCalculator();
        this.stream = stream;
    }

    public void print(String studentId, List<Submission> subs, List<CourseInfo> courses) {
        prepareData(subs, courses);

        stream.printf("opiskelijanumero %s\n", studentId);
        stream.println();

        for (CourseInfo course : usersCourses) {
            stream.println(course.getPrintName());
            stream.println();

            // assumption: course listing contains all courses that submissions can belong to
            List<Submission> courseSubs = courseToSubmissions.get(course.getName());
            printCourseSubmissions(course, courseSubs);

            stream.println();
            stream.printf("yhteensä: %d/%d tehtävää, %s tuntia\n",
                subStats.countDoneExercises(courseSubs),
                courseStats.countAllExercises(course),
                hoursFormat.format(subStats.countUsedHours(courseSubs))
            );
            stream.println();
        }
    }

    private void printCourseSubmissions(CourseInfo course, List<Submission> subs) {
        // Print course submissions by week, ordered by week number
        // assumption: subs contains only one course's subs
        List<Submission> sortedSubs = immutableSort(subs, (a, b) -> a.getWeek() - b.getWeek());

        for (Submission sub : sortedSubs) {
            stream.printf("viikko %d:\n", sub.getWeek());
            stream.printf(" tehtyjä tehtäviä %d/%d, aikaa kului %s tuntia, tehdyt tehtävät: %s\n",
                sub.getExercises().size(),
                courseStats.countExercisesOfWeek(course, sub.getWeek()),
                hoursFormat.format(sub.getHours()),
                formatDoneExercises(sub)
            );
        }
    }

    private static String formatDoneExercises(Submission sub) {
        return Joiner.on(", ").join(sub.getExercises());
    }

    private void prepareData(List<Submission> subs, List<CourseInfo> courses) {
        Map<String, CourseInfo> coursesByName =
            courses
                .stream()
                .collect(Collectors.toMap(CourseInfo::getName, Function.identity()));
        this.courseToSubmissions =
            subs
                .stream()
                .collect(Collectors.groupingBy(Submission::getCourse));
        this.usersCourses =
            subs
                .stream()
                .map(Submission::getCourse)
                .distinct()
                .map(coursesByName::get)
                .collect(Collectors.toList());
    }

    private static <T> List<T> immutableSort(List<T> list, Comparator<? super T> comparator) {
        List<T> newList = new ArrayList<T>(list);
        newList.sort(comparator);
        return newList;
    }
}
