import java.io.IOException;
import java.lang.IllegalStateException;
import java.util.*;
import java.io.File;


public class LeituraArquivo {

  //Objeto Scanner para ler o arquivo bd.txt 	
  private static Scanner entrada;  



  //Método para abrir o arquivo arquivo.txt
  public static List<String> dadosProntos() {

    List<String> palavrasProntas = new ArrayList<>(); 
    File arquivo = new File("bd.txt");      

      try {

				entrada = new Scanner(arquivo);
        palavrasProntas = lerDados();
				fecharArquivo();

				}
				
      catch (IOException erroES) {
        System.err.println("Erro ao abrir o arquivo. Finalizando.");
        System.exit(1);//terminar o programa
      }

      return palavrasProntas;

  }

  
  //Metodo para ler os registros do arquivo
  public static List<String> lerDados() {

    List<String> palavras = new ArrayList<>();
  
      try {

       while (entrada.hasNext()) {//enquanto houver dados para ler, mostrar os registros
      
       String comando = entrada.nextLine();
       palavras.add(comando);

      }

    }
    catch (NoSuchElementException erroElemento) {
      System.err.println("Arquivo com problemas. Finalizando.");
      entrada.nextLine();//Descartar a entrada para que o usuário possa tentar de novo
    }
    catch (IllegalStateException erroEstado) {
      System.err.println("Erro ao ler o arquivo. Finalizando.");
    }


    return palavras;

  }//fim do método lerDados




  //Metodo para fechar o arquivo aberto
  public static void fecharArquivo() {
    if (entrada != null)
      entrada.close();
  }


}