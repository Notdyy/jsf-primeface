package my.example.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.example.model.Person;
import my.example.service.NameService;

@WebServlet("/html-result-servlet")
public class HtmlResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NameService nameService = new NameService();
        Person person = new Person();
        person.setFirstName(request.getParameter("fname"));
        person.setLastName(request.getParameter("lname"));

        response.setContentType("text/html");
        try {
            response.getWriter().append("<!DOCTYPE html>\r\n");
            response.getWriter().append("<html>\r\n");
            response.getWriter().append("<head>\r\n");
            response.getWriter().append("<meta charset=\"UTF-8\">\r\n");
            response.getWriter().append("<title>HTML Form</title>\r\n");
            response.getWriter().append("</head>\r\n");
            response.getWriter().append("<body>\r\n");
            response.getWriter().append("Your name is ").append(nameService.display(person)).append("\r\n<br/>");
            response.getWriter().append("<a href=\"form-02.html\"> Back </a>\r\n");
            response.getWriter().append("</body>\r\n");
            response.getWriter().append("</html>");
        } catch (IOException e) {
            // Handle the IOException here
            e.printStackTrace(); // or log the exception
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do nothing
    }
}