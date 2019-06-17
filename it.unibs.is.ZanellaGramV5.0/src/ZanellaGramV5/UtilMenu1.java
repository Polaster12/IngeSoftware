package ZanellaGramV5;

import it.unibs.fp.mylib.MyMenu;

public class UtilMenu1 {
	
	private static final String titolo1 = "HOME";
	private static final String[] voci1 = {"Categorie","Profilo"};
	private static MyMenu menu1 = new MyMenu(titolo1,voci1,true);
	
	public static void menu1() {
		int i;
		do{
			i = menu1.scegli();
			switch(i) {
				case 1:UtilMenu2.menu2();
					break;
				case 2:UtilMenuProfilo.menuProfilo();
					break;
			}      
		}while(i!=0);
	}

}
