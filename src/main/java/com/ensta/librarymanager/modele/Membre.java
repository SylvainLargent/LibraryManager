package com.ensta.librarymanager.modele;

import com.ensta.librarymanager.utils.Abonnement;

public class Membre {
	public Membre(int key_arg, String nom_arg, String prenom_arg,
			String address_arg, String email_arg, String Telephone_arg, Abonnement abo_arg) {
		this.primaryKey = key_arg;
		this.nom = nom_arg;
		this.prenom = prenom_arg;
		this.addresse = address_arg;
		this.email = email_arg;
		this.telephone = Telephone_arg;
		this.abonnement = abo_arg;

	}

	public String toString() {
		String newLine = System.getProperty("line.separator");
		return "Membre : " + newLine +
				"Key : " + this.primaryKey + newLine +
				"Nom : " + this.nom + newLine +
				"Prénom : " + this.prenom + newLine +
				"Adresse : " + this.addresse + newLine +
				"Email : " + this.email + newLine +
				"Telephone : " + this.telephone + newLine +
				"Abonnement : " + this.abonnement + newLine;
	}

	public int getPrimaryKey() {
		return this.primaryKey;
	}

	public String getNom() {
		return this.nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public String getAdresse() {
		return this.addresse;
	}

	public String getEmail() {
		return this.email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public Abonnement getAbonnement() {
		return this.abonnement;
	}

	int primaryKey;
	String nom;
	String prenom;
	String addresse;
	String email;
	String telephone;
	Abonnement abonnement;
}
