package Util;

import Main.Dati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuCategorie {
	
	private static final String titolo2 = "Categorie disponibili";
	private static final String[] voci2 = {"Partita Di Calcio","Torneo di Poker"};
	private static MyMenu menu2 = new MyMenu(titolo2,voci2,true);
	
	public static void menuCategorie(Dati dati) {
		int i;
		do{
			i = menu2.scegli();
			switch(i) {
				case 1:UtilMenuPartitaDiCalcio.menuPartitaDiCalcio(dati);
					break;
				case 2:UtilMenuTorneoPoker.menuPoker(dati);
					break;
			}
		}while(i!=0);
		
	}

}
