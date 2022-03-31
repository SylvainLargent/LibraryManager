package com.ensta.librarymanager.test;

import com.ensta.librarymanager.dao.impl.EmpruntDao;
import com.ensta.librarymanager.dao.impl.LivreDao;
import com.ensta.librarymanager.dao.impl.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.impl.EmpruntService;
import com.ensta.librarymanager.service.impl.LivreService;
import com.ensta.librarymanager.service.impl.MembreService;
import com.ensta.librarymanager.utils.Abonnement;
import java.time.LocalDate;
import java.util.List;

public class ServiceTest {
    public static void main(String args[]) throws ServiceException {
        Livre livre1 = new Livre(0, "Enchanted", "Liam", "LKE");
        Membre membre1 = new Membre(1, "Dupont", "Jean", "Rue du Futur", "mail@free.fr", "0607080901", Abonnement.VIP);
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.of(2022, 11, 22);
        Emprunt emprunt1 = new Emprunt(2, 1, 0, start, end);

        EmpruntService empService = EmpruntService.getInstance();
        List<Emprunt> listEmprunt = empService.getListCurrent();
        System.out.println(listEmprunt);
        LivreService livreService = LivreService.getInstance();
        // List<Livre> listLivre = livreService.getList();
        Livre livre = livreService.getById(3);
        System.out.println(livre);
        MembreService membreService = MembreService.getInstance();
        Membre membre = membreService.getById(5);
        System.out.println(membre);
        // List<Membre> listMembre = membreService.getList();
        // System.out.println(listMembre);

    }
}
