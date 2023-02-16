class TP0101
{
    //Método isFIM

public static boolean isFIM(String s)
{

   return (s.length() == 3 && s.charAt(0) == 'F'&& s.charAt(1) == 'I' && s.charAt(2) == 'M');
}
   //Método de definição de palindromo

public static int IsPalindromo(String s)
{

    int resp = 0, i = 0,UltimaLetra = s.length() -1; 
    int valor,valor2;

    do
    {
         valor = (int)s.charAt(i);                    
         valor2 = (int)s.charAt(UltimaLetra);

        if(valor == valor2)
        { 
            resp = 0;
        }
        else
        {
            resp = 1;
        }
        i++;              
        UltimaLetra--;    

    }
    while(UltimaLetra - i >= 1 || resp != 1);


return resp;

}

    public static void main (String[] args){
String entrada[] = new String[1000];
int numEntrada = 0;
int resp;




do
{
    entrada[numEntrada] = MyIO.readLine();
} 
while (isFIM(entrada[numEntrada++]) == false);
numEntrada--;

for(int i=0; i < numEntrada; i++)
{
    resp = IsPalindromo(entrada[i]);
    if(resp == 0){
        MyIO.println("SIM");

    }
    else
        MyIO.println("NAO");


}




    }


}