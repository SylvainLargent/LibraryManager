package com.ensta.librarymanager.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.dao.IEmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;

public class EmpruntDao implements IEmpruntDao {

    private static EmpruntDao instance;

    private EmpruntDao() {
    }

    public static EmpruntDao getInstance() {
        if (instance == null) {
            instance = new EmpruntDao();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC")) {
            List<Emprunt> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int primaryKey = res.getInt("id");
                int idMembre = res.getInt("idMembre");
                int idLivre = res.getInt("idLivre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                list.add(new Emprunt(primaryKey, idMembre, idLivre, dateEmprunt, dateRetour));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC")) {
            List<Emprunt> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int primaryKey = res.getInt("id");
                int idMembre = res.getInt("idMembre");
                int idLivre = res.getInt("idLivre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                if (dateRetour == null) {
                    list.add(new Emprunt(primaryKey, idMembre, idLivre, dateEmprunt, dateRetour));
                }
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE membre.id = ?")) {
            stmt.setInt(1, idMembre);
            List<Emprunt> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int primaryKey = res.getInt("id");
                int idLivre = res.getInt("idLivre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                if (dateRetour == null) {
                    list.add(new Emprunt(primaryKey, idMembre, idLivre, dateEmprunt, dateRetour));
                }
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(Emprunt emprunt) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE membre.id = ?")) {
            stmt.setInt(1, emprunt.getIdMembre());
            List<Emprunt> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int primaryKey = res.getInt("id");
                int idLivre = res.getInt("idLivre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                if (dateRetour == null) {
                    list.add(new Emprunt(primaryKey, emprunt.getIdMembre(), idLivre, dateEmprunt, dateRetour));
                }
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?")) {
            stmt.setInt(1, idLivre);
            List<Emprunt> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int primaryKey = res.getInt("id");
                int idMembre = res.getInt("idMembre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                list.add(new Emprunt(primaryKey, idMembre, idLivre, dateEmprunt, dateRetour));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

    @Override
    public Optional<Emprunt> getById(Emprunt emprunt) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?")) {
            stmt.setInt(1, emprunt.getPrimaryKey());
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                int idLivre = res.getInt("idLivre");
                int idMembre = res.getInt("idMembre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                return Optional
                        .ofNullable(new Emprunt(emprunt.getPrimaryKey(), idMembre, idLivre, dateEmprunt, dateRetour));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

    @Override
    public Optional<Emprunt> getById(int id) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?")) {
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                int idLivre = res.getInt("idLivre");
                int idMembre = res.getInt("idMembre");
                LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
                LocalDate dateRetour;
                if (res.getDate("dateRetour") != null) {
                    dateRetour = res.getDate("dateRetour").toLocalDate();
                } else {
                    dateRetour = null;
                }
                return Optional
                        .ofNullable(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

    @Override
    public int create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(
                                "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idMembre);
            stmt.setInt(2, idLivre);
            stmt.setDate(3, Date.valueOf(dateEmprunt));
            stmt.setDate(4, null);
            ResultSet resultSet = stmt.getGeneratedKeys();
            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            stmt.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
        // Si on renvoyait un int, on aurait renvoyé return stmt.executeuptdate();
    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(
                                "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?")) {
            stmt.setInt(1, emprunt.getIdMembre());
            stmt.setInt(2, emprunt.getIdLivre());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            stmt.setInt(5, emprunt.getPrimaryKey());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

    @Override
    public int count() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT COUNT(id) AS count FROM emprunt")) {

            ResultSet res = stmt.executeQuery();
            res.next();
            return res.getInt("count");

        } catch (SQLException e) {
            e.printStackTrace(); // Permet de remonter les erreurs de la suite d'exceptions
            throw new DaoException();
        }
    }

}
