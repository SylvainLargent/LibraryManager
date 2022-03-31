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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("livreCount", this.livreService.count());
            request.setAttribute("membreCount", this.membreService.count());
            request.setAttribute("empruntCount", this.empruntService.count());
            List<Emprunt> listEmprunt = this.empruntService.getListCurrent();
            List<Livre> listLivre = new ArrayList<>();
            List<Membre> listMembre = new ArrayList<>();
            for (Emprunt emprunt : listEmprunt) {
                listLivre.add(livreService.getById(emprunt.getIdLivre()));
                listMembre.add(membreService.getById(emprunt.getIdMembre()));
            }
            request.setAttribute("listEmprunt", listEmprunt);
            request.setAttribute("listMembre", listMembre);
            request.setAttribute("listLivre", listLivre);
        } catch (ServiceException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServletException();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
