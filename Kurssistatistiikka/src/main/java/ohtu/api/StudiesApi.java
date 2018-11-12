package ohtu.api;

import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StudiesApi {
    private final Gson gson = new Gson();
    private final Type courseWeekStatsType = new TypeToken<Map<Integer, CourseWeekStats>>() {
    }.getType();

    public CourseStats getCourseStats(String courseName) {
        try {
            String url = constructCourseWeekStatsUrl(courseName);
            String json =
                Request.Get(url)
                    .execute()
                    .returnContent()
                    .asString();

            // this would probably belong to a service layer
            Map<Integer, CourseWeekStats> weekStats = gson.fromJson(json, courseWeekStatsType);
            int totalSubmissions =
                weekStats.values()
                    .stream()
                    .mapToInt(CourseWeekStats::getStudents)
                    .sum();
            int totalExercises =
                weekStats.values()
                    .stream()
                    .mapToInt(CourseWeekStats::getTotalExercises)
                    .sum();
            double totalHours =
                weekStats.values()
                    .stream()
                    .mapToDouble(CourseWeekStats::getTotalHours)
                    .sum();
            return new CourseStats(totalSubmissions, totalExercises, totalHours);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CourseInfo> getCourseInfos() {
        try {
            String json =
                Request.Get(constructCourseInfoUrl())
                    .execute()
                    .returnContent()
                    .asString();
            List<CourseInfo> list = Arrays.asList(gson.fromJson(json, CourseInfo[].class));
            // fix API problem
            for (CourseInfo course : list) {
                if (course.getName().equals("docker-beta devops -with")) {
                    course.setName("rails2018");
                }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Submission> getSubmissions(String studentId) {
        try {
            String url = constructSubmissionsUrl(studentId);
            String json =
                Request.Get(url)
                    .execute()
                    .returnContent()
                    .asString();

            return Arrays.asList(gson.fromJson(json, Submission[].class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String constructCourseInfoUrl() {
        return "https://studies.cs.helsinki.fi/courses/courseinfo";
    }

    private String constructSubmissionsUrl(String studentId) {
        return String.format("https://studies.cs.helsinki.fi/courses/students/%s/submissions", studentId);
    }

    private String constructCourseWeekStatsUrl(String courseName) {
        return String.format("https://studies.cs.helsinki.fi/courses/%s/stats", courseName);
    }
}
