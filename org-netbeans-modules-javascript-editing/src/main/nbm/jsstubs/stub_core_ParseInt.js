/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Parses a string argument and returns an integer of the specified radix or base.
* </p>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var intValue = parseInt(<i>string</i>[, <i>radix</i>]);
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>string</code>Ê</dt><dd> A string that represents the value you want to parse.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>radix</code>Ê</dt><dd> An integer that represents the radix of the above mentioned string.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>parseInt</code> is a top-level function and is not associated with any object.
* </p><p>The <code>parseInt</code> function parses its first argument, a string, and attempts to return an integer of the specified radix (base). For example, a radix of 10 indicates to convert to a decimal number, 8 octal, 16 hexadecimal, and so on. For radixes above 10, the letters of the alphabet indicate numerals greater than 9. For example, for hexadecimal numbers (base 16), A through F are used.
* </p><p>If <code>parseInt</code> encounters a character that is not a numeral in the specified radix, it ignores it and all succeeding characters and returns the integer value parsed up to that point. <code>parseInt</code> truncates numbers to integer values. Leading and trailing spaces are allowed.
* </p><p>If the radix is not specified or is specified as 0, JavaScript assumes the following:
* </p>
* <ul><li> If the input <code>string</code> begins with "0x", the radix is 16 (hexadecimal).
* </li><li> If the input <code>string</code> begins with "0", the radix is eight (octal). This feature is deprecated.
* </li><li> If the input <code>string</code> begins with any other value, the radix is 10 (decimal).
* </li></ul>
* <p>If the first character cannot be converted to a number, <code>parseInt</code> returns <code>NaN</code>.
* </p><p>For arithmetic purposes, the <code>NaN</code> value is not a number in any radix. You can call the <code><a href="Core_JavaScript_1.5_Reference:Global_Functions:isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a></code> function to determine if the result of <code>parseInt</code> is <code>NaN</code>. If <code>NaN</code> is passed on to arithmetic operations, the operation results will also be <code>NaN</code>.
* </p><p>To convert number to its string literal in a particular radix use <code>intValue.toString(radix)</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>parseInt</code> </span></h3>
* <p>The following examples all return 15:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">parseInt("F", 16);
* parseInt("17", 8);
* parseInt("15", 10);
* parseInt(15.99, 10);
* parseInt("FXX123", 16);
* parseInt("1111", 2);
* parseInt("15*3", 10);
* parseInt("12", 13);
* </pre>
* <p>The following examples all return <code>NaN</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">parseInt("Hello", 8); // Not a number at all
* parseInt("546", 2);   // Digits are not valid for binary representations
* </pre>
* <p>Even though the radix is specified differently, the following examples all return 17 because the input <code>string</code> begins with "<code>0x</code>".
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">parseInt("0x11", 16);
* parseInt("0x11", 0);
* parseInt("0x11");
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Functions:isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a>,
* <a href="parseFloat" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:parseFloat">parseFloat</a>,
* <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a>,
* <a href="Number:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Number:toString">Number.toString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var ParseInt = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Parses a string argument and returns an integer of the specified radix or base.
* </p>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var intValue = parseInt(<i>string</i>[, <i>radix</i>]);
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>string</code>Ê</dt><dd> A string that represents the value you want to parse.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>radix</code>Ê</dt><dd> An integer that represents the radix of the above mentioned string.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>parseInt</code> is a top-level function and is not associated with any object.
* </p><p>The <code>parseInt</code> function parses its first argument, a string, and attempts to return an integer of the specified radix (base). For example, a radix of 10 indicates to convert to a decimal number, 8 octal, 16 hexadecimal, and so on. For radixes above 10, the letters of the alphabet indicate numerals greater than 9. For example, for hexadecimal numbers (base 16), A through F are used.
* </p><p>If <code>parseInt</code> encounters a character that is not a numeral in the specified radix, it ignores it and all succeeding characters and returns the integer value parsed up to that point. <code>parseInt</code> truncates numbers to integer values. Leading and trailing spaces are allowed.
* </p><p>If the radix is not specified or is specified as 0, JavaScript assumes the following:
* </p>
* <ul><li> If the input <code>string</code> begins with "0x", the radix is 16 (hexadecimal).
* </li><li> If the input <code>string</code> begins with "0", the radix is eight (octal). This feature is deprecated.
* </li><li> If the input <code>string</code> begins with any other value, the radix is 10 (decimal).
* </li></ul>
* <p>If the first character cannot be converted to a number, <code>parseInt</code> returns <code>NaN</code>.
* </p><p>For arithmetic purposes, the <code>NaN</code> value is not a number in any radix. You can call the <code><a href="Core_JavaScript_1.5_Reference:Global_Functions:isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a></code> function to determine if the result of <code>parseInt</code> is <code>NaN</code>. If <code>NaN</code> is passed on to arithmetic operations, the operation results will also be <code>NaN</code>.
* </p><p>To convert number to its string literal in a particular radix use <code>intValue.toString(radix)</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>parseInt</code> </span></h3>
* <p>The following examples all return 15:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">parseInt("F", 16);
* parseInt("17", 8);
* parseInt("15", 10);
* parseInt(15.99, 10);
* parseInt("FXX123", 16);
* parseInt("1111", 2);
* parseInt("15*3", 10);
* parseInt("12", 13);
* </pre>
* <p>The following examples all return <code>NaN</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">parseInt("Hello", 8); // Not a number at all
* parseInt("546", 2);   // Digits are not valid for binary representations
* </pre>
* <p>Even though the radix is specified differently, the following examples all return 17 because the input <code>string</code> begins with "<code>0x</code>".
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">parseInt("0x11", 16);
* parseInt("0x11", 0);
* parseInt("0x11");
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Functions:isNaN" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:isNaN">isNaN</a>,
* <a href="parseFloat" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:parseFloat">parseFloat</a>,
* <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a>,
* <a href="Number:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Number:toString">Number.toString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function parseInt(string, radix) {};

