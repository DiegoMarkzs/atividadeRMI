import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteRemoto extends Remote {
    void receberMensagem(String de, String msg) throws RemoteException;
    void receberArquivo(String de, String nome, byte[] dados) throws RemoteException;
}
