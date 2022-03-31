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

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("listLivre", livreService.getListDispo());
            request.setAttribute("listMembre", membreService.getListMembreEmpruntPossible());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("idLivre"), 10);
        int idMembre = Integer.parseInt(request.getParameter("idMembre"), 10);
        LocalDate dateEmprunt = LocalDate.now();
        try {
            empruntService.create(idMembre, idLivre, dateEmprunt);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        List<Emprunt> listEmprunt;
        try {
            listEmprunt = this.empruntService.getListCurrent();
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
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp").forward(request, response);
    }
}
