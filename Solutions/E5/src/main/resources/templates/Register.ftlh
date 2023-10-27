<#include "layout.ftlh">
<#macro page_title>
    Register
</#macro>

<#macro page_body>
    <h2 class="message">Register</h2>

    <form id="RegisterForm">
        <div class="form-horizontal">
            <hr />
            <div id="ErrorMessage" class="error" style="display: none"></div>

            <div class="form-group">
                User name (required)
                <div class="control-input">
                    <input type="text" id="UserName"/>
                </div>
            </div>

            <div class="form-group">
                Password (required)
                <div class="control-input">
                    <input type="text" id="Password"/>
                </div>
            </div>

            <div class="form-group">
                Re-enter password (required)
                <div class="control-input">
                    <input type="text" id="PasswordReEnter"/>
                </div>
            </div>

            <div class="form-group">
                <input type="submit" value="Register" id="RegisterButton" class="btn btn-default" />
            </div>
        </div>
    </form>
</#macro>

<#macro scripts>
    <script>
        $(function() {
            $('#RegisterForm').submit(function(event) {
                $.ajax('/api/user',
                    {
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            userName: $('#UserName').val(),
                            password: $('#Password').val(),
                            passwordReEnter: $('#PasswordReEnter').val()
                        })
                    })
                    .then(
                        function success() {
                            console.log("user registered");
                            window.location.href = '/Login';
                        },
                        function fail(request) {
                            if (request.status === 400) {
                                console.log(request);
                                $('#ErrorMessage').show();
                                $('#ErrorMessage').html(request.responseJSON.message);
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
