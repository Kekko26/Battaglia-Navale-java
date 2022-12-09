package it.almaviva.dta;

import java.util.Scanner;

public class BattagliaNavale {

	//metodo che inizializza le matrici 11x11
	public static void inizializzaTabella(String[][] tabella) {
		int ASCIIcounter = 65; 
		int counter = 1;
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				//se la riga è zero inserisco le lettere della tabella
				if(i==0 && j!=0){
					tabella[i][j] = "|"+(char)ASCIIcounter+ "|";
					ASCIIcounter++;
				}				
				//se la colonna è zero inserisco i numeri
				else if(j==0 && i!=0) {
					if(counter==10) {
						tabella[i][j] = " "+counter;
						counter++;
					}
					else{
						tabella[i][j] = "  " + counter;
						counter++;
					}
				}
				//lascio l elemento 00 vuoto per praticità
				else if(i==0 && j==0) {
					tabella[0][0] = "   ";
				}
				//se non è interessato nell'indicizzazione della tabella l'elemento viene inizializzato come segue
				else {
					tabella[i][j] = "|_|";
				}
			}
		}
	}
	//funzione che, passata la tabella ed il giocatore corrente mostra la tabella
	public static void mostraTabella(String[][] tabella, String giocatoreAttuale) {
		if(giocatoreAttuale.equalsIgnoreCase("Giocatore 1")) {
			System.out.println("\nTABELLA GIOCATORE 1:");
		}
		else {
			System.out.println("\nTABELLA GIOCATORE 2:");
		}
		for(int i=0; i<11; i++) {
			for(int j=0; j<11; j++) {
				System.out.print(tabella[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	//funzione che ritorna l'indice della tabella equivalente alla lettera inserita, se non in lista ritorna -1
	public static int convertiLetteraInNumero(String lettera) {
		int numero = 0;
		switch(lettera.toLowerCase()) {
		case "a":
			numero = 1;
			break;
		case "b":
			numero = 2;
			break;
		case "c":
			numero = 3;
			break;
		case "d":
			numero = 4;
			break;
		case "e":
			numero = 5;
			break;
		case "f":
			numero = 6;
			break;
		case "g":
			numero = 7;
			break;
		case "h":
			numero = 8;
			break;
		case "i":
			numero = 9;
			break;
		case "j":
			numero = 10;
			break;
		default:
			numero = -1;
			break;
		}
		return numero;
	}

	//funzione alla quale va passata la tabella, la direzione in cui contare la disponibilità delle caselle, la grandezza della nave, l'ordinata e l'ascissa da cui partire ed il nome della nave
	public static boolean verificaDisponibilitaEdInserisciNave(String[][]tabella, String direzione, int grandezzaNave, int ordinata, int ascissa, String tipoNave) {
		boolean isFree = true;
		switch(direzione.toLowerCase()) {
		//seguono le 4 direzioni possibili, con verifica se gli indici occupabili dalla nave siano effettivamente esistenti e liberi, nel caso aggiunge la nave e ritorna il booleano con l'esito
		case "destra": 
			if(ascissa+grandezzaNave-1>10 || ascissa+grandezzaNave-1<0) {
				return isFree=false;
			}
			for(int i=0; i<grandezzaNave; i++) {
				if(tabella[ordinata][ascissa+i].equalsIgnoreCase("|_|")) {
				}
				else {
					return isFree=false;
				}
			}
			if(isFree) {
				for(int i=0; i<grandezzaNave; i++) {
					tabella[ordinata][ascissa+i] = "|"+tipoNave.charAt(0)+"|";
				}
			}
			break;

		case "sinistra": 
			if(ascissa-grandezzaNave-1>10 || ascissa+grandezzaNave-1<0) {
				return isFree=false;
			}
			for(int i=0; i<grandezzaNave; i++) {
				if(tabella[ordinata][ascissa-i].equalsIgnoreCase("|_|")) {
				}
				else {
					return isFree=false;
				}
			}
			if(isFree) {
				for(int i=0; i<grandezzaNave; i++) {
					tabella[ordinata][ascissa-i] = "|"+tipoNave.charAt(0)+"|";
				}
			}
			break;

		case "sopra": 
			if(ordinata-grandezzaNave-1>10 || ordinata+grandezzaNave-1<0) {
				return isFree=false;
			}
			for(int i=0; i<grandezzaNave; i++) {
				if(tabella[ordinata-i][ascissa].equalsIgnoreCase("|_|")) {
				}
				else {
					return isFree=false;
				}
			}
			if(isFree) {
				for(int i=0; i<grandezzaNave; i++) {
					tabella[ordinata-i][ascissa] = "|"+tipoNave.charAt(0)+"|";
				}
			}
			break;

		case "sotto": 
			if(ordinata+grandezzaNave-1>10 || ordinata+grandezzaNave-1<0) {
				return isFree=false;
			}
			for(int i=0; i<grandezzaNave; i++) {
				if(tabella[ordinata+i][ascissa].equalsIgnoreCase("|_|")) {
				}
				else {
					return isFree=false;
				}
			}
			if(isFree) {
				for(int i=0; i<grandezzaNave; i++) {
					tabella[ordinata+i][ascissa] = "|"+tipoNave.charAt(0)+"|";
				}
			}
			break;
			default: 
				isFree = false;
		}
		return isFree;
	}

	//funzione a cui, passata tabella, lunghezza e nome della nave procede a richiederne gli indici e verificarli, per poi inserire la nave qualora possibile
	public static void inserisciNave(String giocatore, String[][] tabella, int lunghezzaNave, String tipoNave) {
		String giocatoreAttuale;
		if(giocatore.equalsIgnoreCase("Giocatore 1")) {
			giocatoreAttuale = "Giocatore 1";
		}
		else {
			giocatoreAttuale = "Giocatore 2";
		}
		Scanner input = new Scanner(System.in);
		String ascissa;
		int ordinata;
		int ascissaNumerata;
		boolean inserito = false;
		String direzione;

		while(!inserito) {
			System.out.println(giocatoreAttuale + ", in che lettera vuoi inserire la nave di tipo " + tipoNave +  " ? (occupa " + lunghezzaNave + " caselle)");
			ascissa = input.nextLine();
			System.out.println("In che numero vuoi inserire la nave di tipo  " + tipoNave +  " ?");
			ordinata = input.nextInt();
			input.nextLine();

			//converto la lettera inserita in un indice applicabile alla mia matrice
			ascissaNumerata = convertiLetteraInNumero(ascissa);
			//verifico che la lettera inserita rientri in quelle della tabella, stessa cosa con il numero e infine se il posto è libero
			if(ascissaNumerata != -1 && ordinata>0 && ordinata<11 && tabella[ordinata][ascissaNumerata] == "|_|" ) {
				if(lunghezzaNave==1) {
					tabella[ordinata][ascissaNumerata] = "|L|";
					inserito = true;
				}
				else {
					System.out.println("verso dove vuoi inserire la " + tipoNave +" ?");
					direzione = input.nextLine();
					//	assegno ad inserito il ritorno della funzione verificaDisponibilità che ritorna vero se è riuscito ad inserire la nave
					inserito = verificaDisponibilitaEdInserisciNave(tabella, direzione, lunghezzaNave, ordinata, ascissaNumerata, tipoNave);
				}

			}
			if(!inserito) {
				System.out.println("Inserimento non valido, riprova.");
				continue;
			}
		}
	}
	
//	funzione che, passata la tabella da verificare, colonna e riga del colpo e l'iniziale della nave colpita con la sintassi "|iniziale|" verifica se sia stata solo colpita o affondata
	public static void verificaDintorni(String[][] tabella, int colonnaColpo, int rigaColpo, String inizialeNave){
		boolean affondato = true;
		
//se l'iniziale della nave è |L| è una Lancia quindi direttamente affondata
		if(inizialeNave.equals("|L|")) {
			System.out.println("Colpito e affondato");
			return;
		}
		
//verifica se seguono X sulle righe
//verifico che la massima verifica disponibile (quella della corazzata) rientri nella grandezza delle righe della matrice
		if(rigaColpo+1<11) {
//verifico verso il basso
			for(int i=1; i<=3; i++){
				if(tabella[rigaColpo+i][colonnaColpo].equalsIgnoreCase("|X|") && rigaColpo+i+1<11) {
					continue;
				}
				else if(tabella[rigaColpo+i][colonnaColpo].equals(inizialeNave)) {
					affondato = false;
					System.out.println("Colpito");
					return;
				}
				else {
					break;
				}
			}
		}
		if(rigaColpo-1>0) {
//stessa verifica ma alla sulle righe verso l'alto
			for(int i=1; i<=3; i++){
				if(tabella[rigaColpo-i][colonnaColpo].equalsIgnoreCase("|X|") && rigaColpo-i-1>0) {
					continue;
				}
				else if(tabella[rigaColpo-i][colonnaColpo].equals(inizialeNave)) {
					affondato = false;
					System.out.println("Colpito");
					return;
				}
				else {
					break;
				}
			}
		}
		
//verifica se seguono X sulle righe
//verifico che la massima verifica disponibile (quella della corazzata) rientri nella grandezza delle righe della matrice
		if(colonnaColpo+1<11) {
//verifico alla destra
			for(int i=1; i<=3; i++){
				if(tabella[rigaColpo][colonnaColpo+i].equalsIgnoreCase("|X|") && colonnaColpo+i+1<11) {
					continue;
				}
				else if(tabella[rigaColpo][colonnaColpo+i].equals(inizialeNave)) {
					affondato = false;
					System.out.println("Colpito");
					return;
				}
				else {
					break;
				}
			}
		}
		if(colonnaColpo-1>0) {
//stessa verifica ma alla sulle colonne verso sinistra
			for(int i=1; i<=3; i++){
				if(tabella[rigaColpo][colonnaColpo-i].equalsIgnoreCase("|X|") && colonnaColpo-i-1>0) {
					continue;
				}
				else if(tabella[rigaColpo][colonnaColpo-i].equals(inizialeNave)) {
					affondato = false;
					System.out.println("Colpito");
					return;
				}
				else {
					break;
				}
			}
		}
		
		if(affondato) {
			System.out.println("Colpito e affondato");
		}
	}
	
//funzione che, data in ingresso tabella e coordinate spara il colpo e ne verifica l'esito
	public static int sparaColpo(String[][] tabella, int rigaColpo, int colonnaColpo, int contatoreColpiAttuale) {
		
		switch(tabella[rigaColpo][colonnaColpo]) {
		
		case "|C|":
			tabella[rigaColpo][colonnaColpo] = "|X|";
			verificaDintorni(tabella, colonnaColpo, rigaColpo, "|C|");
			contatoreColpiAttuale++;
			return contatoreColpiAttuale;

		case "|c|":
			tabella[rigaColpo][colonnaColpo] = "|X|";
			verificaDintorni(tabella, colonnaColpo, rigaColpo, "|c|");
			contatoreColpiAttuale++;
			return contatoreColpiAttuale;
			
		case "|L|":
			tabella[rigaColpo][colonnaColpo] = "|X|";
			verificaDintorni(tabella, colonnaColpo, rigaColpo, "|L|");
			contatoreColpiAttuale++;
			return contatoreColpiAttuale;
			
		case "|S|":
			tabella[rigaColpo][colonnaColpo] = "|X|";
			verificaDintorni(tabella, colonnaColpo, rigaColpo, "|S|");
			contatoreColpiAttuale++;
			return contatoreColpiAttuale;	
			
		default:
			System.out.println("Mancato!");
			return contatoreColpiAttuale;
	}	
	}
	
	//funzione che, data in ingresso tabella e coordinate segna sulla tabella passata la X del colpo, O se non colpisce nulla, DA UTILIZZARE SOLO PER LA TABELLA SEGNACOLPI, NON SU QUELLA CON LE NAVI
		public static void sparaColpoTabellaColpi(String[][] tabellaColpi, String[][] tabellaNavi, int rigaColpo, int colonnaColpo) {
			
			switch(tabellaNavi[rigaColpo][colonnaColpo]) {
			
			case "|C|":
				tabellaColpi[rigaColpo][colonnaColpo] = "|X|";
				break;
				
			case "|c|":
				tabellaColpi[rigaColpo][colonnaColpo] = "|X|";
				break;
				
			case "|L|":
				tabellaColpi[rigaColpo][colonnaColpo] = "|X|";
				break;
				
			case "|S|":
				tabellaColpi[rigaColpo][colonnaColpo] = "|X|";
				break;
				
			default:
				tabellaColpi[rigaColpo][colonnaColpo] = "|O|";
				break;
		}	
		}
		
	

	public static void main(String[] args) {
//		dichiaro i parametri per le navi da schierare
		int numeroCorazzate = 1;
		int numeroSottomarini = 3;
		int numeroCorvette = 3;
		int numeroLance = 2;
		
//		booleano test mode
		boolean testMode = false;
		
		
		boolean isGiocatore1 = true;
		boolean isWinner = false;
		Scanner input = new Scanner(System.in);
		
		int rigaColpo;
		int colonnaColpo;
		
		//dichiaro le tabelle dei due giocatori
		String[][] tabellaGiocatore1 = new String[11][11];
		String[][] tabellaGiocatore2 = new String[11][11];
		
		String[][] tabellaColpiGiocatore1 = new String[11][11];
		String[][] tabellaColpiGiocatore2 = new String[11][11];

		//inizializzo le tabelle
		inizializzaTabella(tabellaGiocatore1);
		inizializzaTabella(tabellaGiocatore2);
		
		inizializzaTabella(tabellaColpiGiocatore1);
		inizializzaTabella(tabellaColpiGiocatore2);
		
//		Chiedo se l'utente vuole giocare in modalità testing (potendo osservare la tabella navi avversaria durante il proprio turno)
		System.out.println("Vuoi giocare in modalità TESTING ? (La modalità testing permette di osservare la disposizione delle navi nemiche in fase di attacco).");
		if(input.nextLine().equalsIgnoreCase("si")) {
			testMode = true;
		}
		else {
			testMode = false;
		}
		

		
//		fase preparatoria partita - numero e tipo di navi
		System.out.println("Quante corazzate vuoi schierare ? (occupano 4 caselle)");
		numeroCorazzate = input.nextInt();
		System.out.println("Quanti sottomarini vuoi schierare ? (occupano 3 caselle)");
		numeroSottomarini = input.nextInt();
		System.out.println("Quante corvette vuoi schierare ? (occupano 2 caselle)");
		numeroCorvette = input.nextInt();
		System.out.println("Quante lance vuoi schierare ? (occupano 1 casella)");
		numeroLance = input.nextInt();
		input.nextLine();
		
//		dichiaro il totale dei colpi richiesti per vincere dipendentemente dal numero di navi scelte
		int totaleColpiVittoria = (numeroCorazzate*4) + (numeroSottomarini*3) + (numeroCorvette*2) + (numeroLance*1);
		
		if(totaleColpiVittoria!=0) {
		System.out.println("Regole del gioco: \n-Si vince una volta messi a segno " + totaleColpiVittoria + " colpi\n-Se due navi dello stesso tipo vengono posizionate adiacentemente per affondare la nave bisognerà distruggerle entrambe, la regola non vale per le navi di tipo Lancia\n-La sintassi giusta per sparare i colpi è: Lettera - > invio - > Numero - > invio\n-Le possibili direzioni verso cui schierare le navi che occupano più di una casella sono: sopra, sotto, destra, sinistra");
		}
		
		//fase preparatoria tabella giocatore 1 - Inserire i nomi delle navi con le lettere maiuscole, nel caso di iniziale uguale è possibile utilizzare la minuscola
		for(int i=0; i<numeroCorazzate; i++) {
		mostraTabella(tabellaGiocatore1, "Giocatore 1");
		inserisciNave("Giocatore 1" ,tabellaGiocatore1, 4, "Corazzata");
		}

		for(int i=0; i<numeroSottomarini; i++) {
			mostraTabella(tabellaGiocatore1, "Giocatore 1");
			inserisciNave("Giocatore 1", tabellaGiocatore1, 3, "Sottomarino");
		}

		for(int i=0; i<numeroCorvette; i++) {
			mostraTabella(tabellaGiocatore1, "Giocatore 1");
			inserisciNave("Giocatore 1", tabellaGiocatore1, 2, "corvetta");
		}

		for(int i=0; i<numeroLance; i++) {
			mostraTabella(tabellaGiocatore1, "Giocatore 1");
			inserisciNave("Giocatore 1", tabellaGiocatore1, 1, "Lancia");
		}
		
//		fase preparatoria giocatore 2
		for(int i=0; i<numeroCorazzate; i++) {
		mostraTabella(tabellaGiocatore2, "Giocatore 2");
		inserisciNave("Giocatore 2" ,tabellaGiocatore2, 4, "Corazzata");
		}

		for(int i=0; i<numeroSottomarini; i++) {
			mostraTabella(tabellaGiocatore2, "Giocatore 2");
			inserisciNave("Giocatore 2", tabellaGiocatore2, 3, "Sottomarino");
		}

		for(int i=0; i<numeroCorvette; i++) {
			mostraTabella(tabellaGiocatore2, "Giocatore 2");
			inserisciNave("Giocatore 2", tabellaGiocatore2, 2, "corvetta");
		}

		for(int i=0; i<numeroLance; i++) {
			mostraTabella(tabellaGiocatore2, "Giocatore 2");
			inserisciNave("Giocatore 2", tabellaGiocatore2, 1, "Lancia");
		}
	
		
//		IL GIOCO INIZIA
		boolean isInvalid = false;
		isGiocatore1 = true;
		String giocatoreAttuale = "Giocatore 1";
		String avversarioAttuale = "Giocatore 2";
		int contatoreColpiGiocatore1 = 0;
		int contatoreColpiGiocatore2 = 0;
		int contatoreColpiAttuale = 0;
		String [][] tabellaAvversario = tabellaGiocatore2;
		String [][] tabellaColpiGiocatoreAttuale = tabellaColpiGiocatore1;
		
		while(!isWinner && !isInvalid) {
			if(totaleColpiVittoria==0) {
				isInvalid = true;
				break;
			}
//			Switch giocatoreAttuale
			if(isGiocatore1) {
				giocatoreAttuale = "Giocatore 1";
				avversarioAttuale = "Giocatore 2";
				tabellaAvversario = tabellaGiocatore2;
				tabellaColpiGiocatoreAttuale = tabellaColpiGiocatore1;
				contatoreColpiGiocatore1 = contatoreColpiAttuale;
				contatoreColpiAttuale = contatoreColpiGiocatore2;
			}
			else {
				giocatoreAttuale = "Giocatore 2";
				avversarioAttuale = "Giocatore 1";
				tabellaAvversario = tabellaGiocatore1;
				tabellaColpiGiocatoreAttuale = tabellaColpiGiocatore2;
				contatoreColpiGiocatore2 = contatoreColpiAttuale;
				contatoreColpiAttuale = contatoreColpiGiocatore1;
			}
			
//			chiedo al giocatore di colpire e verifico che le coordinate siano utilizzabili
			if(testMode) {
				mostraTabella(tabellaAvversario, avversarioAttuale);
			}
			else {
				mostraTabella(tabellaColpiGiocatoreAttuale, giocatoreAttuale);
			}
			do {
			System.out.println(giocatoreAttuale + ", inserisci la lettera in cui colpire: ");
			colonnaColpo = convertiLetteraInNumero(input.nextLine());
			
			System.out.println("Inserisci il numero in cui colpire");
			rigaColpo = input.nextInt();
			input.nextLine();
			
			if(colonnaColpo<1 || colonnaColpo>10 || rigaColpo<1 || rigaColpo>10) {
				System.out.println("Colpo fuori portata, riprova");
			}
			}while(colonnaColpo<1 || colonnaColpo>10 || rigaColpo<1 || rigaColpo>10);
//			sparaColpo ritorna il numero di colpi messi a segno dal giocatore corrente
			sparaColpoTabellaColpi(tabellaColpiGiocatoreAttuale, tabellaAvversario, rigaColpo, colonnaColpo);
			contatoreColpiAttuale = sparaColpo(tabellaAvversario, rigaColpo, colonnaColpo, contatoreColpiAttuale);
			
			if(contatoreColpiAttuale==totaleColpiVittoria) {
				isWinner=true;
				break;
			}
			
//			Switch per il prossimo turno
			isGiocatore1 = !isGiocatore1;
		}
		if(isWinner) {
		mostraTabella(tabellaAvversario, avversarioAttuale);
		System.out.println(giocatoreAttuale + " ha abbattuto tutte le navi nemiche, complimenti!");
		}
		if(isInvalid) {
			System.out.println("Numero di navi insufficiente, la partita è annullata.");
		}
	}

}

//parametrizzare i valori nel for di schieramento navi, creare una modalità testing dove mi mostra la tabella avversaria in fase di sparo