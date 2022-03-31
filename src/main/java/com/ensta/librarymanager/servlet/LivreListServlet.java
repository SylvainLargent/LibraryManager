package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.*;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/livre_list")
public class LivreListServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Livre> listLivre;
            listLivre = livreService.getList();
            request.setAttribute("listLivre", listLivre);
        } catch (ServiceException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
