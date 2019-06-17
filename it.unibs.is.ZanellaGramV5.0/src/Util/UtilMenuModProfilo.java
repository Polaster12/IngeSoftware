package Util;

import java.util.Vector;

import Main.Dati;
import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuModProfilo {
	
	private static final String titolo5 = "Modifica Profilo: parametri modificabili";
	private static final String[] voci5 = {"Fascia di Età","Aggiungi Categoria","Rimuovi Categoria"};
	private static MyMenu menuModificaProfilo = new MyMenu(titolo5,voci5,true);
	
	public static void menuModificaProfilo(Dati dati) {
		int i;
		do{
			i = menuModificaProfilo.scegli();
			switch(i) {
				case 1: dati.getMioAccount().setFascia(new FasciaDiEta(InputDati.leggiInteroNonNegativo("Età minima --> "),
						InputDati.leggiInteroNonNegativo("Età massima --> ")));
					break;
				case 2: UtilMenuAccesso.menuInteressi(dati);
					break;
				case 3: menuInteressiRemove(dati);
					break;
			}
		}while(i!=0);
		
	}

	private static void menuInteressiRemove(Dati dati) {
		String titolo = "Seleziona la categoria alla quale non sei più interessato/a";
		int i;
		
		do {
			
			Vector<String> categorieInteresse = dati.getMioAccount().getCategorieInteresse();
			
			String[] voci = new String[categorieInteresse.size()];
			
			for(int j=0;j<categorieInteresse.size();j++) {
				voci[j] = categorieInteresse.get(j);
			}
			
			MyMenu menu = new MyMenu(titolo,voci,true);
			i = menu.scegli()-1;
			if (i>=0){
				dati.getMioAccount().removeCategorieInteresse(categorieInteresse.get(i));
				System.out.println("\nCategoria rimossa dai tuoi interessi");
			}
		} while(i>=0);
	}

}
