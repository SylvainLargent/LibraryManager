package com.ensta.librarymanager.test;

import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.utils.Abonnement;
import java.time.LocalDate;

public class ModeleTest {
	public static void main(String args[]) {
		Livre livre1 = new Livre(0, "Enchanted", "Liam", "LKE");
		Membre membre1 = new Membre(1, "Dupont", "Jean", "Rue du Futur", "mail@free.fr", "0607080901", Abonnement.VIP);
		LocalDate start = LocalDate.now();
		LocalDate end = LocalDate.of(2022, 11, 22);
		Emprunt emprunt1 = new Emprunt(2, 1, 0, start, end);
		System.out.println(livre1);
		System.out.println(membre1);
		System.out.println(emprunt1);

	}

}
