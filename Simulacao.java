public class Simulacao {    
    
	private int threadsLeitor;//Valores que comeca a simulacao para leitor
	private int threadsEscritor;//Valores que comeca a simulacao para escritor
	private ExecucaoThreads teste = new ExecucaoThreads();
	private String log = "";
	static int cenario = 1;
	


	public Simulacao(int threadsLeitor, int threadsEscritor){
		
		this.threadsEscritor = threadsEscritor;
		this.threadsLeitor = threadsLeitor;	
		teste.carregandoArray();

		System.out.println("\n\n******** SIMULACAO CRIADA ******* LEITORES = " + threadsLeitor + " - ESCRITORES = " + threadsEscritor);					

	}
	

	public void simulaTudo() throws InterruptedException{

		for(int simulacao = 0; simulacao < 50; simulacao++){	//Valor 10 temporario, para agilizar desenvolvimento (Correto = 50)
			
			System.out.print((simulacao+1) + ", ");

			//Faz os testes com o algoritmo Leitores/Escritores										
			teste.execucaoThreadsLeituraEscrita(threadsLeitor,threadsEscritor, true);
			long tempoClock = teste.getTempoGasto();
			log +=  "\n" + threadsLeitor + ";" + threadsEscritor + ";" + (simulacao+1) + ";" + tempoClock + ";true;" + cenario;
			

			//	Faz os testes sem o algoritmo Leitores/Escritores			
			teste.execucaoThreadsLeituraEscrita(threadsLeitor,threadsEscritor, false);    			
			tempoClock = teste.getTempoGasto();			
			log +=  "\n" + threadsLeitor + ";" + threadsEscritor + ";" + (simulacao+1) + ";" + tempoClock + ";false;" + cenario;

		}

		Log.gravarArquivoLog("log", log);
		log = "";
		cenario++;

	}
	
 
}