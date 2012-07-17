/**
* <h2> <span> Summary </span></h2>
* <p>The value undefined.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Core Global Property</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* undefined
* </code>
* </p>
* <h2> <span> Description </span></h2>
* <p><code>undefined</code> is a property of the <i>global object</i>, i.e. it is a variable in global scope.
* </p><p>The initial value of <code>undefined</code> is the primitive value <code>undefined</code>.
* </p><p>A variable that has not been assigned a value is of type undefined. A method or statement also returns <code>undefined</code> if the variable that is being evaluated does not have an assigned value. A function returns <code>undefined</code> if a value was not <a href="Statements:return" shape="rect" title="Core JavaScript 1.5 Reference:Statements:return">returned</a>.
* </p><p>You can use <code>undefined</code> and the strict equality and inequality operators to determine whether a variable has a value. In the following code, the variable <code>x</code> is not defined, and the <code>if</code> statement evaluates to true.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var x;
* if (x === undefined) {
* // these statements execute
* }
* if (x !== undefined) {
* // these statements do not execute
* }
* </pre>
* <p>Note: The strict equality operator rather than the standard equality operator must be used here, because <code>x == undefined</code> also checks whether <code>x</code> is <code>null</code>, while strict equality doesn't. <code>null</code> is not equivalent to <code>undefined</code>. See <a href="Operators:Comparison_Operators" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Comparison Operators">comparison operators</a> for details.
* </p><p>Alternatively, <a href="Operators:Special_Operators:typeof_Operator" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Special Operators:typeof Operator">typeof</a> can be used:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var x;
* if (typeof x == 'undefined') {
* // these statements execute
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Undefined = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p>The value undefined.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Core Global Property</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* undefined
* </code>
* </p>
* <h2> <span> Description </span></h2>
* <p><code>undefined</code> is a property of the <i>global object</i>, i.e. it is a variable in global scope.
* </p><p>The initial value of <code>undefined</code> is the primitive value <code>undefined</code>.
* </p><p>A variable that has not been assigned a value is of type undefined. A method or statement also returns <code>undefined</code> if the variable that is being evaluated does not have an assigned value. A function returns <code>undefined</code> if a value was not <a href="Statements:return" shape="rect" title="Core JavaScript 1.5 Reference:Statements:return">returned</a>.
* </p><p>You can use <code>undefined</code> and the strict equality and inequality operators to determine whether a variable has a value. In the following code, the variable <code>x</code> is not defined, and the <code>if</code> statement evaluates to true.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var x;
* if (x === undefined) {
* // these statements execute
* }
* if (x !== undefined) {
* // these statements do not execute
* }
* </pre>
* <p>Note: The strict equality operator rather than the standard equality operator must be used here, because <code>x == undefined</code> also checks whether <code>x</code> is <code>null</code>, while strict equality doesn't. <code>null</code> is not equivalent to <code>undefined</code>. See <a href="Operators:Comparison_Operators" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Comparison Operators">comparison operators</a> for details.
* </p><p>Alternatively, <a href="Operators:Special_Operators:typeof_Operator" shape="rect" title="Core JavaScript 1.5 Reference:Operators:Special Operators:typeof Operator">typeof</a> can be used:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var x;
* if (typeof x == 'undefined') {
* // these statements execute
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function undefined() {};

