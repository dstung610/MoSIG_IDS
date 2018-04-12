JFLAGS =
JAVAC = javac
JAVA = java
LIB = src/.:lib/amqp-client-4.0.2.jar:lib/slf4j-api-1.7.21.jar:lib/slf4j-simple-1.7.22.jar:GUI/.

.SUFFIXES: .java .class
.java.class:
		$(JAVAC) $(JFLAGS) -cp $(LIB) $*.java

CLASSES = \
		src/GameUtils.java \
		src/GameServer.java \
		src/GameClient.java \
		src/GUI.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) src/*.class

runServer:
	$(JAVA) -cp $(LIB) GameServer $(name)
    # @echo "argument is $(name)"
runClient:
	$(JAVA) -cp $(LIB) GameClient $(name)
runGUI:
	$(JAVA) -cp $(LIB) GUI $(node)