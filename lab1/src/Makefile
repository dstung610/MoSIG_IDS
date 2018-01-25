all: client_code server_code

client_code:
	javac EchoClient.java

server_code:
	javac EchoServer.java

clean:
	rm -rf *.class

run_server:
	exec java EchoServer $(p)

run_client:
	exec java EchoClient $(h) $(p)
