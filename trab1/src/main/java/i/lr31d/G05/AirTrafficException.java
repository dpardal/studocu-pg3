package i.lr31d.G05;

public class AirTrafficException extends Exception{

    public AirTrafficException(String msg){
        super("Aviso: " + msg);
    }

    public AirTrafficException(){
        super("Aviso: aeronave fora da rota");
    }

}
