package Controller;

import DBMS.Dati;
import it.unibs.fp.mylib.MyMenu;

public class UtilMenuHome {
	
	private static final String titolo1 = "HOME";
	private static final String[] voci1 = {"Categorie","Profilo"};
	private static MyMenu menu1 = new MyMenu(titolo1,voci1,true);
	
	public static void menuHome(Dati dati) {
		int i;
		do{
			i = menu1.scegli();
			switch(i) {
				case 1:UtilMenuCategorie.menuCategorie(dati);
					break;
				case 2:UtilMenuProfilo.menuProfilo(dati);
					break;
			}      
		}while(i!=0);
	}

}
