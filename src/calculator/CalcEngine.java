package calculator;

import java.util.Stack;

public class CalcEngine
{
    char operator;
    String displayString;

    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready 
     * for use.
     */
    public CalcEngine()
    {
        operator =' ';
        displayString=null;
    }
    
    public String getDisplayString()
    {
    	return displayString+"";
    }
    
    /**
     * Adds Button pressed to the infix equation in the calculator window.
     * (If button is not = or C)
     */
    public void buttonPressed(String c){
    	if (displayString == null){
    		displayString = c;
    	}
    	else{
    		displayString = displayString+ c;
    	}
    }
    
    /**
     * Calls method to convert the infix statement to postfix and then method
     * to evaluate the postfix equation
     */
    public void equalsPressed()
    {
    	displayString = conversion(displayString);
    	displayString = evaluate(displayString).toString();
    	
    }
    
    /**
     * Tests if the character is an operator
     */
    private static boolean isOperator(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^'
                || c == '(' || c == ')';
    }
    
    /**
     * Decides the other which operations are performed for the equation according to
     * the standard rules of priority.
     */
    private static boolean lowerPrec(char op1, char op2)
    {
        switch (op1)
        {
            case '+':
            	return !(op2 == '+' || op2 == '-');
            case '-':
                return !(op2 == '+' || op2 == '-');

            case '*':
            	return op2 == '^' || op2 == '(';
            case '/':
                return op2 == '^' || op2 == '(';

            case '^':
                return op2 == '(';

            case '(':
                return true;

            default:
                return false;
        }
    }
    
    /**
     * Converts to infix output equation to a postfix equation.
     */
    public static String conversion(String infix)
    {
        Stack<Character> s = new Stack<Character>();
        StringBuffer postfix = new StringBuffer(infix.length());
        char ch;

        for (int i = 0; i < infix.length(); i++)
        {
            ch = infix.charAt(i);
            
            //Adds operands to the postfix String.
            if (!isOperator(ch))
            {
                postfix.append(ch);
            }
            
            /**
             * Deals with close bracket.
             * Adds Tokens within the brackets to the postfix String.
             */
            else
            {
            	postfix.append(" ");
                if (ch == ')')
                {
                	/**
                	 * Iterates through tokens on the stack until a closed bracket
                	 * is reached, it then removes the '(' from the stack.
                	 */
                    while (!s.isEmpty() && s.peek() != '(')
                    {
                    	postfix.append(" ");
                        postfix.append(s.pop());
                        postfix.append(" ");
                    }
                    if (!s.isEmpty())
                    {
                        s.pop();
                    }
                }
                else if(ch == '(' && i==0)
                {
                	s.push(ch);
                }

                else
                {
                	/* 
                	 * if operator is higher priority than topmost operator on the stack
                	 * ,places the operator on the stack.
                	 */
                    if (!(s.isEmpty()) && !lowerPrec(ch, s.peek()))
                    {
                        s.push(ch);
                    }
                    else
                    {
                        while (!s.isEmpty() && lowerPrec(ch, s.peek()))
                        {
                            Character ele = s.pop();
                            if (ch != '(')
                            {
                            	postfix.append(" ");
                                postfix.append(ele);

                                postfix.append(" ");
                            } else {
                              ch = ele;
                            }
                        }
                        s.push(ch);
                    }

                }
            }
        }
        //empties the stack, appending all remaining tokens to the postfix String.
        while (!s.isEmpty()) {
          Character element = s.pop();
          postfix.append(" ");
        	  postfix.append(element);
          postfix.append(" ");
        }
        return postfix.toString();
    }
    
    /**
     * Gives the result to the equation.
     */
    private Double evaluate(String post){
    	String p = post;
    	
    	// parses the post string.
    	String[] tokens = p.split("\\s+");
    	Stack<Double> operands =new Stack<Double>();
    	
    	// iterates through tokens array
    	for (String token : tokens)
    	{
    		// Pushes any multiple digit numbers on to the stack.
    		if(token.length()!=1)
    		{
    			operands.push(Double.parseDouble(token));
    		}
    		
    		// Pushes any single digit operands on to the stack.
    		else if(!isOperator(token.charAt(0)))
    		{
    			operands.push(Double.parseDouble(token));
    		}
    		
    		
    		else if(isOperator(token.charAt(0)) && token.charAt(0)!= '(')
    		{
    			double op1 = operands.pop();
    			double op2 = operands.pop();
    			double result = equals(op1,op2,token.charAt(0));
    			operands.push(result);
    		}
    		
    	}
    	return operands.pop();

    }
    
    /**
     * Defines the operations for the corresponding operator and returns result
     * of the operation.
     */
    public double equals(double op1, double op2, char operator)
    {
    	double result = 0;
    	
        if (operator == '+') {
			result = op1 + op2;
		}
	    else if (operator == '-') {
	    	result = op2 - op1;
		}
		else if (operator == '*') {
			result = op2 * op1;
		}
		else if (operator == '/') {
			result = op2 / op1;
		}
		else if (operator == '^') {
			double power = op2;
			
			for(int i=1;i<op1;i++)
			{
				power = power*op2;
			}
			result = power;
		}
        return result;

    }

    
    public void clear()
    {
        displayString = null;

    }

    /**
     * Return the title of this calculation engine.
     */
    public String getTitle()
    {
        return("My Calculator");
    }

    /**
     * Return the author of this engine. This string is displayed as it is,
     * so it should say something like "Written by H. Simpson".
     */
    public String getAuthor()
    {
        return("Jamie Sheehan");
    }

    /**
     * Return the version number of this engine. This string is displayed as 
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.0");
    }

}