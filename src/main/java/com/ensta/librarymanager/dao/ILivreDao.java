package com.ensta.librarymanager.dao;

import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Livre;

public interface ILivreDao {
	public List<Livre> getList() throws DaoException;

	public Optional<Livre> getById(int id) throws DaoException;

	public int create(String titre, String auteur, String isbn) throws DaoException;

	public void update(Livre livre) throws DaoException;

	public void delete(Livre livre) throws DaoException;

	public int count() throws DaoException;
}
