import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ExecucaoThreads {

	private MedidorTempo medidor = new MedidorTempo(); // Medir o tempo em ms
	private List<Thread> threads;
	private Base baseDados;
	private long tempoGasto;


	// Metodo para carregar o array de palavras
	public void carregandoArray(){
		baseDados = new Base(LeituraArquivo.dadosProntos());		
	}


	// Metodo para executar as threads do objeto escritor e leitor
	public void execucaoThreadsLeituraEscrita(int threadsLeitor, int threadsEscritor, boolean leitorEscritor) throws InterruptedException {

		baseDados.habilitaLeitorEscritor(leitorEscritor);

		inicializaThreads(threadsLeitor, threadsEscritor);
		processaThreads();

	}

	private void inicializaThreads(int threadsLeitor, int threadsEscritor) {

		threads = new ArrayList<>();// Array de threads

		//	Passo 1
		//	Preenchendo o array de objetos Runnable de leitura e escrita de acordo com a combinacao passada
		 
		for (int i = 0; i < threadsLeitor; i++) {
			Thread leitura = new Thread(new Leitor(baseDados));
			threads.add(leitura);
		}

		for (int j = 0; j < threadsEscritor; j++) {
			Thread escrita = new Thread(new Escritor(baseDados));
			threads.add(escrita);
		}

		/*
		 * Vamos embaralhar os itens da ArrayList, rodar cada thread na sequencia (a
		 * aleatoriedade na posicao do objeto dentro do arranjo deve ajudar a dissipar
		 * quaisquer efeito determinısticos nesse experimento.).
		 */
		Collections.shuffle(threads);

	}

	private void processaThreads() throws InterruptedException {

		// O tempo deve ser marcado entre o final do povoamento do arranjo de threads
		// (ou seja, apos todos os objetos terem sido criados mas nao terem iniciado sua
		// execucao)
		// e o termino da ultima thread.

		medidor.comecaCpuTime();

		// Iniciando as threads definidas (leitura e escrita)
		for (Thread thread : threads)
			thread.start();

		for (Thread thread : threads)
			thread.join();

		tempoGasto = medidor.terminaCpuTime();

	}

	public long getTempoGasto() {
		return tempoGasto;
	}

}