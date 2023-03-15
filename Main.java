import java.nio.file.FileSystems;
import java.nio.charset.*;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
// atributos dos personagens
class Personagem {
	private String nome;
	private int altura;
	private String alturaString;
	private double peso;
	private String pesoString;
	private String corDoCabelo;
	private String corDaPele;
	private String corDosOlhos;
	private String anoNascimento;
	private String genero;
	private String homeworld;

	public String getNome(){
		return nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}

	public int getAltura(){
		return altura;
	}

	public void setAltura(int altura){
		this.altura = altura;
	}

	public double getPeso(){
		return peso;
	}

	public void setPeso(double peso){
		this.peso = peso;
	}

	public String getAlturaString(){
		return alturaString;
	}

	public void setAlturaString(String altura){
		this.alturaString = altura;
	}

	public String getPesoString(){
		return pesoString;
	}

	public void setPesoString(String peso){
		this.pesoString = peso;
	}

	public String getCorDoCabelo(){
		return corDoCabelo;
	}

	public void setCorDoCabelo(String corDoCabelo){
		this.corDoCabelo = corDoCabelo;
	}

	public String getCorDaPele(){
		return corDaPele;
	}

	public void setCorDaPele(String corDaPele){
		this.corDaPele = corDaPele;
	}

	public String getCorDosOlhos(){
		return corDosOlhos;
	}

	public void setCorDosOlhos(String corDosOlhos){
		this.corDosOlhos = corDosOlhos;
	}

	public String getAnoNascimento(){
		return anoNascimento;
	}

	public void setAnoNascimento(String anoNascimento){
		this.anoNascimento = anoNascimento;
	}

	public String getGenero(){
		return genero;
	}

	public void setGenero(String genero){
		this.genero = genero;
	}

	public String getHomeworld(){
		return homeworld;
	}

	public void setHomeworld(String homeworld){
		this.homeworld = homeworld;
	}

	public static Personagem fromPersonagemJson(PersonagemJson personagemJson){
		Personagem personagem = new Personagem();
		personagem.setNome(personagemJson.name);
		
		try {
			personagem.setAltura(Integer.valueOf(personagemJson.height));	
		}catch(NumberFormatException ex){
			personagem.setAltura(Integer.valueOf(0));	
		}
		try {
			personagem.setPeso(Double.valueOf(personagemJson.mass));
		}catch(NumberFormatException ex){
			personagem.setPeso(Double.valueOf(0));
		}

		personagem.setAlturaString(personagemJson.height.equals("unknown") ? "0" : personagemJson.height);	
		personagem.setPesoString(personagemJson.mass.equals("unknown") ? "0" : personagemJson.mass);
		
        personagem.setCorDoCabelo(personagemJson.hair_color);
        personagem.setCorDaPele(personagemJson.skin_color);
        personagem.setCorDosOlhos(personagemJson.eye_color);
        personagem.setAnoNascimento(personagemJson.birth_year);
        personagem.setGenero(personagemJson.gender);
        personagem.setHomeworld(personagemJson.homeworld);
		return personagem;
	}

	@Override
	public String toString(){
		String retorno = " ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## ";
		retorno = String.format(retorno, this.nome,this.alturaString,this.pesoString,this.corDoCabelo,this.corDaPele,this.corDosOlhos,this.anoNascimento,this.genero,this.homeworld);
		return retorno;
	}
}

class PersonagemJson {
	public String name;
	public String height;
	public String mass;
	public String hair_color;
	public String skin_color;
	public String eye_color;
	public String birth_year;
	public String gender;
	public String homeworld;

	public PersonagemJson fromJsonLine(String jsonString){
		jsonString = jsonString.substring(1, jsonString.length());
		jsonString = jsonString.substring(0, jsonString.length() - 1);

		String columnName;
		String columnValue;
		
		String[] splittedColumns = new String[9];

		String jsonStringToProcess = jsonString;
		String columnAux = "";
		Boolean inQuotes = false, inList = false;
		int columnIndex = 0;
		while(jsonStringToProcess.length() > 0 && columnIndex < 9){
			if(jsonStringToProcess.charAt(0) == ',' && !inQuotes && !inList){
				splittedColumns[columnIndex++] = columnAux;
				columnAux = "";
			}else if(jsonStringToProcess.charAt(0) == '\''){
				inQuotes = !inQuotes;
			}else if(jsonStringToProcess.charAt(0) == '['){
				inList = true;
			}else if(jsonStringToProcess.charAt(0) == ']'){
				inList = false;
			}
			if(jsonStringToProcess.charAt(0) != ',' || inQuotes || inList) columnAux += jsonStringToProcess.charAt(0);
			jsonStringToProcess = jsonStringToProcess.substring(1, jsonStringToProcess.length());
		}
		
		String[] vetorNomeColunaValorColuna;

		for(int i = 0; i < splittedColumns.length; i++){
			columnAux = splittedColumns[i];
			vetorNomeColunaValorColuna = columnAux.split(":");
			if(vetorNomeColunaValorColuna.length == 2){
				columnName = vetorNomeColunaValorColuna[0];
				columnName = columnName.trim();
				columnName = columnName.substring(1, columnName.length());
				columnName = columnName.substring(0, columnName.length() - 1);
				
				columnValue = vetorNomeColunaValorColuna[1];
				columnValue = columnValue.trim();
				if(columnValue.charAt(0) == '\''){
					columnValue = columnValue.substring(1, columnValue.length());
					columnValue = columnValue.substring(0, columnValue.length() - 1);	
				}

				switch(columnName){
					case "name":
					    this.name = columnValue;
					    break;
					case "height":
					    this.height = columnValue;
					    break;
					case "mass":
					    this.mass = columnValue;
					    break;
					case "hair_color":
					    this.hair_color = columnValue;
					    break;
					case "skin_color":
					    this.skin_color = columnValue;
					    break;
					case "eye_color":
					    this.eye_color = columnValue;
					    break;
					case "birth_year":
					    this.birth_year = columnValue;
					    break;
					case "gender":
					    this.gender = columnValue;
					    break;
					case "homeworld":
					    this.homeworld = columnValue;
					    break;
				}
			}else{
			}
		}
		return this;
	}
}


class Main {
	public static void main(String[] args) {
		MyIO.setCharset("utf-8");
		String input = MyIO.readLine();

		File filePersonagem;
		BufferedReader fileScanner;
		String jsonText;
		String line;
		while(!input.trim().toUpperCase().equals("FIM")){
			try {
				filePersonagem = FileSystems.getDefault().getPath(input).toFile();
				fileScanner = new BufferedReader(new InputStreamReader(new FileInputStream(filePersonagem), Charset.forName("utf-8")));
				jsonText = "";
				while((line = fileScanner.readLine()) != null){
					jsonText += line;
				}
				
				PersonagemJson personagemJson = new PersonagemJson().fromJsonLine(jsonText);
				Personagem personagem = Personagem.fromPersonagemJson(personagemJson);
				MyIO.println(personagem.toString());
				fileScanner.close();
	
			}catch(IOException ex){
				MyIO.println(input + " não existe");
			}finally{
				input = MyIO.readLine();
			}
		}
	}
}