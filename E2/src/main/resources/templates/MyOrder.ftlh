<#include "layout.ftlh">
<#macro page_title>
    My Order
</#macro>

<#macro page_body>
    <h2>My Order</h2>
    <hr />
    <#if !(authenticationService.isLoggedIn(springMacroRequestContext)) >
    <h3>Please <a href="/Login">Login</a> to see your order!</h3>
    <#else>
    <h3>Order Details</h3>
    <ul>
        <li>Delivery Address: <span class="address" id="deliveryAddress"></span></li>
        <li>Delivery Date: <span class="date" id="DeliveryDate"></span></li>
        <li>Delivery Time: <span class="time" id="DeliveryTime"></span> </li>
    </ul>
    <form action="/OrderDetails" method="get">
    <input type="submit" value="Change" id="ChangeDetailsButton" class="btn btn-default" />
    </form>
    <hr />
    <h3>Ordered Items</h3>
    <table>
        <thead><th>Name</th><th>Size</th><th>Extra cheese</th></thead>
        <tbody id="OrderItems"></tbody>
    </table>
    <hr />
    <h4>Subtotal: <span id="SubtotalPrice"></span></h4>
    <h4>Delivery Costs: <span id="DeliveryCosts"></span></h4>
    <h3>Total Price: <span id="TotalPrice"></span></h3>
    <form id="OrderForm">
        <input type="submit" value="Order Now!" id="PlaceOrderButton" class="btn btn-default" />
    </form>
    </#if>
</#macro>

<#macro scripts>
    <#if authenticationService.isLoggedIn(springMacroRequestContext) >
        <script language="javascript">
            function getSelectOption(value, text, selectedValue){
                return '<option value="' + value + '"' + (value === selectedValue ? ' selected="selected"' : '') + '>' + text + '</option>'
            }
            function updatePrice(order){
                $('#SubtotalPrice').html('$' + order.prices.subTotal);
                $('#DeliveryCosts').html('$' + order.prices.deliveryCosts);
                $('#TotalPrice').html('$' + order.prices.total);
            }

            $(function() {
                $.ajax('/api/order')
                    .then(
                        function success(order) {
                            console.log(order);
                            $('#deliveryAddress').html(order.deliveryAddress.streetAddress +
                                ', ' +
                                order.deliveryAddress.city +
                                ', ' +
                                order.deliveryAddress.zip);
                            $('#DeliveryDate').html(order.deliveryDate.substring(0, 10)); //yyyy-MM-dd
                            $('#DeliveryTime').html(order.deliveryTime.substring(0, 5)); //h\\:mm
                            updatePrice(order);
                            const table = $("#OrderItems");
                            order.orderItems.forEach(function(orderItem) {
                                const tr = $('<tr>');
                                tr.append($('<td class="order-item-name">').html(orderItem.name));
                                tr.append($('<td class="order-item-size">').html('<select name="size" class="item-size-change" data-id="' + orderItem.id + '">' + getSelectOption('Small', 'Small', orderItem.size) + getSelectOption('Medium', 'Medium', orderItem.size) + getSelectOption('Large', 'Large', orderItem.size) + '</select>'));
                                tr.append($('<td class="order-item-extra-cheese">').html('<input type="checkbox" class="order-item-extra-cheese-checkbox" data-id="' + orderItem.id + '"' + (orderItem.extraCheese ? ' checked="checked"' : '') + '/>'));
                                tr.append($('<td class="order-item-delete">').html('<button type="button" class="order-item-delete-button" data-id="' + orderItem.id + '">Remove</button>'));
                                table.append(tr);
                            });
                            $('.item-size-change').change(function() {
                                const itemId = $(this).attr('data-id');
                                const sizeValue = $('option:selected', this).attr('value');
                                console.log("updating order item #" + itemId + ' to ' + sizeValue);
                                $.ajax('/api/order/items/' + itemId,
                                    {
                                        method: 'PUT',
                                        contentType: 'application/json',
                                        data: JSON.stringify(
                                            {
                                                size: sizeValue
                                            })
                                    })
                                    .then(
                                        function success(updatedOrder) {
                                            console.log("order item updated");
                                            console.log(updatedOrder)
                                            updatePrice(updatedOrder);
                                        },
                                        function fail(request) {
                                            console.log(request);
                                            alert('Request failed (' + request.status + '). ' + request.statusText);
                                        }
                                    );
                            });
                            $('.order-item-extra-cheese-checkbox').change(function() {
                                const itemId = $(this).attr('data-id');
                                const isChecked = $(this).is(':checked');
                                console.log("updating order item #" + itemId + ' extra cheese to ' + isChecked);
                                $.ajax('/api/order/items/' + itemId,
                                    {
                                        method: 'PUT',
                                        contentType: 'application/json',
                                        data: JSON.stringify(
                                            {
                                                extraCheese: isChecked
                                            })
                                    })
                                    .then(
                                        function success(updatedOrder) {
                                            console.log("order item updated");
                                            console.log(updatedOrder)
                                            updatePrice(updatedOrder);
                                        },
                                        function fail(request) {
                                            console.log(request);
                                            alert('Request failed (' + request.status + '). ' + request.statusText);
                                        }
                                    );
                            });
                            $('.order-item-delete-button').click(function() {
                                const button = $(this);
                                const itemId = $(this).attr('data-id');
                                console.log("removing order item #" + itemId);
                                $.ajax('/api/order/items/' + itemId,
                                    {
                                        method: 'DELETE',
                                        contentType: 'application/json',
                                    })
                                    .then(
                                        function success(updatedOrder) {
                                            console.log("order item deleted");
                                            console.log(updatedOrder)
                                            const tr = button.parent().closest('tr');
                                            tr.remove();
                                            updatePrice(updatedOrder);
                                            return false;
                                        },
                                        function fail(request) {
                                            console.log(request);
                                            alert('Request failed (' + request.status + '). ' + request.statusText);
                                        }
                                    );
                            });
                        },
                        function fail(request) {
                            console.log(request);
                            alert('Request failed (' + request.status + '). ' + request.statusText);
                        }
                    );
                $('#OrderForm').submit(function(event) {
                    $.ajax('/api/order', { method: 'POST' })
                        .then(
                            function success() {
                                console.log("ordered");
                                window.location.href = '/';
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
    </#if>


</#macro>

<@display_page/>
