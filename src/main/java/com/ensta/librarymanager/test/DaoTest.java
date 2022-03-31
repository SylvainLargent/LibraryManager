package com.ensta.librarymanager.test;

import com.ensta.librarymanager.dao.impl.EmpruntDao;
import com.ensta.librarymanager.dao.impl.LivreDao;
import com.ensta.librarymanager.dao.impl.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.utils.Abonnement;
import java.time.LocalDate;
import java.util.List;

public class DaoTest {
    public static void main(String args[]) throws DaoException {
        Livre livre1 = new Livre(0, "Enchanted", "Liam", "LKE");
        Membre membre1 = new Membre(1, "Dupont", "Jean", "Rue du Futur", "mail@free.fr", "0607080901", Abonnement.VIP);
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.of(2022, 11, 22);
        Emprunt emprunt1 = new Emprunt(2, 1, 0, start, end);

        EmpruntDao empDao = EmpruntDao.getInstance();
        List<Emprunt> listEmprunt = empDao.getList();
        System.out.println(listEmprunt);
        LivreDao livreDao = LivreDao.getInstance();
        List<Livre> listLivre = livreDao.getList();
        System.out.println(listLivre);
        MembreDao membreDao = MembreDao.getInstance();
        List<Membre> listMembre = membreDao.getList();
        System.out.println(listMembre);

    }
}
