<#macro page_title>
    [TITLE]
</#macro>


<#macro page_head>
    <title><@page_title/> - Geek Pizza</title>
</#macro>

<#macro page_body></#macro>
<#macro scripts></#macro>

<#macro display_page>
<!DOCTYPE html>
<html>
<head>
    <@page_head/>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/css/site.css" />
</head>
<body>
<div id="Header">
    <div id="Title">
        <h1>Geek Pizza</h1>
        <h3>A BDD sample application</h3>
    </div>
    <ul class="nav">
        <li class="menu-button"><a href="/">Home</a></li>
        <li class="menu-button"><a href="/Menu">Menu</a></li>
        <li class="menu-button"><a href="/MyOrder">My Order</a></li>
        <#if authenticationService.isAdmin(springMacroRequestContext) >
            <li class="menu-button admin-button"><a href="/Admin">Admin</a></li>
        </#if>
    </ul>
    <div id="LoginInfo">
        <#if authenticationService.isLoggedIn(springMacroRequestContext) >
            Logged in as <span id="LoggedInUser">${authenticationService.getCurrentUserName(springMacroRequestContext)}</span>
            <a href="#" class="menu-button" onclick="logout()">Logout</a>
        <#else>
            <a class="menu-button" href="/Login">Login</a>
            <a class="menu-button" href="/Register">Register</a>
        </#if>
    </div>
    <div id="TestBar">
        For testing:
        <a href="#" onclick="resetData()">[Reset Data]</a>
        <a href="#" onclick="defaultLogin()">[Default Login]</a>
    </div>
</div>
<div id="Content">
    <@page_body/>
</div>
<div id="Footer">
    <p>Copyright (c) 2017-2023, Spec Solutions, Gaspar Nagy</p>
    <p>Learn more about our BDD courses at <a href="https://www.specsolutions.eu/services/training/" target="_blank">https://www.specsolutions.eu/services/training/</a>.</p>
</div>

<script src="/lib/jquery.min.js"></script>

<@scripts/>
<script language="javascript">
    function logout() {
        $.ajax('/api/auth', { method: 'DELETE' })
            .then(
                function success() {
                    console.log("logged out");
                    window.location.href = '/';
                },
                function fail(request) {
                    console.log(request);
                    alert('Request failed (' + request.status + '). ' + request.statusText);
                }
            );
    }
    function resetData() {
        $.ajax('/api/test/Seed', { method: 'POST' })
            .then(
                function success() {
                    console.log("database has been reset");
                    window.location.href = '/';
                },
                function fail(request) {
                    console.log(request);
                    alert('Request failed (' + request.status + '). ' + request.statusText);
                }
            );
    }
    function defaultLogin() {
        $.ajax('/api/test/DefaultLogin', { method: 'POST' })
            .then(
                function success() {
                    console.log("logged in with default user");
                    window.location.href = '/';
                },
                function fail(request) {
                    console.log(request);
                    alert('Request failed (' + request.status + '). ' + request.statusText);
                }
            );
    }
</script>
</body>
</html>
</#macro>