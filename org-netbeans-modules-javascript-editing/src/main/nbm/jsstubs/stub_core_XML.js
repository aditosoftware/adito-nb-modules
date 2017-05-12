/**
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p><code>XML</code> is a native XML object in JavaScript for working with XML-stucture. XML declarations like <code>&lt;?xml version="1.0"?&gt;</code> are not allowed.
* </p>
* <h2> <span> Created by </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new XML()</pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new XML("&lt;myXmlRoot&gt;myData&lt;/myXmlRoot&gt;")</pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">&lt;myXmlRoot&gt;myData&lt;/myXmlRoot&gt;</pre>
* <h2> <span> Parameters </span></h2>
* <p style="font-weight: bold;">xmlStr</p>
* <p>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-style: italic;">optional</span> you can pass a xml-String here that will be converted into a XML-Object. Remember that XML declarations like <code>&lt;?xml version="1.0"?&gt;</code> are not allowed.</p>
* <h2> <span> Properties </span></h2>
* <p style="font-weight: bold;">ignoreWhitespace</p>
* <p>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-style: italic;">default: true</span> Ignores whitespace between nodes and leading and trailing whitespace within text nodes.</p>
* <p style="font-weight: bold;">prettyPrinting</p>
* <p>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-style: italic;">default: true</span> When true, toXMLString() includes newlines and indenting for the serialization of XML-Objects.</p>
* <p style="font-weight: bold;">prettyIndent</p>
* <p>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-style: italic;">default: 2</span> he number of spaces to indent each level in the XML tree. Ignored if prettyPrinting is false.</p>
* 
* <h2> <span> Methods </span></h2>
* <p>None.</p>
* <h2> <span> Interesting links with additional information </span></h2>
* <ul>
*     <li><a href="http://wso2.com/project/mashup/0.2/docs/e4xquickstart.html">http://wso2.com/project/mashup/0.2/docs/e4xquickstart.html</a></li>
*     <li><a href="https://developer.mozilla.org/en-US/docs/Archive/Web/E4X_tutorial">https://developer.mozilla.org/en-US/docs/Archive/Web/E4X_tutorial</a></li>
*     <li><a href="https://developer.mozilla.org/en-US/docs/Archive/Web/E4X/Processing_XML_with_E4X">https://developer.mozilla.org/en-US/docs/Archive/Web/E4X/Processing_XML_with_E4X</a></li>
*     <li><a href="https://developer.mozilla.org/en-US/docs/Archive/Web/E4X_tutorial/The_global_XML_object">https://developer.mozilla.org/en-US/docs/Archive/Web/E4X_tutorial/The_global_XML_object</a></li>
* </ul>
* <hr />
* <p style="text-align: center;">License: <a href="https://creativecommons.org/licenses/by-sa/2.5/" shape="rect" title="MDC:Copyrights">Creative Commons Attribution-ShareAlike license (CC-BY-SA), v2.5 or any later version</a></p>
* 
*/
var XML = {
      ignoreWhitespace: null
    , prettyPrinting: null
    , prettyIndent: null
    , toXmlString: function (){}
    
}
function XML() { };
function XML(xmlStr) { };