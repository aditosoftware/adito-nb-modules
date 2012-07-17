/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Date&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Core JavaScript 1.5 Reference:Objects:Date</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>Lets you work with dates and times.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Date</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Date()
* new Date(<i>milliseconds</i>)
* new Date(<i>dateString</i>)
* new Date(<i>yr_num</i>, <i>mo_num</i>, <i>day_num</i>
* [, <i>hr_num</i>, <i>min_num</i>, <i>sec_num</i>, <i>ms_num</i>])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>milliseconds</code>Ê</dt><dd> Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> String value representing a date. The string should be in a format recognized by the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>yr_num, mo_num, day_num</code>Ê</dt><dd> Integer values representing part of a date. As an integer value, the month is represented by 0 to 11 with 0=January and 11=December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hr_num, min_num, sec_num, ms_num</code>Ê</dt><dd> Integer values representing part of a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you supply no arguments, the constructor creates a <code>Date</code> object for today's date and time according to local time. If you supply some arguments but not others, the missing arguments are set to 0. If you supply any arguments, you must supply at least the year, month, and day. You can omit the hours, minutes, seconds, and milliseconds.
* </p><p>The date is measured in milliseconds since midnight 01 January, 1970 UTC. A day holds 86,400,000 milliseconds. The Date object range is -100,000,000 days to 100,000,000 days relative to 01 January, 1970 UTC.
* </p><p>The <code>Date</code> object provides uniform behavior across platforms.
* </p><p>The <code>Date</code> object supports a number of UTC (universal) methods, as well as local time methods. UTC, also known as Greenwich Mean Time (GMT), refers to the time as set by the World Time Standard. The local time is the time known to the computer where JavaScript is executed.
* </p><p>For compatibility with millennium calculations (in other words, to take into account the year 2000), you should always specify the year in full; for example, use 1998, not 98. To assist you in specifying the complete year, JavaScript includes the methods <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a></code>, <code><a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a></code>, <code><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a></code>, and <code><a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a></code>.
* </p><p>The following example returns the time elapsed between <code>timeA</code> and <code>timeB</code> in milliseconds.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">timeA = new Date();
* // Statements here to take some action.
* timeB = new Date();
* timeDifference = timeB - timeA;
* </pre>
* <h2> <span> Properties </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </li><li> <a href="Date:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:prototype">prototype</a>: Allows the addition of properties to a <code>Date</code> object.
* </li></ul>
* <h2> <span> Static methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:now" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:now">now</a>: Returns the numeric value corresponding to the current time.
* </li><li> <a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>: Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </li><li> <a href="Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">UTC</a>: Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </li></ul>
* <h2> <span> Methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDate">getDate</a>: Returns the day of the month for the specified date according to local time.
* </li><li> <a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDay">getDay</a>: Returns the day of the week for the specified date according to local time.
* </li><li> <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a>: Returns the year of the specified date according to local time.
* </li><li> <a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a>: Returns the hour in the specified date according to local time.
* </li><li> <a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMilliseconds">getMilliseconds</a>: Returns the milliseconds in the specified date according to local time.
* </li><li> <a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a>: Returns the minutes in the specified date according to local time.
* </li><li> <a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMonth">getMonth</a>: Returns the month in the specified date according to local time.
* </li><li> <a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a>: Returns the seconds in the specified date according to local time.
* </li><li> <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>: Returns the numeric value corresponding to the time for the specified date according to universal time.
* </li><li> <a href="Date:getTimezoneOffset" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTimezoneOffset">getTimezoneOffset</a>: Returns the time-zone offset in minutes for the current locale.
* </li><li> <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>: Returns the day (date) of the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>: Returns the day of the week in the specified date according to universal time.
* </li><li> <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>: Returns the year in the specified date according to universal time.
* </li><li> <a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>: Returns the hours in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>: Returns the milliseconds in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMinutes">getUTCMinutes</a>: Returns the minutes in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMonth">getUTCMonth</a>: Returns the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCSeconds">getUTCSeconds</a>: Returns the seconds in the specified date according to universal time.
* </li><li> <a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Returns the year in the specified date according to local time. Use <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a> instead.
* </li><li> <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>: Sets the day of the month for a specified date according to local time.
* </li><li> <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>: Sets the full year for a specified date according to local time.
* </li><li> <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setHours">setHours</a>: Sets the hours for a specified date according to local time.
* </li><li> <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMilliseconds">setMilliseconds</a>: Sets the milliseconds for a specified date according to local time.
* </li><li> <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMinutes">setMinutes</a>: Sets the minutes for a specified date according to local time.
* </li><li> <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMonth">setMonth</a>: Sets the month for a specified date according to local time.
* </li><li> <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setSeconds">setSeconds</a>: Sets the seconds for a specified date according to local time.
* </li><li> <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>: Sets the value of the <code>Date</code> object according to local time.
* </li><li> <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCDate">setUTCDate</a>: Sets the day of the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a>: Sets the full year for a specified date according to universal time.
* </li><li> <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCHours">setUTCHours</a>: Sets the hour for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>: Sets the milliseconds for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMinutes">setUTCMinutes</a>: Sets the minutes for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMonth">setUTCMonth</a>: Sets the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCSeconds">setUTCSeconds</a>: Sets the seconds for a specified date according to universal time.
* </li><li> <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setYear">setYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Sets the year for a specified date according to local time. Use <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a> instead.
* </li><li> <a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>: Converts a date to a string, using the Internet GMT conventions. Use <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a> instead.
* </li><li> <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>: Converts a date to a string, using the current locale's conventions. Overrides the <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a> method.
* </li><li> <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>: Returns the "date" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toLocaleFormat" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleFormat">toLocaleFormat</a>: Converts a date to a string, using a format string.
* </li><li> <a href="Date:toLocaleTimeString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleTimeString">toLocaleTimeString</a>: Returns the "time" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toSource">toSource</a>: Returns an object literal representing the specified <code>Date</code> object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </li><li> <a href="Date:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toString">toString</a>: Returns a string representing the specified <code>Date</code> object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </li><li> <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>: Converts a date to a string, using the universal time convention.
* </li><li> <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>: Returns the primitive value of a <code>Date</code> object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </li></ul>
* <p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Several ways to assign dates </span></h3>
* <p>The following examples show several ways to assign dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date();
* birthday = new Date("December 17, 1995 03:24:00");
* birthday = new Date(1995,11,17);
* birthday = new Date(1995,11,17,3,24,0);
* </pre>
* <h3> <span> Example: Calculating elapsed time </span></h3>
* <p>The following examples show how to determine the elapsed time between two dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// using static methods
* var start = Date.now();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = Date.now();
* var elapsed = end - start; // time in milliseconds
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// if you have Date objects
* var start = new Date();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = new Date();
* var elapsed = end.getTime() - start.getTime(); // time in milliseconds
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
var Date = {
  // This is just a stub for a builtin native JavaScript object.
/**
* <h2> <span> Summary </span></h2>
* <p>Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1"><b>Static</b></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: added <code>ms</code> parameter.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Date.UTC(<i>year</i>, <i>month</i>[, <i>date</i>[, <i>hrs</i>[, <i>min</i>[, <i>sec</i>[, <i>ms</i>]]]]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>year</code>Ê</dt><dd> A year after 1900.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>month</code>Ê</dt><dd> An integer between 0 and 11 representing the month.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>date</code>Ê</dt><dd> An integer between 1 and 31 representing the day of the month.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hrs</code>Ê</dt><dd> An integer between 0 and 23 representing the hours.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>min</code>Ê</dt><dd> An integer between 0 and 59 representing the minutes.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>sec</code>Ê</dt><dd> An integer between 0 and 59 representing the seconds.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>ms</code>Ê</dt><dd> An integer between 0 and 999 representing the milliseconds.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>UTC</code> takes comma-delimited date parameters and returns the number of milliseconds between January 1, 1970, 00:00:00, universal time and the time you specified.
* </p><p>You should specify a full year for the year; for example, 1998. If a year between 0 and 99 is specified, the method converts the year to a year in the 20th century (1900 + year); for example, if you specify 95, the year 1995 is used.
* </p><p>The <code>UTC</code> method differs from the <code>Date</code> constructor in two ways.
* </p>
* <ul><li> <code>Date.UTC</code> uses universal time instead of the local time.
* </li><li> <code>Date.UTC</code> returns a time value as a number instead of creating a <code>Date</code> object.
* </li></ul>
* <p>If a parameter you specify is outside of the expected range, the <code>UTC</code> method updates the other parameters to allow for your number. For example, if you use 15 for month, the year will be incremented by 1 (year + 1), and 3 will be used for the month.
* </p><p>Because <code>UTC</code> is a static method of <code>Date</code>, you always use it as <code>Date.UTC()</code>, rather than as a method of a <code>Date</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>Date.UTC</code> </span></h3>
* <p>The following statement creates a <code>Date</code> object using GMT instead of local time:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">gmtDate = new Date(Date.UTC(96, 11, 1, 0, 0, 0));
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
UTC: function(year, month, date, hrs, min, sec, ms) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns a reference to the <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a> function that created the instance's prototype. Note that the value of this property is a reference to the function itself, not a string containing the function's name.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
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
* <p>Returns the day of the month for the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
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
* getDate()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getDate</code> is an integer between 1 and 31.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getDate</code> </span></h3>
* <p>The second statement below assigns the value 25 to the variable <code>day</code>, based on the value of the <code>Date</code> object <code>Xmas95</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas95 = new Date("December 25, 1995 23:15:00")
* day = Xmas95.getDate()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>,
* <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>,
* <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getDate: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the day of the week for the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getDay()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getDay</code> is an integer corresponding to the day of the week: 0 for Sunday, 1 for Monday, 2 for Tuesday, and so on.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getDay</code> </span></h3>
* <p>The second statement below assigns the value 1 to <code>weekday</code>, based on the value of the <code>Date</code> object <code>Xmas95</code>. December 25, 1995, is a Monday.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas95 = new Date("December 25, 1995 23:15:00");
* weekday = Xmas95.getDay();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>,
* <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getDay: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the year of the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getFullYear()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getFullYear</code> is an absolute number. For dates between the years 1000 and 9999, <code>getFullYear</code> returns a four-digit number, for example, 1995. Use this function to make sure a year is compliant with years after 2000.
* </p><p>Use this method instead of the <code>getYear</code> method.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getFullYear</code> </span></h3>
* <p>The following example assigns the four-digit value of the current year to the variable <code>yr</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var today = new Date();
* var yr = today.getFullYear();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a>,
* <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>,
* <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getFullYear: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the hour for the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getHours()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getHours</code> is an integer between 0 and 23.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getHours</code> </span></h3>
* <p>The second statement below assigns the value 23 to the variable <code>hours</code>, based on the value of the <code>Date</code> object <code>Xmas95</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas95 = new Date("December 25, 1995 23:15:00")
* hours = Xmas95.getHours()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCHours">getUTCHours</a>,
* <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setHours">setHours</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getHours: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the milliseconds in the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getMilliseconds()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getMilliseconds</code> is a number between 0 and 999.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getMilliseconds</code> </span></h3>
* <p>The following example assigns the milliseconds portion of the current time to the variable <code>ms</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var ms;
* Today = new Date();
* ms = Today.getMilliseconds();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>,
* <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setMilliseconds">setMilliseconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getMilliseconds: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the minutes in the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getMinutes()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getMinutes</code> is an integer between 0 and 59.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getMinutes</code> </span></h3>
* <p>The second statement below assigns the value 15 to the variable <code>minutes</code>, based on the value of the <code>Date</code> object <code>Xmas95</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas95 = new Date("December 25, 1995 23:15:00")
* minutes = Xmas95.getMinutes()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCMinutes">getUTCMinutes</a>,
* <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setMinutes">setMinutes</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getMinutes: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the month in the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getMonth()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getMonth</code> is an integer between 0 and 11.  0 corresponds to January, 1 to February, and so on.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getMonth</code> </span></h3>
* <p>The second statement below assigns the value 11 to the variable <code>month</code>, based on the value of the <code>Date</code> object <code>Xmas95</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas95 = new Date("December 25, 1995 23:15:00")
* month = Xmas95.getMonth()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCMonth">getUTCMonth</a>,
* <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setMonth">setMonth</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getMonth: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the seconds in the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getSeconds()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getSeconds</code> is an integer between 0 and 59.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getSeconds</code> </span></h3>
* <p>The second statement below assigns the value 30 to the variable <code>secs</code>, based on the value of the <code>Date</code> object <code>Xmas95</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas95 = new Date("December 25, 1995 23:15:30")
* secs = Xmas95.getSeconds()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCSeconds">getUTCSeconds</a>,
* <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setSeconds">setSeconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getSeconds: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the numeric value corresponding to the time for the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getTime()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by the <code>getTime</code> method is the number of milliseconds since 1 January 1970 00:00:00. You can use this method to help assign a date and time to another <code>Date</code> object.
* </p><p>This method is functionally equivalent to the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a> method.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getTime</code> </span></h3>
* <p>The following example assigns the date value of <code>theBigDay</code> to <code>sameAsBigDay</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date("July 1, 1999")
* sameAsBigDay = new Date()
* sameAsBigDay.setTime(theBigDay.getTime())
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>,
* <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>,
* <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getTime: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the time-zone offset in minutes for the current locale.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getTimezoneOffset()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The time-zone offset is the minutes in difference, the Greenwich Mean Time (GMT) is relative to your local time. For example, if your time zone is GMT+10, -600 will be returned. Daylight savings time prevents this value from being a constant.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getTimezoneOffset</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* x = new Date()
* currentTimeZoneOffsetInHours = x.getTimezoneOffset()/60
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getTimezoneOffset: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the day (date) of the month in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCDate()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCDate</code> is an integer between 1 and 31.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCDate</code> </span></h3>
* <p>The following example assigns the day portion of the current date to the variable <code>d</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var d;
* Today = new Date();
* d = Today.getUTCDate();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getDate">getDate</a>,
* <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCDay">getUTCDay</a>,
* <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCDate">setUTCDate</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCDate: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the day of the week in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCDay()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCDay</code> is an integer corresponding to the day of the week: 0 for Sunday, 1 for Monday, 2 for Tuesday, and so on.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCDay</code> </span></h3>
* <p>The following example assigns the weekday portion of the current date to the variable <code>weekday</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var weekday;
* Today = new Date()
* weekday = Today.getUTCDay()
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getDay">getDay</a>,
* <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCDate">getUTCDate</a>,
* <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCDate">setUTCDate</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCDay: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the year in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCFullYear()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCFullYear</code> is an absolute number that is compliant with year-2000, for example, 1995.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCFullYear</code> </span></h3>
* <p>The following example assigns the four-digit value of the current year to the variable <code>yr</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var yr;
* Today = new Date();
* yr = Today.getUTCFullYear();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getFullYear">getFullYear</a>,
* <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setFullYear">setFullYear</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCFullYear: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the hours in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCHours
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCHours</code> is an integer between 0 and 23.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCHours</code> </span></h3>
* <p>The following example assigns the hours portion of the current time to the variable <code>hrs</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var hrs;
* Today = new Date();
* hrs = Today.getUTCHours();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getHours">getHours</a>,
* <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCHours">setUTCHours</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCHours: undefined,
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the milliseconds in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCMilliseconds()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCMilliseconds</code> is an integer between 0 and 999.
* </p>
* <h2> <span> Examples </span></h2>
* <h2> <span> Example: Using <code>getUTCMilliseconds</code> =</span></h2>
* <p>The following example assigns the milliseconds portion of the current time to the variable <code>ms</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var ms;
* Today = new Date();
* ms = Today.getUTCMilliseconds();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMilliseconds">getMilliseconds</a>,
* <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCMilliseconds">setUTCmilliseconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCMilliseconds: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the minutes in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCMinutes()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCMinutes</code> is an integer between 0 and 59.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCMinutes</code> </span></h3>
* <p>The following example assigns the minutes portion of the current time to the variable <code>min</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var min;
* Today = new Date();
* min = Today.getUTCMinutes();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMinutes">getMinutes</a>,
* <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCMinutes">setUTCMinutes</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
getUTCMinutes: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the month of the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCMonth()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCMonth</code> is an integer between 0 and 11 corresponding to the month.  0 for January, 1 for February, 2 for March, and so on.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCMonth</code> </span></h3>
* <p>The following example assigns the month portion of the current date to the variable <code>mon</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var mon;
* Today = new Date();
* mon = Today.getUTCMonth();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMonth">getMonth</a>,
* <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCMonth">setUTCMonth</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCMonth: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the seconds in the specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getUTCSeconds()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>getUTCSeconds</code> is an integer between 0 and 59.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>getUTCSeconds</code> </span></h3>
* <p>The following example assigns the seconds portion of the current time to the variable <code>sec</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var sec;
* Today = new Date();
* sec = Today.getUTCSeconds();
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getSeconds">getSeconds</a>,
* <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCSeconds">setUTCSeconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getUTCSeconds: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <div style="border: 1px solid #5151FF; background-color: #B9B9FF; font-weight: bold; text-align: center; padding: 0px 10px 0px 10px; margin: 10px 0px 10px 0px;"><p style="margin: 4px 0px 4px 0px;">Deprecated</p></div>
* 
* <h2> <span> Summary </span></h2>
* <p>Returns the year in the specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: deprecated; also <code>getYear</code> returns the year minus 1900 regardless of the date specified.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* getYear()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p><code>getYear</code> is no longer used and has been replaced by the <a href="Core_JavaScript_1.5_Reference:Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getFullYear">getFullYear</a> method.
* </p><p>The <code>getYear</code> method returns the year minus 1900; thus:
* </p>
* <ul><li> For years greater than or equal to 2000, the value returned by <code>getYear</code> is 100 or greater. For example, if the year is 2026, <code>getYear</code> returns 126.
* </li></ul>
* <ul><li> For years between and including 1900 and 1999, the value returned by <code>getYear</code> is between 0 and 99. For example, if the year is 1976, <code>getYear</code> returns 76.
* </li></ul>
* <ul><li> For years less than 1900, the value returned by <code>getYear</code> is less than 0. For example, if the year is 1800, <code>getYear</code> returns -100.
* </li></ul>
* <p>To take into account years before and after 2000, you should use <code><a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getFullYear">getFullYear</a></code> instead of <code>getYear</code> so that the year is specified in full.
* </p>
* <h2> <span> Backward Compatibility </span></h2>
* <h3> <span> JavaScript 1.2 and earlier </span></h3>
* <p>The <code>getYear</code> method returns either a 2-digit or 4-digit year:
* </p>
* <ul><li> For years between and including 1900 and 1999, the value returned by <code>getYear</code> is the year minus 1900. For example, if the year is 1976, the value returned is 76.
* </li></ul>
* <ul><li> For years less than 1900 or greater than 1999, the value returned by <code>getYear</code> is the four-digit year. For example, if the year is 1856, the value returned is 1856. If the year is 2026, the value returned is 2026.
* </li></ul>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Years between 1900 and 1999 </span></h3>
* <p>The second statement assigns the value 95 to the variable <code>year</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas = new Date("December 25, 1995 23:15:00")
* year = Xmas.getYear() // returns 95
* </pre>
* <h3> <span> Example: Years above 1999 </span></h3>
* <p>The second statement assigns the value 100 to the variable <code>year</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas = new Date("December 25, 2000 23:15:00")
* year = Xmas.getYear() // returns 100
* </pre>
* <h3> <span> Example: Years below 1900 </span></h3>
* <p>The second statement assigns the value -100 to the variable <code>year</code>.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas = new Date("December 25, 1800 23:15:00")
* year = Xmas.getYear() // returns -100
* </pre>
* <h3> <span> Example: Setting and getting a year between 1900 and 1999 </span></h3>
* <p>The second statement assigns the value 95 to the variable <code>year</code>, representing the year 1995.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Xmas.setYear(95)
* year = Xmas.getYear() // returns 95
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getFullYear">getFullYear</a>,
* <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCFullYear">getUTCFullYear</a>,
* <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setYear">setYear</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
getYear: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the number of milliseconds elapsed since 1 January 1970 00:00:00 UTC.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1"><b>Static</b></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Not part of any standard</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var timeInMs = Date.now();
* </pre>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>now</code> method returns the milliseconds elapsed since 1 January 1970 00:00:00 UTC up until now as a <a href="Core_JavaScript_1.5_Reference:Global_Objects:Number" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Number">number</a>.
* </p><p>When using <code>now</code> to create timestamps or unique IDs, keep in mind that the resolution may be 15 milliseconds on Windows (see <a href="https://bugzilla.mozilla.org/show_bug.cgi?id=363258" rel="nofollow" shape="rect" title="https://bugzilla.mozilla.org/show_bug.cgi?id=363258">bug 363258</a>), so you could end up with several equal values if <code>now</code> is called multiple times within a short time span.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>now</code> </span></h3>
* <p>The following example uses <code>now</code> to create a timestamp.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var timestamp = Date.now();
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
now: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="2" rowspan="1"><b>Static</b></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NEX 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* Date.parse(dateString)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> A string representing a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>parse</code> method takes a date string (such as "<code>Dec 25, 1995</code>") and returns the number of milliseconds since January 1, 1970, 00:00:00 (local time). This function is useful for setting date values based on string values, for example in conjunction with the <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a></code> method and the <code><a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></code> object.
* </p><p>Given a string representing a time, <code>parse</code> returns the time value. It accepts the IETF standard date syntax: "<code>Mon, 25 Dec 1995 13:30:00 GMT</code>". It understands the continental US time-zone abbreviations, but for general use, use a time-zone offset, for example, "<code>Mon, 25 Dec 1995 13:30:00 GMT+0430</code>" (4 hours, 30 minutes east of the Greenwich meridian). If you do not specify a time zone, the local time zone is assumed. GMT and UTC are considered equivalent.
* </p><p>Because <code>parse</code> is a static method of <code>Date</code>, you always use it as <code>Date.parse()</code>, rather than as a method of a <code>Date</code> object you created.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>parse</code> </span></h3>
* <p>If <code>IPOdate</code> is an existing <code>Date</code> object, then you can set it to August 9, 1995 as follows:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">IPOdate.setTime(Date.parse("Aug 9, 1995"))Ê;
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">Date.UTC</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
*/
parse: function(dateString) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Represents the prototype for this class. You can use the prototype to add properties or methods to all instances of a class. For information on prototypes, see <a href="Function:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Function:prototype">Function.prototype</a>.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Property of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
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
* <h2> <span> Summary </span></h2>
* <p>Sets the day of the month for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setDate(<i>dayValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>dayValue</code>Ê</dt><dd> An integer from 1 to 31, representing the day of the month.
* </dd></dl>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setDate</code> </span></h3>
* <p>The second statement below changes the day for <code>theBigDay</code> to July 24 from its original value.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date("July 27, 1962 23:30:00")
* theBigDay.setDate(24)
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getDate">getDate</a>,
* <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCDate">setUTCDate</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setDate: function(dayValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the full year for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setFullYear(<i>yearValue</i>[, <i>monthValue</i>[, <i>dayValue</i>]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>yearValue</code>Ê</dt><dd> An integer specifying the numeric value of the year, for example, 1995.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>monthValue</code>Ê</dt><dd> An integer between 0 and 11 representing the months January through December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dayValue</code>Ê</dt><dd> An integer between 1 and 31 representing the day of the month. If you specify the dayValue parameter, you must also specify the monthValue.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>monthValue</code> and <code>dayValue</code> parameters, the values returned from the <code>getMonth</code> and <code>getDate</code> methods are used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setFullYear</code> attempts to update the other parameters and the date information in the <code>Date</code> object accordingly. For example, if you specify 15 for <code>monthValue</code>, the year is incremented by 1 (year + 1), and 3 is used for the month.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setFullYear</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setFullYear(1997);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCFullYear">getUTCFullYear</a>,
* <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCFullYear">setUTCFullYear</a>,
* <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setYear">setYear</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setFullYear: function(yearValue, monthValue, dayValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the hours for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: Added <code>minutesValue</code>, <code>secondsValue</code>, and <code>msValue</code> parameters.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setHours(<i>hoursValue</i>[, <i>minutesValue</i>[, <i>secondsValue</i>[, <i>msValue</i>]]])
* </code>
* </p>
* <h3> <span> Versions prior to JavaScript 1.3 </span></h3>
* <p><code>
* setHours(<i>hoursValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>hoursValue</code>Ê</dt><dd> An integer between 0 and 23, representing the hour.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>minutesValue</code>Ê</dt><dd> An integer between 0 and 59, representing the minutes.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>secondsValue</code>Ê</dt><dd> An integer between 0 and 59, representing the seconds. If you specify the <code>secondsValue</code> parameter, you must also specify the <code>minutesValue</code>.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>msValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds. If you specify the <code>msValue</code> parameter, you must also specify the <code>minutesValue</code> and <code>secondsValue</code>.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>minutesValue</code>, <code>secondsValue</code>, and <code>msValue</code> parameters, the values returned from the <code>getUTCMinutes</code>, <code>getUTCSeconds</code>, and <code>getMilliseconds</code> methods are used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setHours</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 100 for <code>secondsValue</code>, the minutes will be incremented by 1 (min + 1), and 40 will be used for seconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setHours</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay.setHours(7)
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getHours">getHours</a>,
* <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCHours">setUTCHours</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setHours: function(hoursValue, minutesValue, secondsValue, msValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the milliseconds for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setMilliseconds(<i>millisecondsValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>millisecondsValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you specify a number outside the expected range, the date information in the <code>Date</code> object is updated accordingly. For example, if you specify 1005, the number of seconds is incremented by 1, and 5 is used for the milliseconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setMilliseconds</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setMilliseconds(100);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMilliseconds">getMilliseconds</a>,
* <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setMilliseconds: function(millisecondsValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the minutes for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: Added <code>secondsValue</code> and <code>msValue</code> parameters.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setMinutes(<i>minutesValue</i>[, <i>secondsValue</i>[, <i>msValue</i>]])
* </code>
* </p>
* <h3> <span> Versions prior to JavaScript 1.3 </span></h3>
* <p><code>
* setMinutes(<i>minutesValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>minutesValue</code>Ê</dt><dd> An integer between 0 and 59, representing the minutes.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>secondsValue</code>Ê</dt><dd> An integer between 0 and 59, representing the seconds. If you specify the <code>secondsValue</code> parameter, you must also specify the <code>minutesValue</code>.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>msValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds. If you specify the <code>msValue</code> parameter, you must also specify the <code>minutesValue</code> and <code>secondsValue</code>.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>secondsValue</code> and <code>msValue</code> parameters, the values returned from <code>getSeconds</code> and <code>getMilliseconds</code> methods are used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setMinutes</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 100 for <code>secondsValue</code>, the minutes (<code>minutesValue</code>) will be incremented by 1 (<code>minutesValue</code> + 1), and 40 will be used for seconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setMinutes</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay.setMinutes(45)
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMinutes">getMinutes</a>,
* <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCMinutes">setUTCMinutes</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setMinutes: function(minutesValue, secondsValue, msValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Set the month for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: Added <code>dayValue</code> parameter.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setMonth(<i>monthValue</i>[, <i>dayValue</i>])
* </code>
* </p>
* <h3> <span> Versions prior to JavaScript 1.3 </span></h3>
* <p><code>
* setMonth(<i>monthValue</i>)
* </code>
* </p>
* <h2> <span> Parameter </span></h2>
* <dl><dt style="font-weight:bold"> <code>monthValue</code>Ê</dt><dd> An integer between 0 and 11 (representing the months January through December).
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dayValue</code>Ê</dt><dd> An integer from 1 to 31, representing the day of the month.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>dayValue</code> parameter, the value returned from the <code>getDate</code> method is used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setMonth</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 15 for <code>monthValue</code>, the year will be incremented by 1 (year + 1), and 3 will be used for month.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setMonth</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay.setMonth(6)
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMonth">getMonth</a>,
* <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCMonth">setUTCMonth</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setMonth: function(monthValue, dayValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the seconds for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: Added <code>msValue</code> parameter.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setSeconds(<i>secondsValue</i>[, <i>msValue</i>])
* </code>
* </p>
* <h3> <span> Versions prior to JavaScript 1.3 </span></h3>
* <p><code>
* setSeconds(<i>secondsValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>secondsValue</code>Ê</dt><dd> An integer between 0 and 59.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>msValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>msValue</code> parameter, the value returned from the <code>getMilliseconds</code> method is used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setSeconds</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 100 for <code>secondsValue</code>, the minutes stored in the <code>Date</code> object will be incremented by 1, and 40 will be used for seconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setSeconds</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay.setSeconds(30)
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getSeconds">getSeconds</a>,
* <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCSeconds">setUTCSeconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setSeconds: function(secondsValue, msValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the value of a <code>Date</code> object according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setTime(<i>timeValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>timeValue</code>Ê</dt><dd> An integer representing the number of milliseconds since 1 January 1970, 00:00:00.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>Use the <code>setTime</code> method to help assign a date and time to another <code>Date</code> object.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setTime</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date("July 1, 1999")
* sameAsBigDay = new Date()
* sameAsBigDay.setTime(theBigDay.getTime())
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getTime">getTime</a>,
* <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCHours">setUTCHours</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setTime: function(timeValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the day of the month for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCDate(<i>dayValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>dayValue</code>Ê</dt><dd> An integer from 1 to 31, representing the day of the month.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If a parameter you specify is outside of the expected range, <code>setUTCDate</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 40 for <code>dayValue</code>, and the month stored in the <code>Date</code> object is June, the day will be changed to 10 and the month will be incremented to July.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCDate</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCDate(20);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCDate">getUTCDate</a>,
* <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setDate">setDate</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCDate: function(dayValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the full year for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCFullYear(<i>yearValue</i>[, <i>monthValue</i>[, <i>dayValue</i>]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>yearValue</code>Ê</dt><dd> An integer specifying the numeric value of the year, for example, 1995.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>monthValue</code>Ê</dt><dd> An integer between 0 and 11 representing the months January through December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dayValue</code>Ê</dt><dd> An integer between 1 and 31 representing the day of the month. If you specify the <code>dayValue</code> parameter, you must also specify the <code>monthValue</code>.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>monthValue</code> and <code>dayValue</code> parameters, the values returned from the <code>getMonth</code> and <code>getDate</code> methods are used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setUTCFullYear</code> attempts to update the other parameters and the date information in the <code>Date</code> object accordingly. For example, if you specify 15 for <code>monthValue</code>, the year is incremented by 1 (year + 1), and 3 is used for the month.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCFullYear</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCFullYear(1997);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCFullYear">getUTCFullYear</a>,
* <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setFullYear">setFullYear</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCFullYear: function(yearValue, monthValue, dayValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the hour for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCHours(<i>hoursValue</i>[, <i>minutesValue</i>[, <i>secondsValue</i>[, <i>msValue</i>]]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>hoursValue</code>Ê</dt><dd> An integer between 0 and 23, representing the hour.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>minutesValue</code>Ê</dt><dd> An integer between 0 and 59, representing the minutes.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>secondsValue</code>Ê</dt><dd> An integer between 0 and 59, representing the seconds. If you specify the <code>secondsValue</code> parameter, you must also specify the <code>minutesValue</code>.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>msValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds. If you specify the <code>msValue</code> parameter, you must also specify the <code>minutesValue</code> and <code>secondsValue</code>.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>minutesValue</code>, <code>secondsValue</code>, and <code>msValue</code> parameters, the values returned from the <code>getUTCMinutes</code>, <code>getUTCSeconds</code>, and <code>getUTCMilliseconds</code> methods are used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setUTCHours</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 100 for <code>secondsValue</code>, the minutes will be incremented by 1 (min + 1), and 40 will be used for seconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCHours</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCHours(8);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCHours">getUTCHours</a>,
* <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setHours">setHours</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCHours: function(hoursValue, minutesValue, secondsValue, msValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the milliseconds for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCMilliseconds(<i>millisecondsValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>millisecondsValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If a parameter you specify is outside of the expected range, <code>setUTCMilliseconds</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 1100 for <code>millisecondsValue</code>, the seconds stored in the <code>Date</code> object will be incremented by 1, and 100 will be used for milliseconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCMilliseconds</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCMilliseconds(500);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>,
* <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setMilliseconds">setMilliseconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCMilliseconds: function(millisecondsValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the minutes for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCMinutes(<i>minutesValue</i>[, <i>secondsValue</i>[, <i>msValue</i>]])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>minutesValue</code>Ê</dt><dd> An integer between 0 and 59, representing the minutes.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>secondsValue</code>Ê</dt><dd> An integer between 0 and 59, representing the seconds. If you specify the <code>secondsValue</code> parameter, you must also specify the <code>minutesValue</code>.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>msValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds. If you specify the <code>msValue</code> parameter, you must also specify the <code>minutesValue</code> and <code>secondsValue</code>.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>secondsValue</code> and <code>msValue</code> parameters, the values returned from <code>getUTCSeconds</code> and <code>getUTCMilliseconds</code> methods are used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setUTCMinutes</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 100 for <code>secondsValue</code>, the minutes (<code>minutesValue</code>) will be incremented by 1 (<code>minutesValue</code> + 1), and 40 will be used for seconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCMinutes</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCMinutes(43);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCMinutes">getUTCMinutes</a>,
* <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setMinutes">setMinutes</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCMinutes: function(minutesValue, secondsValue, msValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the month for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCMonth(<i>monthValue</i>[, <i>dayValue</i>])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>monthValue</code>Ê</dt><dd> An integer between 0 and 11, representing the months January through December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dayValue</code>Ê</dt><dd> An integer from 1 to 31, representing the day of the month.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>dayValue</code> parameter, the value returned from the <code>getUTCDate</code> method is used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setUTCMonth</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 15 for <code>monthValue</code>, the year will be incremented by 1 (year + 1), and 3 will be used for month.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCMonth</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCMonth(11);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCMonth">getUTCMonth</a>,
* <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setMonth">setMonth</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCMonth: function(monthValue, dayValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Sets the seconds for a specified date according to universal time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setUTCSeconds(<i>secondsValue</i>[, <i>msValue</i>])
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>secondsValue</code>Ê</dt><dd> An integer between 0 and 59.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>msValue</code>Ê</dt><dd> A number between 0 and 999, representing the milliseconds.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you do not specify the <code>msValue</code> parameter, the value returned from the <code>getUTCMilliseconds</code> methods is used.
* </p><p>If a parameter you specify is outside of the expected range, <code>setUTCSeconds</code> attempts to update the date information in the <code>Date</code> object accordingly. For example, if you use 100 for <code>secondsValue</code>, the minutes stored in the <code>Date</code> object will be incremented by 1, and 40 will be used for seconds.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setUTCSeconds</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay = new Date();
* theBigDay.setUTCSeconds(20);
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getUTCSeconds">getUTCSeconds</a>,
* <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setSeconds">setSeconds</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setUTCSeconds: function(secondsValue, msValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <div style="border: 1px solid #5151FF; background-color: #B9B9FF; font-weight: bold; text-align: center; padding: 0px 10px 0px 10px; margin: 10px 0px 10px 0px;"><p style="margin: 4px 0px 4px 0px;">Deprecated</p></div>
* 
* <h2> <span> Summary </span></h2>
* <p>Sets the year for a specified date according to local time.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: Deprecated.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* setYear(<i>yearValue</i>)
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>yearValue</code>Ê</dt><dd> An integer.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p><code>setYear</code> is no longer used and has been replaced by the <code>setFullYear</code> method.
* </p><p>If <code>yearValue&lt;/code. is a number between 0 and 99 (inclusive), then the year for &lt;code&gt;dateObjectName</code> is set to 1900 + <code>yearValue</code>. Otherwise, the year for <code>dateObjectName</code> is set to <code>yearValue</code>.
* </p><p>To take into account years before and after 2000, you should use <code><a href="Core_JavaScript_1.5_Reference:Objects:Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setFullYear">setFullYear</a></code> instead of <code><a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setYear">setYear</a></code> so that the year is specified in full.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>setYear</code> </span></h3>
* <p>The first two lines set the year to 1996.  The third sets the year to 2000.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* theBigDay.setYear(96)
* theBigDay.setYear(1996)
* theBigDay.setYear(2000)
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getYear">getYear</a>,
* <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setFullYear">setFullYear</a>,
* <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:setUTCFullYear">setUTCFullYear</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
setYear: function(yearValue) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <div style="border: 1px solid #5151FF; background-color: #B9B9FF; font-weight: bold; text-align: center; padding: 0px 10px 0px 10px; margin: 10px 0px 10px 0px;"><p style="margin: 4px 0px 4px 0px;">Deprecated</p></div>
* 
* <h2> <span> Summary </span></h2>
* <p>Converts a date to a string, using Internet GMT conventioins.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0
* <p>JavaScript 1.3: Deprecated.
* </p>
* </td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* toGMTString()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p><code>toGMTString</code> is no longer used and has been replaced by the <code>toUTCString</code> method.
* </p><p>The exact format of the value returned by <code>toGMTString</code> varies according to the platform.
* </p><p>You should use <a href="Core_JavaScript_1.5_Reference:Objects:Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toUTCString">Date.toUTCString</a> instead of <code>toGMTSTring</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>toGMTString</code> </span></h3>
* <p>In the following example, today is a <code>Date</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* today.toGMTString()
* </pre>
* <p>In this example, the <code>toGMTString</code> method converts the date to GMT (UTC) using the operating system's time-zone offset and returns a string value that is similar to the following form. The exact format depends on the platform.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* Mon, 18 Dec 1995 17:28:35 GMT
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toLocaleString">toLocaleString</a>,
* <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toUTCString">toUTCString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toGMTString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Converts a date to a string, returning the "date" portion using the current locale's conventions.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* toLocaleDateString()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>toLocaleDateString</code> method relies on the underlying operating system in formatting dates. It converts the date to a string using the formatting convention of the operating system where the script is running. For example, in the United States, the month appears before the date (04/15/98), whereas in Germany the date appears before the month (15.04.98). If the operating system is not year-2000 compliant and does not use the full year for years before 1900 or over 2000, <code>toLocaleDateString</code> returns a string that is not year-2000 compliant. <code>toLocaleDateString</code> behaves similarly to <code>toString</code> when converting a year that the operating system does not properly format.
* </p><p>Methods such as <code><a href="Core_JavaScript_1.5_Reference:Objects:Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getHours">getHours</a></code>, <code><a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMinutes">getMinutes</a></code>, and <code><a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getSeconds">getSeconds</a></code> give more portable results than <code>toLocaleDateString</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>toLocaleDateString</code> </span></h3>
* <p>In the following example, <code>today</code> is a <code>Date</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* today = new Date(95,11,18,17,28,35) //months are represented by 0 to 11
* today.toLocaleDateString()
* </pre>
* <p>In this example, <code>toLocaleDateString</code> returns a string value that is similar to the following form. The exact format depends on the platform.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* 12/18/95
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toGMTString">toGMTString</a>,
* <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toUTCString">toUTCString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toLocaleDateString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p><b>Converts a date to a string using the specified formatting.</b>
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.6</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">none</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var formattedDateString = <i>dateObj</i>.toLocaleFormat(<i>formatString</i>);
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>formatString</code>Ê</dt><dd> A format string in the same format expected by the <code><a href="http://www.opengroup.org/onlinepubs/007908799/xsh/strftime.html" rel="nofollow" shape="rect" title="http://www.opengroup.org/onlinepubs/007908799/xsh/strftime.html">strftime()</a></code> function in C.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>The <code>toLocaleFormat()</code> provides greater software control over the formatting of the generated date and/or time, making use of localized names for months and days of the week.  However, ordering of the day and month and other localization tasks are not handled automatically since you have control over the order in which they occur.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>toLocaleFormat</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">var today = new Date();
* var date = today.toLocaleFormat("%A, %B %e, %Y");
* </pre>
* <p>In this example, <code>toLocaleFormat()</code> returns a string such as "Wednesday, October  3, 2007".
* </p>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a>,
* <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>,
* <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>
* <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
toLocaleFormat: function(formatString) {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Converts a date to a string, using the current locale's conventions.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* toLocaleString()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>toLocaleString</code> method relies on the underlying operating system in formatting dates. It converts the date to a string using the formatting convention of the operating system where the script is running. For example, in the United States, the month appears before the date (04/15/98), whereas in Germany the date appears before the month (15.04.98). If the operating system is not year-2000 compliant and does not use the full year for years before 1900 or over 2000, <code>toLocaleString</code> returns a string that is not year-2000 compliant. <code>toLocaleString</code> behaves similarly to <code>toString</code> when converting a year that the operating system does not properly format.
* </p><p>Methods such as <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a></code>, <code><a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a></code>, and <code><a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a></code> give more portable results than <code>toLocaleString</code>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Using <code>toLocaleString</code> </span></h3>
* <p>In the following example, <code>today</code> is a <code>Date</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date(95,11,18,17,28,35); //months are represented by 0 to 11
* today.toLocaleString();
* </pre>
* <p>In this example, <code>toLocaleString</code> returns a string value that is similar to the following form. The exact format depends on the platform.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">12/18/95 17:28:35
* </pre>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a>,
* <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>,
* <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toLocaleString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Converts a date to a string, returning the "date" portion using the current locale's conventions.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* toLocaleTimeString()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>toLocaleTimeString</code> method relies on the underlying operating system in formatting dates. It converts the date to a string using the formatting convention of the operating system where the script is running. For example, in the United States, the month appears before the date (04/15/98), whereas in Germany the date appears before the month (15.04.98). If the operating system is not year-2000 compliant and does not use the full year for years before 1900 or over 2000, <code>toLocaleTimeString</code> returns a string that is not year-2000 compliant. <code>toLocaleTimeString</code> behaves similarly to <code>toString</code> when converting a year that the operating system does not properly format.
* </p><p>Methods such as <code><a href="Core_JavaScript_1.5_Reference:Objects:Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getHours">getHours</a></code>, <code><a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getMinutes">getMinutes</a></code>, and <code><a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:getSeconds">getSeconds</a></code> give more consistent results than <code>toLocaleTimeString</code>. Use <code>toLocaleTimeString</code> when then intent is to display to the user a string formatted using the regional format of chosen by the user. Be aware that this method, due to its nature, behaves differently depending on the operating system and on the user's settings.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Using <code>toLocaleTimeString</code> </span></h3>
* <p>In the following example, <code>today</code> is a <code>Date</code> object:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* today = new Date(95,11,18,17,28,35) //months are represented by 0 to 11
* today.toLocaleTimeString()
* </pre>
* <p>In this example, <code>toLocaleTimeString</code> returns a string value that is similar to the following form. The exact format depends on the platform.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* 17:28:35
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Objects:Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toGMTString">toGMTString</a>,
* <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toUTCString">toUTCString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toLocaleTimeString: function() {
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
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code><i>date</i>.toSource()</code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The <code>toSource</code> method returns the following values:
* </p>
* <ul><li> For the built-in <code>Date</code> object, <code>toSource</code> returns the following string indicating that the source code is not available:
* </li></ul>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">function Date() {
* [native code]
* }
* </pre>
* <ul><li> For instances of <code>Date</code>, <code>toSource</code> returns a string representing the source code.
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
* <p>Returns a string representing the specified Date object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.0, NES 2.0</td>
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
* <p>The <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></code> object overrides the <code>toString</code> method of the <code><a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a></code> object; it does not inherit <code><a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a></code>. For <code><a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></code> objects, the <code>toString</code> method returns a string representation of the object.
* </p><p>JavaScript calls the <code>toString</code> method automatically when a date is to be represented as a text value or when a date is referred to in a string concatenation.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>toString</code> </span></h3>
* <p>The following assigns the <code>toString</code> value of a <code>Date</code> object to <code>myVar</code>:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* x = new Date();
* myVar=x.toString();   //assigns a value to myVar similar to:
* //Mon Sep 28 14:36:22 GMT-0700 (Pacific Daylight Time) 1998
* </pre>
* <h2> <span> See also </span></h2>
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
* <p>Converts a date to a string, using the universal time convention.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Date</a></td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">Implemented in:</td>
* <td colspan="1" rowspan="1">JavaScript 1.3</td>
* </tr>
* <tr>
* <td colspan="1" rowspan="1">ECMA Version:</td>
* <td colspan="1" rowspan="1">ECMA-262</td>
* </tr>
* </table>
* <h2> <span> Syntax </span></h2>
* <p><code>
* toUTCString()
* </code>
* </p>
* <h2> <span> Parameters </span></h2>
* <p>None.
* </p>
* <h2> <span> Description </span></h2>
* <p>The value returned by <code>toUTCString</code> is a readable string formatted according to <abbr title="Coordinated Universal Time">UTC</abbr> convention. The format of the return value may vary according to the platform.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>toUTCString</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">
* var today = new Date();
* var UTCstring = today.toUTCString();
* // Mon, 03 Jul 2006 21:44:38 GMT
* </pre>
* <h2> <span> See Also </span></h2>
* <p><a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date:toLocaleString">toLocaleString</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type String
*/
toUTCString: function() {
  // This is just a stub for a builtin native JavaScript object.
},
/**
* <h2> <span> Summary </span></h2>
* <p>Returns the primitive value of a Date object.
* </p>
* <table border="1" style="background:#FFFFFF none repeat scroll 0%;border: 1px solid #666666;margin-bottom:10px;margin-top:10px" width="100%">
* <tr>
* <td colspan="2" rowspan="1" style="background:#DDDDDD none repeat scroll 0%; border:1px solid #BBBBBB;">Method of <a href="Date" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date">Date</a></td>
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
* <p>The <code>valueOf</code> method returns the primitive value of a <code>Date</code> object as a number data type, the number of milliseconds since midnight 01 January, 1970 UTC.
* </p><p>This method is functionally equivalent to the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a> method.
* </p><p>This method is usually called internally by JavaScript and not explicitly in code.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Using <code>valueOf</code> </span></h3>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">x = new Date(56, 6, 17);
* myVar = x.valueOf();      //assigns -424713600000 to myVar
* </pre>
* <p>
* </p>
* <h2> <span> See also </span></h2>
* <p><a href="Core_JavaScript_1.5_Reference:Global_Objects:Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a>,
* <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>
* </p>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
 * @type Number
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
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Date&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Core JavaScript 1.5 Reference:Objects:Date</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>Lets you work with dates and times.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Date</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Date()
* new Date(<i>milliseconds</i>)
* new Date(<i>dateString</i>)
* new Date(<i>yr_num</i>, <i>mo_num</i>, <i>day_num</i>
* [, <i>hr_num</i>, <i>min_num</i>, <i>sec_num</i>, <i>ms_num</i>])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>milliseconds</code>Ê</dt><dd> Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> String value representing a date. The string should be in a format recognized by the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>yr_num, mo_num, day_num</code>Ê</dt><dd> Integer values representing part of a date. As an integer value, the month is represented by 0 to 11 with 0=January and 11=December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hr_num, min_num, sec_num, ms_num</code>Ê</dt><dd> Integer values representing part of a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you supply no arguments, the constructor creates a <code>Date</code> object for today's date and time according to local time. If you supply some arguments but not others, the missing arguments are set to 0. If you supply any arguments, you must supply at least the year, month, and day. You can omit the hours, minutes, seconds, and milliseconds.
* </p><p>The date is measured in milliseconds since midnight 01 January, 1970 UTC. A day holds 86,400,000 milliseconds. The Date object range is -100,000,000 days to 100,000,000 days relative to 01 January, 1970 UTC.
* </p><p>The <code>Date</code> object provides uniform behavior across platforms.
* </p><p>The <code>Date</code> object supports a number of UTC (universal) methods, as well as local time methods. UTC, also known as Greenwich Mean Time (GMT), refers to the time as set by the World Time Standard. The local time is the time known to the computer where JavaScript is executed.
* </p><p>For compatibility with millennium calculations (in other words, to take into account the year 2000), you should always specify the year in full; for example, use 1998, not 98. To assist you in specifying the complete year, JavaScript includes the methods <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a></code>, <code><a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a></code>, <code><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a></code>, and <code><a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a></code>.
* </p><p>The following example returns the time elapsed between <code>timeA</code> and <code>timeB</code> in milliseconds.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">timeA = new Date();
* // Statements here to take some action.
* timeB = new Date();
* timeDifference = timeB - timeA;
* </pre>
* <h2> <span> Properties </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </li><li> <a href="Date:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:prototype">prototype</a>: Allows the addition of properties to a <code>Date</code> object.
* </li></ul>
* <h2> <span> Static methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:now" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:now">now</a>: Returns the numeric value corresponding to the current time.
* </li><li> <a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>: Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </li><li> <a href="Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">UTC</a>: Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </li></ul>
* <h2> <span> Methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDate">getDate</a>: Returns the day of the month for the specified date according to local time.
* </li><li> <a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDay">getDay</a>: Returns the day of the week for the specified date according to local time.
* </li><li> <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a>: Returns the year of the specified date according to local time.
* </li><li> <a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a>: Returns the hour in the specified date according to local time.
* </li><li> <a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMilliseconds">getMilliseconds</a>: Returns the milliseconds in the specified date according to local time.
* </li><li> <a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a>: Returns the minutes in the specified date according to local time.
* </li><li> <a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMonth">getMonth</a>: Returns the month in the specified date according to local time.
* </li><li> <a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a>: Returns the seconds in the specified date according to local time.
* </li><li> <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>: Returns the numeric value corresponding to the time for the specified date according to universal time.
* </li><li> <a href="Date:getTimezoneOffset" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTimezoneOffset">getTimezoneOffset</a>: Returns the time-zone offset in minutes for the current locale.
* </li><li> <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>: Returns the day (date) of the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>: Returns the day of the week in the specified date according to universal time.
* </li><li> <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>: Returns the year in the specified date according to universal time.
* </li><li> <a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>: Returns the hours in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>: Returns the milliseconds in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMinutes">getUTCMinutes</a>: Returns the minutes in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMonth">getUTCMonth</a>: Returns the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCSeconds">getUTCSeconds</a>: Returns the seconds in the specified date according to universal time.
* </li><li> <a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Returns the year in the specified date according to local time. Use <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a> instead.
* </li><li> <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>: Sets the day of the month for a specified date according to local time.
* </li><li> <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>: Sets the full year for a specified date according to local time.
* </li><li> <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setHours">setHours</a>: Sets the hours for a specified date according to local time.
* </li><li> <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMilliseconds">setMilliseconds</a>: Sets the milliseconds for a specified date according to local time.
* </li><li> <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMinutes">setMinutes</a>: Sets the minutes for a specified date according to local time.
* </li><li> <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMonth">setMonth</a>: Sets the month for a specified date according to local time.
* </li><li> <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setSeconds">setSeconds</a>: Sets the seconds for a specified date according to local time.
* </li><li> <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>: Sets the value of the <code>Date</code> object according to local time.
* </li><li> <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCDate">setUTCDate</a>: Sets the day of the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a>: Sets the full year for a specified date according to universal time.
* </li><li> <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCHours">setUTCHours</a>: Sets the hour for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>: Sets the milliseconds for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMinutes">setUTCMinutes</a>: Sets the minutes for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMonth">setUTCMonth</a>: Sets the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCSeconds">setUTCSeconds</a>: Sets the seconds for a specified date according to universal time.
* </li><li> <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setYear">setYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Sets the year for a specified date according to local time. Use <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a> instead.
* </li><li> <a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>: Converts a date to a string, using the Internet GMT conventions. Use <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a> instead.
* </li><li> <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>: Converts a date to a string, using the current locale's conventions. Overrides the <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a> method.
* </li><li> <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>: Returns the "date" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toLocaleFormat" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleFormat">toLocaleFormat</a>: Converts a date to a string, using a format string.
* </li><li> <a href="Date:toLocaleTimeString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleTimeString">toLocaleTimeString</a>: Returns the "time" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toSource">toSource</a>: Returns an object literal representing the specified <code>Date</code> object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </li><li> <a href="Date:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toString">toString</a>: Returns a string representing the specified <code>Date</code> object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </li><li> <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>: Converts a date to a string, using the universal time convention.
* </li><li> <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>: Returns the primitive value of a <code>Date</code> object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </li></ul>
* <p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Several ways to assign dates </span></h3>
* <p>The following examples show several ways to assign dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date();
* birthday = new Date("December 17, 1995 03:24:00");
* birthday = new Date(1995,11,17);
* birthday = new Date(1995,11,17,3,24,0);
* </pre>
* <h3> <span> Example: Calculating elapsed time </span></h3>
* <p>The following examples show how to determine the elapsed time between two dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// using static methods
* var start = Date.now();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = Date.now();
* var elapsed = end - start; // time in milliseconds
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// if you have Date objects
* var start = new Date();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = new Date();
* var elapsed = end.getTime() - start.getTime(); // time in milliseconds
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Date() {};
/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Date&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Core JavaScript 1.5 Reference:Objects:Date</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>Lets you work with dates and times.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Date</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Date()
* new Date(<i>milliseconds</i>)
* new Date(<i>dateString</i>)
* new Date(<i>yr_num</i>, <i>mo_num</i>, <i>day_num</i>
* [, <i>hr_num</i>, <i>min_num</i>, <i>sec_num</i>, <i>ms_num</i>])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>milliseconds</code>Ê</dt><dd> Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> String value representing a date. The string should be in a format recognized by the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>yr_num, mo_num, day_num</code>Ê</dt><dd> Integer values representing part of a date. As an integer value, the month is represented by 0 to 11 with 0=January and 11=December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hr_num, min_num, sec_num, ms_num</code>Ê</dt><dd> Integer values representing part of a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you supply no arguments, the constructor creates a <code>Date</code> object for today's date and time according to local time. If you supply some arguments but not others, the missing arguments are set to 0. If you supply any arguments, you must supply at least the year, month, and day. You can omit the hours, minutes, seconds, and milliseconds.
* </p><p>The date is measured in milliseconds since midnight 01 January, 1970 UTC. A day holds 86,400,000 milliseconds. The Date object range is -100,000,000 days to 100,000,000 days relative to 01 January, 1970 UTC.
* </p><p>The <code>Date</code> object provides uniform behavior across platforms.
* </p><p>The <code>Date</code> object supports a number of UTC (universal) methods, as well as local time methods. UTC, also known as Greenwich Mean Time (GMT), refers to the time as set by the World Time Standard. The local time is the time known to the computer where JavaScript is executed.
* </p><p>For compatibility with millennium calculations (in other words, to take into account the year 2000), you should always specify the year in full; for example, use 1998, not 98. To assist you in specifying the complete year, JavaScript includes the methods <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a></code>, <code><a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a></code>, <code><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a></code>, and <code><a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a></code>.
* </p><p>The following example returns the time elapsed between <code>timeA</code> and <code>timeB</code> in milliseconds.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">timeA = new Date();
* // Statements here to take some action.
* timeB = new Date();
* timeDifference = timeB - timeA;
* </pre>
* <h2> <span> Properties </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </li><li> <a href="Date:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:prototype">prototype</a>: Allows the addition of properties to a <code>Date</code> object.
* </li></ul>
* <h2> <span> Static methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:now" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:now">now</a>: Returns the numeric value corresponding to the current time.
* </li><li> <a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>: Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </li><li> <a href="Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">UTC</a>: Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </li></ul>
* <h2> <span> Methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDate">getDate</a>: Returns the day of the month for the specified date according to local time.
* </li><li> <a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDay">getDay</a>: Returns the day of the week for the specified date according to local time.
* </li><li> <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a>: Returns the year of the specified date according to local time.
* </li><li> <a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a>: Returns the hour in the specified date according to local time.
* </li><li> <a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMilliseconds">getMilliseconds</a>: Returns the milliseconds in the specified date according to local time.
* </li><li> <a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a>: Returns the minutes in the specified date according to local time.
* </li><li> <a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMonth">getMonth</a>: Returns the month in the specified date according to local time.
* </li><li> <a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a>: Returns the seconds in the specified date according to local time.
* </li><li> <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>: Returns the numeric value corresponding to the time for the specified date according to universal time.
* </li><li> <a href="Date:getTimezoneOffset" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTimezoneOffset">getTimezoneOffset</a>: Returns the time-zone offset in minutes for the current locale.
* </li><li> <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>: Returns the day (date) of the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>: Returns the day of the week in the specified date according to universal time.
* </li><li> <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>: Returns the year in the specified date according to universal time.
* </li><li> <a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>: Returns the hours in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>: Returns the milliseconds in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMinutes">getUTCMinutes</a>: Returns the minutes in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMonth">getUTCMonth</a>: Returns the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCSeconds">getUTCSeconds</a>: Returns the seconds in the specified date according to universal time.
* </li><li> <a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Returns the year in the specified date according to local time. Use <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a> instead.
* </li><li> <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>: Sets the day of the month for a specified date according to local time.
* </li><li> <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>: Sets the full year for a specified date according to local time.
* </li><li> <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setHours">setHours</a>: Sets the hours for a specified date according to local time.
* </li><li> <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMilliseconds">setMilliseconds</a>: Sets the milliseconds for a specified date according to local time.
* </li><li> <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMinutes">setMinutes</a>: Sets the minutes for a specified date according to local time.
* </li><li> <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMonth">setMonth</a>: Sets the month for a specified date according to local time.
* </li><li> <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setSeconds">setSeconds</a>: Sets the seconds for a specified date according to local time.
* </li><li> <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>: Sets the value of the <code>Date</code> object according to local time.
* </li><li> <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCDate">setUTCDate</a>: Sets the day of the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a>: Sets the full year for a specified date according to universal time.
* </li><li> <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCHours">setUTCHours</a>: Sets the hour for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>: Sets the milliseconds for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMinutes">setUTCMinutes</a>: Sets the minutes for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMonth">setUTCMonth</a>: Sets the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCSeconds">setUTCSeconds</a>: Sets the seconds for a specified date according to universal time.
* </li><li> <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setYear">setYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Sets the year for a specified date according to local time. Use <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a> instead.
* </li><li> <a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>: Converts a date to a string, using the Internet GMT conventions. Use <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a> instead.
* </li><li> <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>: Converts a date to a string, using the current locale's conventions. Overrides the <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a> method.
* </li><li> <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>: Returns the "date" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toLocaleFormat" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleFormat">toLocaleFormat</a>: Converts a date to a string, using a format string.
* </li><li> <a href="Date:toLocaleTimeString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleTimeString">toLocaleTimeString</a>: Returns the "time" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toSource">toSource</a>: Returns an object literal representing the specified <code>Date</code> object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </li><li> <a href="Date:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toString">toString</a>: Returns a string representing the specified <code>Date</code> object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </li><li> <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>: Converts a date to a string, using the universal time convention.
* </li><li> <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>: Returns the primitive value of a <code>Date</code> object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </li></ul>
* <p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Several ways to assign dates </span></h3>
* <p>The following examples show several ways to assign dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date();
* birthday = new Date("December 17, 1995 03:24:00");
* birthday = new Date(1995,11,17);
* birthday = new Date(1995,11,17,3,24,0);
* </pre>
* <h3> <span> Example: Calculating elapsed time </span></h3>
* <p>The following examples show how to determine the elapsed time between two dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// using static methods
* var start = Date.now();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = Date.now();
* var elapsed = end - start; // time in milliseconds
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// if you have Date objects
* var start = new Date();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = new Date();
* var elapsed = end.getTime() - start.getTime(); // time in milliseconds
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Date(dateString) {};
/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Date&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Core JavaScript 1.5 Reference:Objects:Date</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>Lets you work with dates and times.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Date</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Date()
* new Date(<i>milliseconds</i>)
* new Date(<i>dateString</i>)
* new Date(<i>yr_num</i>, <i>mo_num</i>, <i>day_num</i>
* [, <i>hr_num</i>, <i>min_num</i>, <i>sec_num</i>, <i>ms_num</i>])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>milliseconds</code>Ê</dt><dd> Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> String value representing a date. The string should be in a format recognized by the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>yr_num, mo_num, day_num</code>Ê</dt><dd> Integer values representing part of a date. As an integer value, the month is represented by 0 to 11 with 0=January and 11=December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hr_num, min_num, sec_num, ms_num</code>Ê</dt><dd> Integer values representing part of a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you supply no arguments, the constructor creates a <code>Date</code> object for today's date and time according to local time. If you supply some arguments but not others, the missing arguments are set to 0. If you supply any arguments, you must supply at least the year, month, and day. You can omit the hours, minutes, seconds, and milliseconds.
* </p><p>The date is measured in milliseconds since midnight 01 January, 1970 UTC. A day holds 86,400,000 milliseconds. The Date object range is -100,000,000 days to 100,000,000 days relative to 01 January, 1970 UTC.
* </p><p>The <code>Date</code> object provides uniform behavior across platforms.
* </p><p>The <code>Date</code> object supports a number of UTC (universal) methods, as well as local time methods. UTC, also known as Greenwich Mean Time (GMT), refers to the time as set by the World Time Standard. The local time is the time known to the computer where JavaScript is executed.
* </p><p>For compatibility with millennium calculations (in other words, to take into account the year 2000), you should always specify the year in full; for example, use 1998, not 98. To assist you in specifying the complete year, JavaScript includes the methods <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a></code>, <code><a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a></code>, <code><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a></code>, and <code><a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a></code>.
* </p><p>The following example returns the time elapsed between <code>timeA</code> and <code>timeB</code> in milliseconds.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">timeA = new Date();
* // Statements here to take some action.
* timeB = new Date();
* timeDifference = timeB - timeA;
* </pre>
* <h2> <span> Properties </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </li><li> <a href="Date:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:prototype">prototype</a>: Allows the addition of properties to a <code>Date</code> object.
* </li></ul>
* <h2> <span> Static methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:now" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:now">now</a>: Returns the numeric value corresponding to the current time.
* </li><li> <a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>: Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </li><li> <a href="Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">UTC</a>: Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </li></ul>
* <h2> <span> Methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDate">getDate</a>: Returns the day of the month for the specified date according to local time.
* </li><li> <a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDay">getDay</a>: Returns the day of the week for the specified date according to local time.
* </li><li> <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a>: Returns the year of the specified date according to local time.
* </li><li> <a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a>: Returns the hour in the specified date according to local time.
* </li><li> <a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMilliseconds">getMilliseconds</a>: Returns the milliseconds in the specified date according to local time.
* </li><li> <a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a>: Returns the minutes in the specified date according to local time.
* </li><li> <a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMonth">getMonth</a>: Returns the month in the specified date according to local time.
* </li><li> <a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a>: Returns the seconds in the specified date according to local time.
* </li><li> <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>: Returns the numeric value corresponding to the time for the specified date according to universal time.
* </li><li> <a href="Date:getTimezoneOffset" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTimezoneOffset">getTimezoneOffset</a>: Returns the time-zone offset in minutes for the current locale.
* </li><li> <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>: Returns the day (date) of the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>: Returns the day of the week in the specified date according to universal time.
* </li><li> <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>: Returns the year in the specified date according to universal time.
* </li><li> <a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>: Returns the hours in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>: Returns the milliseconds in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMinutes">getUTCMinutes</a>: Returns the minutes in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMonth">getUTCMonth</a>: Returns the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCSeconds">getUTCSeconds</a>: Returns the seconds in the specified date according to universal time.
* </li><li> <a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Returns the year in the specified date according to local time. Use <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a> instead.
* </li><li> <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>: Sets the day of the month for a specified date according to local time.
* </li><li> <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>: Sets the full year for a specified date according to local time.
* </li><li> <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setHours">setHours</a>: Sets the hours for a specified date according to local time.
* </li><li> <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMilliseconds">setMilliseconds</a>: Sets the milliseconds for a specified date according to local time.
* </li><li> <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMinutes">setMinutes</a>: Sets the minutes for a specified date according to local time.
* </li><li> <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMonth">setMonth</a>: Sets the month for a specified date according to local time.
* </li><li> <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setSeconds">setSeconds</a>: Sets the seconds for a specified date according to local time.
* </li><li> <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>: Sets the value of the <code>Date</code> object according to local time.
* </li><li> <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCDate">setUTCDate</a>: Sets the day of the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a>: Sets the full year for a specified date according to universal time.
* </li><li> <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCHours">setUTCHours</a>: Sets the hour for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>: Sets the milliseconds for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMinutes">setUTCMinutes</a>: Sets the minutes for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMonth">setUTCMonth</a>: Sets the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCSeconds">setUTCSeconds</a>: Sets the seconds for a specified date according to universal time.
* </li><li> <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setYear">setYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Sets the year for a specified date according to local time. Use <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a> instead.
* </li><li> <a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>: Converts a date to a string, using the Internet GMT conventions. Use <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a> instead.
* </li><li> <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>: Converts a date to a string, using the current locale's conventions. Overrides the <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a> method.
* </li><li> <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>: Returns the "date" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toLocaleFormat" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleFormat">toLocaleFormat</a>: Converts a date to a string, using a format string.
* </li><li> <a href="Date:toLocaleTimeString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleTimeString">toLocaleTimeString</a>: Returns the "time" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toSource">toSource</a>: Returns an object literal representing the specified <code>Date</code> object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </li><li> <a href="Date:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toString">toString</a>: Returns a string representing the specified <code>Date</code> object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </li><li> <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>: Converts a date to a string, using the universal time convention.
* </li><li> <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>: Returns the primitive value of a <code>Date</code> object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </li></ul>
* <p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Several ways to assign dates </span></h3>
* <p>The following examples show several ways to assign dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date();
* birthday = new Date("December 17, 1995 03:24:00");
* birthday = new Date(1995,11,17);
* birthday = new Date(1995,11,17,3,24,0);
* </pre>
* <h3> <span> Example: Calculating elapsed time </span></h3>
* <p>The following examples show how to determine the elapsed time between two dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// using static methods
* var start = Date.now();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = Date.now();
* var elapsed = end - start; // time in milliseconds
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// if you have Date objects
* var start = new Date();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = new Date();
* var elapsed = end.getTime() - start.getTime(); // time in milliseconds
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Date(milliseconds) {};
/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Date&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Core JavaScript 1.5 Reference:Objects:Date</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>Lets you work with dates and times.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Date</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Date()
* new Date(<i>milliseconds</i>)
* new Date(<i>dateString</i>)
* new Date(<i>yr_num</i>, <i>mo_num</i>, <i>day_num</i>
* [, <i>hr_num</i>, <i>min_num</i>, <i>sec_num</i>, <i>ms_num</i>])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>milliseconds</code>Ê</dt><dd> Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> String value representing a date. The string should be in a format recognized by the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>yr_num, mo_num, day_num</code>Ê</dt><dd> Integer values representing part of a date. As an integer value, the month is represented by 0 to 11 with 0=January and 11=December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hr_num, min_num, sec_num, ms_num</code>Ê</dt><dd> Integer values representing part of a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you supply no arguments, the constructor creates a <code>Date</code> object for today's date and time according to local time. If you supply some arguments but not others, the missing arguments are set to 0. If you supply any arguments, you must supply at least the year, month, and day. You can omit the hours, minutes, seconds, and milliseconds.
* </p><p>The date is measured in milliseconds since midnight 01 January, 1970 UTC. A day holds 86,400,000 milliseconds. The Date object range is -100,000,000 days to 100,000,000 days relative to 01 January, 1970 UTC.
* </p><p>The <code>Date</code> object provides uniform behavior across platforms.
* </p><p>The <code>Date</code> object supports a number of UTC (universal) methods, as well as local time methods. UTC, also known as Greenwich Mean Time (GMT), refers to the time as set by the World Time Standard. The local time is the time known to the computer where JavaScript is executed.
* </p><p>For compatibility with millennium calculations (in other words, to take into account the year 2000), you should always specify the year in full; for example, use 1998, not 98. To assist you in specifying the complete year, JavaScript includes the methods <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a></code>, <code><a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a></code>, <code><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a></code>, and <code><a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a></code>.
* </p><p>The following example returns the time elapsed between <code>timeA</code> and <code>timeB</code> in milliseconds.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">timeA = new Date();
* // Statements here to take some action.
* timeB = new Date();
* timeDifference = timeB - timeA;
* </pre>
* <h2> <span> Properties </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </li><li> <a href="Date:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:prototype">prototype</a>: Allows the addition of properties to a <code>Date</code> object.
* </li></ul>
* <h2> <span> Static methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:now" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:now">now</a>: Returns the numeric value corresponding to the current time.
* </li><li> <a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>: Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </li><li> <a href="Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">UTC</a>: Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </li></ul>
* <h2> <span> Methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDate">getDate</a>: Returns the day of the month for the specified date according to local time.
* </li><li> <a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDay">getDay</a>: Returns the day of the week for the specified date according to local time.
* </li><li> <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a>: Returns the year of the specified date according to local time.
* </li><li> <a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a>: Returns the hour in the specified date according to local time.
* </li><li> <a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMilliseconds">getMilliseconds</a>: Returns the milliseconds in the specified date according to local time.
* </li><li> <a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a>: Returns the minutes in the specified date according to local time.
* </li><li> <a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMonth">getMonth</a>: Returns the month in the specified date according to local time.
* </li><li> <a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a>: Returns the seconds in the specified date according to local time.
* </li><li> <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>: Returns the numeric value corresponding to the time for the specified date according to universal time.
* </li><li> <a href="Date:getTimezoneOffset" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTimezoneOffset">getTimezoneOffset</a>: Returns the time-zone offset in minutes for the current locale.
* </li><li> <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>: Returns the day (date) of the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>: Returns the day of the week in the specified date according to universal time.
* </li><li> <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>: Returns the year in the specified date according to universal time.
* </li><li> <a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>: Returns the hours in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>: Returns the milliseconds in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMinutes">getUTCMinutes</a>: Returns the minutes in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMonth">getUTCMonth</a>: Returns the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCSeconds">getUTCSeconds</a>: Returns the seconds in the specified date according to universal time.
* </li><li> <a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Returns the year in the specified date according to local time. Use <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a> instead.
* </li><li> <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>: Sets the day of the month for a specified date according to local time.
* </li><li> <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>: Sets the full year for a specified date according to local time.
* </li><li> <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setHours">setHours</a>: Sets the hours for a specified date according to local time.
* </li><li> <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMilliseconds">setMilliseconds</a>: Sets the milliseconds for a specified date according to local time.
* </li><li> <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMinutes">setMinutes</a>: Sets the minutes for a specified date according to local time.
* </li><li> <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMonth">setMonth</a>: Sets the month for a specified date according to local time.
* </li><li> <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setSeconds">setSeconds</a>: Sets the seconds for a specified date according to local time.
* </li><li> <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>: Sets the value of the <code>Date</code> object according to local time.
* </li><li> <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCDate">setUTCDate</a>: Sets the day of the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a>: Sets the full year for a specified date according to universal time.
* </li><li> <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCHours">setUTCHours</a>: Sets the hour for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>: Sets the milliseconds for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMinutes">setUTCMinutes</a>: Sets the minutes for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMonth">setUTCMonth</a>: Sets the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCSeconds">setUTCSeconds</a>: Sets the seconds for a specified date according to universal time.
* </li><li> <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setYear">setYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Sets the year for a specified date according to local time. Use <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a> instead.
* </li><li> <a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>: Converts a date to a string, using the Internet GMT conventions. Use <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a> instead.
* </li><li> <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>: Converts a date to a string, using the current locale's conventions. Overrides the <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a> method.
* </li><li> <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>: Returns the "date" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toLocaleFormat" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleFormat">toLocaleFormat</a>: Converts a date to a string, using a format string.
* </li><li> <a href="Date:toLocaleTimeString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleTimeString">toLocaleTimeString</a>: Returns the "time" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toSource">toSource</a>: Returns an object literal representing the specified <code>Date</code> object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </li><li> <a href="Date:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toString">toString</a>: Returns a string representing the specified <code>Date</code> object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </li><li> <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>: Converts a date to a string, using the universal time convention.
* </li><li> <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>: Returns the primitive value of a <code>Date</code> object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </li></ul>
* <p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Several ways to assign dates </span></h3>
* <p>The following examples show several ways to assign dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date();
* birthday = new Date("December 17, 1995 03:24:00");
* birthday = new Date(1995,11,17);
* birthday = new Date(1995,11,17,3,24,0);
* </pre>
* <h3> <span> Example: Calculating elapsed time </span></h3>
* <p>The following examples show how to determine the elapsed time between two dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// using static methods
* var start = Date.now();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = Date.now();
* var elapsed = end - start; // time in milliseconds
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// if you have Date objects
* var start = new Date();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = new Date();
* var elapsed = end.getTime() - start.getTime(); // time in milliseconds
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Date(yr_num, mo_num, day_num) {};
/**
* <div id="contentSub">(Redirected from <a href="http://developer.mozilla.org/en/docs/index.php?title=Core_JavaScript_1.5_Reference:Objects:Date&amp;redirect=no" shape="rect" title="Core JavaScript 1.5 Reference:Objects:Date">Core JavaScript 1.5 Reference:Objects:Date</a>)</div>
* 
* <h2> <span> Summary </span></h2>
* <p><b>Core Object</b>
* </p><p>Lets you work with dates and times.
* </p>
* <h2> <span> Created by </span></h2>
* <p>The <code>Date</code> constructor:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">new Date()
* new Date(<i>milliseconds</i>)
* new Date(<i>dateString</i>)
* new Date(<i>yr_num</i>, <i>mo_num</i>, <i>day_num</i>
* [, <i>hr_num</i>, <i>min_num</i>, <i>sec_num</i>, <i>ms_num</i>])
* </pre>
* <h2> <span> Parameters </span></h2>
* <dl><dt style="font-weight:bold"> <code>milliseconds</code>Ê</dt><dd> Integer value representing the number of milliseconds since 1 January 1970 00:00:00 UTC.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>dateString</code>Ê</dt><dd> String value representing a date. The string should be in a format recognized by the <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a> method.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>yr_num, mo_num, day_num</code>Ê</dt><dd> Integer values representing part of a date. As an integer value, the month is represented by 0 to 11 with 0=January and 11=December.
* </dd></dl>
* <dl><dt style="font-weight:bold"> <code>hr_num, min_num, sec_num, ms_num</code>Ê</dt><dd> Integer values representing part of a date.
* </dd></dl>
* <h2> <span> Description </span></h2>
* <p>If you supply no arguments, the constructor creates a <code>Date</code> object for today's date and time according to local time. If you supply some arguments but not others, the missing arguments are set to 0. If you supply any arguments, you must supply at least the year, month, and day. You can omit the hours, minutes, seconds, and milliseconds.
* </p><p>The date is measured in milliseconds since midnight 01 January, 1970 UTC. A day holds 86,400,000 milliseconds. The Date object range is -100,000,000 days to 100,000,000 days relative to 01 January, 1970 UTC.
* </p><p>The <code>Date</code> object provides uniform behavior across platforms.
* </p><p>The <code>Date</code> object supports a number of UTC (universal) methods, as well as local time methods. UTC, also known as Greenwich Mean Time (GMT), refers to the time as set by the World Time Standard. The local time is the time known to the computer where JavaScript is executed.
* </p><p>For compatibility with millennium calculations (in other words, to take into account the year 2000), you should always specify the year in full; for example, use 1998, not 98. To assist you in specifying the complete year, JavaScript includes the methods <code><a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a></code>, <code><a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a></code>, <code><a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a></code>, and <code><a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a></code>.
* </p><p>The following example returns the time elapsed between <code>timeA</code> and <code>timeB</code> in milliseconds.
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">timeA = new Date();
* // Statements here to take some action.
* timeB = new Date();
* timeDifference = timeB - timeA;
* </pre>
* <h2> <span> Properties </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:constructor" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:constructor">constructor</a>: Specifies the function that creates an object's prototype.
* </li><li> <a href="Date:prototype" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:prototype">prototype</a>: Allows the addition of properties to a <code>Date</code> object.
* </li></ul>
* <h2> <span> Static methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:now" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:now">now</a>: Returns the numeric value corresponding to the current time.
* </li><li> <a href="Date:parse" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:parse">parse</a>: Parses a string representation of a date, and returns the number of milliseconds since January 1, 1970, 00:00:00, local time.
* </li><li> <a href="Date:UTC" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:UTC">UTC</a>: Accepts the same parameters as the longest form of the constructor, and returns the number of milliseconds in a <code>Date</code> object since January 1, 1970, 00:00:00, universal time.
* </li></ul>
* <h2> <span> Methods </span></h2>
* <ul><li> <a href="Core_JavaScript_1.5_Reference:Global_Objects:Date:getDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDate">getDate</a>: Returns the day of the month for the specified date according to local time.
* </li><li> <a href="Date:getDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getDay">getDay</a>: Returns the day of the week for the specified date according to local time.
* </li><li> <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a>: Returns the year of the specified date according to local time.
* </li><li> <a href="Date:getHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getHours">getHours</a>: Returns the hour in the specified date according to local time.
* </li><li> <a href="Date:getMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMilliseconds">getMilliseconds</a>: Returns the milliseconds in the specified date according to local time.
* </li><li> <a href="Date:getMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMinutes">getMinutes</a>: Returns the minutes in the specified date according to local time.
* </li><li> <a href="Date:getMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getMonth">getMonth</a>: Returns the month in the specified date according to local time.
* </li><li> <a href="Date:getSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getSeconds">getSeconds</a>: Returns the seconds in the specified date according to local time.
* </li><li> <a href="Date:getTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTime">getTime</a>: Returns the numeric value corresponding to the time for the specified date according to universal time.
* </li><li> <a href="Date:getTimezoneOffset" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getTimezoneOffset">getTimezoneOffset</a>: Returns the time-zone offset in minutes for the current locale.
* </li><li> <a href="Date:getUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDate">getUTCDate</a>: Returns the day (date) of the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCDay" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCDay">getUTCDay</a>: Returns the day of the week in the specified date according to universal time.
* </li><li> <a href="Date:getUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCFullYear">getUTCFullYear</a>: Returns the year in the specified date according to universal time.
* </li><li> <a href="Date:getUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCHours">getUTCHours</a>: Returns the hours in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMilliseconds">getUTCMilliseconds</a>: Returns the milliseconds in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMinutes">getUTCMinutes</a>: Returns the minutes in the specified date according to universal time.
* </li><li> <a href="Date:getUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCMonth">getUTCMonth</a>: Returns the month in the specified date according to universal time.
* </li><li> <a href="Date:getUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getUTCSeconds">getUTCSeconds</a>: Returns the seconds in the specified date according to universal time.
* </li><li> <a href="Date:getYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getYear">getYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Returns the year in the specified date according to local time. Use <a href="Date:getFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:getFullYear">getFullYear</a> instead.
* </li><li> <a href="Date:setDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setDate">setDate</a>: Sets the day of the month for a specified date according to local time.
* </li><li> <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a>: Sets the full year for a specified date according to local time.
* </li><li> <a href="Date:setHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setHours">setHours</a>: Sets the hours for a specified date according to local time.
* </li><li> <a href="Date:setMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMilliseconds">setMilliseconds</a>: Sets the milliseconds for a specified date according to local time.
* </li><li> <a href="Date:setMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMinutes">setMinutes</a>: Sets the minutes for a specified date according to local time.
* </li><li> <a href="Date:setMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setMonth">setMonth</a>: Sets the month for a specified date according to local time.
* </li><li> <a href="Date:setSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setSeconds">setSeconds</a>: Sets the seconds for a specified date according to local time.
* </li><li> <a href="Date:setTime" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setTime">setTime</a>: Sets the value of the <code>Date</code> object according to local time.
* </li><li> <a href="Date:setUTCDate" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCDate">setUTCDate</a>: Sets the day of the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCFullYear">setUTCFullYear</a>: Sets the full year for a specified date according to universal time.
* </li><li> <a href="Date:setUTCHours" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCHours">setUTCHours</a>: Sets the hour for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMilliseconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMilliseconds">setUTCMilliseconds</a>: Sets the milliseconds for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMinutes" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMinutes">setUTCMinutes</a>: Sets the minutes for a specified date according to universal time.
* </li><li> <a href="Date:setUTCMonth" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCMonth">setUTCMonth</a>: Sets the month for a specified date according to universal time.
* </li><li> <a href="Date:setUTCSeconds" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setUTCSeconds">setUTCSeconds</a>: Sets the seconds for a specified date according to universal time.
* </li><li> <a href="Date:setYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setYear">setYear</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>Ê: Sets the year for a specified date according to local time. Use <a href="Date:setFullYear" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:setFullYear">setFullYear</a> instead.
* </li><li> <a href="Date:toGMTString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toGMTString">toGMTString</a> <span style="border: 1px solid #9898F0; background-color: #DDDDFF; font-size: 9px; vertical-align: text-top;">Deprecated</span>: Converts a date to a string, using the Internet GMT conventions. Use <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a> instead.
* </li><li> <a href="Date:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleString">toLocaleString</a>: Converts a date to a string, using the current locale's conventions. Overrides the <a href="Object:toLocaleString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toLocaleString">Object.toLocaleString</a> method.
* </li><li> <a href="Date:toLocaleDateString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleDateString">toLocaleDateString</a>: Returns the "date" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toLocaleFormat" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleFormat">toLocaleFormat</a>: Converts a date to a string, using a format string.
* </li><li> <a href="Date:toLocaleTimeString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toLocaleTimeString">toLocaleTimeString</a>: Returns the "time" portion of the Date as a string, using the current locale's conventions.
* </li><li> <a href="Date:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toSource">toSource</a>: Returns an object literal representing the specified <code>Date</code> object; you can use this value to create a new object.  Overrides the <a href="Object:toSource" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toSource">Object.toSource</a> method.
* </li><li> <a href="Date:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toString">toString</a>: Returns a string representing the specified <code>Date</code> object.  Overrides the <a href="Object:toString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:toString">Object.toString</a> method.
* </li><li> <a href="Date:toUTCString" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:toUTCString">toUTCString</a>: Converts a date to a string, using the universal time convention.
* </li><li> <a href="Date:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Date:valueOf">valueOf</a>: Returns the primitive value of a <code>Date</code> object.  Overrides the <a href="Object:valueOf" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:valueOf">Object.valueOf</a> method.
* </li></ul>
* <p>In addition, this object inherits the <a href="Object:watch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:watch">watch</a> and <a href="Object:unwatch" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object:unwatch">unwatch</a> methods from <a href="Object" shape="rect" title="Core JavaScript 1.5 Reference:Global Objects:Object">Object</a>.
* </p>
* <h2> <span> Examples </span></h2>
* <h3> <span> Example: Several ways to assign dates </span></h3>
* <p>The following examples show several ways to assign dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">today = new Date();
* birthday = new Date("December 17, 1995 03:24:00");
* birthday = new Date(1995,11,17);
* birthday = new Date(1995,11,17,3,24,0);
* </pre>
* <h3> <span> Example: Calculating elapsed time </span></h3>
* <p>The following examples show how to determine the elapsed time between two dates:
* </p>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// using static methods
* var start = Date.now();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = Date.now();
* var elapsed = end - start; // time in milliseconds
* </pre>
* <pre style="background:#EEEEEE none repeat scroll 0% 50%;border:1px solid #666666;padding:5px 5px" xml:space="preserve">// if you have Date objects
* var start = new Date();
* // the event you'd like to time goes here:
* doSomethingForALongTime();
* var end = new Date();
* var elapsed = end.getTime() - start.getTime(); // time in milliseconds
* </pre>
* 
* <ul style="list-style-type:none;font-size:0.9em;text-align:center">
* <li id="f-copyright">Content is available under <a href="http://developer.mozilla.org/en/docs/MDC:Copyrights" shape="rect" title="MDC:Copyrights">these licenses</a>.</li>	  		<li id="f-about"><a href="http://developer.mozilla.org/en/docs/MDC:About" shape="rect" title="MDC:About">About MDC</a></li>	  				</ul>
*/
function Date(yr_num, mo_num, day_num, hr_num, min_num, sec_num, ms_num) {};

