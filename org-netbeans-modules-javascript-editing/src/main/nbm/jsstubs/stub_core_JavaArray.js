/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:JavaArray&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:JavaArray">Core JavaScript 1.5 Reference:Objects:JavaArray</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A wrapped Java array accessed from within JavaScript code is a member of the type <code>JavaArray</code>.
* </p>
* <h2> <span> Created by </span></h2>
* <p>Any Java method which returns an array. In addition, you can create a <code>JavaArray</code> with an arbitrary data type using the <code>newInstance</code> method of the <code>Array</code> class:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">public static Object newInstance(Class componentType,
* int length)
* throws NegativeArraySizeException
* </pre>
* <h2> <span> Description </span></h2>
* <p>The <code>JavaArray</code> object is an instance of a Java array that is created in or passed to JavaScript. <code>JavaArray</code> is a wrapper for the instance; all references to the array instance are made through the <code>JavaArray</code>.
* </p><p>In JavaScript 1.4 and later, the <code>componentType</code> parameter is either a <code>JavaClass</code> object representing the type of the array or class object, such as one returned by <code>java.lang.Class.forName</code>. In JavaScript 1.3 and earlier, <code>componentType</code> must be a class object.
* </p><p>Use zero-based indexes to access the elements in a <code>JavaArray</code> object, just as you do to access elements in an array in Java. For example:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var javaString = new java.lang.String("Hello world!");
* var byteArray = javaString.getBytes();
* byteArray[0] // returns 72
* byteArray[1] // returns 101
* </pre>
* <p>Any Java data brought into JavaScript is converted to JavaScript data types. When the <code>JavaArray</code> is passed back to Java, the array is unwrapped and can be used by Java code. See the <a href="Core_JavaScript_1.5_Guide" shape="rect" title="Core JavaScript 1.5 Guide">Core JavaScript 1.5 Guide</a> for more information about data type conversions.
* </p><p>In JavaScript 1.4 and later, the methods of <code>java.lang.Object</code> are inherited by <code>JavaArray</code>.
* </p>
* <h2> <span> Backward compatibility </span></h2>
* <h3> <span> JavaScript 1.3 and earlier </span></h3>
* <p>The methods of <code>java.lang.Object</code> are not inherited by <code>JavaArray</code>. In addition, the <code>toString</code> method is inherited from the <code>Object</code> object and returns the following value:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">[object JavaArray]
* </pre>
* <p>You must specify a class object, such as one returned by <code>java.lang.Object.forName</code>, for the <code>componentType</code> parameter of <code>newInstance</code> when you use this method to create an array. You cannot use a <code>JavaClass</code> object for the <code>componentType</code> parameter.
* </p>
* <h2> <span> Properties </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:JavaArray:length" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaArray:length">length</a>: The number of elements in the Java array represented by <code>JavaArray</code>.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:JavaArray:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaArray:toString">toString</a>: In JavaScript 1.4, this method is overridden by the inherited method <code>java.lang.Object.toString</code>.  in JavaScript 1.3 and earlier, this method returns a string identifying the object as a <code>JavaArray</code>.
* </p><p>In JavaScript 1.4 and later, <code>JavaArray</code> also inherits methods from the Java array superclass, <code>java.lang.Object</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Instantiating a <code>JavaArray</code> in JavaScript </span></h3>
* <p>In this example, the <code>JavaArray</code> <code>byteArray</code> is created by the <code>java.lang.String.getBytes</code> method, which returns an array.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var javaString = new java.lang.String("Hello world!");
* var byteArray = javaString.getBytes();
* </pre>
* <h3> <span> Example: Instantiating a <code>JavaArray</code> in JavaScript with the <code>newInstance</code> method </span></h3>
* <p>In JavaScript 1.4, you can use a <code>JavaClass</code> object as the argument for the <code>newInstance</code> method which creates the array, as shown in the following code:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var dogs = java.lang.reflect.Array.newInstance(java.lang.String, 5);
* </pre>
* <p>In JavaScript 1.1, use a class object returned by <code>java.lang.Class.forName</code> as the argument for the newInstance method, as shown in the following code:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var dataType = java.lang.Class.forName("java.lang.String");
* var dogs = java.lang.reflect.Array.newInstance(dataType, 5);
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var JavaArray = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>The number of elements in the Java array represented by the <code>JavaArray</code> object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="JavaArray" shape="rect" title="Core JavaScript 1.5 Reference:Objects:JavaArray">JavaArray</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES 2.0</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>Unlike <code><a href="Core_JavaScript_1.5_Reference:Objects:Array:length" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Array:length">Array.length</a></code>, <code>JavaArray.length</code> is a read-only property. You cannot change the value of the <code>JavaArray.length</code> property because Java arrays have a fixed number of elements.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Array:length" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Array:length">Array.length</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
length: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a string representation of the <code>JavaArray</code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="JavaArray" shape="rect" title="Core JavaScript 1.5 Reference:Objects:JavaArray">JavaArray</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES 2.0</td>
* </tr>
* </table>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>Calls the method <code>java.lang.Object.toString</code>, which returns the value of the following expression:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* JavaArray.getClass().getName() + '@' +
* java.lang.Integer.toHexString(JavaArray.hashCode())
* </pre>
* <h2> <span> Backward Compatibility </span></h2>
* <h3> <span> JavaScript 1.3 and earlier </span></h3>
* <p>The <code>toString</code> method is inherited from the <code>Object</code> object and returns the following value:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* [object JavaArray]
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
toString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
};

