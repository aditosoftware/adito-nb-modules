/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Encodes a Uniform Resource Identifier (URI) component by replacing each instance of certain characters by one, two, or three escape sequences representing the UTF-8 encoding of the character.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* encodeURIComponent(<i>String</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>String</code>Ê</dt><dd> A component of a Uniform Resource Identifier.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>encodeURIComponent</code> escapes all characters except the following: alphabetic, decimal digits, <code>- _ .Ê! ~ * ' ( )</code>
* </p><p>To avoid unexpected requests to the server, you should call <code>encodeURIComponent</code> on any user-entered parameters that will be passed as part of a URI. For example, a user could type "<kbd>Thyme &amp;time=again</kbd>" for a variable <code>comment</code>. Not using <code>encodeURIComponent</code> on this variable will give <code>comment=Thyme%20&amp;time=again</code>. Note that the ampersand and the equal sign mark a new key and value pair. So instead of having a POST <code>comment</code> key equal to "<kbd>Thyme &amp;time=again</kbd>", you have two POST keys, one equal to "<kbd>Thyme </kbd>" and another (<code>time</code>) equal to <kbd>again</kbd>.
* </p>
* <h2> <span> See also </span></h2>
* <p><a href="decodeURI" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURI">decodeURI</a>,
* <a href="decodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURIComponent">decodeURIComponent</a>,
* <a href="encodeURI" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:encodeURI">encodeURI</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var EncodeURIComponent = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Encodes a Uniform Resource Identifier (URI) component by replacing each instance of certain characters by one, two, or three escape sequences representing the UTF-8 encoding of the character.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* encodeURIComponent(<i>String</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>String</code>Ê</dt><dd> A component of a Uniform Resource Identifier.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>encodeURIComponent</code> escapes all characters except the following: alphabetic, decimal digits, <code>- _ .Ê! ~ * ' ( )</code>
* </p><p>To avoid unexpected requests to the server, you should call <code>encodeURIComponent</code> on any user-entered parameters that will be passed as part of a URI. For example, a user could type "<kbd>Thyme &amp;time=again</kbd>" for a variable <code>comment</code>. Not using <code>encodeURIComponent</code> on this variable will give <code>comment=Thyme%20&amp;time=again</code>. Note that the ampersand and the equal sign mark a new key and value pair. So instead of having a POST <code>comment</code> key equal to "<kbd>Thyme &amp;time=again</kbd>", you have two POST keys, one equal to "<kbd>Thyme </kbd>" and another (<code>time</code>) equal to <kbd>again</kbd>.
* </p>
* <h2> <span> See also </span></h2>
* <p><a href="decodeURI" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURI">decodeURI</a>,
* <a href="decodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURIComponent">decodeURIComponent</a>,
* <a href="encodeURI" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:encodeURI">encodeURI</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function encodeURIComponent(String) {};

