/**
* <h2> <span> Summary </span></h2>
* <p>Represents an error when a value is not of the expected type.
* </p>
* <h2> <span> Description </span></h2>
* <p>A TypeError is thrown when an operand or argument passed to a function is incompatible with the type expected by that operator or function.
* </p>
* <h2> <span> Properties </span></h2>
* <dl><dt style="font-weight:bold"> <a href="TypeError:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError:prototype">prototype</a>
* </dt><dd> Allows the addition of properties to an TypeError object.
* </dd></dl>
* <p><code>TypeError</code> instances will inherit from <code>TypeError.prototype</code>. For a list of properties and methods inherited by <code>TypeError</code> instances, see <a href="TypeError:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError:prototype">TypeError.prototype</a>.
* </p>
* <h2> <span> See also </span></h2>
* <ul><li><a href="Core_JavaScript_1.5_Reference:Global_Objects:Function:apply" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:apply">apply</a>
* </li><li><a href="Function:call" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:call">call</a>
* </li><li><a href="Error" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error">Error</a>
* </li></ul>
* 
* <div id="catlinks"><p><a href="http://developer.mozilla.org/en/docs/Special:Categories" shape="rect" title="Special:Categories">Category</a>: <span dir="ltr"><a href="http://developer.mozilla.org/en/docs/Category:NeedsContent" shape="rect" title="Category:NeedsContent">NeedsContent</a></span></p></div>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var TypeError = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a reference to the object's constructor.
* </p>
* <h2> <span> Description </span></h2>
* <p>Returns a reference to the <a href="Core_JavaScript_1.5_Reference:Global_Objects:TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError">TypeError</a> function that created the instance's prototype. The value of this property is a reference to the function itself, not a string containing the function's name. If this is desired, use the <a href="TypeError:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError:name">name</a> property or the non-standard <a href="Function:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:name">Function.prototype.name</a> property.
* </p><p>Note that both an instance's <code>constructor</code> and <code>name</code> properties can be modified.
* </p>
* <h2> <span> See also </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:TypeError:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError:name">name</a>
* </li><li> <a href="Function:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:name">Function.prototype.name</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
constructor: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>A name for the type of error.
* </p>
* <h2> <span> Description </span></h2>
* <p>By default, <a href="Core_JavaScript_1.5_Reference:Global_Functions:TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:TypeError">TypeError</a> instances are given the name "ReferenceError".
* </p>
* <h2> <span> See also </span></h2>
* <ul><li><a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">message</a>
* </li><li><a href="Error:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:toString">toString</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
name: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Represents the prototype for this class.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError">TypeError</a></td>
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
* <p>All <a href="Core_JavaScript_1.5_Reference:Global_Functions:TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:TypeError">TypeError</a> instances inherit from <code>TypeError.prototype</code>. You can use the prototype to add properties or methods to all instances.
* </p>
* <h2> <span> Properties </span></h2>
* <dl><dt style="font-weight:bold"> <a href="Core_JavaScript_1.5_Reference:Global_Objects:TypeError:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError:constructor">constructor</a>
* </dt><dd> Specifies the function that created an instance's prototype.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <a href="TypeError:name" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError:name">name</a>
* </dt><dd> Error name.
* </dd></dl>
* <div>Although <a href="http://developer.mozilla.org/en/docs/ECMA-262" shape="rect" title="ECMA-262">ECMA-262</a> specifies that <a href="TypeError" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:TypeError">TypeError</a> should provide its own <code>message</code> property, in <a href="http://developer.mozilla.org/en/docs/SpiderMonkey" shape="rect" title="SpiderMonkey">SpiderMonkey</a>, it inherits <a href="Error:message" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:message">Error.prototype.message</a>.</div>
* <h2> <span> See also </span></h2>
* <ul><li><a href="Core_JavaScript_1.5_Reference:Global_Objects:Error:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Error:prototype">Error.prototype</a>
* </li><li><a href="Function:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:prototype">Function.prototype</a>
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
prototype: undefined,
};

