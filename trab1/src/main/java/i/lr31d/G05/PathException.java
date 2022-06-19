package i.lr31d.G05;

public class PathException extends Exception{
    public PathException(String msg){
        super(msg);
    }
    public PathException(){
        super("Ligação inválida");
    }
}
