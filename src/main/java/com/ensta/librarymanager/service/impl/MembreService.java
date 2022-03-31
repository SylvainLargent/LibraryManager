package com.ensta.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.impl.EmpruntDao;
import com.ensta.librarymanager.dao.impl.LivreDao;
import com.ensta.librarymanager.dao.impl.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.IMembreService;

public class MembreService implements IMembreService {
    private static MembreService instance;

    private MembreService() {
    }

    public static MembreService getInstance() {
        if (instance == null) {
            instance = new MembreService();
        }
        return instance;
    }

    // Attributs
    private EmpruntDao empruntDao = EmpruntDao.getInstance();
    private LivreDao livreDao = LivreDao.getInstance();
    private MembreDao membreDao = MembreDao.getInstance();
    private EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    public List<Membre> getList() throws ServiceException {
        try {
            return membreDao.getList();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        try {
            List<Membre> listMembre = membreDao.getList();
            List<Membre> resultat = new ArrayList<>();
            for (Membre membre : listMembre) {
                if (empruntService.isEmpruntPossible(membre)) {
                    resultat.add(membre);
                }
            }
            return resultat;
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public Membre getById(int id) throws ServiceException {
        try {
            if (membreDao.getById(id).isPresent()) {
                Membre ref = membreDao.getById(id).get();
                return ref;
            }
            return null;
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone)
            throws ServiceException {
        try {
            return membreDao.create(nom, prenom, adresse, email, telephone);
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public void update(Membre membre) throws ServiceException {
        try {
            membreDao.update(membre);
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            if (membreDao.getById(id).isPresent()) {
                membreDao.delete(membreDao.getById(id).get());
            } else {
                System.out.println("Le membre n'est pas dans la base de donnée");
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            return membreDao.count();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

}
