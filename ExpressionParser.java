import java.util.*;
// import com.sun.swing.internal.plaf.basic.resources.basic;

public class ExpressionParser {

    // This is the main function for you to implement
    public static int simplify(String expression){

        if(expression.length() == 0 || expression == ""){
            IllegalArgumentException a = new IllegalArgumentException ( "Emilis, String is empty!");
            throw a;
        }   

        for ( int i=0; i < expression.length(); ++i){
            if( !(Character.isDigit(expression.charAt(i))) && "+-*/()".indexOf(expression.charAt(i)) == -1 ){
                IllegalArgumentException b = new IllegalArgumentException("Emilis, String contains invalid characters.");
                throw b;
            } 
        }

        for ( int i=0; i < expression.length()-1; ++i){
            if("+*/".indexOf(expression.charAt(i)) != -1 && "+*/".indexOf(expression.charAt(i+1)) != -1 ){
                IllegalArgumentException c = new IllegalArgumentException("Emilis, there are two or more operators + / * in a row.");
                throw c;
            }
        }

        if( ")/*+".indexOf(expression.charAt(0)) != -1 || "-(/*+".indexOf(expression.charAt(expression.length()-1)) != -1){
            IllegalArgumentException d = new IllegalArgumentException("Emilis, String starts or ends with an invalid operator.");
            throw d;
        }

        int openBracket = 0;
        int closeBracket = 0; 
        for ( int i=0; i < expression.length(); ++i){
            if(expression.charAt(i) == '('){
                openBracket += 1;
            } else if(expression.charAt(i) == ')'){
                closeBracket += 1;
            }
        }
        if(openBracket != closeBracket){
            IllegalArgumentException e = new IllegalArgumentException("Emilis, there is an opening or closing bracket missing.");
            throw e;
        }
        
        ArrayList<String> cutText = new ArrayList<String>();
        int result = 0;
        
        String number = "";
        for ( int i=0; i < expression.length(); ++i){
            if ( Character.isDigit(expression.charAt(i)) ) {
                number += String.valueOf(expression.charAt(i));
            } else if( !Character.isDigit(expression.charAt(i)) ){
                if (number.length() > 0){
                    cutText.add(number);
                }    
                number = "";
                cutText.add(expression.substring(i, i+1));
            } 
        }
        cutText.add(number);
        
        System.out.println(cutText);

        for(int i = 0; i < cutText.size(); ++i){
            if(cutText.indexOf("/") == i && cutText.indexOf("0") == i+1){
                IllegalArgumentException f = new IllegalArgumentException("Emilis, you can't divede by 0!");
                throw f;
            }
        }
        
        if( cutText.size() == 1){
            result = Integer.parseInt(cutText.get(0));
        } 
        
        if(cutText.get(0).equals("-") && "()+-*/".indexOf(cutText.get(1)) == -1){
            cutText.set(1, String.valueOf(Integer.valueOf(cutText.get(1)) * -1));
            cutText.remove(cutText.get(0));
        }
        
        for(int i = 0; i < cutText.size(); ++i){
            if(cutText.get(i).equals("-") && cutText.get(i+1).equals("(")){
                cutText.set(i, "-1");
                cutText.add(i+1, "*");
                ++i;
            } else if(cutText.get(i).equals("(") && cutText.get(i+1).equals("-")){
                cutText.set(i+2, "-" + cutText.get(i+2));
                cutText.remove(i+1);
                --i;
            }
        }
   
        System.out.println(cutText);
        while(cutText.indexOf(")") != -1){
            int subA = cutText.lastIndexOf("(");
            int subB = cutText.indexOf(")");
            // System.out.println(subA);
            // System.out.println(subB);
            ArrayList<String> inBrackets = new ArrayList<String>(cutText.subList(subA+1, subB));
            System.out.println(inBrackets);
            while(inBrackets.size() > 1){
                while(inBrackets.indexOf("*") != -1 || inBrackets.indexOf("/") != -1 ){
                    if((inBrackets.indexOf("*") < inBrackets.indexOf("/") && inBrackets.indexOf("*") != -1) || inBrackets.indexOf("/") == -1){
                        result = Integer.valueOf(inBrackets.get(inBrackets.indexOf("*")-1)) * Integer.valueOf(inBrackets.get(inBrackets.indexOf("*")+1));
                        inBrackets.set(inBrackets.indexOf("*")-1, String.valueOf(result));
                        inBrackets.remove(inBrackets.indexOf("*")+1);
                        inBrackets.remove(inBrackets.indexOf("*"));
                        System.out.println(inBrackets);
                    } else if((inBrackets.indexOf("/") < inBrackets.indexOf("*") && inBrackets.indexOf("/") != -1) || inBrackets.indexOf("*") == -1){
                        result = Integer.valueOf(inBrackets.get(inBrackets.indexOf("/")-1)) * Integer.valueOf(inBrackets.get(inBrackets.indexOf("/")+1));
                        inBrackets.set(inBrackets.indexOf("/")-1, String.valueOf(result));
                        inBrackets.remove(inBrackets.indexOf("/")+1);
                        inBrackets.remove(inBrackets.indexOf("/"));
                        System.out.println(inBrackets);
                    }
                }
                while(inBrackets.indexOf("+") != -1 || inBrackets.indexOf("-") != -1 ){
                    if ((inBrackets.indexOf("+") < inBrackets.indexOf("-") && inBrackets.indexOf("+") != -1)  || inBrackets.indexOf("-") == -1){
                        result = Integer.valueOf(inBrackets.get(inBrackets.indexOf("+")-1)) + Integer.valueOf(inBrackets.get(inBrackets.indexOf("+")+1));
                        inBrackets.set(inBrackets.indexOf("+")-1, String.valueOf(result));
                        inBrackets.remove(inBrackets.indexOf("+")+1);
                        inBrackets.remove(inBrackets.indexOf("+"));
                        System.out.println(inBrackets);
                    }else if ((inBrackets.indexOf("-") < inBrackets.indexOf("+") && inBrackets.indexOf("-") != -1) || inBrackets.indexOf("+") == -1){
                        // System.out.println(inBrackets);
                        // System.out.println("error");
                        // System.out.println(inBrackets.get(inBrackets.indexOf("-")-1));
                        // System.out.println(Integer.valueOf(inBrackets.get(inBrackets.indexOf("-")+1)));
                        result = Integer.valueOf(inBrackets.get(inBrackets.indexOf("-")-1)) - Integer.valueOf(inBrackets.get(inBrackets.indexOf("-")+1));
                        inBrackets.set(inBrackets.indexOf("-")-1, String.valueOf(result));
                        inBrackets.remove(inBrackets.indexOf("-")+1);
                        inBrackets.remove(inBrackets.indexOf("-"));
                        System.out.println(inBrackets);
                    }
                }
                System.out.println(cutText);
                // System.out.println(subA);
                // System.out.println(subB);
                cutText.set(subA, String.valueOf(result));
                    System.out.println(cutText);
                    for(int i = subA; i < subB; ++i){
                        cutText.remove(subA+1);
                        System.out.println(cutText);
                }
            }
        }
        
        while(cutText.indexOf("*") != -1 || cutText.indexOf("/") != -1 ){
            if((cutText.indexOf("*") < cutText.indexOf("/") && cutText.indexOf("*") != -1) || cutText.indexOf("/") == -1){
                result = Integer.valueOf(cutText.get(cutText.indexOf("*")-1)) * Integer.valueOf(cutText.get(cutText.indexOf("*")+1));
                cutText.set(cutText.indexOf("*")-1, String.valueOf(result));
                cutText.remove(cutText.indexOf("*")+1);
                cutText.remove(cutText.indexOf("*"));
                System.out.println(cutText);
            } else if ((cutText.indexOf("/") < cutText.indexOf("*") && cutText.indexOf("/") != -1) || cutText.indexOf("*") == -1){
                result = Integer.valueOf(cutText.get(cutText.indexOf("/")-1)) / Integer.valueOf(cutText.get(cutText.indexOf("/")+1));
                cutText.set(cutText.indexOf("/")-1, String.valueOf(result));
                cutText.remove(cutText.indexOf("/")+1);
                cutText.remove(cutText.indexOf("/"));
                System.out.println(cutText);
            }            
        }

        while(cutText.indexOf("+") != -1 || cutText.indexOf("-") != -1 ){
            if ((cutText.indexOf("+") < cutText.indexOf("-") && cutText.indexOf("+") != -1)  || cutText.indexOf("-") == -1){
                result = Integer.valueOf(cutText.get(cutText.indexOf("+")-1)) + Integer.valueOf(cutText.get(cutText.indexOf("+")+1));
                cutText.set(cutText.indexOf("+")-1, String.valueOf(result));
                cutText.remove(cutText.indexOf("+")+1);
                cutText.remove(cutText.indexOf("+"));
                System.out.println(cutText);
            } else if ((cutText.indexOf("-") < cutText.indexOf("+") && cutText.indexOf("-") != -1) || cutText.indexOf("+") == -1){
                result = Integer.valueOf(cutText.get(cutText.indexOf("-")-1)) - Integer.valueOf(cutText.get(cutText.indexOf("-")+1));
                cutText.set(cutText.indexOf("-")-1, String.valueOf(result));
                cutText.remove(cutText.indexOf("-")+1);
                cutText.remove(cutText.indexOf("-"));
                System.out.println(cutText);
            }
        }
        System.out.println(result);
        return result;
    }
    
    public static void main(String[] args){        
    // HELPER CLASS
        class TestParams {
            String expression;
            int value;
            TestParams(String expression, int value){
                this.expression = expression;
                this.value = value;
            }
            boolean match(){
                if (value == ExpressionParser.simplify(expression))
                    return true;
                return false;
            }
        }

        // TEST CASES (you can add)
        TestParams[] tests = new TestParams[] {
                //Illegal Arguments
            // new TestParams("", 0),// empty string
            // new TestParams("2!3()/*-+", 0), // invalid characters
            // new TestParams("2-*/3/3-64", 0), // two or more operators in a row
            // new TestParams("(2(3+5)", 0), // uneven so of closing and opening brackets
            // new TestParams(")2+2(", 0), // starts or ends vith invalid op
            // new TestParams("3/0", 1),
            // new TestParams(" 4 51+2!!9+11+1Ų2ŪŪ.2", 503),
            // new TestParams("8*(2+4)-(15-5)/2", 43), /doesnt work with sibling brackets


            // new TestParams("1", 1),
            // new TestParams("1+3", 4),
            // new TestParams("1+3", 4),
            // new TestParams("1+3+3", 7),
            // new TestParams("9+89", 98),
            // new TestParams("451+29+11+12", 503),
            // new TestParams("6-3", 3),
            // new TestParams("230-5-15-100", 110),
            // new TestParams("3-6", -3),
            // new TestParams("60/12", 5),
            // new TestParams("0/3", 0),
            // new TestParams("10*8", 80),
            new TestParams("-(100+4*5-32/4+(4/4*2+2-66)+5-10)-2-2-6/2", 38),
            new TestParams("-(-100+4*-(5-32/4+4/4*(-2+16/-(2*5+14/6-4)+5+20)-10-2-4)+2*6+4)", -320),
            // new TestParams("0*3", 0),
            // new TestParams("0/3", 0),

            // new TestParams("1+3", 4),
            // new TestParams("9+8*4", 41),
            // new TestParams("-1+18", 17),
        };
        
        // run tests
        for (int i = 0; i < tests.length; ++i){
            if (!tests[i].match()){
                System.out.format("Expression %20s," , "\"" + tests[i].expression + "\"");
                System.out.format(" %10s,", "value " + ExpressionParser.simplify(tests[i].expression));
                System.out.format(" %20s\n", "expected " + tests[i].value);
            }
        }
    }
}
