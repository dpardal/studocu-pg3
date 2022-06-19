package parte1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


// 4.
public class Course {

    // tira partido do método forEachIf, realizado na questão 2, para determinar os k estudantes de uma
    // escola que obtiveram, num determinado semestre, a melhor média de notas nas disciplinas realizadas
    // nesse semestre, tendo realizado pelo menos 5 disciplinas, para lhes ser atribuída uma bolsa de estudos
    // para o semestre seguinte.

    // Em caso de empate de notas, será utilizado o número de aluno, dando prioridade aos alunos com
    // menor número. Os alunos estão distribuídos por turmas, estando cada turma descrita num ficheiro
    // de texto em que cada linha contém os dados de um aluno no formato indicado na questão 3.
    // Os estudantes estão organizados em turmas de um dado semestre, sendo cada turma representada
    // por um ficheiro cudo nome (class_name) segue o seguinte formato:
    // <class_name>::= <course_name> <semester> <class_number> <regime> <entension>
    // <course_name> := <word>
    // <regime> ::= ‘D’ | ‘N’
    // <semester> ::= ‘1’..’6’
    // <class number> ::= ‘1’..’9’
    // <extension> ::= ‘.dat’
    // LT51D, LT51N e LEIRT31D são exemplos de nomes de turmas.
    // O parâmetro dirName indica o nome da pasta onde constam os ficheiros das turmas dos vários
    // semestres.
    public static Student[] getKBestStudents( int k, int semester, String dirName) throws IOException {
        ArrayList<Student> bestStudents = new ArrayList<>( k );
        boolean existsFileForTargetSemester = false;
        File[] files = new File( dirName ).listFiles( file -> !file.isDirectory() ); // todos os ficheiros não-pasta
        if( files == null ){ throw new IOException("There are no files in this directory"); }
        else {
            for (File f : files) {
                int digits = new Scanner( f.getName() ).useDelimiter("\\D+").nextInt(); //  \D ==> A non-digit: [^0-9]
                if (digits / 10 == semester) {   // aqui é o sitio certo
                    existsFileForTargetSemester = true;

                    Reader fReader = new FileReader(f);
                    BufferedReader in = new BufferedReader(fReader);
                    Function<String, Student> mapper = Student::fromDescription;
                    Predicate<Student> pred = s -> s.getUnitsNumber() >= 5;
                    Consumer<Student> action = e -> e.compare_and_add_or_set( bestStudents, k);

                    AlgorithmUtils.forEachIf( in, mapper, pred, action );
                }
            }
        }
        if( ! existsFileForTargetSemester ){
            throw new IOException("There are no college class files for this semester");
        }
        return ( Student[] )bestStudents.toArray();
    }




    //5.
    public static void main( String directory, int semester, int scholarshipsNumber ) {

        try {
            Student[] scholarshipsHolders = Course.getKBestStudents( scholarshipsNumber, semester, directory );
            System.out.println( "The scholarship holders for the next semester will be:\n" );
            for( Student s : scholarshipsHolders ){
                System.out.println( s.getName() + ";\n" );
            }
        }
        catch (IOException e){
            System.out.println( e );
        }
    }





}
