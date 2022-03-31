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

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {
    LivreService livreService = LivreService.getInstance();
    MembreService membreService = MembreService.getInstance();
    EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String empruntDefaultString = request.getParameter("id");
            int empruntDefault = -1;
            Livre livreDefault = null;
            Membre membreDefault = null;
            if (empruntDefaultString == null) {
                empruntDefault = 0;
            } else {
                empruntDefault = Integer.parseInt(empruntDefaultString, 10);
                livreDefault = livreService.getById(empruntService.getById(empruntDefault).getIdLivre());
                membreDefault = membreService.getById(empruntService.getById(empruntDefault).getIdMembre());
            }
            String query = request.getParameter("show");
            List<Emprunt> listEmprunt;
            if (query == "all") {
                listEmprunt = this.empruntService.getList();
            } else {
                listEmprunt = this.empruntService.getListCurrent();
            }
            List<Livre> listLivre = new ArrayList<>();
            List<Membre> listMembre = new ArrayList<>();
            for (Emprunt emprunt : listEmprunt) {
                listLivre.add(livreService.getById(emprunt.getIdLivre()));
                listMembre.add(membreService.getById(emprunt.getIdMembre()));
            }
            request.setAttribute("listEmprunt", listEmprunt);
            request.setAttribute("listMembre", listMembre);
            request.setAttribute("listLivre", listLivre);
            request.setAttribute("empruntDefault", empruntDefault);
            request.setAttribute("livreDefault", livreDefault);
            request.setAttribute("membreDefault", membreDefault);
        } catch (ServiceException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmprunt = Integer.parseInt(request.getParameter("id"), 10);

        try {
            empruntService.returnBook(idEmprunt);
            ;
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServletException();
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
