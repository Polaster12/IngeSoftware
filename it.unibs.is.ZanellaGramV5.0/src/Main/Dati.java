package Main;

import Account.Account;
import it.unibs.fp.mylib.Data;

public class Dati {

	private static Account mioAccount; 

	private static Container container;
	
	private static Data dataOdierna;
	
	public Dati() {
		
	}
	
	public static void setDataOdierna(Data a) {
		dataOdierna = a;
	}
	
	public static Data getDataOdierna() {
		return dataOdierna;
	}

	public static Account getMioAccount() {
		return mioAccount;
	}

	public static void setMioAccount(Account a) {
		mioAccount = a;
	}
	
	public static Container getContainer() {
		return container;
	}
	
	public static void setContainer(Container c) {
		container=c;
	}
}
