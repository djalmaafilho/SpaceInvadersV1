package util;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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

	public synchronized static List<Image> carregarComerciais()
			throws MalformedURLException, IOException {
		String urlSite = "http://www.qualiti.com";
		String str = consultarUrl(urlSite);

		str = str.substring(str.indexOf("<div id=\"slideShow\">"));
		String[] tags = str.toString().split("<div class=\"sld\">");

		List<Image> listaFiguras = new ArrayList<Image>();
		for (String string : tags) {
			if (string.contains("<img")) {
				string = string.substring(string.indexOf("<img"),
						string.indexOf("/>"));
				string = string.substring(string.indexOf("src="),
						string.indexOf("border"));
				string = string.replace("..", urlSite).replace("src=\"", "")
						.replace("\"", "");
				listaFiguras.add(recuperarImagemUrl(string));
			}
		}

		return listaFiguras;
	}

	private static Image recuperarImagemUrl(String urlSite)
			throws MalformedURLException, IOException {
		URL url;
		url = new URL(urlSite);
		URLConnection yc = url.openConnection();
		InputStream is = new BufferedInputStream(yc.getInputStream());
	    Image image = ImageIO.read(is);

		return image;
	}

	private static String consultarUrl(String urlSite)
			throws MalformedURLException, IOException {
		URL url;
		url = new URL(urlSite);
		URLConnection yc = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;
		StringBuilder strB = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			strB.append(inputLine);
		in.close();
		return strB.toString();
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