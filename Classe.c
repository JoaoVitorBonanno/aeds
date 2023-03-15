#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Personagem_s {
  char *nome;
  char *altura;
  char *alturaString;
  char *peso;
  char *pesoString;
  char *corDoCabelo;
  char *corDaPele;
  char *corDosOlhos;
  char *anoNascimento;
  char *genero;
  char *homeworld;
};

typedef struct Personagem_s Personagem_t;

void strtrm(char *str) {
  if (str[0] == ' ') {
    for (int i = 1; i <= strlen(str); i++) {
      str[i - 1] = str[i];
    }
  }
  if (str[strlen(str) - 1] == ' ') {
    str[strlen(str) - 1] = str[strlen(str)];
  }
}

void fromJsonLine(char *jsonString, Personagem_t *personagemRetorno) {
  for (int i = 1; i <= strlen(jsonString); i++) {
    jsonString[i - 1] = jsonString[i];
  }

  jsonString[strlen(jsonString) - 1] = jsonString[strlen(jsonString)];

  char **splittedColumns = calloc(9, sizeof(char *));
  for (int i = 0; i < 9; i++) {
    splittedColumns[i] = calloc(500, sizeof(char));
  }

  char jsonStringToProcess[2000];
  strcpy(jsonStringToProcess, jsonString);
  char columnAux[500];
  int idxColumnAux = 0;
  bool inQuotes = false, inList = false;
  int columnIndex = 0;
  while (strlen(jsonStringToProcess) > 0 && columnIndex < 9) {
		
    if (jsonStringToProcess[0] == ',' && inQuotes == false && inList == false) {
      columnAux[idxColumnAux] = '\0';
      idxColumnAux = 0;
      strcpy(splittedColumns[columnIndex++], columnAux);

    } 
		else if (jsonStringToProcess[0] == '\'') {
      inQuotes = inQuotes == true ? false : true;
    } 
		else if (jsonStringToProcess[0] == '[') {
      inList = true;
    } 
		else if (jsonStringToProcess[0] == ']') {
      inList = false;
    }
    if (jsonStringToProcess[0] != ',' || inQuotes == true || inList == true)
      columnAux[idxColumnAux++] = jsonStringToProcess[0];

    for (int i = 1; i <= strlen(jsonStringToProcess); i++) {
      jsonStringToProcess[i - 1] = jsonStringToProcess[i];
    }
  }
	
  char **vetorNomeColunaValorColuna = calloc(2, sizeof(char *));
  for (int i = 0; i < 2; i++) {
    vetorNomeColunaValorColuna[i] = calloc(500, sizeof(char));
  }
  int idxColunaSplit;
  char token[3] = ":";

  char columnName[150];
  char columnValue[350];

  for (int i = 0; i < columnIndex; i++) {
    idxColunaSplit = 0;
    strcpy(columnAux, splittedColumns[i]);

    strcpy(vetorNomeColunaValorColuna[0], strtok(columnAux, token));
    strcpy(vetorNomeColunaValorColuna[1], strtok(NULL, token));

    if (vetorNomeColunaValorColuna[0] != NULL &&
        vetorNomeColunaValorColuna[1] != NULL) {

      strcpy(columnName, vetorNomeColunaValorColuna[0]);
      strtrm(columnName);

      for (int i = 1; i <= strlen(columnName); i++) {
        columnName[i - 1] = columnName[i];
      }

      columnName[strlen(columnName) - 1] = columnName[strlen(columnName)];

      strcpy(columnValue, vetorNomeColunaValorColuna[1]);
      strtrm(columnValue);

      if (columnValue[0] == '\'') {
        for (int i = 1; i <= strlen(columnValue); i++) {
          columnValue[i - 1] = columnValue[i];
        }

        columnValue[strlen(columnValue) - 1] = columnValue[strlen(columnValue)];
      }

      if (strcmp(columnName, "name") == 0) {
				personagemRetorno->nome = calloc(500, sizeof(char));
        strcpy(personagemRetorno->nome, columnValue);
      } else if (strcmp(columnName, "height") == 0) {
				personagemRetorno->altura = calloc(500, sizeof(char));
				if(strcmp(columnValue, "unknown") == 0){
					strcpy(personagemRetorno->altura, "0");	
				}else{
					strcpy(personagemRetorno->altura, columnValue);	
				}
      } else if (strcmp(columnName, "mass") == 0) {
				personagemRetorno->peso = calloc(500, sizeof(char));
				if(strcmp(columnValue, "unknown") == 0){
					strcpy(personagemRetorno->peso, "0");	
				}else{
					strcpy(personagemRetorno->peso, columnValue);	
				}
        
      } else if (strcmp(columnName, "hair_color") == 0) {
				personagemRetorno->corDoCabelo = calloc(500, sizeof(char));
        strcpy(personagemRetorno->corDoCabelo, columnValue);
      } else if (strcmp(columnName, "skin_color") == 0) {
				personagemRetorno->corDaPele = calloc(500, sizeof(char));
        strcpy(personagemRetorno->corDaPele, columnValue);
      } else if (strcmp(columnName, "eye_color") == 0) {
				personagemRetorno->corDosOlhos = calloc(500, sizeof(char));
        strcpy(personagemRetorno->corDosOlhos, columnValue);
      } else if (strcmp(columnName, "birth_year") == 0) {
				personagemRetorno->anoNascimento = calloc(500, sizeof(char));
        strcpy(personagemRetorno->anoNascimento, columnValue);
      } else if (strcmp(columnName, "gender") == 0) {
				personagemRetorno->genero = calloc(500, sizeof(char));
        strcpy(personagemRetorno->genero, columnValue);
      } else if (strcmp(columnName, "homeworld") == 0) {
				personagemRetorno->homeworld = calloc(500, sizeof(char));
        strcpy(personagemRetorno->homeworld, columnValue);
      }
    }
  }
}

void printPersonagem(Personagem_t* personagem){
	printf(" ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## \n",personagem->nome,personagem->altura,personagem->peso,personagem->corDoCabelo,personagem->corDaPele,personagem->corDosOlhos,personagem->anoNascimento,personagem->genero,personagem->homeworld
		);
}

int main(void) {
  char *jsonLine = calloc(2000, sizeof(char));
  Personagem_t *personagemRetorno = malloc(sizeof(Personagem_t));

	char input[200];
	scanf("%[^\n]", input);
	
	while(strcmp(input, "FIM") != 0){		
		FILE *arquivo;
		arquivo = fopen(input, "r");
	
		if(arquivo == NULL){
			printf("Não foi possível abrir o arquivo %s\n", input);
		}else{
			fseek(arquivo, 0, SEEK_END); 
			long size = ftell(arquivo);
			fseek(arquivo, 0, SEEK_SET); 
			fread(jsonLine, 1, size, arquivo);
			fromJsonLine(jsonLine, personagemRetorno);
			printPersonagem(personagemRetorno);
		}
	
		scanf("\n%[^\n]", input);
	}
  return 0;
}
