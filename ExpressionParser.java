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
        // System.out.println(cutText);

        if( cutText.size() == 1){
            result = Integer.parseInt(cutText.get(0));
        } 
        
        // int mult = cutText.indexOf("*");
        // int div = cutText.indexOf("/");
        // int add = cutText.indexOf("+");
        // int sub = cutText.indexOf("-");
        System.out.println(cutText);
        while(cutText.size() > 1){
            // for(int i = 0; i < cutText.size(); ++i){}
            if(cutText.indexOf("*") != -1){
                result = Integer.valueOf(cutText.get(cutText.indexOf("*")-1)) * Integer.valueOf(cutText.get(cutText.indexOf("*")+1));
                // System.out.println(Integer.valueOf(cutText.get(cutText.indexOf("*")-1)));
                // System.out.println(Integer.valueOf(cutText.get(cutText.indexOf("*")+1)));
                // System.out.println(result);
                cutText.set(cutText.indexOf("*")-1, String.valueOf(result));
                cutText.remove(cutText.indexOf("*")+1);
                cutText.remove(cutText.indexOf("*"));
            } else if(cutText.indexOf("*") == -1 && cutText.indexOf("/") != -1){
                result = Integer.valueOf(cutText.get(cutText.indexOf("/")-1)) / Integer.valueOf(cutText.get(cutText.indexOf("/")+1));
                // System.out.println(result);
                cutText.set(cutText.indexOf("/")-1, String.valueOf(result));
                cutText.remove(cutText.indexOf("/")+1);
                cutText.remove(cutText.indexOf("/"));
            } else{
                if (cutText.indexOf("+") != -1){
                    result = Integer.valueOf(cutText.get(cutText.indexOf("+")-1)) + Integer.valueOf(cutText.get(cutText.indexOf("+")+1));
                    cutText.set(cutText.indexOf("+")-1, String.valueOf(result));
                    cutText.remove(cutText.indexOf("+")+1);
                    cutText.remove(cutText.indexOf("+"));
                }
                if (cutText.indexOf("-") != -1){
                    result = Integer.valueOf(cutText.get(cutText.indexOf("-")-1)) - Integer.valueOf(cutText.get(cutText.indexOf("-")+1));
                    cutText.set(cutText.indexOf("-")-1, String.valueOf(result));
                    cutText.remove(cutText.indexOf("-")+1);
                    cutText.remove(cutText.indexOf("-"));
                }

            }
            // System.out.println(cutText.get(0));
            // System.out.println(cutText.indexOf("*"));

        }

        //     System.out.println(cutText);
        //     // System.out.println(cutText.indexOf("*"));
        // for(int i = 1; i < cutText.size()-1; ++i){
        //     int a = Integer.parseInt(cutText.get(i-1));
        //     int b = Integer.parseInt(cutText.get(i+1));
        //     int c = 0;
        //     System.out.println(a);
        //     System.out.println(b);
        //     // System.out.println(cutText.get(1)); 
        //     System.out.println(cutText.get(i));
        //     if(cutText.get(i) == "*"){
        //         System.out.println(a);
        //         c = a * b;
        //         // System.out.println(a);
        //     } else if(cutText.get(i) == "/") {
        //         c = a / b;
        //     } else {
        //         c = a + b;
        //     }
        //     cutText.remove(i-1);
        //     cutText.remove(i-1);
        //     cutText.set(i-1, String.valueOf(c));
        //     System.out.println(c);
        // }
          
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
            // new TestParams("", 0),// empty string
            // new TestParams("2!3()/*-+", 0), // invalid characters
            // new TestParams("2-*/3/3-64", 0), // two or more operators in a row
            // new TestParams("(2(3+5)", 0),
            // new TestParams(")2+2(", 0),
            // new TestParams("1", 1),
            // new TestParams("1+3", 4),
            // new TestParams("1+3", 4),
            // new TestParams("1+3+3", 7),
            // new TestParams("9+89", 98),
            // new TestParams("451+29+11+12", 503),
            // new TestParams(" 4 51+2!!9+11+1Ų2ŪŪ.", 503),

            // new TestParams("6-3", 3),
            // new TestParams("230-5-15-100", 110),
            // new TestParams("3-6", -3),
            // new TestParams("60/12", 5),
            // new TestParams("0/3", 0),
            // new TestParams("10*8", 40),
            new TestParams("100+4*5-8/4*2", 320),
            // new TestParams("0/3", 0),
            // new TestParams("0/3", 0),
            // new TestParams("0/3", 0),
            // new TestParams("3/0", 1),

            // new TestParams("1+3", 4),
            // new TestParams("-1+18", 17),
            // new TestParams("9+8*4", 288),
            // new TestParams("8*(2+4)-(15-5)/2", 43),
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
