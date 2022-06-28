package com.google.sps.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the value entered in the form.
        String textValue = request.getParameter("text-input");

        // Sanitize user input to remove HTML tags and JavaScript.
        long timestamp = System.currentTimeMillis();

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Comment");
        FullEntity commentEntity =
            Entity.newBuilder(keyFactory.newKey())
                .set("comment", textValue)
                .set("timestamp", timestamp)
                .build();
        datastore.put(commentEntity);

        // Print the value so you can see it in the server logs.
        System.out.println("You submitted: " + textValue);

        // Write the value to the response so the user can see it.
        response.getWriter().println("You submitted: " + textValue);

        // Using logger
        LOGGER.log(Level.INFO, "My first Log Message");

        response.sendRedirect("/index.html");
    }
}
