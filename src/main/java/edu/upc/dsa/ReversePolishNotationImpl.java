package edu.upc.dsa;

import java.util.*;

import org.apache.log4j.Logger;

public class ReversePolishNotationImpl implements ReversePolishNotation {

    private static ReversePolishNotation instance;
    protected double resultat;
    final static Logger logger = Logger.getLogger(ReversePolishNotationImpl.class);

    @Override
    public double FerOperacio(String expresioRPN) {

        logger.info("FerOperacio("+expresioRPN+")");

        if(expresioRPN==null || expresioRPN.isEmpty()) {
            logger.error("FerOperacio: esta buida");
            IllegalArgumentException e = new IllegalArgumentException("La exprecio no pot estar buida");
            throw e;
        }

        String[] tokens = expresioRPN.split(" ");
        Stack<Double> stack = new Stack<>();

        for(String token : tokens){

            if(token.equals("(") || token.equals(")")) {
                logger.error("FerOperacio: no se permeten parentesis");
                IllegalArgumentException e = new IllegalArgumentException("No se permeten parentesis");
                throw e;
            }

            if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")){
                if(stack.size() < 2){
                    logger.error("No hi ha suficients Operands");
                    IllegalArgumentException e = new IllegalArgumentException("No hi ha suficients operands");
                    throw  e;
                }

                double segonoperant = stack.pop();
                double primeroperant = stack.pop();


                switch (token){
                    case "+":
                        resultat = primeroperant + segonoperant;
                        stack.push(resultat);
                        break;
                    case "-":
                        resultat = primeroperant - segonoperant;
                        stack.push(resultat);
                        break;
                    case "*":
                        resultat = primeroperant * segonoperant;
                        stack.push(resultat);
                        break;
                    case "/":
                        if(segonoperant == 0){
                            logger.error("No pots dividir per Zero");
                            throw new ArithmeticException("No pots dividir per Zero");
                        }else{
                            resultat = primeroperant / segonoperant;
                            stack.push(resultat);
                        }
                        break;
                }
                logger.info("S ha afegit resultat a la pila");
            }else{
                try{
                    stack.push(Double.parseDouble(token));
                    logger.info("S ha afegit numero a la pila");

                } catch (NumberFormatException e) {
                    logger.error("aquest tocken no es ni un numero ni un operador");
                    throw new IllegalArgumentException(e);
                }
            }
            
        }

        if (stack.size() != 1) {
            logger.error("Expressió mal formada");
            throw new IllegalArgumentException("Expressió mal formada");
        }

        resultat = stack.pop();
        logger.info("Operacio acabada resultat = " + resultat);
        return resultat;

    }


}
