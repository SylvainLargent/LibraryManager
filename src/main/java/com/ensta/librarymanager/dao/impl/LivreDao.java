package com.ensta.librarymanager.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ensta.librarymanager.dao.ILivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.*;

public class LivreDao implements ILivreDao {
    private static LivreDao instance;

    private LivreDao() {
    }

    public static LivreDao getInstance() {
        if (instance == null) {
            instance = new LivreDao();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT id, titre, auteur, isbn FROM livre")) {
            List<Livre> list = new ArrayList<>();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int key = res.getInt("id");
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                list.add(new Livre(key, titre, auteur, isbn));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public Optional<Livre> getById(int id) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT id, titre, auteur, isbn FROM livre WHERE id = ?")) {

            stmt.setInt(1, id); // Remplacement du ?
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                String titre = res.getString("titre");
                String auteur = res.getString("auteur");
                String isbn = res.getString("isbn");
                return Optional.ofNullable(new Livre(id, titre, auteur, isbn));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, titre);
            stmt.setString(2, auteur);
            stmt.setString(3, isbn);
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
    public void update(Livre livre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?")) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getIsbn());
            stmt.setInt(4, livre.getPrimaryKey());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public void delete(Livre livre) throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("DELETE FROM livre WHERE id = ?")) {
            stmt.setInt(1, livre.getPrimaryKey());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    @Override
    public int count() throws DaoException {
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement stmt = conn
                        .prepareStatement("SELECT COUNT(id) AS count FROM livre")) {
            ResultSet res = stmt.executeQuery();
            res.next();
            return res.getInt("count");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

}
