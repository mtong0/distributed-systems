package com.example.Project1Task2;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "dogFinder", value = "/dog-finder")
public class DogFinder extends HttpServlet {
    public void init() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String breed = request.getParameter("breed").toLowerCase();
        Dog dog = Scrapper.scrapInfo(breed);
        request.setAttribute("breed", breed.substring(0, 1).toUpperCase()+breed.substring(1));
        request.setAttribute("friendly", dog.friendly);
        request.setAttribute("height", dog.height);
        request.setAttribute("weight", dog.weight);
        request.setAttribute("lifespan", dog.lifespan);
        request.setAttribute("image", Scrapper.getImage(breed));
        try {
            request.getRequestDispatcher("dog.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}