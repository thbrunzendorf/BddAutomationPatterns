<#include "layout.ftlh">
<#macro page_title>
    Order Details
</#macro>

<#macro page_body>
    <h2>Order Details</h2>

    <form id="OrderDetailsForm">
        <div class="form-horizontal">
            <hr />
            <div class="error"></div>

            <div class="form-group">
                <label for="DeliveryStreetAddress" class="control-label">Delivery Address / Street (required)</label>
                <div class="control-input">
                    <input type="text" id="DeliveryStreetAddress"/>
                </div>
            </div>

            <div class="form-group">
                <label for="DeliveryCity" class="control-label">Delivery Address / City (required)</label>
                <div class="control-input">
                    <input type="text" id="DeliveryCity"/>
                </div>
            </div>

            <div class="form-group">
                <label for="DeliveryZip" class="control-label">Delivery Address / Zip</label>
                <div class="control-input">
                    <input type="text" id="DeliveryZip"/>
                </div>
            </div>

            <div class="form-group">
                <label for="DeliveryDate" class="control-label">Delivery Date (required)</label>
                <div class="control-input">
                    <input type="text" id="DeliveryDate"/>
                </div>
            </div>

            <div class="form-group">
                <label for="DeliveryTime" class="control-label">Delivery Time</label>
                <div class="control-input">
                    <input type="text" id="DeliveryTime"/>
                </div>
            </div>

            <div class="form-group">
                <input type="submit" value="Save" id="SaveButton" class="btn btn-default" />
            </div>
        </div>
    </form>
</#macro>

<#macro scripts>
    <script language="javascript">
        $(function() {
            $.ajax('/api/order')
                .then(
                    function success(order) {
                        console.log(order);
                        $('#DeliveryStreetAddress').val(order.deliveryAddress.streetAddress);
                        $('#DeliveryCity').val(order.deliveryAddress.city);
                        $('#DeliveryZip').val(order.deliveryAddress.zip);
                        $('#DeliveryDate').val(order.deliveryDate.substring(0, 10)); //yyyy-MM-dd
                        $('#DeliveryTime').val(order.deliveryTime.substring(0, 5)); //h\\:mm
                    },
                    function fail(request) {
                        console.log(request);
                        alert('Request failed (' + request.status + '). ' + request.statusText);
                    }
                );
            $('#OrderDetailsForm').submit(function(event) {
                $.ajax('/api/order',
                    {
                        method: 'PUT',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            deliveryStreetAddress: $('#DeliveryStreetAddress').val(),
                            deliveryCity: $('#DeliveryCity').val(),
                            deliveryZip: $('#DeliveryZip').val(),
                            deliveryDate: $('#DeliveryDate').val(),
                            deliveryTime: $('#DeliveryTime').val()
                        })
                    })
                    .then(
                        function success() {
                            console.log("order updated");
                            window.location.href = '/MyOrder';
                        },
                        function fail(request) {
                            console.log(request);
                            alert('Request failed (' + request.status + '). ' + request.statusText);
                        }
                    );
                // stop the form from submitting the normal way and refreshing the page
                event.preventDefault();
            });

        });
    </script>
</#macro>

<@display_page/>
