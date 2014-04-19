To use the sh file:

Compile Server.java: javac -classpath .:mysql-connector-java-5.1.30-bin.jar Server.java.
Upload voteServerStart.sh, Server.class(if not already there), and mysql-connector-java-5.1.30-bin.jar files to turing.
Run the sh file in command prompt with "sh voteServerStart.sh".
This will run the server class and assign port 1795.

This is terminated in windows clients(as far as I'm aware) by Ctrl+C