package my.example.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.example.model.Person;
import my.example.service.NameService;

@WebServlet("/text-result-servlet")
public class TextResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NameService nameService = new NameService();

        Person person = new Person();
        person.setFirstName(request.getParameter("fname"));
        person.setLastName(request.getParameter("lname"));

        try {
            response.getWriter().append(nameService.display(person));
        } catch (IOException e) {
            // Handle the IOException here
            e.printStackTrace(); // or log the exception
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Do nothing
    }
}