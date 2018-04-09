JFLAGS =
JAVAC = javac
JAVA = java
LIB = src/.:lib/amqp-client-4.0.2.jar:lib/slf4j-api-1.7.21.jar:lib/slf4j-simple-1.7.22.jar

.SUFFIXES: .java .class
.java.class:
		$(JAVAC) $(JFLAGS) -cp $(LIB) $*.java

CLASSES = \
		src/Receiver.java \
		src/GameSettings.java \
		src/Sender.java \
		src/GameServer.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) src/*.class

runServer:
	$(JAVA) -cp $(LIB) GameServer $(name)
    # @echo "argument is $(name)"
runClient:
	# $(JAVA) -cp $(LIB) GameClient $1