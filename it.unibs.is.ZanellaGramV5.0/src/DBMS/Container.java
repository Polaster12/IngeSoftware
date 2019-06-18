package DBMS;

import java.io.Serializable;
import java.util.Vector;

import Account.Account;
import Eventi.Evento;
import Eventi.PartitaDiCalcio;
import Eventi.TorneoDiPoker;

public class Container implements Serializable {
	

	public static final String[] categorie = {PartitaDiCalcio.nome,TorneoDiPoker.nome};

	private Vector<Account> accounts;
	private Vector<PartitaDiCalcio> partite;
	private Vector<TorneoDiPoker> tornei;
	
	public Container (Vector<Account> a, Vector<PartitaDiCalcio> p, Vector<TorneoDiPoker> t) {
		accounts = a;
		partite = p;
		tornei = t;
	}

	public Vector<Account> getAccounts() {
		return accounts;
	}

	public Vector<PartitaDiCalcio> getPartite() {
		return partite;
	}
	
	public Vector<TorneoDiPoker> getTornei() {
		return tornei;
	}

	public void setPartite(Vector<PartitaDiCalcio> partite) {
		this.partite = partite;
	}
	
	public void setTornei(Vector<TorneoDiPoker> feste) {
		this.tornei = feste;
	}
	
	public int sizeAccount() {
		return accounts.size();
	}
	
	public void addAccount(Account a) {
		accounts.add(a);
	}
	
	public int sizeCategorie() {
		return categorie.length;
	}
	
	public Account cercaAccount(String nome) {
		for(int i=0; i<sizeAccount(); i++) {
			if(nome.equals(accounts.get(i).getNomignolo())) {
				return accounts.get(i);
			}
		}
		return null;
	}

	public Vector<PartitaDiCalcio> partiteAperte(){
		Vector<PartitaDiCalcio> partiteAperte = new Vector<>();
			
		for(PartitaDiCalcio p:partite) {
			if (p.isAperto())
				partiteAperte.add(p);
		}
		
		return partiteAperte;
	}

	public Vector<TorneoDiPoker> torneiDiPokerAperti() {
		Vector<TorneoDiPoker> aperti = new Vector<>();
		
		for(TorneoDiPoker f:tornei) {
			if (f.isAperto())
				aperti.add(f);
		}
		return aperti;
	}

	public Vector<Account> listaInvitabili(Evento e) {
		Vector<Account> invitabili = new Vector<>();
		for(Account a:accounts) {
			for(int i=0; i<a.sizeEventi(); i++) {
				if(a.getEvento(i).getNome().equals(e.getNome()) && e.getPropositore().isPropositore(a.getEvento(i)) &&
						!a.equals(e.getPropositore())) {
					invitabili.add(a);
				}
			}
		}
		return invitabili;
	}
	
	public Vector<String> altreCategorie(Account mioAccount){
		Vector<String> altreCategorie = new Vector<>();
		
		for(String s:categorie) {
			if (!mioAccount.getCategorieInteresse().contains(s))
				altreCategorie.add(s);
		}
		
		return altreCategorie;
	}
}
