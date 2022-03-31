package com.ensta.librarymanager.dao;

import java.util.List;
import java.util.Optional;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Membre;

public interface IMembreDao {
	public List<Membre> getList() throws DaoException;

	public Optional<Membre> getById(int id) throws DaoException;

	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException;

	public void update(Membre membre) throws DaoException;

	public void delete(Membre membre) throws DaoException;

	public int count() throws DaoException;
}
