<html>
<head>
    <title>Account</title>
</head>
<body style="padding: 50px;">
<h2>Welcome, ${client.phoneNumber}</h2>
<label>Your credits: ${client.credits}</label><br/>
<h3>Which operation do you want?</h3>
<ul>
    <li><a href="payment">Make payment</a></li>
    <li><a href="reportpayments">Report payments</a></li>
</ul>
<a href="logout">Log out</a>
</body>
</html>