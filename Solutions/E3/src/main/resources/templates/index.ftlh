<#include "layout.ftlh">
<#macro page_title>
    Home
</#macro>

<#macro page_body>
    <h2 class="message"></h2>
</#macro>

<#macro scripts>
    <script language="javascript">
        $(function() {
            $.ajax('/api/home')
                .then(
                    function success(viewModel) {
                        console.log(viewModel);
                        $(".message").html(viewModel.welcomeMessage);
                    },
                    function fail(request) {
                        console.log(request);
                        alert('Request failed (' + request.status + '). ' + request.statusText);
                    }
                );
        });
    </script>
</#macro>

<@display_page/>
