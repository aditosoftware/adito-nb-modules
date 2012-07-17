/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:RegExp&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">Core JavaScript 1.5 Reference:Objects:RegExp</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A regular expression object contains the pattern of a regular expression. It has properties and methods for using that regular expression to find and replace matches in strings.
* </p><p>In addition to the properties of an individual regular expression object that you create using the <code>RegExp</code> constructor function, the predefined <code>RegExp</code> object has static properties that are set whenever any regular expression is used.
* </p>
* <h2> <span> Created by </span></h2>
* <p>A literal text format or the <code>RegExp</code> constructor function.
* </p><p>The literal format is used as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><i>/pattern/flags</i>
* </pre>
* <p>The constructor function is used as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new RegExp("<i>pattern</i>"[, "<i>flags</i>"])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>pattern</code>Ê</dt><dd> The text of the regular expression.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>flags</code></dt><dd> If specified, flags can have any combination of the following values: <code>g</code> - global match, <code>i</code> - ignore case, <code>m</code> - match over multiple lines.
* </dd></dl>
* <p>Notice that the parameters to the literal format do not use quotation marks to indicate strings, while the parameters to the constructor function do use quotation marks. So the following expressions create the same regular expression:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">/ab+c/i
* new RegExp("ab+c", "i")
* </pre>
* <h2> <span> Description </span></h2>
* <p>When using the constructor function, the normal string escape rules (preceding special characters with \ when included in a string) are necessary. For example, the following are equivalent:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">re = new RegExp("\\w+")
* re = /\w+/
* </pre>
* <h3> <span> Special characters in regular expressions </span></h3>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Character</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Meaning</td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\</code></td>
* <td colspan="1" rowspan="1">
* <p>For characters that are usually treated literally, indicates that the next character is special and not to be interpreted literally.
* </p><p>For example, <code>/b/</code> matches the character 'b'. By placing a backslash in front of b, that is by using <code>/\b/</code>, the character becomes special to mean match a word boundary.
* </p><p>-or-
* </p><p>For characters that are usually treated specially, indicates that the next character is not special and should be interpreted literally.
* </p><p>For example, * is a special character that means 0 or more occurrences of the preceding character should be matched; for example, <code>/a* /</code> means match 0 or more a's. To match <code>*</code> literally, precede the it with a backslash; for example, <code>/a\* /</code> matches 'a*'.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>^</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches beginning of input. If the multiline flag is set to true, also matches immediately after a line break character.
* </p><p>For example, <code>/^A/</code> does not match the 'A' in "an A", but does match the first 'A' in "An A."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>$</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches end of input. If the multiline flag is set to true, also matches immediately before a line break character.
* </p><p>For example, <code>/t$/</code> does not match the 't' in "eater", but does match it in "eat".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>*</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 0 or more times.
* </p><p>For example, <code>/bo* /</code> matches 'boooo' in "A ghost booooed" and 'b' in "A bird warbled", but nothing in "A goat grunted".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>+</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 1 or more times. Equivalent to <code>{1,}</code>.
* </p><p>For example, <code>/a+/</code> matches the 'a' in "candy" and all the a's in "caaaaaaandy".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>?</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 0 or 1 time.
* </p><p>For example, <code>/e?le?/</code> matches the 'el' in "angel" and the 'le' in "angle."
* </p><p>If used immediately after any of the quantifiers <code>*</code>, <code>+</code>, <code>?</code>, or <code>{}</code>, makes the quantifier non-greedy (matching the minimum number of times), as opposed to the default, which is greedy (matching the maximum number of times).
* </p><p>Also used in lookahead assertions, described under <code>(?=)</code>, <code>(?!)</code>, and <code>(?:)</code> in this table.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>.</code></td>
* <td colspan="1" rowspan="1">
* <p>(The decimal point) matches any single character except the newline characters: \n \r \u2028 or \u2029. (<code>[\s\S]</code> can be used to match any character including newlines.)
* </p><p>For example, <code>/.n/</code> matches 'an' and 'on' in "nay, an apple is on the tree", but not 'nay'.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>(<i>x</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> and remembers the match. These are called capturing parentheses.
* </p><p>For example, <code>/(foo)/</code> matches and remembers 'foo' in "foo bar." The matched substring can be recalled from the resulting array's elements <code>[1], ..., [<i>n</i>]</code> or from the predefined <code>RegExp</code> object's properties <code>$1, ..., $9</code>.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>(?:<i>x</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> but does not remember the match. These are called non-capturing parentheses. The matched substring can not be recalled from the resulting array's elements <code>[1], ..., [<i>n</i>]</code> or from the predefined <code>RegExp</code> object's properties <code>$1, ..., $9</code>.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>(?=<i>y</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> only if <code><i>x</i></code> is followed by <code><i>y</i></code>. For example, <code>/Jack(?=Sprat)/</code> matches 'Jack' only if it is followed by 'Sprat'. <code>/Jack(?=Sprat|Frost)/</code> matches 'Jack' only if it is followed by 'Sprat' or 'Frost'. However, neither 'Sprat' nor 'Frost' is part of the match results.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>(?!<i>y</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> only if <code><i>x</i></code> is not followed by <code><i>y</i></code>. For example, <code>/\d+(?!\.)/</code> matches a number only if it is not followed by a decimal point.
* </p><p><code>/\d+(?!\.)/.exec("3.141")</code> matches 141 but not 3.141.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>|<i>y</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches either <code><i>x</i></code> or <code><i>y</i></code>.
* </p><p>For example, <code>/green|red/</code> matches 'green' in "green apple" and 'red' in "red apple."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. Matches exactly <code><i>n</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{2}/</code> doesn't match the 'a' in "candy," but it matches all of the a's in "caandy," and the first two a's in "caaandy."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>,}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. Matches at least <code><i>n</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{2,}</code> doesn't match the 'a' in "candy", but matches all of the a's in "caandy" and in "caaaaaaandy."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>,<i>m</i>}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> and <code><i>m</i></code> are positive integers. Matches at least <code><i>n</i></code> and at most <code><i>m</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{1,3}/</code> matches nothing in "cndy", the 'a' in "candy," the first two a's in "caandy," and the first three a's in "caaaaaaandy". Notice that when matching "caaaaaaandy", the match is "aaa", even though the original string had more a's in it.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[<i>xyz</i>]</code></td>
* <td colspan="1" rowspan="1">
* <p>A character set. Matches any one of the enclosed characters. You can specify a range of characters by using a hyphen.
* </p><p>For example, <code>[abcd]</code> is the same as <code>[a-d]</code>. They match the 'b' in "brisket" and the 'c' in "ache".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[^<i>xyz</i>]</code></td>
* <td colspan="1" rowspan="1">
* <p>A negated or complemented character set. That is, it matches anything that is not enclosed in the brackets. You can specify a range of characters by using a hyphen.
* </p><p>For example, <code>[^abc]</code> is the same as <code>[^a-c]</code>. They initially match 'r' in "brisket" and 'h' in "chop."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[\b]</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a backspace. (Not to be confused with <code>\b</code>.)
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\b</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a word boundary, such as a space. (Not to be confused with <code>[\b]</code>.)
* </p><p>For example, <code>/\bn\w/</code> matches the 'no' in "noonday"; <code>/\wy\b/</code> matches the 'ly' in "possibly yesterday."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\B</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a non-word boundary.
* </p><p>For example, <code>/\w\Bn/</code> matches 'on' in "noonday", and <code>/y\B\w/</code> matches 'ye' in "possibly yesterday."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\c<i>X</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>X</i></code> is a letter from A - Z. Matches a control character in a string.
* </p><p>For example, <code>/\cM/</code> matches control-M in a string.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\d</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a digit character(Basic Latin alphabet). Equivalent to <code>[0-9]</code>.
* </p><p><b>Note</b>: In Firefox 2 and earlier, matches a digit character from any alphabet.(<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=378738" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=378738">bug 378738</a>)
* </p><p>For example, <code>/\d/</code> or <code>/[0-9]/</code> matches '2' in "B2 is the suite number."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\D</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any non-digit character(Basic Latin alphabet). Equivalent to <code>[^0-9]</code>.
* </p><p><b>Note</b>: In Firefox 2 and earlier, all  alphabet. (<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=378738" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=378738">bug 378738</a>)
* </p><p>For example, <code>/\D/</code> or <code>/[^0-9]/</code> matches 'B' in "B2 is the suite number."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\f</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a form-feed.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\n</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a linefeed.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\r</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a carriage return.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\s</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a single white space character, including space, tab, form feed, line feed and other unicode spaces.<small><sup id="ref_equivalent_s"><a href="RegExp#endnote_equivalent_s" rel="nofollow" shape="rect" title="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:RegExp#endnote_equivalent_s">[1]</a></sup></small>
* </p><p>For example, <code>/\s\w* /</code> matches ' bar' in "foo bar."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\S</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a single character other than white space.<small><sup id="ref_equivalent_S"><a href="RegExp#endnote_equivalent_S" rel="nofollow" shape="rect" title="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:RegExp#endnote_equivalent_S">[2]</a></sup></small>
* </p><p>For example, <code>/\S\w* /</code> matches 'foo' in "foo bar."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\t</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a tab.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\v</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a vertical tab.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\w</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any (Basic Latin alphabet) alphanumeric character including the underscore. Equivalent to <code>[A-Za-z0-9_]</code>.
* </p><p>For example, <code>/\w/</code> matches 'a' in "apple," '5' in "$5.28," and '3' in "3D."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\W</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any non-(Basic Latin)word character. Equivalent to <code>[^A-Za-z0-9_]</code>.
* </p><p>For example, <code>/\W/</code> or <code>/[^$A-Za-z0-9_]/</code> matches '%' in "50%."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\<i>n</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. A back reference to the last substring matching the n parenthetical in the regular expression (counting left parentheses).
* </p><p>For example, <code>/apple(,)\sorange\1/</code> matches 'apple, orange,' in "apple, orange, cherry, peach." A more complete example follows this table.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\0</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a NUL character. Do not follow this with another digit.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\x<i>hh</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the character with the code <code><i>hh</i></code> (two hexadecimal digits)
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\u<i>hhhh</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the character with code <code><i>hhhh</i></code> (four hexadecimal digits).
* </p>
* </td>
* </tr>
* </table>
* <p>The literal notation provides compilation of the regular expression when the expression is evaluated. Use literal notation when the regular expression will remain constant. For example, if you use literal notation to construct a regular expression used in a loop, the regular expression won't be recompiled on each iteration.
* </p><p>The constructor of the regular expression object, for example, <code>new RegExp("ab+c")</code>, provides runtime compilation of the regular expression. Use the constructor function when you know the regular expression pattern will be changing, or you don't know the pattern and are getting it from another source, such as user input.
* </p><p>A separate predefined <code>RegExp</code> object is available in each window; that is, each separate thread of JavaScript execution gets its own <code>RegExp</code> object. Because each script runs to completion without interruption in a thread, this assures that different scripts do not overwrite values of the <code>RegExp</code> object.
* </p>
* <ol><li><cite id="endnote_equivalent_s"><a href="RegExp#ref_equivalent_s" shape="rect" title=""><b>^</b></a></cite>ÊEquivalent to:
* </li></ol>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>[\t\n\v\f\r \u00a0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]</code>
* </pre>
* <ol><li><cite id="endnote_equivalent_S"><a href="RegExp#ref_equivalent_S" shape="rect" title=""><b>^</b></a></cite>ÊEquivalent to:
* </li></ol>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>[^\t\n\v\f\r \u00a0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]</code>
* </pre>
* <h2> <span> Properties </span></h2>
* <p>Note that several of the <code>RegExp</code> properties have both long and short (Perl-like) names. Both names always refer to the same value. Perl is the programming language from which JavaScript modeled its regular expressions.
* </p><p><a href="Core_JavaScript_1.5_Reference:Global_Objects:RegExp:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </p><p><a href="RegExp:global" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:global">global</a>: Whether to test the regular expression against all possible matches in a string, or only against the first. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:ignoreCase" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:ignoreCase">ignoreCase</a>: Whether to ignore case while attempting a match in a string. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:lastIndex" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:lastIndex">lastIndex</a>: The index at which to start the next match. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:multiline" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:multiline">multiline</a>: Whether or not to search in strings across multiple lines. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:prototype">prototype</a>: Allows the addition of properties to all objects.
* </p><p><a href="RegExp:source" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:source">source</a>: The text of the pattern. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:RegExp:exec" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:exec">exec</a>: Executes a search for a match in its string parameter.
* </p><p><a href="RegExp:test" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:test">test</a>: Tests for a match in its string parameter.
* </p><p><a href="RegExp:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:toSource">toSource</a>: Returns an object literal representing the specified object; you can use this value to create a new object. Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </p><p><a href="RegExp:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:toString">toString</a>: Returns a string representing the specified object. Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </p><p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using the <code>replace</code> method </span></h3>
* <p>The following script uses the <code>replace</code> method to switch the words in the string. In the replacement text, the script uses "<code>$1</code>" and "<code>$2</code>" to indicate the results of the corresponding matching parentheses in the regular expression pattern.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var re = /(\w+)\s(\w+)/;
* var str = "John Smith";
* var newstr = str.replace(re, "$2, $1");
* document.write(newstr);
* </pre>
* <p>This displays "Smith, John".
* </p><p>
* </p>
* <h2> <span> See also </span></h2>
* <ul><li>The <a href="Core_JavaScript_1.5_Guide:Regular_Expressions" shape="rect" title="Core JavaScript 1.5 Guide:Regular Expressions">Core JavaScript 1.5 Guide:Regular Expressions</a> chapter.
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var RegExp = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a reference to the <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp">RegExp</a> function that created the instance's prototype. Note that the value of this property is a reference to the function itself, not a string containing the function's name.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p>See <a href="Core_JavaScript_1.5_Reference:Objects:Object:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Object:constructor">Object.constructor</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
constructor: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Executes a search for a match in a specified string. Returns a result array, or <code>null</code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3 (first syntax only)</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var result1 = <i>regexp</i>.exec(<i>str</i>);
* var result2 = <i>regexp</i>(<i>str</i>); // Mozilla extension
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>regexp</code>Ê</dt><dd> The name of the regular expression.  It can be a variable name or a literal.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>str</code>Ê</dt><dd> The string against which to match the regular expression.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>As shown in the syntax description, a regular expression's exec method can be called either directly, (with <code>regexp.exec(str)</code>) or indirectly (with <code>regexp(str)</code>).
* </p><p>If you are executing a match simply to find true or false, use the <code><a href="Core_JavaScript_1.5_Reference:Objects:RegExp:test" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp:test">test</a></code> method or the <code>String</code> <code><a href="String:search" shape="rect" title="Core JavaScript 1.5 Reference:Objects:String:search">search</a></code> method.
* </p><p>If the match succeeds, the <code>exec</code> method returns an array and updates properties of the regular expression object. If the match fails, the <code>exec</code> method returns <code>null</code>.
* </p><p>Consider the following example:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// Match one d followed by one or more b's followed by one d
* // Remember matched b's and the following d
* // Ignore case
* var myRe = /d(b+)(d)/ig;
* var myArray = myRe.exec("cdbBdbsbz");
* </pre>
* <p>The following table shows the results for this script:
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Object</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property/Index</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Description</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Example</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="5"><code>myArray</code></td>
* <td colspan="1" rowspan="1"><code>Ê</code></td>
* <td colspan="1" rowspan="1">The content of <code>myArray</code>.</td>
* <td colspan="1" rowspan="1"><code>["dbBd", "bB", "d"]</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>index</code></td>
* <td colspan="1" rowspan="1">The 0-based index of the match in the string.</td>
* <td colspan="1" rowspan="1"><code>1</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>input</code></td>
* <td colspan="1" rowspan="1">The original string.</td>
* <td colspan="1" rowspan="1"><code>cdbBdbsbz</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>[0]</code></td>
* <td colspan="1" rowspan="1">The last matched characters</td>
* <td colspan="1" rowspan="1"><code>dbBd</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>[1], ...[<i>n</i>]</code></td>
* <td colspan="1" rowspan="1">The parenthesized substring matches, if any.  The number of possible parenthesized substrings is unlimited.</td>
* <td colspan="1" rowspan="1"><code>[1] = bB[2] = d</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="5"><code>myRe</code></td>
* <td colspan="1" rowspan="1"><code>lastIndex</code></td>
* <td colspan="1" rowspan="1">The index at which to start the next match.</td>
* <td colspan="1" rowspan="1"><code>5</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>ignoreCase</code></td>
* <td colspan="1" rowspan="1">Indicates if the "<code>i</code>" flag was used to ignore case.</td>
* <td colspan="1" rowspan="1"><code>true</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>global</code></td>
* <td colspan="1" rowspan="1">Indicates if the "<code>g</code>" flag was used for a global match.</td>
* <td colspan="1" rowspan="1"><code>true</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>multiline</code></td>
* <td colspan="1" rowspan="1">Indicates if the "<code>m</code>" flag was used to search in strings across multiple line.</td>
* <td colspan="1" rowspan="1"><code>false</code></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1"><code>source</code></td>
* <td colspan="1" rowspan="1">The text of the pattern.</td>
* <td colspan="1" rowspan="1"><code>d(b+)(d)</code></td>
* </tr>
* </table>
* <p>If your regular expression uses the "<code>g</code>" flag, you can use the <code>exec</code> method multiple times to find successive matches in the same string. When you do so, the search starts at the substring of <code>str</code> specified by the regular expression's <code>lastIndex</code> property. For example, assume you have this script:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var myRe = /ab* /g;
* var str = "abbcdefabh";
* var myArray;
* while ((myArray = myRe.exec(str))Ê!= null)
* {
* var msg = "Found " + myArray[0] + ".  ";
* msg += "Next match starts at " + myRe.lastIndex;
* print(msg);
* }
* </pre>
* <p>This script displays the following text:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">Found abb. Next match starts at 3
* Found ab. Next match starts at 9
* </pre>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>exec</code> </span></h3>
* <p>In the following example, the function executes a match against the input. It then cycles through the array to see if other names match the user's name.
* </p><p>This script assumes that first names of registered party attendees are preloaded into the array <code>A</code>, perhaps by gathering them from a party database.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var A = ["Frank", "Emily", "Jane", "Harry", "Nick", "Beth", "Rick",
* "Terrence", "Carol", "Ann", "Terry", "Frank", "Alice", "Rick",
* "Bill", "Tom", "Fiona", "Jane", "William", "Joan", "Beth"];
* 
* function lookup(input)
* {
* var firstName = /\w+/i.exec(input);
* if (!firstName)
* {
* print(input + " isn't a name!");
* return;
* }
* 
* var count = 0;
* for (var i = 0; i &lt; A.length; i++)
* {
* if (firstName[0].toLowerCase() == A[i].toLowerCase())
* count++;
* }
* var midstring = count == 1
* Ê? " other has ";
* Ê: " others have ";
* print("Thanks, " + count + midstring + "the same name!")
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
exec: function(str) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Whether or not the "<code>g</code>" flag is used with the regular expression.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a> instances</td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Read-only</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0
* <p>JavaScript 1.5: <code>global</code> is a property of a <code>RegExp</code> instance, not the <code>RegExp</code> object.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p><code>global</code> is a property of an individual regular expression object.
* </p><p>The value of <code>global</code> is true if the "<code>g</code>" flag was used; otherwise, <code>false</code>. The "<code>g</code>" flag indicates that the regular expression should be tested against all possible matches in a string.
* </p><p>You cannot change this property directly.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Boolean
*/
global: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Whether or not the "<code>i</code>" flag is used with the regular expression.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a> instances</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0
* <p>JavaScript 1.5: <code>ignoreCase</code> is a property of a <code>RegExp</code> instance, not the <code>RegExp</code> object.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p><code>ignoreCase</code> is a property of an individual regular expression object.
* </p><p>The value of <code>ignoreCase</code> is true if the "<code>i</code>" flag was used; otherwise, false. The "<code>i</code>" flag indicates that case should be ignored while attempting a match in a string.
* </p><p>You cannot change this property directly.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Boolean
*/
ignoreCase: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>A read/write integer property that specifies the index at which to start the next match.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0
* <p>JavaScript 1.5: <code>lastIndex</code> is a property of a <code>RegExp</code> instance, not the <code>RegExp</code> object.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p><code>lastIndex</code> is a property of an individual regular expression object.
* </p><p>This property is set only if the regular expression used the "<code>g</code>" flag to indicate a global search. The following rules apply:
* </p>
* <ul><li> If <code>lastIndex</code> is greater than the length of the string, <code>regexp.test</code> and <code>regexp.exec</code> fail, and <code>lastIndex</code> is set to 0.
* </li></ul>
* <ul><li> If <code>lastIndex</code> is equal to the length of the string and if the regular expression matches the empty string, then the regular expression matches input starting at <code>lastIndex</code>.
* </li></ul>
* <ul><li> If <code>lastIndex</code> is equal to the length of the string and if the regular expression does not match the empty string, then the regular expression mismatches input, and <code>lastIndex</code> is reset to 0.
* </li></ul>
* <ul><li> Otherwise, <code>lastIndex</code> is set to the next position following the most recent match.
* </li></ul>
* <p>For example, consider the following sequence of statements:
* </p>
* <dl><dt style="font-weight:bold"> <code>re = /(hi)?/g</code>Ê</dt><dd> Matches the empty string.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>re("hi")</code>Ê</dt><dd> Returns <code>["hi", "hi"]</code> with <code>lastIndex</code> equal to 2.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>re("hi")</code>Ê</dt><dd> Returns <code>[""]</code>, an empty array whose zeroth element is the match string. In this case, the empty string because <code>lastIndex</code> was 2 (and still is 2) and "<code>hi</code>" has length 2.
* </dd></dl>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
lastIndex: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Reflects whether or not to search in strings across multiple lines.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a> instances</td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0
* <p>JavaScript 1.5: <code>multiline</code> is a property of a <code>RegExp</code> instance, not the <code>RegExp</code> object.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p><code>multiline</code> is a property of an individual regular expression object..
* </p><p>The value of <code>multiline</code> is true if the "<code>m</code>" flag was used; otherwise, false. The "<code>m</code>" flag indicates that a multiline input string should be treated as multiple lines.  For example, if "<code>m</code>" is used, "<code>^</code>" and "<code>$</code>" change from matching at only the start or end of the entire string to the start or end of any line within the string.
* </p><p>You cannot change this property directly.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
multiline: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Represents the prototype for this class. You can use the prototype to add properties or methods to all instances of a class. For information on prototypes, see <a href="Function:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Function:prototype">Function.prototype</a>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
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
* <h2> <span> Summary </span></h2>
* <p>A read-only property that contains the text of the pattern, excluding the forward slashes.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a> instances</td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1">
* <p><b>Static</b>
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0
* <p>JavaScript 1.5: <code>source</code> is a property of a <code>RegExp</code> instance, not the <code>RegExp</code> object.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Description </span></h2>
* <p><code>source</code> is a property of an individual regular expression object.
* </p><p>You cannot change this property directly.
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
source: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Executes the search for a match between a regular expression and a specified string. Returns <code>true</code> or <code>false</code>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.2, NES3.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* <i>regexp</i>.test([<i>str</i>])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>regexp</code>Ê</dt><dd> The name of the regular expression. It can be a variable name or a literal.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>str</code>Ê</dt><dd> The string against which to match the regular expression.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>When you want to know whether a pattern is found in a string use the <code>test</code> method (similar to the <code><a href="Core_JavaScript_1.5_Reference:Objects:String:search" shape="rect" title="Core JavaScript 1.5 Reference:Objects:String:search">String.search</a></code> method); for more information (but slower execution) use the <code><a href="RegExp:exec" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp:exec">exec</a></code> method (similar to the <code><a href="String:match" shape="rect" title="Core JavaScript 1.5 Reference:Objects:String:match">String.match</a></code> method).
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>test</code> </span></h3>
* <p>The following example prints a message which depends on the success of the test:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* function testinput(re, str){
* if (re.test(str))
* midstring = " contains ";
* else
* midstring = " does not contain ";
* document.write (str + midstring + re.source);
* }
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Boolean
*/
test: function(str) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <div style="border: 1px solid #FFB752; background-color: #FEE3BC; font-weight: bold; text-align: center; padding: 0px 10px 0px 10px; margin: 10px 0px 10px 0px;"><p style="margin: 4px 0px 4px 0px;">Non-standard</p></div>
* 
* <h2> <span> Summary </span></h2>
* <p>Returns a string representing the source code of the object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code><i>regexp</i>.toSource()</code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>toSource</code> method returns the following values:
* </p>
* <ul><li> For the built-in <code>RegExp</code> object, <code>toSource</code> returns the following string indicating that the source code is not available:
* </li></ul>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">function RegExp() {[native code]}
* </pre>
* <ul><li> For instances of <code>RegExp</code>, <code>toSource</code> returns a string representing the source code.
* </li></ul>
* <p>This method is usually called internally by JavaScript and not explicitly in code.
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.prototype.toSource</a>
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
* <p>Returns a string representing the specified object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="RegExp" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp">RegExp</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.1, NES2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262, Edition 3</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code><i>regexp</i>.toString()</code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>RegExp</code> object overrides the <code>toString</code> method of the <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a></code> object; it does not inherit <code><a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a></code>. For <code>RegExp</code> objects, the <code>toString</code> method returns a string representation of the regular expression.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>toString</code> </span></h3>
* <p>The following example displays the string value of a <code>RegExp</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* myExp = new RegExp("a+b+c");
* alert(myExp.toString());       // displays "/a+b+c/"
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.prototype.toString</a>
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
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:RegExp&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">Core JavaScript 1.5 Reference:Objects:RegExp</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A regular expression object contains the pattern of a regular expression. It has properties and methods for using that regular expression to find and replace matches in strings.
* </p><p>In addition to the properties of an individual regular expression object that you create using the <code>RegExp</code> constructor function, the predefined <code>RegExp</code> object has static properties that are set whenever any regular expression is used.
* </p>
* <h2> <span> Created by </span></h2>
* <p>A literal text format or the <code>RegExp</code> constructor function.
* </p><p>The literal format is used as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><i>/pattern/flags</i>
* </pre>
* <p>The constructor function is used as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new RegExp("<i>pattern</i>"[, "<i>flags</i>"])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>pattern</code>Ê</dt><dd> The text of the regular expression.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>flags</code></dt><dd> If specified, flags can have any combination of the following values: <code>g</code> - global match, <code>i</code> - ignore case, <code>m</code> - match over multiple lines.
* </dd></dl>
* <p>Notice that the parameters to the literal format do not use quotation marks to indicate strings, while the parameters to the constructor function do use quotation marks. So the following expressions create the same regular expression:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">/ab+c/i
* new RegExp("ab+c", "i")
* </pre>
* <h2> <span> Description </span></h2>
* <p>When using the constructor function, the normal string escape rules (preceding special characters with \ when included in a string) are necessary. For example, the following are equivalent:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">re = new RegExp("\\w+")
* re = /\w+/
* </pre>
* <h3> <span> Special characters in regular expressions </span></h3>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Character</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Meaning</td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\</code></td>
* <td colspan="1" rowspan="1">
* <p>For characters that are usually treated literally, indicates that the next character is special and not to be interpreted literally.
* </p><p>For example, <code>/b/</code> matches the character 'b'. By placing a backslash in front of b, that is by using <code>/\b/</code>, the character becomes special to mean match a word boundary.
* </p><p>-or-
* </p><p>For characters that are usually treated specially, indicates that the next character is not special and should be interpreted literally.
* </p><p>For example, * is a special character that means 0 or more occurrences of the preceding character should be matched; for example, <code>/a* /</code> means match 0 or more a's. To match <code>*</code> literally, precede the it with a backslash; for example, <code>/a\* /</code> matches 'a*'.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>^</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches beginning of input. If the multiline flag is set to true, also matches immediately after a line break character.
* </p><p>For example, <code>/^A/</code> does not match the 'A' in "an A", but does match the first 'A' in "An A."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>$</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches end of input. If the multiline flag is set to true, also matches immediately before a line break character.
* </p><p>For example, <code>/t$/</code> does not match the 't' in "eater", but does match it in "eat".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>*</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 0 or more times.
* </p><p>For example, <code>/bo* /</code> matches 'boooo' in "A ghost booooed" and 'b' in "A bird warbled", but nothing in "A goat grunted".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>+</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 1 or more times. Equivalent to <code>{1,}</code>.
* </p><p>For example, <code>/a+/</code> matches the 'a' in "candy" and all the a's in "caaaaaaandy".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>?</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 0 or 1 time.
* </p><p>For example, <code>/e?le?/</code> matches the 'el' in "angel" and the 'le' in "angle."
* </p><p>If used immediately after any of the quantifiers <code>*</code>, <code>+</code>, <code>?</code>, or <code>{}</code>, makes the quantifier non-greedy (matching the minimum number of times), as opposed to the default, which is greedy (matching the maximum number of times).
* </p><p>Also used in lookahead assertions, described under <code>(?=)</code>, <code>(?!)</code>, and <code>(?:)</code> in this table.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>.</code></td>
* <td colspan="1" rowspan="1">
* <p>(The decimal point) matches any single character except the newline characters: \n \r \u2028 or \u2029. (<code>[\s\S]</code> can be used to match any character including newlines.)
* </p><p>For example, <code>/.n/</code> matches 'an' and 'on' in "nay, an apple is on the tree", but not 'nay'.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>(<i>x</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> and remembers the match. These are called capturing parentheses.
* </p><p>For example, <code>/(foo)/</code> matches and remembers 'foo' in "foo bar." The matched substring can be recalled from the resulting array's elements <code>[1], ..., [<i>n</i>]</code> or from the predefined <code>RegExp</code> object's properties <code>$1, ..., $9</code>.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>(?:<i>x</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> but does not remember the match. These are called non-capturing parentheses. The matched substring can not be recalled from the resulting array's elements <code>[1], ..., [<i>n</i>]</code> or from the predefined <code>RegExp</code> object's properties <code>$1, ..., $9</code>.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>(?=<i>y</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> only if <code><i>x</i></code> is followed by <code><i>y</i></code>. For example, <code>/Jack(?=Sprat)/</code> matches 'Jack' only if it is followed by 'Sprat'. <code>/Jack(?=Sprat|Frost)/</code> matches 'Jack' only if it is followed by 'Sprat' or 'Frost'. However, neither 'Sprat' nor 'Frost' is part of the match results.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>(?!<i>y</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> only if <code><i>x</i></code> is not followed by <code><i>y</i></code>. For example, <code>/\d+(?!\.)/</code> matches a number only if it is not followed by a decimal point.
* </p><p><code>/\d+(?!\.)/.exec("3.141")</code> matches 141 but not 3.141.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>|<i>y</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches either <code><i>x</i></code> or <code><i>y</i></code>.
* </p><p>For example, <code>/green|red/</code> matches 'green' in "green apple" and 'red' in "red apple."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. Matches exactly <code><i>n</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{2}/</code> doesn't match the 'a' in "candy," but it matches all of the a's in "caandy," and the first two a's in "caaandy."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>,}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. Matches at least <code><i>n</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{2,}</code> doesn't match the 'a' in "candy", but matches all of the a's in "caandy" and in "caaaaaaandy."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>,<i>m</i>}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> and <code><i>m</i></code> are positive integers. Matches at least <code><i>n</i></code> and at most <code><i>m</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{1,3}/</code> matches nothing in "cndy", the 'a' in "candy," the first two a's in "caandy," and the first three a's in "caaaaaaandy". Notice that when matching "caaaaaaandy", the match is "aaa", even though the original string had more a's in it.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[<i>xyz</i>]</code></td>
* <td colspan="1" rowspan="1">
* <p>A character set. Matches any one of the enclosed characters. You can specify a range of characters by using a hyphen.
* </p><p>For example, <code>[abcd]</code> is the same as <code>[a-d]</code>. They match the 'b' in "brisket" and the 'c' in "ache".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[^<i>xyz</i>]</code></td>
* <td colspan="1" rowspan="1">
* <p>A negated or complemented character set. That is, it matches anything that is not enclosed in the brackets. You can specify a range of characters by using a hyphen.
* </p><p>For example, <code>[^abc]</code> is the same as <code>[^a-c]</code>. They initially match 'r' in "brisket" and 'h' in "chop."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[\b]</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a backspace. (Not to be confused with <code>\b</code>.)
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\b</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a word boundary, such as a space. (Not to be confused with <code>[\b]</code>.)
* </p><p>For example, <code>/\bn\w/</code> matches the 'no' in "noonday"; <code>/\wy\b/</code> matches the 'ly' in "possibly yesterday."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\B</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a non-word boundary.
* </p><p>For example, <code>/\w\Bn/</code> matches 'on' in "noonday", and <code>/y\B\w/</code> matches 'ye' in "possibly yesterday."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\c<i>X</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>X</i></code> is a letter from A - Z. Matches a control character in a string.
* </p><p>For example, <code>/\cM/</code> matches control-M in a string.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\d</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a digit character(Basic Latin alphabet). Equivalent to <code>[0-9]</code>.
* </p><p><b>Note</b>: In Firefox 2 and earlier, matches a digit character from any alphabet.(<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=378738" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=378738">bug 378738</a>)
* </p><p>For example, <code>/\d/</code> or <code>/[0-9]/</code> matches '2' in "B2 is the suite number."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\D</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any non-digit character(Basic Latin alphabet). Equivalent to <code>[^0-9]</code>.
* </p><p><b>Note</b>: In Firefox 2 and earlier, all  alphabet. (<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=378738" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=378738">bug 378738</a>)
* </p><p>For example, <code>/\D/</code> or <code>/[^0-9]/</code> matches 'B' in "B2 is the suite number."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\f</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a form-feed.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\n</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a linefeed.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\r</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a carriage return.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\s</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a single white space character, including space, tab, form feed, line feed and other unicode spaces.<small><sup id="ref_equivalent_s"><a href="RegExp#endnote_equivalent_s" rel="nofollow" shape="rect" title="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:RegExp#endnote_equivalent_s">[1]</a></sup></small>
* </p><p>For example, <code>/\s\w* /</code> matches ' bar' in "foo bar."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\S</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a single character other than white space.<small><sup id="ref_equivalent_S"><a href="RegExp#endnote_equivalent_S" rel="nofollow" shape="rect" title="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:RegExp#endnote_equivalent_S">[2]</a></sup></small>
* </p><p>For example, <code>/\S\w* /</code> matches 'foo' in "foo bar."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\t</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a tab.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\v</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a vertical tab.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\w</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any (Basic Latin alphabet) alphanumeric character including the underscore. Equivalent to <code>[A-Za-z0-9_]</code>.
* </p><p>For example, <code>/\w/</code> matches 'a' in "apple," '5' in "$5.28," and '3' in "3D."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\W</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any non-(Basic Latin)word character. Equivalent to <code>[^A-Za-z0-9_]</code>.
* </p><p>For example, <code>/\W/</code> or <code>/[^$A-Za-z0-9_]/</code> matches '%' in "50%."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\<i>n</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. A back reference to the last substring matching the n parenthetical in the regular expression (counting left parentheses).
* </p><p>For example, <code>/apple(,)\sorange\1/</code> matches 'apple, orange,' in "apple, orange, cherry, peach." A more complete example follows this table.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\0</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a NUL character. Do not follow this with another digit.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\x<i>hh</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the character with the code <code><i>hh</i></code> (two hexadecimal digits)
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\u<i>hhhh</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the character with code <code><i>hhhh</i></code> (four hexadecimal digits).
* </p>
* </td>
* </tr>
* </table>
* <p>The literal notation provides compilation of the regular expression when the expression is evaluated. Use literal notation when the regular expression will remain constant. For example, if you use literal notation to construct a regular expression used in a loop, the regular expression won't be recompiled on each iteration.
* </p><p>The constructor of the regular expression object, for example, <code>new RegExp("ab+c")</code>, provides runtime compilation of the regular expression. Use the constructor function when you know the regular expression pattern will be changing, or you don't know the pattern and are getting it from another source, such as user input.
* </p><p>A separate predefined <code>RegExp</code> object is available in each window; that is, each separate thread of JavaScript execution gets its own <code>RegExp</code> object. Because each script runs to completion without interruption in a thread, this assures that different scripts do not overwrite values of the <code>RegExp</code> object.
* </p>
* <ol><li><cite id="endnote_equivalent_s"><a href="RegExp#ref_equivalent_s" shape="rect" title=""><b>^</b></a></cite>ÊEquivalent to:
* </li></ol>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>[\t\n\v\f\r \u00a0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]</code>
* </pre>
* <ol><li><cite id="endnote_equivalent_S"><a href="RegExp#ref_equivalent_S" shape="rect" title=""><b>^</b></a></cite>ÊEquivalent to:
* </li></ol>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>[^\t\n\v\f\r \u00a0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]</code>
* </pre>
* <h2> <span> Properties </span></h2>
* <p>Note that several of the <code>RegExp</code> properties have both long and short (Perl-like) names. Both names always refer to the same value. Perl is the programming language from which JavaScript modeled its regular expressions.
* </p><p><a href="Core_JavaScript_1.5_Reference:Global_Objects:RegExp:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </p><p><a href="RegExp:global" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:global">global</a>: Whether to test the regular expression against all possible matches in a string, or only against the first. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:ignoreCase" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:ignoreCase">ignoreCase</a>: Whether to ignore case while attempting a match in a string. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:lastIndex" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:lastIndex">lastIndex</a>: The index at which to start the next match. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:multiline" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:multiline">multiline</a>: Whether or not to search in strings across multiple lines. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:prototype">prototype</a>: Allows the addition of properties to all objects.
* </p><p><a href="RegExp:source" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:source">source</a>: The text of the pattern. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:RegExp:exec" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:exec">exec</a>: Executes a search for a match in its string parameter.
* </p><p><a href="RegExp:test" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:test">test</a>: Tests for a match in its string parameter.
* </p><p><a href="RegExp:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:toSource">toSource</a>: Returns an object literal representing the specified object; you can use this value to create a new object. Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </p><p><a href="RegExp:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:toString">toString</a>: Returns a string representing the specified object. Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </p><p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using the <code>replace</code> method </span></h3>
* <p>The following script uses the <code>replace</code> method to switch the words in the string. In the replacement text, the script uses "<code>$1</code>" and "<code>$2</code>" to indicate the results of the corresponding matching parentheses in the regular expression pattern.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var re = /(\w+)\s(\w+)/;
* var str = "John Smith";
* var newstr = str.replace(re, "$2, $1");
* document.write(newstr);
* </pre>
* <p>This displays "Smith, John".
* </p><p>
* </p>
* <h2> <span> See also </span></h2>
* <ul><li>The <a href="Core_JavaScript_1.5_Guide:Regular_Expressions" shape="rect" title="Core JavaScript 1.5 Guide:Regular Expressions">Core JavaScript 1.5 Guide:Regular Expressions</a> chapter.
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function RegExp(pattern) {};
/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:RegExp&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:RegExp">Core JavaScript 1.5 Reference:Objects:RegExp</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>A regular expression object contains the pattern of a regular expression. It has properties and methods for using that regular expression to find and replace matches in strings.
* </p><p>In addition to the properties of an individual regular expression object that you create using the <code>RegExp</code> constructor function, the predefined <code>RegExp</code> object has static properties that are set whenever any regular expression is used.
* </p>
* <h2> <span> Created by </span></h2>
* <p>A literal text format or the <code>RegExp</code> constructor function.
* </p><p>The literal format is used as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><i>/pattern/flags</i>
* </pre>
* <p>The constructor function is used as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new RegExp("<i>pattern</i>"[, "<i>flags</i>"])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>pattern</code>Ê</dt><dd> The text of the regular expression.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>flags</code></dt><dd> If specified, flags can have any combination of the following values: <code>g</code> - global match, <code>i</code> - ignore case, <code>m</code> - match over multiple lines.
* </dd></dl>
* <p>Notice that the parameters to the literal format do not use quotation marks to indicate strings, while the parameters to the constructor function do use quotation marks. So the following expressions create the same regular expression:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">/ab+c/i
* new RegExp("ab+c", "i")
* </pre>
* <h2> <span> Description </span></h2>
* <p>When using the constructor function, the normal string escape rules (preceding special characters with \ when included in a string) are necessary. For example, the following are equivalent:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">re = new RegExp("\\w+")
* re = /\w+/
* </pre>
* <h3> <span> Special characters in regular expressions </span></h3>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Character</td>
* <td colspan="1" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Meaning</td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\</code></td>
* <td colspan="1" rowspan="1">
* <p>For characters that are usually treated literally, indicates that the next character is special and not to be interpreted literally.
* </p><p>For example, <code>/b/</code> matches the character 'b'. By placing a backslash in front of b, that is by using <code>/\b/</code>, the character becomes special to mean match a word boundary.
* </p><p>-or-
* </p><p>For characters that are usually treated specially, indicates that the next character is not special and should be interpreted literally.
* </p><p>For example, * is a special character that means 0 or more occurrences of the preceding character should be matched; for example, <code>/a* /</code> means match 0 or more a's. To match <code>*</code> literally, precede the it with a backslash; for example, <code>/a\* /</code> matches 'a*'.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>^</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches beginning of input. If the multiline flag is set to true, also matches immediately after a line break character.
* </p><p>For example, <code>/^A/</code> does not match the 'A' in "an A", but does match the first 'A' in "An A."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>$</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches end of input. If the multiline flag is set to true, also matches immediately before a line break character.
* </p><p>For example, <code>/t$/</code> does not match the 't' in "eater", but does match it in "eat".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>*</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 0 or more times.
* </p><p>For example, <code>/bo* /</code> matches 'boooo' in "A ghost booooed" and 'b' in "A bird warbled", but nothing in "A goat grunted".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>+</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 1 or more times. Equivalent to <code>{1,}</code>.
* </p><p>For example, <code>/a+/</code> matches the 'a' in "candy" and all the a's in "caaaaaaandy".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>?</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the preceding item 0 or 1 time.
* </p><p>For example, <code>/e?le?/</code> matches the 'el' in "angel" and the 'le' in "angle."
* </p><p>If used immediately after any of the quantifiers <code>*</code>, <code>+</code>, <code>?</code>, or <code>{}</code>, makes the quantifier non-greedy (matching the minimum number of times), as opposed to the default, which is greedy (matching the maximum number of times).
* </p><p>Also used in lookahead assertions, described under <code>(?=)</code>, <code>(?!)</code>, and <code>(?:)</code> in this table.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>.</code></td>
* <td colspan="1" rowspan="1">
* <p>(The decimal point) matches any single character except the newline characters: \n \r \u2028 or \u2029. (<code>[\s\S]</code> can be used to match any character including newlines.)
* </p><p>For example, <code>/.n/</code> matches 'an' and 'on' in "nay, an apple is on the tree", but not 'nay'.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>(<i>x</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> and remembers the match. These are called capturing parentheses.
* </p><p>For example, <code>/(foo)/</code> matches and remembers 'foo' in "foo bar." The matched substring can be recalled from the resulting array's elements <code>[1], ..., [<i>n</i>]</code> or from the predefined <code>RegExp</code> object's properties <code>$1, ..., $9</code>.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>(?:<i>x</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> but does not remember the match. These are called non-capturing parentheses. The matched substring can not be recalled from the resulting array's elements <code>[1], ..., [<i>n</i>]</code> or from the predefined <code>RegExp</code> object's properties <code>$1, ..., $9</code>.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>(?=<i>y</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> only if <code><i>x</i></code> is followed by <code><i>y</i></code>. For example, <code>/Jack(?=Sprat)/</code> matches 'Jack' only if it is followed by 'Sprat'. <code>/Jack(?=Sprat|Frost)/</code> matches 'Jack' only if it is followed by 'Sprat' or 'Frost'. However, neither 'Sprat' nor 'Frost' is part of the match results.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>(?!<i>y</i>)</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches <code><i>x</i></code> only if <code><i>x</i></code> is not followed by <code><i>y</i></code>. For example, <code>/\d+(?!\.)/</code> matches a number only if it is not followed by a decimal point.
* </p><p><code>/\d+(?!\.)/.exec("3.141")</code> matches 141 but not 3.141.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code><i>x</i>|<i>y</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches either <code><i>x</i></code> or <code><i>y</i></code>.
* </p><p>For example, <code>/green|red/</code> matches 'green' in "green apple" and 'red' in "red apple."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. Matches exactly <code><i>n</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{2}/</code> doesn't match the 'a' in "candy," but it matches all of the a's in "caandy," and the first two a's in "caaandy."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>,}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. Matches at least <code><i>n</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{2,}</code> doesn't match the 'a' in "candy", but matches all of the a's in "caandy" and in "caaaaaaandy."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>{<i>n</i>,<i>m</i>}</code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> and <code><i>m</i></code> are positive integers. Matches at least <code><i>n</i></code> and at most <code><i>m</i></code> occurrences of the preceding item.
* </p><p>For example, <code>/a{1,3}/</code> matches nothing in "cndy", the 'a' in "candy," the first two a's in "caandy," and the first three a's in "caaaaaaandy". Notice that when matching "caaaaaaandy", the match is "aaa", even though the original string had more a's in it.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[<i>xyz</i>]</code></td>
* <td colspan="1" rowspan="1">
* <p>A character set. Matches any one of the enclosed characters. You can specify a range of characters by using a hyphen.
* </p><p>For example, <code>[abcd]</code> is the same as <code>[a-d]</code>. They match the 'b' in "brisket" and the 'c' in "ache".
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[^<i>xyz</i>]</code></td>
* <td colspan="1" rowspan="1">
* <p>A negated or complemented character set. That is, it matches anything that is not enclosed in the brackets. You can specify a range of characters by using a hyphen.
* </p><p>For example, <code>[^abc]</code> is the same as <code>[^a-c]</code>. They initially match 'r' in "brisket" and 'h' in "chop."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>[\b]</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a backspace. (Not to be confused with <code>\b</code>.)
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\b</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a word boundary, such as a space. (Not to be confused with <code>[\b]</code>.)
* </p><p>For example, <code>/\bn\w/</code> matches the 'no' in "noonday"; <code>/\wy\b/</code> matches the 'ly' in "possibly yesterday."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\B</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a non-word boundary.
* </p><p>For example, <code>/\w\Bn/</code> matches 'on' in "noonday", and <code>/y\B\w/</code> matches 'ye' in "possibly yesterday."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\c<i>X</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>X</i></code> is a letter from A - Z. Matches a control character in a string.
* </p><p>For example, <code>/\cM/</code> matches control-M in a string.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\d</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a digit character(Basic Latin alphabet). Equivalent to <code>[0-9]</code>.
* </p><p><b>Note</b>: In Firefox 2 and earlier, matches a digit character from any alphabet.(<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=378738" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=378738">bug 378738</a>)
* </p><p>For example, <code>/\d/</code> or <code>/[0-9]/</code> matches '2' in "B2 is the suite number."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\D</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any non-digit character(Basic Latin alphabet). Equivalent to <code>[^0-9]</code>.
* </p><p><b>Note</b>: In Firefox 2 and earlier, all  alphabet. (<a href="https://bugzilla.mozilla.org/show_bug.cgi?id=378738" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=378738">bug 378738</a>)
* </p><p>For example, <code>/\D/</code> or <code>/[^0-9]/</code> matches 'B' in "B2 is the suite number."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\f</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a form-feed.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\n</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a linefeed.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\r</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a carriage return.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\s</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a single white space character, including space, tab, form feed, line feed and other unicode spaces.<small><sup id="ref_equivalent_s"><a href="RegExp#endnote_equivalent_s" rel="nofollow" shape="rect" title="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:RegExp#endnote_equivalent_s">[1]</a></sup></small>
* </p><p>For example, <code>/\s\w* /</code> matches ' bar' in "foo bar."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\S</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a single character other than white space.<small><sup id="ref_equivalent_S"><a href="RegExp#endnote_equivalent_S" rel="nofollow" shape="rect" title="http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:RegExp#endnote_equivalent_S">[2]</a></sup></small>
* </p><p>For example, <code>/\S\w* /</code> matches 'foo' in "foo bar."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\t</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a tab.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\v</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a vertical tab.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\w</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any (Basic Latin alphabet) alphanumeric character including the underscore. Equivalent to <code>[A-Za-z0-9_]</code>.
* </p><p>For example, <code>/\w/</code> matches 'a' in "apple," '5' in "$5.28," and '3' in "3D."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\W</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches any non-(Basic Latin)word character. Equivalent to <code>[^A-Za-z0-9_]</code>.
* </p><p>For example, <code>/\W/</code> or <code>/[^$A-Za-z0-9_]/</code> matches '%' in "50%."
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\<i>n</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Where <code><i>n</i></code> is a positive integer. A back reference to the last substring matching the n parenthetical in the regular expression (counting left parentheses).
* </p><p>For example, <code>/apple(,)\sorange\1/</code> matches 'apple, orange,' in "apple, orange, cherry, peach." A more complete example follows this table.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\0</code></td>
* <td colspan="1" rowspan="1">
* <p>Matches a NUL character. Do not follow this with another digit.
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\x<i>hh</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the character with the code <code><i>hh</i></code> (two hexadecimal digits)
* </p>
* </td>
* </tr>
* 
* <tr>
* <td colspan="1" rowspan="1"><code>\u<i>hhhh</i></code></td>
* <td colspan="1" rowspan="1">
* <p>Matches the character with code <code><i>hhhh</i></code> (four hexadecimal digits).
* </p>
* </td>
* </tr>
* </table>
* <p>The literal notation provides compilation of the regular expression when the expression is evaluated. Use literal notation when the regular expression will remain constant. For example, if you use literal notation to construct a regular expression used in a loop, the regular expression won't be recompiled on each iteration.
* </p><p>The constructor of the regular expression object, for example, <code>new RegExp("ab+c")</code>, provides runtime compilation of the regular expression. Use the constructor function when you know the regular expression pattern will be changing, or you don't know the pattern and are getting it from another source, such as user input.
* </p><p>A separate predefined <code>RegExp</code> object is available in each window; that is, each separate thread of JavaScript execution gets its own <code>RegExp</code> object. Because each script runs to completion without interruption in a thread, this assures that different scripts do not overwrite values of the <code>RegExp</code> object.
* </p>
* <ol><li><cite id="endnote_equivalent_s"><a href="RegExp#ref_equivalent_s" shape="rect" title=""><b>^</b></a></cite>ÊEquivalent to:
* </li></ol>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>[\t\n\v\f\r \u00a0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]</code>
* </pre>
* <ol><li><cite id="endnote_equivalent_S"><a href="RegExp#ref_equivalent_S" shape="rect" title=""><b>^</b></a></cite>ÊEquivalent to:
* </li></ol>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve"><code>[^\t\n\v\f\r \u00a0\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u2028\u2029\u3000]</code>
* </pre>
* <h2> <span> Properties </span></h2>
* <p>Note that several of the <code>RegExp</code> properties have both long and short (Perl-like) names. Both names always refer to the same value. Perl is the programming language from which JavaScript modeled its regular expressions.
* </p><p><a href="Core_JavaScript_1.5_Reference:Global_Objects:RegExp:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </p><p><a href="RegExp:global" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:global">global</a>: Whether to test the regular expression against all possible matches in a string, or only against the first. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:ignoreCase" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:ignoreCase">ignoreCase</a>: Whether to ignore case while attempting a match in a string. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:lastIndex" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:lastIndex">lastIndex</a>: The index at which to start the next match. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:multiline" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:multiline">multiline</a>: Whether or not to search in strings across multiple lines. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p><p><a href="RegExp:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:prototype">prototype</a>: Allows the addition of properties to all objects.
* </p><p><a href="RegExp:source" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:source">source</a>: The text of the pattern. As of JavaScript 1.5, a property of a RegExp instance, not the RegExp object.
* </p>
* <h2> <span> Methods </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:RegExp:exec" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:exec">exec</a>: Executes a search for a match in its string parameter.
* </p><p><a href="RegExp:test" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:test">test</a>: Tests for a match in its string parameter.
* </p><p><a href="RegExp:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:toSource">toSource</a>: Returns an object literal representing the specified object; you can use this value to create a new object. Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </p><p><a href="RegExp:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:RegExp:toString">toString</a>: Returns a string representing the specified object. Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </p><p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using the <code>replace</code> method </span></h3>
* <p>The following script uses the <code>replace</code> method to switch the words in the string. In the replacement text, the script uses "<code>$1</code>" and "<code>$2</code>" to indicate the results of the corresponding matching parentheses in the regular expression pattern.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var re = /(\w+)\s(\w+)/;
* var str = "John Smith";
* var newstr = str.replace(re, "$2, $1");
* document.write(newstr);
* </pre>
* <p>This displays "Smith, John".
* </p><p>
* </p>
* <h2> <span> See also </span></h2>
* <ul><li>The <a href="Core_JavaScript_1.5_Guide:Regular_Expressions" shape="rect" title="Core JavaScript 1.5 Guide:Regular Expressions">Core JavaScript 1.5 Guide:Regular Expressions</a> chapter.
* </li></ul>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function RegExp(pattern,flags) {};

