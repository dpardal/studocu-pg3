package parte1;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
// 3.
public class Student implements Comparable<Student>{
    String name;
    int num;
    int units;
    float averageGrade;

    public Student (String name, int num, int units, float averageGrade ){
        this.name = name;
        this.num = num;
        this.units = units;
        this.averageGrade = averageGrade;
    }

    // cria novo aluno à custa de uma string
    public static Student fromDescription ( String desc ) {
        String[] subString = desc.split(":");
        return new Student(
                subString[0].trim(),
                parseInt( subString[1].trim() ),
                parseInt( subString[2].trim() ),
                parseFloat( subString[3].trim() )
        );
    }

    public String getName() {
        return name;
    }

    public int getStudentNumber() {
        return num;
    }

    public int getUnitsNumber() {
        return units;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    @Override
    public int compareTo(Student s) {
        return ( int )( this.getAverageGrade() - s.getAverageGrade() );
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", units=" + units +
                ", averageGrade=" + averageGrade +
                '}';
    }

    // método auxiliar à clareza do ponto 4.
    public void compare_and_add_or_set( ArrayList<Student> bestStudents, int k ){
        for( int i = 0; i < k; ++i ){
            Student targetStudent = bestStudents.get(i);
            if( targetStudent == null ){                        // célula vazio
                bestStudents.add(this);
            } else {
                if( this.compareTo( targetStudent ) > 0)                // comparação de médias
                    bestStudents.set( i, this );
                else if( this.compareTo( targetStudent ) == 0) {        // desempate por numero de aluno
                    if (targetStudent.getStudentNumber() - this.getStudentNumber() > 0)
                        bestStudents.set(i, this);
                }
            }
        }
    }



}
