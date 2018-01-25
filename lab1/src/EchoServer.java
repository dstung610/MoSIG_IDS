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

    private static class IntOperator {
      static public final int s_Op_Plus = 0;
      static public final int s_Op_Minus = 1;
      static public final int s_Op_Multiply = 2;
      static public final int s_Op_Devide = 3;
      int operator;
      int x, y;
      public IntOperator(int iOperator, int iX, int iY)
      {
        operator = iOperator;
        x = iX;
        y = iY;
      }
      public String Calculate()
      {
        int ret = 0;
        boolean isDivisionByZero = false;
        switch (operator)  {
          case s_Op_Plus:
            ret = x + y;
            break;
          case s_Op_Plus:
            ret = x + y;
            break;
          case s_Op_Plus:
            ret = x + y;
            break;
          case s_Op_Plus:
            isDivisionByZero = (y == 0);
            if (!isDivisionByZero)
              ret = x / y;
            break;
          default:
            break;
        }

        if (isDivisionByZero)
        {
          return "Division By Zero";
        }
        else
        {
          return "" + ret;
        }
      }

    }
    static private IntOperator parseClientMessage(String m)
    {
      IntOperator ret;
      int operator = 0;
      if (m.containt("plus"))
      {
        operator = InitOperator.s_Op_Plus;
      }
      else if (m.containt("minus"))
      {
        operator = InitOperator.s_Op_Minus;
      }
      else if (m.containt("multiply"))
      {
        operator = InitOperator.s_Op_Multiply;
      }
      else if (m.containt("divide"))
      {
        operator = InitOperator.s_Op_Devide;
      }
      else
      {

      }

      m = m.substring(m.indexOf(" ") + 1);
      String sX = m.substring(0, m.indexOf(" "));
      String sY = m.substring(m.indexOf(" "));
      ret = new IntOperator(operator, Integer.parseInt(sX), Integer.parseInt(sY));
    }
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Im starting...");
        try (

            ServerSocket serverSocket =
                new ServerSocket(Integer.parseInt(args[0]));
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                IntOperator exp = parseClientMessage(inputLine);
                String outputLine = "result " + exp.Calculate();
                out.println(outputLine);
            }
            System.out.println("I'm ending...");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
