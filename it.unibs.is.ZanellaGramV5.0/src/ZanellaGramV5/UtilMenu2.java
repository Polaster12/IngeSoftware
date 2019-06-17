package ZanellaGramV5;

import it.unibs.fp.mylib.MyMenu;

public class UtilMenu2 {
	
	private static final String titolo2 = "Categorie disponibili";
	private static final String[] voci2 = {"Partita Di Calcio","Torneo di Poker"};
	private static MyMenu menu2 = new MyMenu(titolo2,voci2,true);
	
	public static void menu2() {
		int i;
		do{
			i = menu2.scegli();
			switch(i) {
				case 1:UtilMenuPDC.menuPartitaDiCalcio();
					break;
				case 2:UtilMenuPKR.menuPoker();
					break;
			}
		}while(i!=0);
		
	}

}
