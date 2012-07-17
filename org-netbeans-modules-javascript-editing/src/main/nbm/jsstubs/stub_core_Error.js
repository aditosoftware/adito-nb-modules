/**
* <h2> <span> Summary </span></h2>
* <p>Represents a runtime or user-created error.
* </p>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Error()
* new Error(<i>message</i>)
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>message</code>Ê</dt><dd> Error message.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Runtime errors result in a new <code>Error</code> object being created and thrown.
* </p><p>Besides the generic <code>Error</code>, there are six other core error types in JavaScript:
* </p>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:EvalError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:EvalError">EvalError</a>
* </dt><dd> Raised when an error occurs executing code in <code>eval()</code>
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="RangeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RangeError">RangeError</a>
* </dt><dd> Raised when a numeric variable or parameter is outside of its valid range
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="ReferenceError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:ReferenceError">ReferenceError</a>
* </dt><dd> Raised when de-referencing an invalid reference
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="SyntaxError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:SyntaxError">SyntaxError</a>
* </dt><dd> Raised when a syntax error occurs while parsing code in <code>eval()</code>
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError">TypeError</a>
* </dt><dd> Raised when a variable or parameter is not a valid type
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="URIError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:URIError">URIError</a>
* </dt><dd> Raised when <code>encodeURI()</code> or <code>decodeURI()</code> are passed invalid parameters
* </dd></dl>
* <h2> <span> Properties </span></h2>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">prototype</a>
* </dt><dd> Allows the addition of properties to an <code>Error</code> object.
* </dd></dl>
* <p><code>Error</code> instances will inherit from <code>Error.prototype</code>. For a list of properties and methods inherited by <code>Error</code> instances, see <a href="Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">Error.prototype</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Throwing a generic error </span></h3>
* <p>Usually you create an Error object with the intention of raising it using the <a href="Statements:throw" shape="rect" title="Core JavaScript 1.5 Reference:Statements:throw">throw</a> keyword. You can handle the error using the <a href="Statements:try...catch" shape="rect" title="Core JavaScript 1.5 Reference:Statements:try...catch">try...catch</a> construct:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">try {
* throw new Error("Whoops!");
* } catch (e) {
* alert(e.name + ": " + e.message);
* }
* </pre>
* <h3> <span> Example: Handling a specific error </span></h3>
* <p>You can choose to handle only specific error types by testing the error type with the error's <a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:constructor">constructor</a> property or, if you're writing for modern JavaScript engines, <a href="Operators:Special_Operators:instanceof_Operator" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Special Operators:instanceof Operator">instanceof</a> keyword:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">try {
* foo.bar();
* } catch (e) {
* if (e instanceof EvalError) {
* alert(e.name + ": " + e.message);
* } else if (e instanceof RangeError) {
* alert(e.name + ": " + e.message);
* }
* // ... etc
* }
* </pre>
* <h2> <span> See also </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Statements:throw" shape="rect" title="Core JavaScript 1.5 Reference:Statements:throw">throw</a>
* </li><li> <a href="Statements:try...catch" shape="rect" title="Core JavaScript 1.5 Reference:Statements:try...catch">try...catch</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Error = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a reference to the object's constructor.
* </p>
* <h2> <span> Description </span></h2>
* <p>Returns a reference to the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error">Error</a> function that created the instance's prototype. The value of this property is a reference to the function itself, not a string containing the function's name. If this is desired, use the <a href="Error:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:name">name</a> property or the non-standard <a href="Function:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:name">Function.prototype.name</a> property.
* </p><p>Note that both an instance's <code>constructor</code> and <code>name</code> properties can be modified, although it is less likely that an Error's <code>constructor</code> will be redefined than it is for it to be renamed.
* </p>
* <h2> <span> See also </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:URIError:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:URIError:name">name</a>
* </li><li> <a href="Function:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:name">Function.prototype.name</a>
* </li><li> <a href="Object:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:constructor">Object.prototype.constructor</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
constructor: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>A human-readable description of the error.
* </p>
* <h2> <span> Description </span></h2>
* <p>This property contains a brief description of the error if one is available or has been set. <a href="http://developer.mozilla.org/en/docs/SpiderMonkey" shape="rect" title="SpiderMonkey">SpiderMonkey</a> makes extensive use of the <code>message</code> property for exceptions. The <code>message</code> property combined with the <code><a href="Error:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:name">name</a></code> property is used by the <code><a href="Error:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toString">Error.prototype.toString</a></code> method to create a string representation of the Error.
* </p><p>By default, the <code>message</code> property is an empty string, but this behavior can be overridden for an instance by specifying a message as the first argument to the <a href="Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:Error">Error constructor</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Throwing a custom error </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var e = new Error("Could not parse input"); // e.message is "Could not parse input"
* throw e;
* </pre>
* <h2> <span> See also </span></h2>
* <ul><li><a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:name">name</a>
* </li><li><a href="Error:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toString">toString</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
message: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>A name for the type of error.
* </p>
* <h2> <span> Description </span></h2>
* <p>By default, <a href="Core_JavaScript_1.5_Reference:Global_Functions:Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:Error">Error</a> instances are given the name "Error". The <code>name</code> property, in addition to the <code><a href="Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">message</a></code> property, is used by the <code><a href="Error:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toString">Error.prototype.toString</a></code> method to create a string representation of the error.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Throwing a custom error </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var e = new Error("Malformed input"); // e.name is "Error"
* e.name = "ParseError";                // e.toString() would return
* throw e;                              // "ParseError: Malformed input"
* </pre>
* <h2> <span> See also </span></h2>
* <ul><li><a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">message</a>
* </li><li><a href="Error:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toString">toString</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
name: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Represents the prototype for this class.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error">Error</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>All <a href="Core_JavaScript_1.5_Reference:Global_Functions:Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:Error">Error</a> instances inherit from <code>Error.prototype</code>. You can use the prototype to add properties or methods to all instances.
* </p>
* <h2> <span> Properties </span></h2>
* <h3> <span> Standard properties </span></h3>
* <dl><dt style="font-weight:bold"> <a href="Error:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:constructor">constructor</a>
* </dt><dd> Specifies the function that created an instance's prototype.
* </dd><dt style="font-weight:bold"> <a href="Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">message</a>
* </dt><dd> Error message.
* </dd><dt style="font-weight:bold"> <a href="Error:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:name">name</a>
* </dt><dd> Error name.
* </dd></dl>
* <h3> <span> Vendor-specific extensions </span></h3>
* <div style="border: 1px solid #FFB752; background-color: #FEE3BC; font-weight: bold; text-align: center; padding: 0px 10px 0px 10px; margin: 10px 0px 10px 0px;"><p style="margin: 4px 0px 4px 0px;">Non-standard</p></div>
* <h4> <span> Internet Explorer </span></h4>
* <dl><dt style="font-weight:bold"> <a href="http://developer.mozilla.org/en/docs/index.php?title=Error:description&amp;action=edit" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:description">description</a>
* </dt><dd> Error description. Similar to <a href="Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">message</a>.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="http://developer.mozilla.org/en/docs/index.php?title=Error:number&amp;action=edit" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:number">number</a>
* </dt><dd> Error number.
* </dd></dl>
* <h4> <span> Mozilla </span></h4>
* <dl><dt style="font-weight:bold"> <a href="http://developer.mozilla.org/en/docs/index.php?title=JCore_JavaScript_1.5_Reference:Global_Objects:Error:fileName&amp;action=edit" shape="rect" title="JCore JavaScript 1.5 Reference:Global Objects:Error:fileName">fileName</a>
* </dt><dd> Path to file that raised this error.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="http://developer.mozilla.org/en/docs/index.php?title=Error:.lineNumber&amp;action=edit" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:.lineNumber">lineNumber</a>
* </dt><dd> Line number in file that raised this error.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="http://developer.mozilla.org/en/docs/index.php?title=Error:stack&amp;action=edit" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:stack">stack</a>
* </dt><dd> Stack trace.
* </dd></dl>
* <h2> <span> Methods </span></h2>
* <dl><dt style="font-weight:bold"> <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Global_Objects:Error:toSource&amp;action=edit" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toSource">toSource</a>
* </dt><dd> <span style="border: 1px solid #FFD599; background-color: #FFEFD9; font-size: 9px; vertical-align: text-top;">Non-standard</span>
* </dd><dd> Returns an object literal representing the specified Error object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="Error:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toString">toString</a>
* </dt><dd> Returns a string representing the specified object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </dd></dl>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Function:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:prototype">Function.prototype</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
prototype: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a string representing the specified Error object
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code><i>error</i>.toString()</code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <a href="Core_JavaScript_1.5_Reference:Global_Objects:Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error">Error</a> object overrides the <code><a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.prototype.toString</a></code> method inherited by all objects. According to <a href="http://developer.mozilla.org/en/docs/ECMA-262" shape="rect" title="ECMA-262">ECMA-262</a>, implementations are free to decide the behavior of this method. <a href="http://developer.mozilla.org/en/docs/SpiderMonkey" shape="rect" title="SpiderMonkey">SpiderMonkey</a> joins string representations of the <code><a href="Error:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:name">name</a></code> and <code><a href="Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">message</a></code> properties with a colon and a space separating the two. If the string representation of either of these two properties is an empty string, this method simply returns the string representation of the property that has a non-zero length. If both properties' string representations are empty strings, this method returns an empty string.
* </p><p>Note that when creating a string representation of the <code>name</code> and <code>message</code> properties, this method does not invoke those properties' <code>toString</code> methods. If the value in either of these properties is not already a string, this method will behave as if that property contained an empty string.
* </p>
* <h2> <span> Example </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var e = new Error("fatal error");
* e.toString(); // returns "Error: fatal error"
* 
* e.name = undefined;
* e.toString(); // returns "fatal error"
* 
* e.message = undefined;
* e.toString(); // returns ""
* 
* e.name = "Error";
* e.toString(); // returns "Error"
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="http://developer.mozilla.org/en/docs/index.php?title=Error:toSource&amp;action=edit" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toSource">toSource</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
};
/**
* <h2> <span> Summary </span></h2>
* <p>Represents a runtime or user-created error.
* </p>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Error()
* new Error(<i>message</i>)
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>message</code>Ê</dt><dd> Error message.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Runtime errors result in a new <code>Error</code> object being created and thrown.
* </p><p>Besides the generic <code>Error</code>, there are six other core error types in JavaScript:
* </p>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:EvalError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:EvalError">EvalError</a>
* </dt><dd> Raised when an error occurs executing code in <code>eval()</code>
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="RangeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RangeError">RangeError</a>
* </dt><dd> Raised when a numeric variable or parameter is outside of its valid range
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="ReferenceError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:ReferenceError">ReferenceError</a>
* </dt><dd> Raised when de-referencing an invalid reference
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="SyntaxError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:SyntaxError">SyntaxError</a>
* </dt><dd> Raised when a syntax error occurs while parsing code in <code>eval()</code>
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError">TypeError</a>
* </dt><dd> Raised when a variable or parameter is not a valid type
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="URIError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:URIError">URIError</a>
* </dt><dd> Raised when <code>encodeURI()</code> or <code>decodeURI()</code> are passed invalid parameters
* </dd></dl>
* <h2> <span> Properties </span></h2>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">prototype</a>
* </dt><dd> Allows the addition of properties to an <code>Error</code> object.
* </dd></dl>
* <p><code>Error</code> instances will inherit from <code>Error.prototype</code>. For a list of properties and methods inherited by <code>Error</code> instances, see <a href="Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">Error.prototype</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Throwing a generic error </span></h3>
* <p>Usually you create an Error object with the intention of raising it using the <a href="Statements:throw" shape="rect" title="Core JavaScript 1.5 Reference:Statements:throw">throw</a> keyword. You can handle the error using the <a href="Statements:try...catch" shape="rect" title="Core JavaScript 1.5 Reference:Statements:try...catch">try...catch</a> construct:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">try {
* throw new Error("Whoops!");
* } catch (e) {
* alert(e.name + ": " + e.message);
* }
* </pre>
* <h3> <span> Example: Handling a specific error </span></h3>
* <p>You can choose to handle only specific error types by testing the error type with the error's <a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:constructor">constructor</a> property or, if you're writing for modern JavaScript engines, <a href="Operators:Special_Operators:instanceof_Operator" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Special Operators:instanceof Operator">instanceof</a> keyword:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">try {
* foo.bar();
* } catch (e) {
* if (e instanceof EvalError) {
* alert(e.name + ": " + e.message);
* } else if (e instanceof RangeError) {
* alert(e.name + ": " + e.message);
* }
* // ... etc
* }
* </pre>
* <h2> <span> See also </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Statements:throw" shape="rect" title="Core JavaScript 1.5 Reference:Statements:throw">throw</a>
* </li><li> <a href="Statements:try...catch" shape="rect" title="Core JavaScript 1.5 Reference:Statements:try...catch">try...catch</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Error(message) {};
/**
* <h2> <span> Summary </span></h2>
* <p>Represents a runtime or user-created error.
* </p>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Error()
* new Error(<i>message</i>)
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>message</code>Ê</dt><dd> Error message.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Runtime errors result in a new <code>Error</code> object being created and thrown.
* </p><p>Besides the generic <code>Error</code>, there are six other core error types in JavaScript:
* </p>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:EvalError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:EvalError">EvalError</a>
* </dt><dd> Raised when an error occurs executing code in <code>eval()</code>
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="RangeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RangeError">RangeError</a>
* </dt><dd> Raised when a numeric variable or parameter is outside of its valid range
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="ReferenceError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:ReferenceError">ReferenceError</a>
* </dt><dd> Raised when de-referencing an invalid reference
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="SyntaxError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:SyntaxError">SyntaxError</a>
* </dt><dd> Raised when a syntax error occurs while parsing code in <code>eval()</code>
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError">TypeError</a>
* </dt><dd> Raised when a variable or parameter is not a valid type
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="URIError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:URIError">URIError</a>
* </dt><dd> Raised when <code>encodeURI()</code> or <code>decodeURI()</code> are passed invalid parameters
* </dd></dl>
* <h2> <span> Properties </span></h2>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">prototype</a>
* </dt><dd> Allows the addition of properties to an <code>Error</code> object.
* </dd></dl>
* <p><code>Error</code> instances will inherit from <code>Error.prototype</code>. For a list of properties and methods inherited by <code>Error</code> instances, see <a href="Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">Error.prototype</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Throwing a generic error </span></h3>
* <p>Usually you create an Error object with the intention of raising it using the <a href="Statements:throw" shape="rect" title="Core JavaScript 1.5 Reference:Statements:throw">throw</a> keyword. You can handle the error using the <a href="Statements:try...catch" shape="rect" title="Core JavaScript 1.5 Reference:Statements:try...catch">try...catch</a> construct:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">try {
* throw new Error("Whoops!");
* } catch (e) {
* alert(e.name + ": " + e.message);
* }
* </pre>
* <h3> <span> Example: Handling a specific error </span></h3>
* <p>You can choose to handle only specific error types by testing the error type with the error's <a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:constructor">constructor</a> property or, if you're writing for modern JavaScript engines, <a href="Operators:Special_Operators:instanceof_Operator" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Special Operators:instanceof Operator">instanceof</a> keyword:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">try {
* foo.bar();
* } catch (e) {
* if (e instanceof EvalError) {
* alert(e.name + ": " + e.message);
* } else if (e instanceof RangeError) {
* alert(e.name + ": " + e.message);
* }
* // ... etc
* }
* </pre>
* <h2> <span> See also </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Statements:throw" shape="rect" title="Core JavaScript 1.5 Reference:Statements:throw">throw</a>
* </li><li> <a href="Statements:try...catch" shape="rect" title="Core JavaScript 1.5 Reference:Statements:try...catch">try...catch</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Error() {};

