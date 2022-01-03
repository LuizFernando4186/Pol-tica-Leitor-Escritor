public class MedidorTempo{

	private long inicio;
	
	public void comecaCpuTime() {
		//Marca o inicio do tempo.
		inicio = System.currentTimeMillis();
	}
	
	public long terminaCpuTime() {
		//Pega o instante que terminou menos oque comecou
		return (System.currentTimeMillis() - inicio);
	}
}