/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Encodes a Uniform Resource Identifier (URI) by replacing each instance of certain characters by one, two, or three escape sequences representing the UTF-8 encoding of the character.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* encodeURI(<i>URI</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>URI</code>Ê</dt><dd> A complete Uniform Resource Identifier.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Assumes that the URI is a complete URI, so does not encode reserved characters that have special meaning in the URI.
* </p><p><code>encodeURI</code> replaces all characters except the following with the appropriate UTF-8 escape sequences:
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Type</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Includes</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Reserved characters</td>
* <td colspan="1" rowspan="1"><code>; , /Ê?Ê: @ &amp; = + $</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Unescaped characters</td>
* <td colspan="1" rowspan="1">alphabetic, decimal digits, <code>- _ .Ê! ~ * ' ( )</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Score</td>
* <td colspan="1" rowspan="1"><code>#</code></td>
* </tr>
* </table>
* <p>Note that <code>encodeURI</code> by itself <i>cannot</i> form proper HTTP GET and POST requests, such as for XMLHTTPRequests, because "&amp;", "+", and "=" are not encoded, which are treated as special characters in GET and POST requests. <code><a href="Core_JavaScript_1.5_Reference:Global_Functions:encodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:encodeURIComponent">encodeURIComponent</a></code>, however, does encode these characters. These behaviors are most likely not consistent across browsers.
* </p>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Functions:decodeURI" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURI">decodeURI</a>,
* <a href="decodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURIComponent">decodeURIComponent</a>,
* <a href="encodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:encodeURIComponent">encodeURIComponent</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var EncodeURI = {
  // This is just a stub for a builtin native JavaScript object.
};
/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Function</b>
* </p><p>Encodes a Uniform Resource Identifier (URI) by replacing each instance of certain characters by one, two, or three escape sequences representing the UTF-8 encoding of the character.
* </p>
* <h2> <span> Syntax </span></h2>
* <p><code>
* encodeURI(<i>URI</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>URI</code>Ê</dt><dd> A complete Uniform Resource Identifier.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Assumes that the URI is a complete URI, so does not encode reserved characters that have special meaning in the URI.
* </p><p><code>encodeURI</code> replaces all characters except the following with the appropriate UTF-8 escape sequences:
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Type</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Includes</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Reserved characters</td>
* <td colspan="1" rowspan="1"><code>; , /Ê?Ê: @ &amp; = + $</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Unescaped characters</td>
* <td colspan="1" rowspan="1">alphabetic, decimal digits, <code>- _ .Ê! ~ * ' ( )</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Score</td>
* <td colspan="1" rowspan="1"><code>#</code></td>
* </tr>
* </table>
* <p>Note that <code>encodeURI</code> by itself <i>cannot</i> form proper HTTP GET and POST requests, such as for XMLHTTPRequests, because "&amp;", "+", and "=" are not encoded, which are treated as special characters in GET and POST requests. <code><a href="Core_JavaScript_1.5_Reference:Global_Functions:encodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:encodeURIComponent">encodeURIComponent</a></code>, however, does encode these characters. These behaviors are most likely not consistent across browsers.
* </p>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Functions:decodeURI" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURI">decodeURI</a>,
* <a href="decodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:decodeURIComponent">decodeURIComponent</a>,
* <a href="encodeURIComponent" shape="rect" title="Core JavaScript 1.5 Reference:Global Functions:encodeURIComponent">encodeURIComponent</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function encodeURI(URI) {};

