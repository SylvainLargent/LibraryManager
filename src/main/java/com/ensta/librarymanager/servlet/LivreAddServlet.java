package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.impl.*;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        int idLivre;
        try {
            idLivre = livreService.create(titre, auteur, isbn);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        String url = "http://localhost:8080/TP3Ensta/livre_details?id=";
        url += idLivre;
        response.sendRedirect(url);
    }
}
