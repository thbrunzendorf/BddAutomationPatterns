<#include "layout.ftlh">
<#macro page_title>
    Admin
</#macro>

<#macro page_body>
    <h2 class="message">Update restaurant menu (Menu Admin)</h2>
    <form id="MenuAdminForm">
        <div class="form-horizontal">
            <hr />
            <div id="ErrorMessage" class="error" style="display: none"></div>

            <div class="form-group">
                <label class="control-label" for="MenuText">Menu</label>
                <div class="control-input">
                <textarea id="MenuText" class="text-box" cols="80" rows="10">
                </textarea>
                </div>
            </div>

            <div class="form-group">
                <input type="submit" value="Update" id="UpdateButton" class="btn btn-default" />
            </div>
        </div>
    </form>
</#macro>

<#macro scripts>
    <script>
        $(function() {
            $.ajax('/api/admin/GetMenuItems')
                .then(
                    function success(menu) {
                        console.log(menu);
                        var menuText = "";
                        $.each(menu,
                            function(index, menuItem) {
                                menuText += menuItem.name + ";" + menuItem.ingredients + ";" + menuItem.calories + ";" +
                                    (menuItem.isInactive ? "inactive" : "") + "\n";
                            });

                        $('#MenuText').val(menuText);
                    },
                    function fail(request) {
                        console.log(request);
                        alert('Request failed (' + request.status + '). ' + request.statusText);
                    }
                );
            $('#MenuAdminForm').submit(function(event) {
                var menuText = $('#MenuText').val();
                var menu = [];
                var menuTextLines = menuText.split("\n");
                $.each(menuTextLines,
                    function(index, menuTextLine) {
                        if (menuTextLine.trim()) {
                            var parts = menuTextLine.split(";");
                            var menuItem = {
                                name: parts[0].trim(),
                                ingredients: parts[1].trim(),
                                calories: parseInt(parts[2].trim()),
                                isInactive: parts.length > 3 && parts[3] === "inactive"
                            };
                            menu.push(menuItem);
                        }
                    });
                $.ajax('/api/admin/UpdateMenu',
                    {
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(menu)
                    })
                    .then(
                        function success() {
                            console.log("menu updated");
                            window.location.href = '/Menu';
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
