package com.example.Project1Task3;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "submitAnswer", urlPatterns = {"/submit-answer", "/getResults"})
public class SubmitAnswer extends HttpServlet {
    //submit the answer
    //https://www.geeksforgeeks.org/getattribute-passing-data-from-server-to-jsp/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String ans = request.getParameter("ans");
        if (ans.equals("A")) {
            Result.numA++;
        } else if (ans.equals("B")) {
            Result.numB++;
        } else if (ans.equals("C")) {
            Result.numC++;
        } else {
            Result.numD++;
        }
        String message = "Your \""+ans+"\" response has been registered";
        String question = "Submit your answer to the next question:";
        request.setAttribute("message", message);
        request.setAttribute("question", question);
        try {
            //forward to the output page
            request.getRequestDispatcher("submit.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    //produce the result page
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\""+
        "\"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        out.println("<html><body>");
        out.println("<h1>Distributed Systems Class Clicker</h1>");
        if (Result.numA + Result.numB +Result.numC + Result.numD == 0) {
            out.println("There are currently no answers");
        } else {
            if (Result.numA != 0) {
                out.println("<div>A: "+Result.numA+"</div>");
            }
            if (Result.numB != 0) {
                out.println("<div>B: "+Result.numB+"</div>");
            }
            if (Result.numC != 0) {
                out.println("<div>C: "+Result.numC+"</div>");
            }
            if (Result.numD != 0) {
                out.println("<div>D: "+Result.numD+"</div>");
            }
            out.println("<div>These results have now been cleared</div>");
            Result.numA = 0;
            Result.numB = 0;
            Result.numC = 0;
            Result.numD = 0;
        }

        out.println("</body></html>");
    }
    public void destroy() {
    }
}