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

@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("id"), 10);
        List<Emprunt> listEmprunt;
        try {
            Livre livre = livreService.getById(idLivre);
            request.setAttribute("livre", livre);
            // Affichage de la liste d'emprunt en cours
            listEmprunt = this.empruntService.getListCurrentByLivre(idLivre);
            List<Membre> listMembre = new ArrayList<>();
            for (Emprunt emprunt : listEmprunt) {
                listMembre.add(membreService.getById(emprunt.getIdMembre()));
            }
            request.setAttribute("listEmprunt", listEmprunt);
            request.setAttribute("listMembre", listMembre);
        } catch (ServiceException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        int idLivre = Integer.parseInt(request.getParameter("id"), 10);
        Livre livre = new Livre(idLivre, titre, auteur, isbn);
        try {
            livreService.update(livre);
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
        }
        this.doGet(request, response);
    }
}
