/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class EchoClient {
    private static HashMap<String, Integer> optionMap = new HashMap<>();

    static {
        optionMap.put("ECHO", 1);
        optionMap.put("ADD", 2);
        optionMap.put("SUB", 3);
        optionMap.put("DIV", 4);
        optionMap.put("MUL", 5);
    }

    public static void printMenu() {
        System.out.println("Choose one of the following options:");
        System.out.println("\tGo to simple Echo: Enter 1");
        System.out.println("\tAdding 2 numbers: Enter 2");
        System.out.println("\tSubtract 2 numbers: Enter 3");
        System.out.println("\tDivide 2 numbers: Enter 4");
        System.out.println("\tMultiple 2 numbers: Enter 5");
        System.out.println();
        System.out.print("Your choice: ");
    }

    public static boolean isOptionValid(int option) {
        boolean res = false;
        for (HashMap.Entry<String, Integer> e : optionMap.entrySet()) {
            int o = e.getValue();
            if (o == option) res = true;
        }
        return res;
    }


    private static String getOptionString(int option) {
        String optionString = "";
        for (HashMap.Entry<String, Integer> e : optionMap.entrySet()) {
            int o = e.getValue();
            if (o == option) optionString += e.getKey();
        }
        return optionString;
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String userInput;
            int option = 0;
            String optionString = "";

            while (!isOptionValid(option)) {
                printMenu();
                userInput = stdIn.readLine();
                option = Integer.parseInt(userInput);
                optionString = getOptionString(option);
            }
            System.out.println("You choose " + optionString);

            while ((userInput = stdIn.readLine()) != null) {
                switch (optionString) {
                    case "ECHO":
                        out.println("ECHO " + userInput);
                        break;
                    case "ADD":
                        out.println("ADD " + userInput);
                        break;
                    case "SUB":
                        out.println("SUB " + userInput);
                        break;
                    case "DIV":
                        out.println("DIV " + userInput);
                        break;
                    case "MUL":
                        out.println("MUL " + userInput);
                        break;
                }

                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

}
