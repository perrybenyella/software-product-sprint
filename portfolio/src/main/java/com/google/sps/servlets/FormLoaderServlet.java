package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
//import com.google.sps.data.Task;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form-loader")
public class FormLoaderServlet extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query =
            Query.newEntityQueryBuilder().setKind("Comment").setOrderBy(OrderBy.desc("timestamp")).build();
        QueryResults<Entity> results = datastore.run(query);

        ArrayList<Map<String, Object>> comments = new ArrayList<>();
        while (results.hasNext()) {
            Entity entity = results.next();

            // long id = entity.getKey().getId();
            // String title = entity.getString("comment");
            // long timestamp = entity.getLong("timestamp");

            // Task task = new Task(id, title, timestamp);
            // comments.add(task);

            comments.add(Map.of(
                "comment", entity.getString("comment"),
                "timestamp", entity.getLong("timestamp")
            ));
        }

        Gson gson = new Gson();

        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(comments));
    }
}
