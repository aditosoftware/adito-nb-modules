/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Packages&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Packages">Core JavaScript 1.5 Reference:Objects:Packages</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A top-level object used to access Java classes from within JavaScript code.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Packages</code> object is a top-level, predefined JavaScript object. You can automatically access it without using a constructor or calling a method.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>Packages</code> object lets you access the public methods and fields of an arbitrary Java class from within JavaScript. The <code>java</code>, <code>netscape</code>, and <code>sun</code> properties represent the packages <code>java.*</code>, <code>netscape.*</code>, and <code>sun.*</code> respectively. Use standard Java dot notation to access the classes, methods, and fields in these packages. For example, you can access a constructor of the <code>Frame</code> class as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var theFrame = new Packages.java.awt.Frame();
* </pre>
* <p>For convenience, JavaScript provides the top-level <code>netscape</code>, <code>sun</code>, and <code>java</code> objects that are synonyms for the Packages properties with the same names. Consequently, you can access Java classes in these packages without the Packages keyword, as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var theFrame = new java.awt.Frame();
* </pre>
* <p>The <code>className</code> property represents the fully qualified path name of any other Java class that is available to JavaScript. You must use the <code>Packages</code> object to access classes outside the <code>netscape</code>, <code>sun</code>, and <code>java</code> packages.
* </p>
* <h2> <span> Properties </span></h2>
* <p><a href="Packages:className" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages:className">className</a>: The fully qualified name of a Java class in a package other than netscape, java, or sun that is available to JavaScript.
* </p><p><a href="Packages:java" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages:java">java</a>: Any class in the Java package java.*.
* </p><p><a href="Packages:netscape" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages:netscape">netscape</a>: Any class in the Java package netscape.*.
* </p><p><a href="Packages:sun" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages:sun">sun</a>: Any class in the Java package sun.*.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: JavaScript function to create a Java dialog box </span></h3>
* <p>The following JavaScript function creates a Java dialog box:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">function createWindow() {
* var theOwner = new Packages.java.awt.Frame();
* var theWindow = new Packages.java.awt.Dialog(theOwner);
* theWindow.setSize(350, 200);
* theWindow.setTitle("Hello, World");
* theWindow.setVisible(true);
* }
* </pre>
* <p>In the previous example, the function instantiates <code>theWindow</code> as a new <code>Packages</code> object. The <code>setSize</code>, <code>setTitle</code>, and <code>setVisible</code> methods are all available to JavaScript as public methods of <code>java.awt.Dialog</code>.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Packages = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>The fully qualified name of a Java class in a package other than <code>netscape</code>, <code>java</code>, or <code>sun</code> that is available to JavaScript.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Packages">Packages</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Packages.<i>classname</i>
* </code>
* </p><p>Where <code><i>classname</i></code> is the fully qualified name of a Java class.
* </p>
* <h2> <span> Description </span></h2>
* <p>You must use the <code><i>className</i></code> property of the <code>Packages</code> object to access classes outside the <code>netscape</code>, <code>sun</code>, and <code>java</code> packages.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Packages.<i>classname</i></code> </span></h3>
* <p>The following code accesses the constructor of the <code>CorbaObject</code> class in the <code>myCompany</code> package from JavaScript:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var theObject = new Packages.myCompany.CorbaObject()
* </pre>
* <p>In this example, the value of the <code><i>className</i></code> property is <code>myCompany.CorbaObject</code>, the fully qualified path name of the <code>CorbaObject</code> class.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
className: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Any class in the Java package <code>java.*</code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Packages">Packages</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Packages.java
* </code>
* </p>
* <h2> <span> Description </span></h2>
* <p>Use the <code>java</code> property to access any class in the <code>java</code> package from within JavaScript. Note that the top-level object <code><a href="java" shape="rect" title="Core JavaScript 1.5 Reference:Objects:java">java</a></code> is a synonym for <code>Packages.java</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Packages.java</code> </span></h3>
* <p>The following code accesses the constructor of the <code>java.awt.Frame</code> class:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var theOwner = new Packages.java.awt.Frame();
* </pre>
* <p>You can simplify this code by using the top-level <code>java</code> object to access the constructor as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var theOwner = new java.awt.Frame();
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
java: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Any class in the Java package <code>netscape.*</code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages">Packages</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Packages.netscape
* </code>
* </p>
* <h2> <span> Description </span></h2>
* <p>Use the <code>netscape</code> property to access any class in the <code>netscape</code> package from within JavaScript. Note that the top-level object <code><a href="netscape" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:netscape">netscape</a></code> is a synonym for <code>Packages.netscape</code>.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
netscape: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Any class in the Java package <code>sun.*</code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Packages" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Packages">Packages</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Packages.sun
* </code>
* </p>
* <h2> <span> Description </span></h2>
* <p>Use the <code>sun</code> property to access any class in the <code>sun</code> package from within JavaScript. Note that the top-level object <code><a href="sun" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:sun">sun</a></code> is a synonym for <code>Packages.sun</code>.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
sun: undefined,
};

