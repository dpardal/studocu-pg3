package parte1;

import java.io.*;


// 1.
public class StreamUtils {

    // a)   Copia do stream in para o stream out substituindo cada sequência de caracteres dígito (valor
    //      numérico) por uma nova sequência contendo o separador ponto entre cada 3 dígitos a contar da direita.
    //      O método retorna o número de caracteres ponto acrescentados.
    public static int copyWithReplace(Reader in, Writer pw) throws IOException {
        StringBuilder formatter = new StringBuilder();  // trata da formatação da substring
        int c, numberOfDots = 0;
        boolean wasWrittingDigit = false;
        while( ( c = in.read() ) != -1){
            if( Character.isDigit( c ) ){
                formatter.append( c );
                wasWrittingDigit = true;
            }
            else{
                if( wasWrittingDigit ) {
                    for (int i = formatter.length() - 1; i > 0; --i) {
                        if ( (formatter.length() - i) % 3 == 0 ){
                            formatter.insert(i, '.');
                            ++numberOfDots;
                        }
                    }
                    // Antes desta implementação estava a criar um Buffered Writer para escrita eficiente
                    // e fechava-o após a escrita. Mas um qualquer BufferedWriter "embrulha" outra stream,
                    // (neste caso o pw que é passado em parâmetro) i.e. corro o risco de escrever 1 SÓ vez.
                    pw.write( formatter.toString() );
                    wasWrittingDigit = false;
                }
                pw.append( (char) c );
            }
            pw.flush();
        }
        return numberOfDots;
    }

    // b)   Este método é uma variante do método copyWithReplace da alínea a) que recebe agora o nome do
    //      ficheiro que contém o texto a copiar, com substituição das sequências de dígitos, para a stream pw.
    //      Nota: na realização deste método, utilize o método copyWithReplace da alínea a).
    public static int copyWithReplace( String filenameIn, Writer pw ) throws IOException {
        try( Reader in = new FileReader( filenameIn ) ) {
            return copyWithReplace( in, pw );
        }
    }

    // c)   Este método recebe por parâmetro o nome de um ficheiro de texto, e usa o método da alínea b) para
    //      retornar uma String com o texto resultante da substituição das sequências de dígitos.
    //      Nota: na realização deste método considere a utilização da classe StringWriter.
    public static String contentWithReplace( String filenameIn ) throws IOException {
        try( Writer pw = new StringWriter() ) {
            copyWithReplace(filenameIn, pw);
            return pw.toString();
        }
    }

    // d)   Recebe por parâmetro o ficheiro de nome filenameIn, e usa o método da alínea b) de forma a que a
    //      cópia com substituição seja apresentada no standard output.
    public static void printWithReplace( String filenameIn ) throws IOException {
        // não meter o System.out num try-with-resources para não ficar sem a stream System.out
        PrintWriter out = new PrintWriter( System.out );
        out.print( contentWithReplace( filenameIn ) );
        // como não fecho o System.out não é feito flush(), nesse caso faço manualmente.
        out.flush();
    }

    // e)   Recebendo por parâmetro o nome do ficheiro de texto com nome fileIn, usa o método da alínea b)
    //      para copiar o texto resultante da substituição das sequências de dígitos para o ficheiro de texto com
    //      nome fileOut.
    public static void copyWithReplace(String fileIn, String fileOut) throws IOException{
        try( Writer pw = new FileWriter( fileOut ) ){
            copyWithReplace( fileIn, pw );
        }
    }
}

