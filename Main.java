class Main {

	public static void main(String[] args) {

		try {

			final int MIN_LEITORES = 0;
			final int MAX_LEITORES = 100;

			// Preparacao dos Logs
			Log.apagarLogs();
			Log.gravarArquivoLog("log", "leitores;escritores;num_simulacao;tempo_gasto;leitor_escritor;cenario");

			// (Leitura,Escrita)
			for (int leitores = MIN_LEITORES; leitores <= MAX_LEITORES; leitores += 1) {

				int escritores = MAX_LEITORES - leitores;
				Simulacao simula = new Simulacao(leitores, escritores); // A soma dos dois tem que ser 100
				simula.simulaTudo();

			}

			System.out.println("\n\n!!! FIM = OK !!!");

		} catch (Exception e) {
			System.out.println(" !!! Erro na simulacao !!!");
			e.printStackTrace();
			return;
		}

	}

}