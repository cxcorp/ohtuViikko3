package ohtu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudiesApi {
    private final Gson gson = new Gson();

    public List<Submission> getSubmissions(String studentId) throws Exception {
        String url = constructSubmissionsUrl(studentId);
        String content =
                Request.Get(url)
                        .execute()
                        .returnContent()
                        .asString();

        return Arrays.asList(gson.fromJson(content, Submission[].class));
    }

    private String constructSubmissionsUrl(String studentId) {
        return String.format("https://studies.cs.helsinki.fi/courses/students/%s/submissions", studentId);
    }
}
