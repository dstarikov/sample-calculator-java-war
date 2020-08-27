package calculator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class InputAnswerTest extends HttpServlet {
    private String[] functions = {"Addition", "Subtraction", "Multiplication", "Division (Decimal)", "Division (Remainder)"};

    // Returns a String with the answer given the user's input
    public static String calculate(int function, double x, double y) {
        String answerString;
        if (function == 0)
        {
            answerString = String.valueOf(x + y);
        }
        else if (function == 1)
        {
            answerString = String.valueOf(x - y);
        }
        else if (function == 2)
        {
            answerString = String.valueOf(x * y);
        }
        else if (function == 3)
        {
            answerString = String.valueOf(x / y);
        } else if (function == 4) {
            int answer = (int) (x / y);
            int answerRemainder = (int) (x % y);
            answerString = String.valueOf(answer) + " with a remainder of " + String.valueOf(answerRemainder);
        } else {
            answerString = "An error occurred with the function types";
        }
        return answerString;
    }

    @Override
    // This method is called when the user presses the "Calculate button"
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pass the user's values to the doGet method
        doGet(req, resp);
    }

    @Override
    // This method is called when the user tries to load the webpage
    // It will calculate the result from the user's inputted values, or use the default values of 1 if an input value is missing.
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double op1;
        double op2;
        int op_type;

        // Check if the user has submitted their values from the webpage
        if (req.getParameter("operand1") != null) {
            // Parse the number from the string
            op1 = Double.parseDouble(req.getParameter("operand1"));
        } else {
            // If they didn't provide a value, use a default of 1.
            op1 = 1;
        }

        if (req.getParameter("operand2") != null) {
            op2 = Double.parseDouble(req.getParameter("operand2"));
        } else {
            op2 = 1;
        }

        if (req.getParameter("operation") != null) {
            op_type = Integer.parseInt(req.getParameter("operation"));
        } else {
            op_type = 0;
        }
        // Calculate the result
        String result = calculate(op_type, op1, op2);

        // Set response content type
        resp.setContentType("text/html");

        // The webpage html code to show is written to this PrintWriter object
        PrintWriter out = resp.getWriter();

        // webpage title - inserted into the HTML below
        String title = "Simple Java Calculator";
        
        // HTML boilerplate code
        out.println("<!doctype html>\n");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + title + "</title>");
        // Reference the HTML css style file
        out.println("<link href=\"static/style.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        // Webpage title
        out.println("<h1>" + title + "</h1>");
        // Input FORM that the doPost()/doGet() methods read user-values from
        out.println("<FORM method=\"post\">");
        // Make user form a table so that stuff is aligned
        out.println("<table>");
        // This creates a new table row
        out.println("<tr>");
        // <td>...</td> creates a table column
        out.println("<td><label for=\"operand1\">First Operand</label></td>");
        // Insert the user-provided or default value of op1 into the webpage
        out.println("<td><input type=\"number\" id=\"operand1\" name=\"operand1\" value=" + op1 + "></td>");
        out.println("</tr><tr>");
        out.println("<td><label for=\"operation\">Operation Type:</label></td>");
        out.println("<td><div class=\"opselect\">");
        // Insert the user-provided or default value of the operation type into the webpage
        out.println("<select id=\"operation\" name=\"operation\">");
        for (int i = 0; i < functions.length; i++) {
            if (i == op_type) {
                out.println("<option selected=\"selected\" value=" + i + ">" + functions[i] + "</option>");
            } else {
                out.println("<option value=" + i + ">" + functions[i] + "</option>");
            }
        }
        out.println("</select>");
        out.println("</div></td>");
        out.println("</tr><tr>");
        out.println("<td><label for=\"operand2\">Second Operand</label></td>");
        // Insert the user-provided or default value of op2 into the webpage
        out.println("<td><input type=\"number\" id=\"operand2\" name=\"operand2\" value=" + op2 + "></td>");
        out.println("</tr><tr>");
        out.println("<td></td>");
        out.println("<td><button type=\"submit\">Calculate</td>");
        out.println("</tr><tr>");
        out.println("<td><label>Result:</label></td>");
        // Insert the calculated result into the webpage
        out.println("<td><output id=\"result\" name=\"result\">" + result + "</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</FORM>");
        out.println("</body>");
        out.println("</html>");
    }
}