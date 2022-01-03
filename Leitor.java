import java.util.*;

public class Leitor implements Runnable{
  
	private Base baseDados;


	public Leitor(Base baseDados){
		this.baseDados = baseDados;
	}
  

  @Override
  //Este método(run()) é chamado quando a thread é iniciada com o .start
  public void run() {
  
  	try {

			//Log.logConsole += "Executando Leitor... " + this + "\n";
			//System.out.println("Executando Leitor... " + this);

			if (baseDados.temLeitorEscritor()){ 
				baseDados.run_comLeitor();
			}
			else{ 
				baseDados.run_semLeitor();			
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

  }
	


	public String toString(){
		Thread t = Thread.currentThread();
		return t.getName() + " - " + t.getState();
	}  

}
