/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>The type of a wrapped Java object accessed from within JavaScript code.
* </p>
* <h2> <span> Created by </span></h2>
* <p>Any Java method which returns an object type. In addition, you can explicitly construct a <code>JavaObject</code> using the object's Java constructor with the <code>Packages</code> keyword:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Packages.<i>JavaClass</i>(<i>parameterList</i>)
* </pre>
* <p><i>JavaClass</i> is the fully-specified name of the object's Java class.
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>parameterList</code>Ê</dt><dd> An optional list of parameters, specified by the constructor of the Java class.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>JavaObject</code> object is an instance of a Java class that is created in or passed to JavaScript. <code>JavaObject</code> is a wrapper for the instance; all references to the class instance are made through the <code>JavaObject</code>.
* </p><p>Any Java data brought into JavaScript is converted to JavaScript data types. When the <code>JavaObject</code> is passed back to Java, it is unwrapped and can be used by Java code. See the <a href="Core_JavaScript_1.5_Guide" shape="rect" title="Core JavaScript 1.5 Guide">Core JavaScript 1.5 Guide</a> for more information about data type conversions.
* </p>
* <h2> <span> Properties </span></h2>
* <p>Inherits public data members from the Java class of which it is an instance as properties. It also inherits public data members from any superclass as properties.
* </p>
* <h2> <span> Methods </span></h2>
* <p>Inherits public methods from the Java class of which it is an instance. The <code>JavaObject</code> also inherits methods from <code>java.lang.Object</code> and any other superclass.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Instantiating a Java Object in JavaScript </span></h3>
* <p>The following code creates the <code>JavaObject</code> <code>theString</code>, which is an instance of the class <code>java.lang.String</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var theString = new Packages.java.lang.String("Hello, world");
* </pre>
* <p>Because the <code>String</code> class is in the <code>java</code> package, you can also use the java synonym and omit the <code>Packages</code> keyword when you instantiate the class:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var theString = new java.lang.String("Hello, world");
* </pre>
* <h3> <span> Example: Accessing methods of a Java object </span></h3>
* <p>Because the <code>JavaObject</code> <code>theString</code> is an instance of <code>java.lang.String</code>, it inherits all the public methods of <code>java.lang.String</code>. The following example uses the <code>startsWith</code> method to check whether <code>theString</code> begins with "Hello".
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var theString = new java.lang.String("Hello, world");
* theString.startsWith("Hello"); // returns true
* </pre>
* <h3> <span> Example: Accessing inherited methods </span></h3>
* <p>Because <code>getClass</code> is a method of <code>Object</code>, and <code>java.lang.String</code> extends <code>Object</code>, the <code>String</code> class inherits the <code>getClass</code> method. Consequently, <code>getClass</code> is also a method of the <code>JavaObject</code> which instantiates <code>String</code> in JavaScript.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var theString = new java.lang.String("Hello, world");
* theString.getClass(); // returns java.lang.String
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:JavaArray" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaArray">JavaArray</a>,
* <a href="JavaClass" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaClass">JavaClass</a>,
* <a href="JavaPackage" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaPackage">JavaPackage</a>,
* <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages">Packages</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var JavaObject = {
  // This is just a stub for a builtin native JavaScript object.
};

