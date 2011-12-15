<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Alice home TV</title>
<link rel="stylesheet" type="text/css" href="/css/hp.css" />
<script type="text/javascript">
	function prepareLogoutUrl()
	{
		var finalUrl = "http://aa.rossoalice.alice.it/aa-logout/logout?finalurlok=";
		finalUrl = finalUrl + escape(top.document.location.href);
		document.getElementById("btnEsciLogin").href = finalUrl;
	}	
</script>
</head>
<body style="background: none;"
	onLoad="top.logged('${isFirstLogin}', '${isIptvUser}','${userProfileType}', '${isNewUser}', '${hasPendingData}'); prepareLogoutUrl();">
<div id="boxLg02">
<p id="boxUser"><strong>Benvenuto | </strong> ${userName}</p>
<p id="btEsci"><span><a target="_parent" href="" title="Esci" onclick="top.unlogged();" id="btnEsciLogin">Esci</a></span></p>
</div>
</body>
</html>
