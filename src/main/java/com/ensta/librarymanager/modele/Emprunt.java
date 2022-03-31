package com.ensta.librarymanager.modele;

import java.time.LocalDate;

public class Emprunt {

	public Emprunt(int key, int membre, int livre, LocalDate t_start, LocalDate t_final) {
		this.primaryKey = key;
		this.idMembre = membre;
		this.idLivre = livre;
		this.dateEmprunt = t_start;
		this.dateRetour = t_final;
	}

	public String toString() {
		String newLine = System.getProperty("line.separator");
		return "Emprunt : " + newLine +
				"Key : " + this.primaryKey + newLine +
				"Id du Membre : " + this.idMembre + newLine +
				"Id du Livre : " + this.idLivre + newLine +
				"Date de l'emprunt : " + this.dateEmprunt + newLine +
				"Date de retour : " + this.dateRetour + newLine;
	}

	public int getPrimaryKey() {
		return this.primaryKey;
	}

	public int getIdMembre() {
		return this.idMembre;
	}

	public int getIdLivre() {
		return this.idLivre;
	}

	public LocalDate getDateEmprunt() {
		return this.dateEmprunt;
	}

	public LocalDate getDateRetour() {
		return this.dateRetour;
	}

	public void setDateRetour(LocalDate dateRetour_arg) {
		this.dateRetour = dateRetour_arg;
	}

	int primaryKey;
	int idMembre;
	int idLivre;
	String titreLivre;
	String auteurLivre;
	LocalDate dateEmprunt;
	LocalDate dateRetour;
}
