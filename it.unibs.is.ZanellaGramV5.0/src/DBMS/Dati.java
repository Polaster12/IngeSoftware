package DBMS;

import java.util.Vector;

import Account.Account;
import Eventi.Evento;
import Eventi.PartitaDiCalcio;
import Eventi.TorneoDiPoker;
import it.unibs.fp.mylib.Data;

public class Dati {

	private static Account mioAccount; 

	private static Container container;
	
	private static Data dataOdierna;
	
	public Dati() {
		
	}
	
	public void setDataOdierna(Data a) {
		dataOdierna = a;
	}
	
	public Data getDataOdierna() {
		return dataOdierna;
	}

	public Account getMioAccount() {
		return mioAccount;
	}

	public void setMioAccount(Account a) {
		mioAccount = a;
	}
	
	public Container getContainer() {
		return container;
	}
	
	public void setContainer(Container c) {
		container=c;
	}
	
	public Vector<PartitaDiCalcio> partiteAperte() {
		return container.partiteAperte();
	}
	
	public Vector<TorneoDiPoker> torneiDiPokerAperti() {
		return container.torneiDiPokerAperti() ;
	}
	
	public Vector<Account> listaInvitabili(Evento e){
		return container.listaInvitabili(e);
	}
	
	public Vector<String> altreCategorie(){
		return container.altreCategorie(mioAccount);
	}
	
	public Vector<String> categorieInteresse(){
		return mioAccount.getCategorieInteresse();
	}
	
	public Account cercaAccount(String nome) {
		return container.cercaAccount(nome);
	}
}
