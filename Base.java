import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class Base{

	//Classe com objetos estaticos para servir como referencia unica na memoria 
	//de toda a base de dados e variaveis de controle

	private boolean comLeitorEscritor;
	private int numLeitores = 0;
	private int numLeitoresEsperando = 0;
	private int numEscritores = 0;
	private int numEscritoresEsperando = 0;
	private Map<Thread, Integer> threadsLeitores = new HashMap<>();
	private Thread threadEscritorAtual = null;


	//Colocando como static o array pertence a classe 
	private List<String> arrayPalavras = new ArrayList<>();


	//	Construtor
	public Base(List<String> arrayPalavras){
		this.arrayPalavras = arrayPalavras;
	}



	//////////////////////////////////////////////
	//    POLITICA  =  SEM LEITOR/ESCRITOR	    //
	//////////////////////////////////////////////

	//	Gerencia o acesso concorrente a base sem o algoritmo de Leitores/Escritores
	public void run_semEscritor() throws InterruptedException{   
		
		run_comEscritor();	
		
	}

	

	public void run_semLeitor() throws InterruptedException{
		
		lockRead_All();
						
		ler();

		Thread.sleep(1);	
		unlockRead();		
		
	}





	//////////////////////////////////////////////
	//    POLITICA  =  COM LEITOR/ESCRITOR	    //
	//////////////////////////////////////////////

	public void run_comEscritor() throws InterruptedException{   
		
		lockWrite();
		
		escrever();

		Thread.sleep(1);	
		unlockWrite();

	}


	public void run_comLeitor() throws InterruptedException{   

		lockRead();
		
		ler();
		
		Thread.sleep(1);	
		unlockRead();

	}



	//////////////////////////////////////////////
	//    		METODOS DE LEITURA/ESCRITA	    	//
	//////////////////////////////////////////////

	private void ler(){

		for(int acesso = 0; acesso < 100; acesso++){
		
			/*Se o objeto for um leitor, ele deve tao somente ler a palavra na posicao desejada, 
			armazenando-a em alguma variavel local (e nao mais usando aquele valor).*/
			String palavra = stringAleatoria(arrayPalavras);

		}

	}


	//Metodo para sortear String do arrayPalavras
  public String stringAleatoria(List<String> lista){
		Random random = new Random();
		int nPosicaoGerado = random.nextInt(lista.size());
		return lista.get(nPosicaoGerado);
  }



	private void escrever() {

		Random random = new Random();
    
		for(int acesso = 0; acesso < 100; acesso++){

			/*Se, contudo, o objeto for um escritor, ele deve escrever: MODIFICADO na posicao correspondente na base.*/
			int nPosicaoGerado = random.nextInt(arrayPalavras.size());
			arrayPalavras.set(nPosicaoGerado, "MODIFICADO");
			
		}
		
  }
	




	public void habilitaLeitorEscritor(boolean value){
		comLeitorEscritor = value;
	}

	public boolean temLeitorEscritor(){
		return comLeitorEscritor;
	}  


	//////////////////////////////////////////////
	//              GETs e SETs                 //
	//////////////////////////////////////////////

	public List<String> getBase(){
		return arrayPalavras;
	}



	//////////////////////////////////////////////
	//             BLOQUEIO BASE                //
	//////////////////////////////////////////////

	public synchronized void lockRead() throws InterruptedException{

		
		Thread threadAtual = Thread.currentThread();
		numLeitoresEsperando++;		
    
		while(!canGrantReadAccess(threadAtual)){
			wait();
		}

		numLeitoresEsperando--;
		threadsLeitores.put(threadAtual, (getReadAccessCount(threadAtual) + 1));
    	numLeitores++;

	}



	public synchronized void lockRead_All() throws InterruptedException{

		Thread threadAtual = Thread.currentThread();

		numLeitoresEsperando++;

		while(!canGrantRead_AllAccess(threadAtual)){
      		wait();
    	}

		numLeitoresEsperando--;
		threadsLeitores.put(threadAtual, (getReadAccessCount(threadAtual) + 1));
    	numLeitores++;

  }




  public synchronized void unlockRead(){    

		Thread threadAtual = Thread.currentThread();
    
		int accessCount = getReadAccessCount(threadAtual);
    
		if(accessCount == 1){ 
			threadsLeitores.remove(threadAtual); 
		}
		else { 
			threadsLeitores.put(threadAtual, (accessCount -1)); 
		}
		
		numLeitores--;
    	notifyAll();

  }



  public synchronized void lockWrite() throws InterruptedException{

	Thread threadAtual = Thread.currentThread();

	numEscritoresEsperando++;

	while(!canGrantWriteAccess(threadAtual)){
		wait();
	}

	numEscritoresEsperando--;
	numEscritores++;
	threadEscritorAtual = threadAtual;
		
  }



  public synchronized void unlockWrite() throws InterruptedException{

    	numEscritores--;

		if (numEscritores == 0){
		    threadEscritorAtual = null;
    	}

    	notifyAll();

  }


	private boolean canGrantReadAccess(Thread callingThread){
		if(hasWriter()) return false;
		return true;
	}

 private boolean canGrantWriteAccess(Thread callingThread){
    if(hasReaders())                   return false;
    if(threadEscritorAtual == null)    return true;
    if(!isWriter(callingThread))       return false;
		if(numLeitoresEsperando > 0)	   return false;
    return true;
  }


	private boolean canGrantRead_AllAccess(Thread callingThread){

		if(hasWriter()) 	return false;
		if(hasReaders())	return false;
		return true;

	}


  private int getReadAccessCount(Thread callingThread){
    Integer accessCount = threadsLeitores.get(callingThread);
    if(accessCount == null) return 0;
    return accessCount.intValue();
  }


  private boolean hasReaders(){
    return threadsLeitores.size() > 0;
  }


  private boolean hasWriter(){
    return threadEscritorAtual != null;
  }

  private boolean isWriter(Thread callingThread){
    return threadEscritorAtual == callingThread;
  }


}
