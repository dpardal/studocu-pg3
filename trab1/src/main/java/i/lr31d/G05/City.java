package i.lr31d.G05;

import java.lang.String;
import static java.lang.Integer.*;
import java.util.Arrays;


public class City implements Place{

    private final String name, country;
    private int population;
    private final int area; // Em km²

    public City(String nm, int p, int a) {
        this.name = nm;
        this.country = "Portugal";
        this.population = p;
        this.area = a;
    }

    public City(String nm, String pais, int p, int a) {
        this.name = nm;
        this.country = pais;
        this.population = p;
        this.area = a;
    }

    public String toString(){
        return this.country + ":" + this.name + " - " + this.area + "km2:" + this.population;
    }

    public boolean equals(City c) {
        if( c==null ){ return false; }
        return this.name.equals(c.name) && this.country.equals(c.country) && this.population == c.population && this.area == c.area;
    }

    public String getCountry() {
        return country;
    }

    public int getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public String getName() {
        return name;
    }

    public int populationDensity(){ return population / area; }

    public int populationChange(double rate){
        int newPopulation;
        int oldPopulation = getPopulation();
        newPopulation = (int) (oldPopulation * Math.pow(Math.E, rate) );
        return newPopulation;
    }

    public int compareTo(City c){ return this.area - c.area; }

    public static City getCity(String str){
        int firstColonIndex = str.indexOf(':');
        String country = str
                .substring(0, firstColonIndex)
                .trim();
        int hifenIndex = str.indexOf('-');
        String name = str
                .substring(firstColonIndex+1, hifenIndex)
                .trim();
        int secondColonIndex = str.indexOf(':', hifenIndex );
        String area = str
                .substring(hifenIndex+1, secondColonIndex-3)
                .trim();
        int a = parseInt(area);
        String population = str
                .substring(secondColonIndex+1)
                .trim();
        int p = parseInt(population);
        return new City(name, country, p, a);
    }

    public static int getCountryCitiesCount(City[] cities, String country){
        int count = 0;
        for( City cid : cities ){
            if( cid.country.equals(country) ) ++count;
        }
        return count;
    }

    public static City smallerCities(City ... cities){
        City smaller = cities[0];
        for( int i = 1; i < cities.length ;i++ ){
            if( cities[i].compareTo( smaller ) < 0 ) smaller = cities[i];
        }
        return smaller;
    }

    public static City[] getTop10(City[] cities){
        // recebe o array e o critério de ordenação (senão nao sabe comparar ... ou só sabe comparar objects)
        // pode ser (não percebi a primeira forma de implementar isto)
        // pode ser feito com uma classe anónima que imlplemente a interface Comparator<Type>
        // pode ser feito com expressão lambda (como está agora)
        Arrays.sort(cities, (City c1, City c2) -> c1.compareTo(c2) );
        // pode ser feito com expressão lambda utilizado uma referência para o método da classe Comparator (se tiverem assinaturas iguais)
        // Arrays.sort( cities, Classe-que-tem-o-metodo-de-comparacao::método-de-comparacao) // se o metodo de comparação for estatico
        // Arrays.sort( cities, this::método-de-comparacao) // se o metodo de comparação for de instancia
        return Arrays.copyOf(cities, 10);
    }

}
