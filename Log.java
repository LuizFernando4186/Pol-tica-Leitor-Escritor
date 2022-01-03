import java.io.*;

public class Log{

 	//////////////////////////////////
	//		   	GRAVAÇÃO	        //
	//////////////////////////////////

	static void gravarArquivoLog(String nameFile, String log){

		String arquivoLog = nameFile + ".txt";

		try{
			
			RandomAccessFile arq = new RandomAccessFile(arquivoLog, "rw");

			Writer csv = new BufferedWriter(new FileWriter(arquivoLog, true));
			csv.append(log);
			csv.close();

		}
		catch (Exception e){
			System.out.println("!!! ERRO NA GRAVACAO DO LOG !!!");
			e.printStackTrace();
		}

	}


	static void apagarLogs(){

		File file = new File("log.txt");
		if (file.exists()) 
			file.delete();
			
	}


}