## Documento completo
https://docs.google.com/document/d/1N8bkbuJEQjbfmxXFpf_RCgp5XOl87PMMan_ajuEqetY/edit?usp=sharing



## Passo a passo instalação
 

Criação dos class
## 1 - Mude para a pasta contendo os arquivos
cd "C:\Users\Marques\Documents\projetos\Parte2RMI\src"


## 2 - Gere os.class dos arquivos
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\javac.exe" -encoding ISO-8859-1 *.java

Conexão com o servidor

## 3 - Gere o stub
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\rmic.exe" Servidor

## 4 - Inicie o servidor 
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\java.exe" ^
-Djava.security.policy="C:\Users\Marques\Documents\projetos\Parte2RMI\src\rmi.policy" ^
Servidor

## Conexão com os clientes(Em outro terminal)


## 5 - Mude para a pasta contendo os arquivos
cd "C:\Users\Marques\Documents\projetos\Parte2RMI\src"

## 6 - Inicie o cliente
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\java.exe" ^
-Djava.security.policy="C:\Users\Marques\Documents\projetos\Parte2RMI\src\rmi.policy" ^
Cliente
