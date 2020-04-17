package app;

import java.io.*;
import java.util.*;
//import java.util.regex.*;

//import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";

	/**
	 * Populates the vars list with simple variables, and arrays lists with
	 * arrays in the expression. For every variable (simple or array), a SINGLE
	 * instance is created and stored, even if it appears more than once in the
	 * expression. At this time, values for all variables and all array items
	 * are set to zero - they will be loaded from a file in the
	 * loadVariableValues method.
	 * 
	 * @param expr
	 *            The expression
	 * @param vars
	 *            The variables array list - already created by the caller
	 * @param arrays
	 *            The arrays array list - already created by the caller
	 */
	public static void makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		/** COMPLETE THIS METHOD **/
		String[] tokens = expr.split("[ ()\\]*\\\t+-/\\d]");
		for (String token : tokens) {
			if (!token.isEmpty()) {
				int index = token.indexOf('[');
				while (index != -1) {
					arrays.add(new Array(token.substring(0, index)));
					token = token.substring(index + 1);
					index = token.indexOf('[');
				}
				if (!token.isEmpty()) {
					vars.add(new Variable(token));
				}
			}
		}
	}

	/**
	 * Loads values for variables and arrays in the expression
	 * 
	 * @param sc
	 *            Scanner for values input
	 * @throws IOException
	 *             If there is a problem with the input
	 * @param vars
	 *            The variables array list, previously populated by
	 *            makeVariableLists
	 * @param arrays
	 *            The arrays array list - previously populated by
	 *            makeVariableLists
	 */
	public static void loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
			throws IOException {
		while (sc.hasNextLine()) {
			StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
			int numTokens = st.countTokens();
			String tok = st.nextToken();
			Variable var = new Variable(tok);
			Array arr = new Array(tok);
			int vari = vars.indexOf(var);
			int arri = arrays.indexOf(arr);
			if (vari == -1 && arri == -1) {
				continue;
			}
			int num = Integer.parseInt(st.nextToken());
			if (numTokens == 2) { // scalar symbol
				vars.get(vari).value = num;
			} else { // array symbol
				arr = arrays.get(arri);
				arr.values = new int[num];
				// following are (index,val) pairs
				while (st.hasMoreTokens()) {
					tok = st.nextToken();
					StringTokenizer stt = new StringTokenizer(tok, " (,)");
					int index = Integer.parseInt(stt.nextToken());
					int val = Integer.parseInt(stt.nextToken());
					arr.values[index] = val;
				}
			}
		}
	}

	/**
	 * Evaluates the expression.
	 * 
	 * @param vars
	 *            The variables array list, with values for all variables in the
	 *            expression
	 * @param arrays
	 *            The arrays array list, with values for all array items
	 * @return Result of evaluation
	 */
	public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
		/** COMPLETE THIS METHOD **/
		StringBuilder sb = new StringBuilder(expr);
		String enclosures = "()[]";
		Stack<Integer> leftIndices = new Stack<>();
		for (int j = 0; j < sb.length(); j++) {
			char c = sb.charAt(j);
			if (enclosures.contains(Character.toString(c))) {
				if (c == '(' || c == '[') {
					leftIndices.push(j);
				} else {
					int i = leftIndices.pop();
					if (leftIndices.isEmpty()) {
						float res = evaluate(sb.substring(i + 1, j), vars, arrays);
						if (c == ')') {
							sb.replace(i, j + 1, Float.toString(res));
						} else {
							sb.replace(i + 1, j, Integer.toString((int) res));
						}
		}
				}
			}
		}

		String validOperators = "+-*/";
		Stack<Float> operands = new Stack<>();
		Stack<Character> operators = new Stack<>();
		sb.insert(0, '+');
		int j = sb.length();
		for (int i = j - 1; i >= 0; i--) {
			char c = sb.charAt(i);
			String substr = sb.substring(i + 1, j).trim();
			float val;
			if (validOperators.contains(Character.toString(c))) {
				if (substr.matches(".*[a-zA-Z].*")) {
					if (substr.contains("[")) {
						int leftBracketIndex = substr.indexOf("[");
						int index = arrays.indexOf(new Array(substr.substring(0, leftBracketIndex)));
						val = arrays.get(index).values[Integer
								.parseInt(substr.substring(leftBracketIndex + 1, substr.length() - 1))];
					} else {
						int index = vars.indexOf(new Variable(substr));
						val = vars.get(index).value;
					}
				} else {
					val = Float.parseFloat(substr);
				}
				operands.push(val);
				j = i;
				if (c == '+' || c == '-') {
					performOperations(true, operators, operands);
				}
				operators.push(c);
			}
		}
		operators.pop();
		performOperations(false, operators, operands);
		return operands.pop();
	}

	private static void performOperations(boolean multiplicative, Stack<Character> operators, Stack<Float> operands) {
		while (!operators.isEmpty() && (!multiplicative || operators.peek() == '*' || operators.peek() == '/')) {
			char c = operators.pop();
			float left = operands.pop();
			float right = operands.pop();
			float res = 0;
			switch (c) {
			case '+':
				res = left + right;
				break;
			case '-':
				res = left - right;
				break;
			case '*':
				res = left * right;
				break;
			case '/':
				res = left / right;
				break;
			}
			operands.push(res);
		}
	}
}

