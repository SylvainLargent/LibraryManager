package com.ensta.librarymanager.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.IEmpruntService;
import com.ensta.librarymanager.utils.Abonnement;
import com.ensta.librarymanager.dao.*;
import com.ensta.librarymanager.dao.impl.EmpruntDao;
import com.ensta.librarymanager.dao.impl.LivreDao;
import com.ensta.librarymanager.dao.impl.MembreDao;

public class EmpruntService implements IEmpruntService {

    private static EmpruntService instance;

    private EmpruntService() {
    }

    public static EmpruntService getInstance() {
        if (instance == null) {
            instance = new EmpruntService();
        }
        return instance;
    }

    // Attributs
    private EmpruntDao empruntDao = EmpruntDao.getInstance();
    private LivreDao livreDao = LivreDao.getInstance();
    private MembreDao membreDao = MembreDao.getInstance();

    @Override
    public List<Emprunt> getList() throws ServiceException {
        try {
            return empruntDao.getList();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws ServiceException {
        try {
            return empruntDao.getListCurrent();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        try {
            return empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        try {
            return empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public Emprunt getById(Emprunt emprunt) throws ServiceException {
        try {
            Optional<Emprunt> empruntOpt = empruntDao.getById(emprunt);
            if (empruntOpt.isPresent()) {
                return empruntOpt.get();
            } else {
                System.out.println("L'emprunt n'est pas présent dans la base de donnée.");
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public Emprunt getById(int idEmprunt) throws ServiceException {
        try {
            Optional<Emprunt> empruntOpt = empruntDao.getById(idEmprunt);
            if (empruntOpt.isPresent()) {
                return empruntOpt.get();
            } else {
                System.out.println("L'emprunt n'est pas présent dans la base de donnée.");
                return null;
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public void returnBook(int id) throws ServiceException {
        try {
            Optional<Emprunt> empruntOpt = empruntDao.getById(id);
            if (empruntOpt.isPresent()) {
                Emprunt emprunt = empruntOpt.get();
                LocalDate dateRetour = LocalDate.now();
                emprunt.setDateRetour(dateRetour);
                empruntDao.update(emprunt);
            } else {
                System.out.println("L'emprunt n'est pas dans la base de données");
            }

        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

    @Override
    public int count() throws ServiceException {
        int result;
        try {
            result = empruntDao.count();
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
        return result;
    }

    @Override
    public boolean isLivreDispo(int idLivre) throws ServiceException {
        try {
            if (livreDao.getById(idLivre).isPresent()) {
                Livre ref = livreDao.getById(idLivre).get();
                for (Emprunt emprunt : empruntDao.getListCurrent()) {
                    if (ref.getPrimaryKey() == emprunt.getIdLivre()) {
                        return false;
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
        return true;
    }

    @Override
    public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        try {
            int books = empruntDao.getListCurrentByMembre(membre.getPrimaryKey()).size();
            if (membre.getAbonnement() == Abonnement.BASIC) {
                if (books < 2) {
                    return true;
                } else {
                    return false;
                }
            } else if (membre.getAbonnement() == Abonnement.PREMIUM) {
                if (books < 5) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (books < 20) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (DaoException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new ServiceException();
        }
    }

}
