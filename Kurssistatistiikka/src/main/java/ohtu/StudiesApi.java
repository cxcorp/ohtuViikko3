package ohtu;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;

import java.util.Arrays;
import java.util.List;

public class StudiesApi {
    private final Gson gson = new Gson();

    public List<CourseInfo> getCourseInfos() throws Exception {
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
    }

    public List<Submission> getSubmissions(String studentId) throws Exception {
        String url = constructSubmissionsUrl(studentId);
        String json =
            Request.Get(url)
                .execute()
                .returnContent()
                .asString();

        return Arrays.asList(gson.fromJson(json, Submission[].class));
    }

    private String constructCourseInfoUrl() {
        return "https://studies.cs.helsinki.fi/courses/courseinfo";
    }

    private String constructSubmissionsUrl(String studentId) {
        return String.format("https://studies.cs.helsinki.fi/courses/students/%s/submissions", studentId);
    }
}
