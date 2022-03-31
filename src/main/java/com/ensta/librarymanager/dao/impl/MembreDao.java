package com.ensta.librarymanager.dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.dao.IMembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.utils.Abonnement;

public class MembreDao implements IMembreDao {
    private static MembreDao instance;

    private MembreDao() {
    }

    public static MembreDao getInstance() {
        if (instance == null) {
            instance = new MembreDao();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre ORDER BY nom, prenom;")) {
            List<Membre> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int key = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abo = Abonnement.valueOf(res.getString("abonnement").toUpperCase());
                list.add(new Membre(key, nom, prenom, adresse, email, telephone, abo));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public Optional<Membre> getById(int id) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(
                                "SELECT id, nom, prenom, adresse, email, telephone, abonnement FROM membre WHERE id = ?")) {

            stmt.setInt(1, id); // Remplacement du ?
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresse = res.getString("adresse");
                String email = res.getString("email");
                String telephone = res.getString("telephone");
                Abonnement abo = Abonnement.valueOf(res.getString("abonnement").toUpperCase());
                return Optional.ofNullable(new Membre(id, nom, prenom, adresse, email, telephone, abo));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(
                                "INSERT INTO membre(nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, adresse);
            stmt.setString(4, email);
            stmt.setString(5, telephone);
            stmt.setString(6, "BASIC");

            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void update(Membre membre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement(
                                "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?")) {
            stmt.setString(1, membre.getNom());
            stmt.setString(2, membre.getPrenom());
            stmt.setString(3, membre.getAdresse());
            stmt.setString(4, membre.getEmail());
            stmt.setString(5, membre.getTelephone());
            stmt.setString(6, String.valueOf(membre.getAbonnement()));
            stmt.setInt(7, membre.getPrimaryKey());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void delete(Membre membre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("DELETE FROM membre WHERE id = ?")) {
            stmt.setInt(1, membre.getPrimaryKey());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public int count() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT COUNT(id) AS count FROM membre")) {

            ResultSet res = stmt.executeQuery();
            res.next();
            return res.getInt("count");

        } catch (SQLException e) {
            throw new DaoException();
        }
    }

}
