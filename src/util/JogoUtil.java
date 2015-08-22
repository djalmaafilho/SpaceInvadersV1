package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class JogoUtil {

	
	
	public static String recuperarTextoArquivo(String arquivoUrl) throws Exception {
		File file = new File(arquivoUrl);

		if (file.exists() == false) {
			throw new Exception("Arquivo inexistente!!!!");
		}

		FileInputStream fis = new FileInputStream(file);
		int caractere = fis.read();
		StringBuilder str = new StringBuilder();

		while (caractere != -1) {
			str.append((char) caractere);
			caractere = fis.read();
		}
		return str.toString();
	}
		
	public static synchronized void salvarLog(Exception e){
		
		String strCaminhoLog = System.getProperty("user.dir")+ File.separator+"log.txt";
		File file = new File(strCaminhoLog);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file, true);
			fos.write('\n');
			fos.write('\r');
			fos.write("Exceção -------------------- data: ".getBytes());
			fos.write(DateFormat.getInstance().format(new GregorianCalendar(Locale.getDefault()).getTime()).toString().getBytes());
			fos.write('\n');
			fos.write('\r');
			e.printStackTrace(new PrintStream(fos));
			fos.write("Exceção -------------------------------------".getBytes());
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	}
}