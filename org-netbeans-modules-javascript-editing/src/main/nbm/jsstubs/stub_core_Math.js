/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Math&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Core JavaScript 1.5 Reference:Objects:Math</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A built-in object that has properties and methods for mathematical constants and functions. For example, the <code>Math</code> object's <a href="Core_JavaScript_1.5_Reference:Global_Objects:Math:PI" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:PI">PI</a> property has the value of pi.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Math</code> object is a top-level, predefined JavaScript object. You can automatically access it without using a constructor or calling a method.
* </p>
* <h2> <span> Description </span></h2>
* <p>All properties and methods of <code>Math</code> are static. You refer to the constant pi as <code>Math.PI</code> and you call the sine function as <code>Math.sin(x)</code>, where <code>x</code> is the method's argument. Constants are defined with the full precision of real numbers in JavaScript.
* </p><p>It is often convenient to use the <a href="Statements:with" shape="rect" title="Core JavaScript 1.5 Reference:Statements:with">with</a> statement when a section of code uses several <code>Math</code> constants and methods, so you don't have to type "Math" repeatedly. For example,
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* with (Math) {
* a = PI * r*r
* y = r*sin(theta)
* x = r*cos(theta)
* }
* </pre>
* <h2> <span> Properties </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Math:E" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:E">E</a>: Euler's constant and the base of natural logarithms, approximately 2.718.
* </p><p><a href="Math:LN2" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:LN2">LN2</a>: Natural logarithm of 2, approximately 0.693.
* </p><p><a href="Math:LN10" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:LN10">LN10</a>: Natural logarithm of 10, approximately 2.302.
* </p><p><a href="Math:LOG2E" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:LOG2E">LOG2E</a>: Base 2 logarithm of E, approximately 1.442.
* </p><p><a href="Math:LOG10E" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:LOG10E">LOG10E</a>: Base 10 logarithm of E, approximately 0.434.
* </p><p><a href="Math:PI" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:PI">PI</a>: Ratio of the circumference of a circle to its diameter, approximately 3.14159.
* </p><p><a href="Math:SQRT1_2" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:SQRT1 2">SQRT1_2</a>: Square root of 1/2; equivalently, 1 over the square root of 2, approximately 0.707.
* </p><p><a href="Math:SQRT2" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:SQRT2">SQRT2</a>: Square root of 2, approximately 1.414.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Math:abs" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:abs">abs</a>: Returns the absolute value of a number.
* </p><p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:acos">acos</a>: Returns the arccosine (in radians) of a number.
* </p><p><a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:asin">asin</a>: Returns the arcsine (in radians) of a number.
* </p><p><a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:atan">atan</a>: Returns the arctangent (in radians) of a number.
* </p><p><a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:atan2">atan2</a>: Returns the arctangent of the quotient of its arguments.
* </p><p><a href="Math:ceil" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:ceil">ceil</a>: Returns the smallest integer greater than or equal to a number.
* </p><p><a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:cos">cos</a>: Returns the cosine of a number.
* </p><p><a href="Math:exp" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:exp">exp</a>: Returns Enumber, where number is the argument, and E is Euler's constant, the base of the natural logarithms.
* </p><p><a href="Math:floor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:floor">floor</a>: Returns the largest integer less than or equal to a number.
* </p><p><a href="Math:log" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:log">log</a>: Returns the natural logarithm (base E) of a number.
* </p><p><a href="Math:max" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:max">max</a>: Returns the largest of zero or more numbers.
* </p><p><a href="Math:min" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:min">min</a>: Returns the smallest of zero or more numbers.
* </p><p><a href="Math:pow" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:pow">pow</a>: Returns base to the exponent power, that is, base exponent.
* </p><p><a href="Math:random" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:random">random</a>: Returns a pseudo-random number between 0 and 1.
* </p><p><a href="Math:round" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:round">round</a>: Returns the value of a number rounded to the nearest integer.
* </p><p><a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:sin">sin</a>: Returns the sine of a number.
* </p><p><a href="Math:sqrt" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:sqrt">sqrt</a>: Returns the square root of a number.
* </p><p><a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Math:tan">tan</a>: Returns the tangent of a number.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Math = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>Euler's constant and the base of natural logarithms, approximately 2.718.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>E</code> is a static property of <code>Math</code>, you always use it as <code>Math.E</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.E</code> </span></h3>
* <p>The following function returns Euler's constant:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getEuler() {
* return Math.E
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
E: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The natural logarithm of 10, approximately 2.302.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>LN10</code> is a static property of <code>Math</code>, you always use it as <code>Math.LN10</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.LN10</code> </span></h3>
* <p>The following function returns the natural log of 10:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getNatLog10() {
* return Math.LN10
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
LN10: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The natural logarithm of 2, approximately 0.693.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>LN2</code> is a static property of <code>Math</code>, you always use it as <code>Math.LN2</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.LN2</code> </span></h3>
* <p>The following function returns the natural log of 2:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getNatLog2() {
* return Math.LN2
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
LN2: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The base 10 logarithm of E (approximately 0.434).
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>LOG10E</code> is a static property of <code>Math</code>, you always use it as <code>Math.LOG10E</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.LOG10E</code> </span></h3>
* <p>The following function returns the base 10 logarithm of <code>E</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getLog10e() {
* return Math.LOG10E
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
LOG10E: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The base 2 logarithm of E (approximately 1.442).
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>LOG2E</code> is a static property of <code>Math</code>, you always use it as <code>Math.LOG2E</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.LOG2E</code> </span></h3>
* <p>The following function returns the base 2 logarithm of <code>E</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getLog2e() {
* return Math.LOG2E
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
LOG2E: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The ratio of the circumference of a circle to its diameter, approximately 3.14159.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>PI</code> is a static property of <code>Math</code>, you always use it as <code>Math.PI</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>PI</code> </span></h3>
* <p>The following function returns the value of pi:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getPi() {
* return Math.PI
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
PI: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The square root of 1/2; equivalently, 1 over the square root of 2, approximately 0.707.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>SQRT1_2</code> is a static property of <code>Math</code>, you always use it as <code>Math.SQRT1_2</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>SQRT1_2</code> </span></h3>
* <p>The following function returns 1 over the square root of 2:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getRoot1_2() {
* return Math.SQRT1_2
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
SQRT1_2: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>The square root of 2, approximately 1.414.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static, Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Because <code>SQRT2</code> is a static property of <code>Math</code>, you always use it as <code>Math.SQRT2</code>, rather than as a property of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.SQRT2</code> </span></h3>
* <p>The following function returns the square root of 2:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getRoot2() {
* return Math.SQRT2
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
SQRT2: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the absolute value of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.abs(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>abs</code> is a static method of <code>Math</code>, so you always use it as <code>Math.abs()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.abs</code> </span></h3>
* <p>The following function returns the absolute value of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getAbs(x) {
* return Math.abs(x)
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
abs: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the arccosine (in radians) of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.acos(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>acos</code> method returns a numeric value between 0 and pi radians for x between -1 and 1. If the value of <code>number</code> is outside this range, it returns <code>NaN</code>.
* </p><p><code>acos</code> is a static method of <code>Math</code>, so you always use it as <code>Math.acos()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.acos</code> </span></h3>
* <p>The following function returns the arccosine of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getAcos(x) {
* return Math.acos(x)
* }
* </pre>
* <p>If you pass -1 to <code>getAcos</code>, it returns 3.141592653589793; if you pass 2, it returns <code>NaN</code> because 2 is out of range.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:asin">asin</a>,
* <a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan">atan</a>,
* <a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan2">atan2</a>,
* <a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:cos">cos</a>,
* <a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:sin">sin</a>,
* <a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:tan">tan</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
acos: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the arcsine (in radians) of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.asin(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>asin</code> method returns a numeric value between -pi/2 and pi/2 radians for x between -1 and 1. If the value of <code>number</code> is outside this range, it returns <code>NaN</code>.
* </p><p><code>asin</code> is a static method of <code>Math</code>, so you always use it as <code>Math.asin()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.asin</code> </span></h3>
* <p>The following function returns the arcsine of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getAsin(x) {
* return Math.asin(x)
* }
* </pre>
* <p>If you pass <code>getAsin</code> the value 1, it returns 1.570796326794897 (pi/2); if you pass it the value 2, it returns <code>NaN</code> because 2 is out of range.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:acos">acos</a>,
* <a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan">atan</a>,
* <a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan2">atan2</a>,
* <a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:cos">cos</a>,
* <a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:sin">sin</a>,
* <a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:tan">tan</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
asin: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the arctangent (in radians) of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.atan(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>atan</code> method returns a numeric value between -pi/2 and pi/2 radians.
* </p><p><code>atan</code> is a static method of <code>Math</code>, so you always use it as <code>Math.atan()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.atan</code> </span></h3>
* <p>The following function returns the arctangent of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getAtan(x) {
* return Math.atan(x)
* }
* </pre>
* <p>If you pass <code>getAtan</code> the value 1, it returns 0.7853981633974483; if you pass it the value .5, it returns 0.4636476090008061.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:acos">acos</a>,
* <a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:asin">asin</a>,
* <a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan2">atan2</a>,
* <a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:cos">cos</a>,
* <a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:sin">sin</a>,
* <a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:tan">tan</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
atan: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the arctangent of the quotient of its arguments.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.atan2(<i>y</i>, <i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>y, x</code>Ê</dt><dd> Numbers.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>atan2</code> method returns a numeric value between -pi and pi representing the angle theta of an (x,y) point. This is the counterclockwise angle, measured in radians, between the positive X axis, and the point (<code>x,y</code>). Note that the arguments to this function pass the y-coordinate first and the x-coordinate second.
* </p><p><code>atan2</code> is passed separate <code>x</code> and <code>y</code> arguments, and <code>atan</code> is passed the ratio of those two arguments.
* </p><p>Because <code>atan2</code> is a static method of <code>Math</code>, you always use it as <code>Math.atan2()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.atan2</code> </span></h3>
* <p>The following function returns the angle of the polar coordinate:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getAtan2(y,x) {
* return Math.atan2(y,x)
* }
* </pre>
* <p>If you pass <code>getAtan2</code> the values (90,15), it returns 1.4056476493802699; if you pass it the values (15,90), it returns 0.16514867741462683.
* </p><p><code>Math.atan2( Â±0, -0 )</code> returns <code>Â±PI</code>.
* <code>Math.atan2( Â±0, +0 )</code> returns <code>Â±0</code>.
* <code>Math.atan2( Â±0, -x )</code> returns <code>Â±PI</code> for x &lt; 0.
* <code>Math.atan2( Â±0, x )</code> returns <code>Â±0</code> for x &gt; 0.
* <code>Math.atan2( y, Â±0 )</code> returns <code>-PI/2</code> for y &gt; 0.
* <code>Math.atan2( Â±y, -Infinity )</code> returns <code>Â±PI</code> for finite y &gt; 0.
* <code>Math.atan2( Â±y, +Infinity )</code> returns <code>Â±0</code> for finite y &gt; 0.
* <code>Math.atan2( Â±Infinity, +x )</code> returns <code>Â±PI/2</code> for finite x.
* <code>Math.atan2( Â±Infinity, -Infinity )</code> returns <code>Â±3*PI/4</code>.
* <code>Math.atan2( Â±Infinity, +Infinity )</code> returns <code>Â±PI/4</code>.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:acos">acos</a>,
* <a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:asin">asin</a>,
* <a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan">atan</a>,
* <a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:cos">cos</a>,
* <a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:sin">sin</a>,
* <a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:tan">tan</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
atan2: function(y, x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the smallest integer greater than or equal to a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.ceil(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Because <code>ceil</code> is a static method of <code>Math</code>, you always use it as <code>Math.ceil()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.ceil</code> </span></h3>
* <p>The following function returns the ceil value of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getCeil(x) {
* return Math.ceil(x)
* }
* </pre>
* <p>If you pass 45.95 to <code>getCeil</code>, it returns 46; if you pass -45.95, it returns -45.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:floor" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:floor">floor</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
ceil: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the cosine of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.cos(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>cos</code> method returns a numeric value between -1 and 1, which represents the cosine of the angle.
* </p><p>Because <code>cos</code> is a static method of <code>Math</code>, you always use it as <code>Math.cos()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.cos</code> </span></h3>
* <p>The following function returns the cosine of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getCos(x) {
* return Math.cos(x)
* }
* </pre>
* <p>If <code>x</code> equals 2*<code>Math.PI</code>, <code>getCos</code> returns 1; if <code>x</code> equals <code>Math.PI</code>, the <code>getCos</code> method returns -1.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:acos">acos</a>,
* <a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:asin">asin</a>,
* <a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan">atan</a>,
* <a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan2">atan2</a>,
* <a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:sin">sin</a>,
* <a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:tan">tan</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
cos: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns <code>E<sup>x</sup></code>, where <code>x</code> is the argument, and <code>E</code> is Euler's constant, the base of the natural logarithms.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.exp(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Because <code>exp</code> is a static method of <code>Math</code>, you always use it as <code>Math.exp()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.exp</code> </span></h3>
* <p>The following function returns the exponential value of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getExp(x) {
* return Math.exp(x)
* }
* </pre>
* <p>If you pass <code>getExp</code> the value 1, it returns 2.718281828459045.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:E" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:E">E</a>,
* <a href="Math:log" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:log">log</a>,
* <a href="Math:pow" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:pow">pow</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
exp: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the largest integer less than or equal to a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.floor(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Because <code>floor</code> is a static method of <code>Math</code>, you always use it as <code>Math.floor()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.floor</code> </span></h3>
* <p>The following function returns the floor value of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getFloor(x) {
* return Math.floor(x)
* }
* </pre>
* <p>If you pass 45.95 to <code>getFloor</code>, it returns 45; if you pass -45.95, it returns -46.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:ceil" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:ceil">ceil</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
floor: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the natural logarithm (base <code>E</code>) of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.log(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If the value of <code>number</code> is negative, the return value is always <code>NaN</code>.
* </p><p>Because <code>log</code> is a static method of <code>Math</code>, you always use it as <code>Math.log()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.log</code> </span></h3>
* <p>The following function returns the natural log of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getLog(x) {
* return Math.log(x)
* }
* </pre>
* <p>If you pass <code>getLog</code> the value 10, it returns 2.302585092994046; if you pass it the value 0, it returns <code>-Infinity</code>; if you pass it the value -1, it returns <code>NaN</code> because -1 is out of range.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:exp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:exp">exp</a>,
* <a href="Math:pow" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:pow">pow</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
log: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the largest of zero or more numbers.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.max([<i>value1</i>[,<i>value2</i>[, ...]]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>value1, value2, ...</code>Ê</dt><dd> Numbers.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Because <code>max</code> is a static method of <code>Math</code>, you always use it as <code>Math.max()</code>, rather than as a method of a <code>Math</code> object you created.
* </p><p>If no arguments are given, the results is <code>-<a href="Core_JavaScript_1.5_Reference:Global_Properties:Infinity" shape="rect" title="Core JavaScript 1.5 Reference:Global Properties:Infinity">Infinity</a></code>
* </p><p>If at least one of arguments cannot be converted to a number, the result is <a href="NaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Properties:NaN">NaN</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.max</code> </span></h3>
* <p>The following function evaluates the variables <code>x</code> and <code>y</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getMax(x,y) {
* return Math.max(x,y)
* }
* </pre>
* <p>If you pass <code>getMax</code> the values 10 and 20, it returns 20; if you pass it the values -10 and -20, it returns -10.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Math:min" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:min">Math.min</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
max: function(value1,value2) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the smallest of zero or more numbers.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.min([<i>value1</i>[,<i>value2</i>[, ...]]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>value1, value2, ...</code>Ê</dt><dd> Numbers.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Because <code>min</code> is a static method of <code>Math</code>, you always use it as <code>Math.min()</code>, rather than as a method of a <code>Math</code> object you created.
* </p><p>If no arguments are given, the result is <code><a href="Core_JavaScript_1.5_Reference:Global_Properties:Infinity" shape="rect" title="Core JavaScript 1.5 Reference:Global Properties:Infinity">Infinity</a></code>.
* </p><p>If at least one of arguments cannot be converted to a number, the result is <code><a href="NaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Properties:NaN">NaN</a></code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.min</code> </span></h3>
* <p>The following function evaluates the variables <code>x</code> and <code>y</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getMin(x,y) {
* return Math.min(x,y)
* }
* </pre>
* <p>If you pass <code>getMin</code> the values 10 and 20, it returns 10; if you pass it the values -10 and -20, it returns -20.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Math:max" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:max">Math.max</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
min: function(value1,value2) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns <code>base</code> to the <code>exponent</code> power, that is, <code>base<sup>exponent</sup></code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.pow(<i>base</i>,<i>exponent</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>base</code>Ê</dt><dd> The base number.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>exponents</code>Ê</dt><dd> The exponent to which to raise <code>base</code>.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Because <code>pow</code> is a static method of <code>Math</code>, you always use it as <code>Math.pow()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.pow</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function raisePower(x,y) {
* return Math.pow(x,y)
* }
* </pre>
* <p>If <code>x</code> is 7 and <code>y</code> is 2, raisePower returns 49 (7 to the power of 2).
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:exp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:exp">exp</a>,
* <a href="Math:log" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:log">log</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
pow: function(base,exponent) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a pseudo-random number in the range [0,1) Ñ that is, between 0 (inclusive) and 1 (exclusive). The random number generator is seeded from the current time, as in Java.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0: Unix only
* <p>JavaScript 1.1, NES 2.0: all platforms
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var randomNumber = Math.random();
* </pre>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Notes </span></h2>
* <p>Note that <a href="Core_JavaScript_1.5_Reference:Objects:Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a> is not a constructor, so you always invoke <code>random</code> as <code>Math.random()</code> and never create instances of <code>Math</code>. Some may thus call the method "static" due to analogy with <a href="Core_JavaScript_1.5_Guide:Class-Based_vs._Prototype-Based_Languages" shape="rect" title="Core JavaScript 1.5 Guide:Class-Based vs. Prototype-Based Languages">class-based OO languages</a> like Java.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.random</code> </span></h3>
* <p>Note that as numbers in JavaScript are IEEE 754 floating point numbers with round-to-nearest-even behavior, these ranges, excluding the one for <code>Math.random()</code> itself, aren't exact, and depending on the bounds it's possible in extremely rare cases (on the order of 1 in 2<sup>62</sup>) to calculate the usually-excluded upper bound.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// Returns a random number between 0 (inclusive) and 1 (exclusive)
* function getRandom()
* {
* return Math.random();
* }
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// Returns a random number between min and max
* function getRandomArbitary(min, max)
* {
* return Math.random() * (max - min) + min;
* }
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// Returns a random integer between min and max
* // Using Math.round() will give you a non-uniform distribution!
* function getRandomInt(min, max)
* {
* return Math.floor(Math.random() * (max - min + 1)) + min;
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
random: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the value of a number rounded to the nearest integer.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.round(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If the fractional portion of <code>number</code> is .5 or greater, the argument is rounded to the next higher integer. If the fractional portion of <code>number</code> is less than .5, the argument is rounded to the next lower integer.
* </p><p>Because <code>round</code> is a static method of <code>Math</code>, you always use it as <code>Math.round()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.round</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* //Returns the value 20
* x=Math.round(20.49)
* 
* //Returns the value 21
* x=Math.round(20.5)
* 
* //Returns the value -20
* x=Math.round(-20.5)
* 
* //Returns the value -21
* x=Math.round(-20.51)
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
round: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the sine of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.sin(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>sin</code> method returns a numeric value between -1 and 1, which represents the sine of the argument.
* </p><p>Because <code>sin</code> is a static method of <code>Math</code>, you always use it as <code>Math.sin()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.sin</code> </span></h3>
* <p>The following function returns the sine of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getSine(x) {
* return Math.sin(x)
* }
* </pre>
* <p>If you pass <code>getSine</code> the value <code>Math.PI/2</code>, it returns 1.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:acos">acos</a>,
* <a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:asin">asin</a>,
* <a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan">atan</a>,
* <a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan2">atan2</a>,
* <a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:cos">cos</a>,
* <a href="Math:tan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:tan">tan</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
sin: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the square root of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.sqrt(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If the value of <code>number</code> is negative, <code>sqrt</code> returns <code>NaN</code>.
* </p><p>Because <code>sqrt</code> is a static method of <code>Math</code>, you always use it as <code>Math.sqrt()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.sqrt</code> </span></h3>
* <p>The following function returns the square root of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getRoot(x) {
* return Math.sqrt(x)
* }
* </pre>
* <p>If you pass <code>getRoot</code> the value 9, it returns 3; if you pass it the value 2, it returns 1.414213562373095.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
sqrt: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the tangent of a number.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Math" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math">Math</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Math.tan(<i>x</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>x</code>Ê</dt><dd> A number representing an angle in radians.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>tan</code> method returns a numeric value that represents the tangent of the angle.
* </p><p>Because <code>tan</code> is a static method of <code>Math</code>, you always use it as <code>Math.tan()</code>, rather than as a method of a <code>Math</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Math.tan</code> </span></h3>
* <p>The following function returns the tangent of the variable <code>x</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getTan(x) {
* return Math.tan(x)
* }
* </pre>
* <p>Because the <code>Math.tan()</code> function accepts radians, but it is often easier to work with degrees, the following function accepts a value in degrees, converts it to radians and returns the tangent.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function getTanDeg(deg) {
* var rad = deg * Math.PI/180;
* return Math.tan(rad)
* }
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Math:acos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:acos">acos</a>,
* <a href="Math:asin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:asin">asin</a>,
* <a href="Math:atan" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan">atan</a>,
* <a href="Math:atan2" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:atan2">atan2</a>,
* <a href="Math:cos" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:cos">cos</a>,
* <a href="Math:sin" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Math:sin">sin</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
tan: function(x) {
  // This is just a stub for a builtin native JavaScript object.
},
};

