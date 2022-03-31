package com.ensta.librarymanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.dao.impl.EmpruntDao;
import com.ensta.librarymanager.dao.impl.LivreDao;
import com.ensta.librarymanager.dao.impl.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.ILivreService;

public class LivreService implements ILivreService {
    private static LivreService instance;

    private LivreService() {
    }

    public static LivreService getInstance() {
        if (instance == null) {
            instance = new LivreService();
        }
        return instance;
    }

    // Attributs
    private EmpruntDao empruntDao = EmpruntDao.getInstance();
    private LivreDao livreDao = LivreDao.getInstance();
    private MembreDao membreDao = MembreDao.getInstance();
    private EmpruntService empruntService = EmpruntService.getInstance();

    @Override
    public List<Livre> getList() throws ServiceException {
        try {
            return livreDao.getList();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public List<Livre> getListDispo() throws ServiceException {
        try {
            List<Livre> listDispo = new ArrayList<>();
            for (Livre livre : livreDao.getList()) {
                if (empruntService.isLivreDispo(livre.getPrimaryKey())) {
                    listDispo.add(livre);
                }
            }
            return listDispo;
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public Livre getById(int id) throws ServiceException {
        try {
            Optional<Livre> livreOpt = livreDao.getById(id);
            if (livreOpt.isPresent()) {
                return livreOpt.get();
            } else {
                System.out.println("Le livre n'est pas présent dans la base de donnée.");
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws ServiceException {
        try {
            if (titre.length() == 0) {
                throw new DaoException();
            } else {
                return livreDao.create(titre, auteur, isbn);
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public void update(Livre livre) throws ServiceException {
        try {
            if (livre.getTitre().length() == 0) {
                throw new DaoException();
            } else {
                livreDao.update(livre);
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }

    }

    @Override
    public void delete(int id) throws ServiceException {
        try {
            Livre livre = livreDao.getById(id).get();
            livreDao.delete(livre);
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public int count() throws ServiceException {
        try {
            return livreDao.count();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

}
