/*
Lucas Ribeiro do Nascimento
Ciframento de Cesar em Java

*/

class CifraCesar 
{

    public static boolean isFim(String s) 
    {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String cifra(String s) 
    {
      
        String cifrada = "";
        for (int i = 0; i < s.length(); i++) 
        {
            
            cifrada = cifrada + (char) (s.charAt(i)+ 3);
            
           
        }

        return (cifrada);

    }

    public static void main(String[] args) 
    {
        String entrada[] = new String[1000];
        int numEntrada = 0;
        String cifrada;
        

        do 
        {
            entrada[numEntrada] = MyIO.readLine();
        } 
        while (isFim(entrada[numEntrada++]) == false);
        numEntrada--;

        for (int i = 0; i < numEntrada; ++i) 
        {
            cifrada = cifra(entrada[i]);
         
            
            

            
                MyIO.println(cifrada);

            

           

        }

    }

}
