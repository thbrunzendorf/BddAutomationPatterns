<#include "layout.ftlh">
<#macro page_title>
    Login
</#macro>

<#macro page_body>
    <h2 class="message">Login</h2>
    <p>This dummy login page is for demo purposes only! Don't copy!</p>
    <p>Use ${defaultDataService.getDefaultUserName()}/${defaultDataService.getDefaultPassword()} as credentials (or ${defaultDataService.getAdminUserName()}/${defaultDataService.getAdminPassword()} for login as administrator).</p>

    <form id="LoginForm">
        <div class="form-horizontal">
            <hr />
            <div id="ErrorMessage" class="error" style="display: none"></div>
            <div class="form-group">
                <label for="Name" class="control-label">Name</label>
                <div class="control-input">
                    <input type="text" id="Name"/>
                </div>
            </div>

            <div class="form-group">
                <label for="Password" class="control-label">Password</label>
                <div class="control-input">
                    <input type="text" id="Password"/>
                </div>
            </div>

            <div class="form-group">
                <input type="submit" value="Login" id="LoginButton" class="btn btn-default" />
            </div>
        </div>
    </form>
</#macro>

<#macro scripts>
    <script>
        $(function() {
            $('#LoginForm').submit(function(event) {
                $('#ErrorMessage').hide();
                $.ajax('/api/auth',
                    {
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(
                            {
                                name: $('#Name').val(),
                                password: $('#Password').val()
                            })
                    })
                    .then(
                        function success(authToken) {
                            console.log("logged in, token: " + authToken);
                            window.location.href = '/';
                        },
                        function fail(request) {
                            if (request.status === 403) {
                                console.log(request);
                                var errorMessage = $('#ErrorMessage');
                                errorMessage.show();
                                errorMessage.html(request.responseJSON.message);
                            } else {
                                console.log(request);
                                alert('Request failed (' + request.status + '). ' + request.statusText);
                            }
                        }
                    );
                // stop the form from submitting the normal way and refreshing the page
                event.preventDefault();
            });
        });
    </script>
</#macro>

<@display_page/>
