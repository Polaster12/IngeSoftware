package ZanellaGramV5;

import java.util.Vector;

import it.unibs.fp.mylib.FasciaDiEta;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuModProfilo {
	
	private static final String titolo5 = "Modifica Profilo: parametri modificabili";
	private static final String[] voci5 = {"Fascia di Età","Aggiungi Categoria","Rimuovi Categoria"};
	private static MyMenu menuModificaProfilo = new MyMenu(titolo5,voci5,true);
	
	public static void menuModificaProfilo() {
		int i;
		do{
			i = menuModificaProfilo.scegli();
			switch(i) {
				case 1: Main.getMioAccount().setFascia(new FasciaDiEta(InputDati.leggiInteroNonNegativo("Età minima --> "),
						InputDati.leggiInteroNonNegativo("Età massima --> ")));
					break;
				case 2: UtilMenu0.menuInteressi();
					break;
				case 3: menuInteressiRemove();
					break;
			}
		}while(i!=0);
		
	}

	private static void menuInteressiRemove() {
		String titolo = "Seleziona la categoria alla quale non sei più interessato/a";
		int i;
		
		do {
			
			Vector<String> categorieInteresse = Main.getMioAccount().getCategorieInteresse();
			
			String[] voci = new String[categorieInteresse.size()];
			
			for(int j=0;j<categorieInteresse.size();j++) {
				voci[j] = categorieInteresse.get(j);
			}
			
			MyMenu menu = new MyMenu(titolo,voci,true);
			i = menu.scegli()-1;
			if (i>=0){
				Main.getMioAccount().removeCategorieInteresse(categorieInteresse.get(i));
				System.out.println("\nCategoria rimossa dai tuoi interessi");
			}
		} while(i>=0);
	}

}
