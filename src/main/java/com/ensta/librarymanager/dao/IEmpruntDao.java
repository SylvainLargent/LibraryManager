package com.ensta.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

public interface IEmpruntDao {
	public List<Emprunt> getList() throws DaoException;

	public List<Emprunt> getListCurrent() throws DaoException;

	public List<Emprunt> getListCurrentByMembre(Emprunt emprunt) throws DaoException;

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException;

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException;

	public Optional<Emprunt> getById(Emprunt emprunt) throws DaoException;

	public Optional<Emprunt> getById(int id) throws DaoException;

	public int create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException;

	public void update(Emprunt emprunt) throws DaoException;

	public int count() throws DaoException;
}
