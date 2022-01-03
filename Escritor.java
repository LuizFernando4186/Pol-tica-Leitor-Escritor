public class Escritor implements Runnable{

	private Base baseDados;
  
	public Escritor(Base baseDados){
		this.baseDados = baseDados;
	}

   
  @Override
  //Este método(run()) é chamado quando a thread é iniciada
  public void run() {
		
		try{ 

			if (baseDados.temLeitorEscritor()){ 
				baseDados.run_comEscritor();
			}
			else{
				baseDados.run_semEscritor();
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

  }


	public String toString(){
		Thread t = Thread.currentThread();
		return t.getName() + " - " + t.getState();
	}  
 
}