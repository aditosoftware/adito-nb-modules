/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Evaluates a string of JavaScript code without reference to a particular object.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* eval(<i>string</i>[, <i>object</i>])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>string</code> </dt><dd> A string representing a JavaScript expression, statement, or sequence of statements. The expression can include variables and properties of existing objects.
* </dd><dt style="font-weight:bold"> <code>object</code> </dt><dd> An optional argument; if specified, the evaluation is restricted to the context of the specified object.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>eval</code> is a top-level function and is not associated with any object.
* </p><p>The argument of the <code>eval</code> function is a string. If the string represents an expression, <code>eval</code> evaluates the expression. If the argument represents one or more JavaScript statements, <code>eval</code> performs the statements. Do not call <code>eval</code> to evaluate an arithmetic expression; JavaScript evaluates arithmetic expressions automatically.
* </p><p>If you construct an arithmetic expression as a string, you can use <code>eval</code> to evaluate it at a later time. For example, suppose you have a variable <code>x</code>. You can postpone evaluation of an expression involving <code>x</code> by assigning the string value of the expression, say "<code>3 * x + 2</code>", to a variable, and then calling <code>eval</code> at a later point in your script.
* </p><p>If the argument of <code>eval</code> is not a string, <code>eval</code> returns the argument unchanged. In the following example, the <code>String</code> constructor is specified, and <code>eval</code> returns a <code>String</code> object rather than evaluating the string.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* eval(new String("2 + 2")); // returns a String object containing "2 + 2"
* eval("2 + 2");             // returns 4
* </pre>
* <p>You can work around this limitation in a generic fashion by using <code>toString</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var expression = new String("2 + 2");
* eval(expression.toString());
* </pre>
* <p>You cannot indirectly use the <code>eval</code> function by invoking it via a name other than <code>eval</code>; if you do, a runtime error might occur. For example, you should not use the following code:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var x = 2;
* var y = 4;
* var myEval = eval;
* myEval("x + y");
* </pre>
* <p>You should not use <code>eval</code> to convert property names into properties.  Consider the following example. The <code>getFieldName(n)</code> function returns the name of the specified form element as a string. The first statement assigns the string value of the third form element to the variable <code>field</code>. The second statement uses <code>eval</code> to display the value of the form element.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var field = getFieldName(3);
* document.write("The field named ", field, " has value of ",
* eval(field + ".value"));
* </pre>
* <p>However <code>eval</code> is not necessary here. In fact, its use here is discouraged.  Instead, use the <a href="Core_JavaScript_1.5_Reference:Operators:Member_Operators" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Member Operators">member operators</a>, which are much faster:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var field = getFieldName(3);
* document.write("The field named ", field, " has value of ",
* field[value]);
* </pre>
* <h2> <span> Backward Compatibility </span></h2>
* <h3> <span> JavaScript 1.3 and earlier </span></h3>
* <p>You can use <code>eval</code> indirectly, although it is discouraged.
* </p>
* <h3> <span> JavaScript 1.1 </span></h3>
* <p><code>eval</code> is also a method of all objects.
* </p>
* <h2> <span> Examples </span></h2>
* <p>The following examples display output using <code>document.write</code>. In server-side JavaScript, you can display the same output by calling the <code>write</code> function instead of using <code>document.write</code>.
* </p>
* <h3> <span> Example: Using <code>eval</code> </span></h3>
* <p>In the following code, both of the statements containing <code>eval</code> return 42. The first evaluates the string "<code>x + y + 1</code>"; the second evaluates the string "<code>42</code>".
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var x = 2;
* var y = 39;
* var z = "42";
* eval("x + y + 1"); // returns 42
* eval(z);           // returns 42
* </pre>
* <h3> <span> Example: Using <code>eval</code> to evaluate a string of JavaScript statements </span></h3>
* <p>The following example uses <code>eval</code> to evaluate the string <code>str</code>. This string consists of JavaScript statements that open an Alert dialog box and assign <code>z</code> a value of 42 if <code>x</code> is five, and assigns 0 to <code>z</code> otherwise. When the second statement is executed, <code>eval</code> will cause these statements to be performed, and it will also evaluate the set of statements and return the value that is assigned to <code>z</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var str = "if (x == 5) {alert('z is 42'); z = 42;} else z = 0; ";
* document.write("&lt;P&gt;z is ", eval(str));
* </pre>
* <h2> <span> Return value </span></h2>
* <p><code>eval</code> returns the value of the last expression evaluated.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var str = "if ( a ) { 1+1; } else { 1+2; }"
* var a = true;
* var b = eval(str);  // returns 2
* alert("b is : " + b);
* a = false;
* b = eval(str);  // returns 3
* alert("b is : " + b);
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Operators:Member_Operators" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Member Operators">member operators</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Eval = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Evaluates a string of JavaScript code without reference to a particular object.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* eval(<i>string</i>[, <i>object</i>])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>string</code> </dt><dd> A string representing a JavaScript expression, statement, or sequence of statements. The expression can include variables and properties of existing objects.
* </dd><dt style="font-weight:bold"> <code>object</code> </dt><dd> An optional argument; if specified, the evaluation is restricted to the context of the specified object.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>eval</code> is a top-level function and is not associated with any object.
* </p><p>The argument of the <code>eval</code> function is a string. If the string represents an expression, <code>eval</code> evaluates the expression. If the argument represents one or more JavaScript statements, <code>eval</code> performs the statements. Do not call <code>eval</code> to evaluate an arithmetic expression; JavaScript evaluates arithmetic expressions automatically.
* </p><p>If you construct an arithmetic expression as a string, you can use <code>eval</code> to evaluate it at a later time. For example, suppose you have a variable <code>x</code>. You can postpone evaluation of an expression involving <code>x</code> by assigning the string value of the expression, say "<code>3 * x + 2</code>", to a variable, and then calling <code>eval</code> at a later point in your script.
* </p><p>If the argument of <code>eval</code> is not a string, <code>eval</code> returns the argument unchanged. In the following example, the <code>String</code> constructor is specified, and <code>eval</code> returns a <code>String</code> object rather than evaluating the string.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* eval(new String("2 + 2")); // returns a String object containing "2 + 2"
* eval("2 + 2");             // returns 4
* </pre>
* <p>You can work around this limitation in a generic fashion by using <code>toString</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var expression = new String("2 + 2");
* eval(expression.toString());
* </pre>
* <p>You cannot indirectly use the <code>eval</code> function by invoking it via a name other than <code>eval</code>; if you do, a runtime error might occur. For example, you should not use the following code:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var x = 2;
* var y = 4;
* var myEval = eval;
* myEval("x + y");
* </pre>
* <p>You should not use <code>eval</code> to convert property names into properties.  Consider the following example. The <code>getFieldName(n)</code> function returns the name of the specified form element as a string. The first statement assigns the string value of the third form element to the variable <code>field</code>. The second statement uses <code>eval</code> to display the value of the form element.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var field = getFieldName(3);
* document.write("The field named ", field, " has value of ",
* eval(field + ".value"));
* </pre>
* <p>However <code>eval</code> is not necessary here. In fact, its use here is discouraged.  Instead, use the <a href="Core_JavaScript_1.5_Reference:Operators:Member_Operators" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Member Operators">member operators</a>, which are much faster:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var field = getFieldName(3);
* document.write("The field named ", field, " has value of ",
* field[value]);
* </pre>
* <h2> <span> Backward Compatibility </span></h2>
* <h3> <span> JavaScript 1.3 and earlier </span></h3>
* <p>You can use <code>eval</code> indirectly, although it is discouraged.
* </p>
* <h3> <span> JavaScript 1.1 </span></h3>
* <p><code>eval</code> is also a method of all objects.
* </p>
* <h2> <span> Examples </span></h2>
* <p>The following examples display output using <code>document.write</code>. In server-side JavaScript, you can display the same output by calling the <code>write</code> function instead of using <code>document.write</code>.
* </p>
* <h3> <span> Example: Using <code>eval</code> </span></h3>
* <p>In the following code, both of the statements containing <code>eval</code> return 42. The first evaluates the string "<code>x + y + 1</code>"; the second evaluates the string "<code>42</code>".
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var x = 2;
* var y = 39;
* var z = "42";
* eval("x + y + 1"); // returns 42
* eval(z);           // returns 42
* </pre>
* <h3> <span> Example: Using <code>eval</code> to evaluate a string of JavaScript statements </span></h3>
* <p>The following example uses <code>eval</code> to evaluate the string <code>str</code>. This string consists of JavaScript statements that open an Alert dialog box and assign <code>z</code> a value of 42 if <code>x</code> is five, and assigns 0 to <code>z</code> otherwise. When the second statement is executed, <code>eval</code> will cause these statements to be performed, and it will also evaluate the set of statements and return the value that is assigned to <code>z</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var str = "if (x == 5) {alert('z is 42'); z = 42;} else z = 0; ";
* document.write("&lt;P&gt;z is ", eval(str));
* </pre>
* <h2> <span> Return value </span></h2>
* <p><code>eval</code> returns the value of the last expression evaluated.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var str = "if ( a ) { 1+1; } else { 1+2; }"
* var a = true;
* var b = eval(str);  // returns 2
* alert("b is : " + b);
* a = false;
* b = eval(str);  // returns 3
* alert("b is : " + b);
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Operators:Member_Operators" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Member Operators">member operators</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function eval(string, object) {};

