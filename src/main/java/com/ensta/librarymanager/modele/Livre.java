package com.ensta.librarymanager.modele;

public class Livre {

	public Livre(int key_arg, String titre_arg, String auteur_arg, String isbn_arg) {
		this.primaryKey = key_arg;
		this.titre = titre_arg;
		this.auteur = auteur_arg;
		this.isbn = isbn_arg;
	}

	public String toString() {
		String newLine = System.getProperty("line.separator");
		return "Livre : " + newLine +
				"Key : " + this.primaryKey + newLine +
				"Titre : " + this.titre + newLine +
				"Auteur : " + this.auteur + newLine +
				"ISBN : " + this.isbn + newLine;
	}

	public int getPrimaryKey() {
		return this.primaryKey;
	}

	public String getTitre() {
		return this.titre;
	}

	public String getAuteur() {
		return this.auteur;
	}

	public String getIsbn() {
		return this.isbn;
	}

	int primaryKey;
	String titre;
	String auteur;
	String isbn;
}
