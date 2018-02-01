/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.net.*;
import java.io.*;

public class EchoServer {

    private static class Action {
        static public final int s_Op_Echo = 0;
        static public final int s_Op_Plus = 1;
        static public final int s_Op_Minus = 2;
        static public final int s_Op_Multiply = 3;
        static public final int s_Op_Divide = 4;
        int operator;
        int x, y;
        String text;

        public Action(int iOperator, int iX, int iY) {
            operator = iOperator;
            x = iX;
            y = iY;
        }

        public Action(int iOperator, String iText) {
            operator = iOperator;
            text = iText;
        }

        public String process() {
            int resArithmetic = 0;
            String resString = "";
            boolean isDivisionByZero = false;
            switch (operator) {
                case s_Op_Echo:
                    resString = text;
                    return resString;
                case s_Op_Plus:
                    resArithmetic = x + y;
                    break;
                case s_Op_Minus:
                    resArithmetic = x - y;
                    break;
                case s_Op_Multiply:
                    resArithmetic = x * y;
                    break;
                case s_Op_Divide:
                    isDivisionByZero = (y == 0);
                    if (!isDivisionByZero)
                        resArithmetic = x / y;
                    break;
                default:
                    break;

            }

            if (isDivisionByZero) {
                return "Division By Zero";
            } else {
                return "" + resString + resArithmetic;
            }

        }

    }

    static private Action parseClientMessage(String m) {
        Action res;
        int operator = 0;
        String[] mSplit = m.split(" ");
        String ms = mSplit[0];
        System.out.println(ms);
        if (ms.contains("ADD")) {
            operator = Action.s_Op_Plus;
        } else if (ms.contains("SUB")) {
            operator = Action.s_Op_Minus;
        } else if (ms.contains("MUL")) {
            operator = Action.s_Op_Multiply;
        } else if (ms.contains("DIV")) {
            operator = Action.s_Op_Divide;
        } else if (ms.contains("ECHO")) {
            operator = Action.s_Op_Echo;
        }

        if (operator == 0) {
            String text = mSplit[1];
            res = new Action(operator, text);
        } else if (operator == 1 || operator == 2 || operator == 3 || operator == 4) {
            String sX = mSplit[1];
            String sY = mSplit[2];
            res = new Action(operator, Integer.parseInt(sX), Integer.parseInt(sY));
        } else {
            String text = mSplit[1];
            res = new Action(operator, text);
        }

        return res;
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Action action = parseClientMessage(inputLine);
                String outputLine = action.process();
                out.println(outputLine);
            }
            System.out.println("I'm ending...");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
