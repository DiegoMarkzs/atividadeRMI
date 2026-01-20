import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class Servidor extends UnicastRemoteObject implements ServidorRemoto {

	private Map<String, ClienteRemoto> clientesAtivos = new HashMap<>();

	public Servidor() throws RemoteException {
	}

	public void escreveMsg(String msg) throws RemoteException {
		System.out.println(msg);
	}

	public Date dataDeHoje() throws RemoteException {
		return new Date();
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException {

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		try {
			// Fazer o registro para a porta desejado
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry ready.");
		} catch (Exception e) {
			System.out.println("Exception starting RMI registry:");
			e.printStackTrace();
		}

		Servidor servidor = new Servidor();

		Naming.rebind("Servidor", servidor);
	}

	public synchronized void registrarCliente(String at, ClienteRemoto cliente)
			throws RemoteException {

		if (clientesAtivos.containsKey(at)) {
			throw new RemoteException("@" + at + " já está em uso");
		}

		clientesAtivos.put(at, cliente);
	}

	public String[] listarClientes() throws RemoteException {
		return clientesAtivos.keySet().toArray(new String[0]);
	}

	public ClienteRemoto buscarCliente(String at) throws RemoteException {
		return clientesAtivos.get(at);
	}

	public void enviarMensagemSala(String de, String msg) throws RemoteException {
    for (ClienteRemoto c : clientesAtivos.values()) {
        c.receberMensagem(de, msg);
    }
}

	@Override
	public void deixarClienteOffline(String at) throws RemoteException {
		clientesAtivos.remove(at);
	}



	

}
