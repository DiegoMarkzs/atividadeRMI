## Documento completo
https://docs.google.com/document/d/1N8bkbuJEQjbfmxXFpf_RCgp5XOl87PMMan_ajuEqetY/edit?usp=sharing

## Passo a passo instalação
 

Criação dos class
1 - 
cd "C:\Users\Marques\Documents\projetos\Parte2RMI\src"


2 - 
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\javac.exe" -encoding ISO-8859-1 *.java

Conexão com o servidor

3 - 
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\rmic.exe" Servidor

4 - 
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\java.exe" ^
-Djava.security.policy="C:\Users\Marques\Documents\projetos\Parte2RMI\src\rmi.policy" ^
Servidor

Conexão com os clientes(Em outro terminal)


5 - 
cd "C:\Users\Marques\Documents\projetos\Parte2RMI\src"

6 - 
"C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot\bin\java.exe" ^
-Djava.security.policy="C:\Users\Marques\Documents\projetos\Parte2RMI\src\rmi.policy" ^
Cliente
