/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>The <code>Boolean</code> object is an object wrapper for a boolean value.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Boolean</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Boolean(<i>value</i>)
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>value</code>Ê</dt><dd> The initial value of the <code>Boolean</code> object. The value is converted to a <code>boolean</code> value, if necessary. If value is omitted or is 0, -0, null, false, <code>NaN</code>, undefined, or the empty string (""), the object has an initial value of false. All other values, including any object or the string "<code>false</code>", create an object with an initial value of true.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Do not confuse the primitive Boolean values true and false with the true and false values of the Boolean object.
* </p><p>Any object whose value is not <code>undefined</code> or <code>null</code>, including a Boolean object whose value is false, evaluates to true when passed to a conditional statement. For example, the condition in the following if statement evaluates to true:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = new Boolean(false);
* if (x) //the condition is true
* </pre>
* <p>This behavior does not apply to Boolean primitives. For example, the condition in the following if statement evaluates to <code>false</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = false;
* if (x) //the condition is false
* </pre>
* <p>Do not use a <code>Boolean</code> object to convert a non-boolean value to a boolean value. Instead, use Boolean as a function to perform this task:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = Boolean(expression);     //preferred
* x = new Boolean(expression); //don't use
* </pre>
* <p>If you specify any object, including a Boolean object whose value is false, as the initial value of a Boolean object, the new Boolean object has a value of true.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">myFalse = new Boolean(false);   // initial value of false
* g = new Boolean(myFalse);       //initial value of true
* myString = new String("Hello"); // string object
* s = new Boolean(myString);      //initial value of true
* </pre>
* <p>Do not use a Boolean object in place of a Boolean primitive.
* </p>
* <h2> <span> Properties </span></h2>
* <p><a href="Boolean:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </p><p><a href="Boolean:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:prototype">prototype</a>: Defines a property that is shared by all Boolean objects.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Boolean:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:toSource">toSource</a>: Returns an object literal representing the specified Boolean object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </p><p><a href="Boolean:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:toString">toString</a>: Returns a string representing the specified object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </p><p><a href="Boolean:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:valueOf">valueOf</a>: Returns the primitive value of a Boolean object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </p><p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Creating <code>Boolean</code> objects with an initial value of false </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">bNoParam = new Boolean();
* bZero = new Boolean(0);
* bNull = new Boolean(null);
* bEmptyString = new Boolean("");
* bfalse = new Boolean(false);
* </pre>
* <h3> <span> Creating <code>Boolean</code> objects with an initial value of true </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">btrue = new Boolean(true);
* btrueString = new Boolean("true");
* bfalseString = new Boolean("false");
* bSuLin = new Boolean("Su Lin");
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Boolean = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a reference to the <a href="Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a> function that created the instance's prototype. Note that the value of this property is a reference to the function itself, not a string containing the function's name.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a></td>
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
* <p>See <a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:constructor">Object.constructor</a>.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
constructor: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Represents the prototype for this class.  You can use the prototype to add properties or methods to all instances of this class. For information on prototypes, see <a href="Function:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:prototype">Function.prototype</a>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a></td>
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
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
prototype: undefined,
/**
* <div style="border: 1px solid #FFB752; background-color: #FEE3BC; font-weight: bold; text-align: center; padding: 0px 10px 0px 10px; margin: 10px 0px 10px 0px;"><p style="margin: 4px 0px 4px 0px;">Non-standard</p></div>
* 
* <h2> <span> Summary </span></h2>
* <p>Returns a string representing the source code of the object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code><i>boolean</i>.toSource()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>toSource</code> method returns the following values:
* </p>
* <ul><li> For the built-in <code>Boolean</code> object, <code>toSource</code> returns the following string indicating that the source code is not available:
* </li></ul>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">function Boolean() {
* [native code]
* }
* </pre>
* <ul><li> For instances of <code>Boolean</code>, <code>toSource</code> returns a string representing the source code.
* </li></ul>
* <p>This method is usually called internally by JavaScript and not explicitly in code.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
toSource: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a string representing the specified Boolean object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a></td>
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
* <h2> <span> Syntax </span></h2>
* <p><code>
* toString()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <a href="Core_JavaScript_1.5_Reference:Global_Objects:Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a> object overrides the <code>toString</code> method of the <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a> object; it does not inherit <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a>. For Boolean objects, the <code>toString</code> method returns a string representation of the object.
* </p><p>JavaScript calls the <code>toString</code> method automatically when a Boolean is to be represented as a text value or when a Boolean is referred to in a string concatenation.
* </p><p>For Boolean objects and values, the built-in <code>toString</code> method returns the string "<code>true</code>" or "<code>false</code>" depending on the value of the boolean object. In the following code, <code>flag.toString</code> returns "<code>true</code>".
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var flag = new Boolean(true)
* var myVar=flag.toString()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the primitive value of a Boolean object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* valueOf()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>valueOf</code> method of <a href="Core_JavaScript_1.5_Reference:Global_Objects:Boolean" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean">Boolean</a> returns the primitive value of a Boolean object or literal Boolean as a Boolean data type.
* </p><p>This method is usually called internally by JavaScript and not explicitly in code.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>valueOf</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* x = new Boolean();
* myVar = x.valueOf()      //assigns false to myVar
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
valueOf: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Watches for a property to be assigned a value and runs a function when that occurs.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES 3.0</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* watch(<i>prop</i>, <i>handler</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>prop</code>Ê</dt><dd> The name of a property of the object.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>handler</code>Ê</dt><dd> A function to call.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Watches for assignment to a property named <code>prop</code> in this object, calling <code>handler(prop, oldval, newval)</code> whenever <code>prop</code> is set and storing the return value in that property. A watchpoint can filter (or nullify) the value assignment, by returning a modified <code>newval</code> (or by returning <code>oldval</code>).
* </p><p>If you delete a property for which a watchpoint has been set, that watchpoint does not disappear. If you later recreate the property, the watchpoint is still in effect.
* </p><p>To remove a watchpoint, use the <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a></code> method. By default, the <code>watch</code> method is inherited by every object descended from <code>Object</code>.
* </p><p>The JavaScript debugger has functionality similar to that provided by this method, as well as other debugging options. For information on the debugger, see <a href="http://developer.mozilla.org/en/docs/Venkman" shape="rect" title="Venkman">Venkman</a>.
* </p><p>In NES 3.0 and 4.x, <code>handler</code> is called from assignments in script as well as native code.  In Firefox, <code>handler</code> is only called from assignments in script, not from native code.  For example, <code>window.watch('location', myHandler)</code> will not call <code>myHandler</code> if the user clicks a link to an anchor within the current document.  However, the following code will call <code>myHandler</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>window.location += '#myAnchor';</code>
* </pre>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>watch</code> and <code>unwatch</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* &lt;script language="JavaScript"&gt;
* 
* var o = {p:1};
* o.watch("p",
* function (id,oldval,newval) {
* document.writeln("o." + id + " changed from "
* + oldval + " to " + newval);
* return newval;
* });
* 
* o.p = 2;
* o.p = 3;
* delete o.p;
* o.p = 4;
* 
* o.unwatch('p');
* o.p = 5;
* 
* &lt;/script&gt;
* </pre>
* <p>This script displays the following:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* o.p changed from 1 to 2
* o.p changed from 2 to 3
* o.p changed from undefined to 4
* </pre>
* <h3> <span> Example: Using <code>watch</code> to validate an object's properties </span></h3>
* <p>You can use <code>watch</code> to test any assignment to an object's properties. This example ensures that every Person always has a valid name and an age between 0 and 200.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* &lt;script language="JavaScript"&gt;
* 
* Person = function(name,age) {
* this.watch("age", Person.prototype._isValidAssignment);
* this.watch("name",Person.prototype._isValidAssignment);
* this.name=name;
* this.age=age;
* };
* 
* Person.prototype.toString = function() { return this.name+","+this.age; };
* 
* Person.prototype._isValidAssignment = function(id,oldval,newval) {
* if (id=="name" &amp;&amp; (!newval || newval.length&gt;30)) { throw new RangeError("invalid name for "+this); }
* if (id=="age"  &amp;&amp; (newval&lt;0 || newval&gt;200))      { throw new RangeError("invalid age  for "+this ); }
* return newval;
* };
* 
* will = new Person("Will",29); // --&gt; Will,29
* document.writeln(will);
* 
* try {
* will.name="";  // --&gt; Error "invalid name for Will,29"
* } catch (e) { document.writeln(e); }
* 
* try {
* will.age=-4;   // --&gt; Error "invalid age  for Will,29"
* } catch (e) { document.writeln(e); }
* 
* &lt;/script&gt;
* 
* </pre>
* <p>This script displays the following:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Will,29
* RangeError: invalid name for Will,29
* RangeError: invalid age  for Will,29
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
watch: function(prop, handler) {
  // This is just a stub for a builtin native JavaScript object.
},
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>The <code>Boolean</code> object is an object wrapper for a boolean value.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Boolean</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Boolean(<i>value</i>)
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>value</code>Ê</dt><dd> The initial value of the <code>Boolean</code> object. The value is converted to a <code>boolean</code> value, if necessary. If value is omitted or is 0, -0, null, false, <code>NaN</code>, undefined, or the empty string (""), the object has an initial value of false. All other values, including any object or the string "<code>false</code>", create an object with an initial value of true.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Do not confuse the primitive Boolean values true and false with the true and false values of the Boolean object.
* </p><p>Any object whose value is not <code>undefined</code> or <code>null</code>, including a Boolean object whose value is false, evaluates to true when passed to a conditional statement. For example, the condition in the following if statement evaluates to true:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = new Boolean(false);
* if (x) //the condition is true
* </pre>
* <p>This behavior does not apply to Boolean primitives. For example, the condition in the following if statement evaluates to <code>false</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = false;
* if (x) //the condition is false
* </pre>
* <p>Do not use a <code>Boolean</code> object to convert a non-boolean value to a boolean value. Instead, use Boolean as a function to perform this task:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = Boolean(expression);     //preferred
* x = new Boolean(expression); //don't use
* </pre>
* <p>If you specify any object, including a Boolean object whose value is false, as the initial value of a Boolean object, the new Boolean object has a value of true.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">myFalse = new Boolean(false);   // initial value of false
* g = new Boolean(myFalse);       //initial value of true
* myString = new String("Hello"); // string object
* s = new Boolean(myString);      //initial value of true
* </pre>
* <p>Do not use a Boolean object in place of a Boolean primitive.
* </p>
* <h2> <span> Properties </span></h2>
* <p><a href="Boolean:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </p><p><a href="Boolean:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:prototype">prototype</a>: Defines a property that is shared by all Boolean objects.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Boolean:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:toSource">toSource</a>: Returns an object literal representing the specified Boolean object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </p><p><a href="Boolean:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:toString">toString</a>: Returns a string representing the specified object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </p><p><a href="Boolean:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:valueOf">valueOf</a>: Returns the primitive value of a Boolean object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </p><p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Creating <code>Boolean</code> objects with an initial value of false </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">bNoParam = new Boolean();
* bZero = new Boolean(0);
* bNull = new Boolean(null);
* bEmptyString = new Boolean("");
* bfalse = new Boolean(false);
* </pre>
* <h3> <span> Creating <code>Boolean</code> objects with an initial value of true </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">btrue = new Boolean(true);
* btrueString = new Boolean("true");
* bfalseString = new Boolean("false");
* bSuLin = new Boolean("Su Lin");
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Boolean(val) {};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>The <code>Boolean</code> object is an object wrapper for a boolean value.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Boolean</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Boolean(<i>value</i>)
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>value</code>Ê</dt><dd> The initial value of the <code>Boolean</code> object. The value is converted to a <code>boolean</code> value, if necessary. If value is omitted or is 0, -0, null, false, <code>NaN</code>, undefined, or the empty string (""), the object has an initial value of false. All other values, including any object or the string "<code>false</code>", create an object with an initial value of true.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Do not confuse the primitive Boolean values true and false with the true and false values of the Boolean object.
* </p><p>Any object whose value is not <code>undefined</code> or <code>null</code>, including a Boolean object whose value is false, evaluates to true when passed to a conditional statement. For example, the condition in the following if statement evaluates to true:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = new Boolean(false);
* if (x) //the condition is true
* </pre>
* <p>This behavior does not apply to Boolean primitives. For example, the condition in the following if statement evaluates to <code>false</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = false;
* if (x) //the condition is false
* </pre>
* <p>Do not use a <code>Boolean</code> object to convert a non-boolean value to a boolean value. Instead, use Boolean as a function to perform this task:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = Boolean(expression);     //preferred
* x = new Boolean(expression); //don't use
* </pre>
* <p>If you specify any object, including a Boolean object whose value is false, as the initial value of a Boolean object, the new Boolean object has a value of true.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">myFalse = new Boolean(false);   // initial value of false
* g = new Boolean(myFalse);       //initial value of true
* myString = new String("Hello"); // string object
* s = new Boolean(myString);      //initial value of true
* </pre>
* <p>Do not use a Boolean object in place of a Boolean primitive.
* </p>
* <h2> <span> Properties </span></h2>
* <p><a href="Boolean:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </p><p><a href="Boolean:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:prototype">prototype</a>: Defines a property that is shared by all Boolean objects.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Boolean:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:toSource">toSource</a>: Returns an object literal representing the specified Boolean object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </p><p><a href="Boolean:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:toString">toString</a>: Returns a string representing the specified object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </p><p><a href="Boolean:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Boolean:valueOf">valueOf</a>: Returns the primitive value of a Boolean object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </p><p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Creating <code>Boolean</code> objects with an initial value of false </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">bNoParam = new Boolean();
* bZero = new Boolean(0);
* bNull = new Boolean(null);
* bEmptyString = new Boolean("");
* bfalse = new Boolean(false);
* </pre>
* <h3> <span> Creating <code>Boolean</code> objects with an initial value of true </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">btrue = new Boolean(true);
* btrueString = new Boolean("true");
* bfalseString = new Boolean("false");
* bSuLin = new Boolean("Su Lin");
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Boolean(value) {};

