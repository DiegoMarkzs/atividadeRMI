import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cliente extends UnicastRemoteObject implements ClienteRemoto {

    private String at;

    protected Cliente() throws RemoteException {
        super();
    }

    public static void main(String[] args)
            throws RemoteException, MalformedURLException, NotBoundException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu nome: ");
        String nome = scanner.nextLine().trim();
        String at = "@" + nome;

        String host = "127.0.0.1";
        if (args.length == 1) {
            host = args[0];
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        ServidorRemoto servidor =
                (ServidorRemoto) Naming.lookup("//" + host + "/Servidor");

        Cliente cliente = new Cliente();
        cliente.at = at;

        servidor.registrarCliente(at, cliente);



        String linha = "";

		System.out.println("Bem vindo ao chat " + at + "!");
		System.out.println("Digite !help para ver os comandos disponíveis.");

		while (!linha.equalsIgnoreCase("5")) {
			System.out.print("\n");

			linha = scanner.nextLine().trim();
			
			String comando = linha;
			
			switch (comando) {
			case "1": // LISTAR USUARIOS
				try {
				String[] clientes = servidor.listarClientes();
				System.out.println("Clientes online:");
				for (String c : clientes) {
					System.out.println(" - " + c);
				}
				} catch (RemoteException e) {
				System.out.println("Erro ao listar clientes: " + e.getMessage());
				}
				break;
			
			case "2": // ENVIAR MENSAGEM
				try {
				System.out.println("Para enviar a mensagem, digite nesse formato: @usuarioDaMensagem SuaMensagem");

				System.out.print("Digite: ");
				String mensagemEnviada = scanner.nextLine().trim();

				String[] partes = mensagemEnviada.split(" ", 2);
				if (partes.length < 2) {
					System.out.println("Formato inválido. Use: @usuarioDaMensagem SuaMensagem");
					break;
				}
				
				String destino = partes[0];
				String mensagem = partes[1];
				
				ClienteRemoto outro = servidor.buscarCliente(destino);
				if (outro != null) {
					outro.receberMensagem(at, mensagem);
					System.out.println("Mensagem enviada para " + destino);
				} else {
					System.out.println("Cliente " + destino + " não está online.");
				}
				} catch (RemoteException e) {
				System.out.println("Erro ao enviar mensagem: " + e.getMessage());
				}
				break;
			
			case "3": // MENSAGEM SALA
				try {
				System.out.print("Digite sua mensagem para a sala: ");
				String mensagem = scanner.nextLine().trim();
				if (mensagem.isEmpty()) {
					System.out.println("Ação invalida \n para enviar a mensagem, apenas digite ela e aperte enter");
					break;
				}
				servidor.enviarMensagemSala(at, mensagem);
				} catch (RemoteException e) {
				System.out.println("Erro ao enviar para sala: " + e.getMessage());
				}
				break;

			case "4": // ENVIAR ARQUIVO
    try {
        System.out.println("Formato: @usuario caminho_completo_do_arquivo");
        System.out.print("Digite: ");
        String entrada = scanner.nextLine().trim();

        String[] partes = entrada.split(" ", 2);
        if (partes.length < 2) {
            System.out.println("Formato inválido.");
            break;
        }

        String destino = partes[0];
        String caminho = partes[1];

        File arquivo = new File(caminho);
        if (!arquivo.exists() || !arquivo.isFile()) {
            System.out.println("Arquivo não encontrado.");
            break;
        }

        byte[] dados = Files.readAllBytes(arquivo.toPath());

        ClienteRemoto outro = servidor.buscarCliente(destino);
        if (outro != null) {
            outro.receberArquivo(at, arquivo.getName(), dados);
            System.out.println("Arquivo enviado para " + destino);
        } else {
            System.out.println("Cliente " + destino + " não está online.");
        }

    } catch (Exception e) {
        System.out.println("Erro ao enviar arquivo: " + e.getMessage());
    }
    break;
			
			case "5": // SAIR
				try {
				System.out.println("Encerrando cliente...");
				servidor.deixarClienteOffline(at);
				} catch (RemoteException e) {
				System.out.println("Erro ao desconectar: " + e.getMessage());
				}
				System.exit(0);
				break;

			case "!help":
				System.out.println("Comandos disponíveis:");
				System.out.println("Digite 1 para listar todos os usuarios online no servidor");
				System.out.println("Digite 2 para poder mandar mensagem para outro usuario online");
				System.out.println("Digite 3 para entrar na sala de mensagem com os todos os usuarios online");
				System.out.println("Digite 4 para enviar um arquivo para outro usuario online");
				System.out.println("Digite 5 para sair do chat");
			}
		}
    }

    @Override
    public void receberMensagem(String de, String msg) throws RemoteException {
        System.out.println(de + ": " + msg);
    }

    @Override
public void receberArquivo(String de, String nome, byte[] dados)
        throws RemoteException {
    try {
        Files.write(Paths.get(nome), dados);
        System.out.println(
            "Arquivo recebido de " + de + ": " + nome + " (salvo com sucesso)"
        );
    } catch (Exception e) {
        System.out.println("Erro ao salvar arquivo: " + e.getMessage());
    }
}
}
