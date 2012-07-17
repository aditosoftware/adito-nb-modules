/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Parses a string argument and returns a floating point number.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* parseFloat(<i>string</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>string</code>Ê</dt><dd> A string that represents the value you want to parse.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>parseFloat</code> is a top-level function and is not associated with any object.
* </p><p><code>parseFloat</code> parses its argument, a string, and returns a floating point number. If it encounters a character other than a sign (+ or -), numeral (0-9), a decimal point, or an exponent, it returns the value up to that point and ignores that character and all succeeding characters. Leading and trailing spaces are allowed.
* </p><p>If the first character cannot be converted to a number, <code>parseFloat</code> returns <code>NaN</code>.
* </p><p>For arithmetic purposes, the <code>NaN</code> value is not a number in any radix. You can call the <code><a href="Core_JavaScript_1.5_Reference:Global_Functions:isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a></code> function to determine if the result of <code>parseFloat</code> is <code>NaN</code>. If <code>NaN</code> is passed on to arithmetic operations, the operation results will also be <code>NaN</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: <code>parseFloat</code> returning a number </span></h3>
* <p>The following examples all return 3.14:
* </p>
* <ul><li><code>parseFloat("3.14");</code>
* </li><li><code>parseFloat("314e-2");</code>
* </li><li><code>parseFloat("0.0314E+2");</code>
* </li><li><code>var x = "3.14";</code><code>parseFloat(x);</code>
* </li><li><code>parseFloat("3.14more non-digit characters");</code>
* </li></ul>
* <h3> <span> Example: <code>parseFloat</code> returning NaN </span></h3>
* <p>The following example returns <code>NaN</code>:
* </p>
* <ul><li><code>parseFloat("FF2");</code>
* </li></ul>
* <h2> <span> See Also </span></h2>
* <p><a href="isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a>,
* <a href="parseInt" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:parseInt">parseInt</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var ParseFloat = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Parses a string argument and returns a floating point number.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* parseFloat(<i>string</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>string</code>Ê</dt><dd> A string that represents the value you want to parse.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>parseFloat</code> is a top-level function and is not associated with any object.
* </p><p><code>parseFloat</code> parses its argument, a string, and returns a floating point number. If it encounters a character other than a sign (+ or -), numeral (0-9), a decimal point, or an exponent, it returns the value up to that point and ignores that character and all succeeding characters. Leading and trailing spaces are allowed.
* </p><p>If the first character cannot be converted to a number, <code>parseFloat</code> returns <code>NaN</code>.
* </p><p>For arithmetic purposes, the <code>NaN</code> value is not a number in any radix. You can call the <code><a href="Core_JavaScript_1.5_Reference:Global_Functions:isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a></code> function to determine if the result of <code>parseFloat</code> is <code>NaN</code>. If <code>NaN</code> is passed on to arithmetic operations, the operation results will also be <code>NaN</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: <code>parseFloat</code> returning a number </span></h3>
* <p>The following examples all return 3.14:
* </p>
* <ul><li><code>parseFloat("3.14");</code>
* </li><li><code>parseFloat("314e-2");</code>
* </li><li><code>parseFloat("0.0314E+2");</code>
* </li><li><code>var x = "3.14";</code><code>parseFloat(x);</code>
* </li><li><code>parseFloat("3.14more non-digit characters");</code>
* </li></ul>
* <h3> <span> Example: <code>parseFloat</code> returning NaN </span></h3>
* <p>The following example returns <code>NaN</code>:
* </p>
* <ul><li><code>parseFloat("FF2");</code>
* </li></ul>
* <h2> <span> See Also </span></h2>
* <p><a href="isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a>,
* <a href="parseInt" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:parseInt">parseInt</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function parseFloat(string) {};

