import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServidorRemoto extends Remote {

    void escreveMsg(String msg) throws RemoteException;

    Date dataDeHoje() throws RemoteException;

    void registrarCliente(String at, ClienteRemoto cliente) throws RemoteException;

    String[] listarClientes() throws RemoteException;

    ClienteRemoto buscarCliente(String at) throws RemoteException;

    void enviarMensagemSala(String de, String msg) throws RemoteException;

	void deixarClienteOffline(String at) throws RemoteException;

}
