/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A JavaScript reference to a Java class.
* </p>
* <h2> <span> Created by </span></h2>
* <p>A reference to the class name used with the <code>Packages</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">Packages.<i>JavaClass</i>
* </pre>
* <p><i>JavaClass</i> is the fully-specified name of the object's Java class. The LiveConnect <code>java</code>, <code>sun</code>, and <code>netscape</code> objects provide shortcuts for commonly used Java packages and also create <code>JavaClass</code> objects.
* </p>
* <h2> <span> Description </span></h2>
* <p>A <code>JavaClass</code> object is a reference to one of the classes in a Java package, such as <code>netscape.javascript.JSObject</code>. A <code>JavaPackage</code> object is a reference to a Java package, such as <code>netscape.javascript</code>. In JavaScript, the <code>JavaPackage</code> and <code>JavaClass</code> hierarchy reflect the Java package and class hierarchy.
* </p><p>You can pass a <code>JavaClass</code> object to a Java method which requires an argument of type <code>java.lang.Class</code>.
* </p>
* <h2> <span> Backward compatibility </span></h2>
* <h3> <span> JavaScript 1.3 and earlier </span></h3>
* <p>You must create a wrapper around an instance of <code>java.lang.Class</code> before you pass it as a parameter to a Java method -- <code>JavaClass</code> objects are not automatically converted to instances of <code>java.lang.Class</code>.
* </p>
* <h2> <span> Properties </span></h2>
* <p>The properties of a <code>JavaClass</code> object are the static fields of the Java class.
* </p>
* <h2> <span> Methods </span></h2>
* <p>The methods of a <code>JavaClass</code> object are the static methods of the Java class.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>JavaClass</code> </span></h3>
* <p>In the following example, <code>x</code> is a JavaClass object referring to java.awt.Font. Because BOLD is a static field in the Font class, it is also a property of the JavaClass object.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = java.awt.Font;
* myFont = x("helv", x.BOLD, 10); // creates a Font object
* </pre>
* <p>The previous example omits the <code>Packages</code> keyword and uses the <code>java</code> synonym because the <code>Font</code> class is in the <code>java</code> package.
* </p>
* <h3> <span> Example </span></h3>
* <p>In the following example, the <code>JavaClass</code> object <code>java.lang.String</code> is passed as an argument to the <code>newInstance</code> method which creates an array:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var cars = java.lang.reflect.Array.newInstance(java.lang.String, 15);
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="JavaArray" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaArray">JavaArray</a>,
* <a href="JavaObject" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaObject">JavaObject</a>,
* <a href="JavaPackage" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaPackage">JavaPackage</a>,
* <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages">Packages</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var JavaClass = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A JavaScript reference to a Java class.
* </p>
* <h2> <span> Created by </span></h2>
* <p>A reference to the class name used with the <code>Packages</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">Packages.<i>JavaClass</i>
* </pre>
* <p><i>JavaClass</i> is the fully-specified name of the object's Java class. The LiveConnect <code>java</code>, <code>sun</code>, and <code>netscape</code> objects provide shortcuts for commonly used Java packages and also create <code>JavaClass</code> objects.
* </p>
* <h2> <span> Description </span></h2>
* <p>A <code>JavaClass</code> object is a reference to one of the classes in a Java package, such as <code>netscape.javascript.JSObject</code>. A <code>JavaPackage</code> object is a reference to a Java package, such as <code>netscape.javascript</code>. In JavaScript, the <code>JavaPackage</code> and <code>JavaClass</code> hierarchy reflect the Java package and class hierarchy.
* </p><p>You can pass a <code>JavaClass</code> object to a Java method which requires an argument of type <code>java.lang.Class</code>.
* </p>
* <h2> <span> Backward compatibility </span></h2>
* <h3> <span> JavaScript 1.3 and earlier </span></h3>
* <p>You must create a wrapper around an instance of <code>java.lang.Class</code> before you pass it as a parameter to a Java method -- <code>JavaClass</code> objects are not automatically converted to instances of <code>java.lang.Class</code>.
* </p>
* <h2> <span> Properties </span></h2>
* <p>The properties of a <code>JavaClass</code> object are the static fields of the Java class.
* </p>
* <h2> <span> Methods </span></h2>
* <p>The methods of a <code>JavaClass</code> object are the static methods of the Java class.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>JavaClass</code> </span></h3>
* <p>In the following example, <code>x</code> is a JavaClass object referring to java.awt.Font. Because BOLD is a static field in the Font class, it is also a property of the JavaClass object.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = java.awt.Font;
* myFont = x("helv", x.BOLD, 10); // creates a Font object
* </pre>
* <p>The previous example omits the <code>Packages</code> keyword and uses the <code>java</code> synonym because the <code>Font</code> class is in the <code>java</code> package.
* </p>
* <h3> <span> Example </span></h3>
* <p>In the following example, the <code>JavaClass</code> object <code>java.lang.String</code> is passed as an argument to the <code>newInstance</code> method which creates an array:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var cars = java.lang.reflect.Array.newInstance(java.lang.String, 15);
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="JavaArray" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaArray">JavaArray</a>,
* <a href="JavaObject" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaObject">JavaObject</a>,
* <a href="JavaPackage" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:JavaPackage">JavaPackage</a>,
* <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages">Packages</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function JavaClass() {};

