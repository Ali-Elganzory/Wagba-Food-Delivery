// Constants
const OrderStatus = {
  Paid: "Paid",
  Preparing: "Preparing",
  Ready: "Ready",
  Delivered: "Delivered",
  Declined: "Declined",
};

// Update order status
const updateOrderStatus = (uid, orderId, status) => {
  const orderReference = ref(database, `orders/${uid}/${orderId}`);
  window.update(orderReference, {
    status,
  });
};
const prepareOrder = (uid, orderId) =>
  updateOrderStatus(uid, orderId, "Preparing");
const declineOrder = (uid, orderId) =>
  updateOrderStatus(uid, orderId, "Declined");
const readyOrder = (uid, orderId) => updateOrderStatus(uid, orderId, "Ready");
const deliveredOrder = (uid, orderId) =>
  updateOrderStatus(uid, orderId, "Delivered");

window.onload = function () {
  // Render orders
  const renderOrders = (orders) => {
    const ordersContainer = document.getElementById("order-list");
    ordersContainer.innerHTML = "";
    orders.forEach((order) => {
      const orderItem = `
        <div id="${
          order.id
        }" class="rounded-md shadow-md flex flex-col bg-gray-50">
          <div class="rounded-t-md h-4 bg-orange-400"></div>
          <div class="p-8 pt-4 bg-gra">
            <div class="flex flex-col space-y-2 grow">
              <div class="flex flex-row justify-between">
                <span class="text-gray-400">Order# ${order.id}</span>
                <span class="text-gray-400">${order.date}</span>
              </div>
              <div class="-mx-8 my-2 border-2 border-gray-200 border-dotted"></div>
              <div class="flex flex-row space-x-6">
                <div class="flex flex-col space-y-2">
                  <div class="flex flex-col">
                    <span class="text-gray-400">Items</span>
                    <span class="text-gray-400">Total</span>
                    <span class="text-gray-400">Gate</span>
                    <span class="text-gray-400">Time</span>
                    <span class="text-gray-400">Status</span>
                  </div>
                </div>
                <div class="flex flex-col space-y-2 flex-1">
                  <div class="flex flex-col">
                    <span>${order.items}</span>
                    <span>${Number(order.total).toFixed(2)} EGP</span>
                    <span>${order.gate}</span>
                    <span>${order.time}</span>
                    <span>${order.status}</span>
                  </div>
                </div>
                <div class="flex flex-col justify-end">
                  <div class="flex flex-row space-x-4">
                    ${
                      (order.status === OrderStatus.Paid &&
                        `
                      <button type="button" class="btn btn-outline-secondary" onclick="declineOrder('${order.uid}', '${order.id}')">
                        Decline
                      </button>
                      <button type="button" class="btn btn-outline-warning" onclick="prepareOrder('${order.uid}', '${order.id}')">
                        Approve
                      </button>
                      `) ||
                      ""
                    }
                    ${
                      (order.status === OrderStatus.Preparing &&
                        `
                      <button type="button" class="btn btn-outline-success" onclick="readyOrder('${order.uid}', '${order.id}')">
                        Ready
                      </button>
                      `) ||
                      ""
                    }
                    ${
                      (order.status === OrderStatus.Ready &&
                        `
                        <button type="button" class="btn btn-outline-success" onclick="deliveredOrder('${order.uid}', '${order.id}')">
                          Delivered
                        </button>`) ||
                      ""
                    }
                    ${
                      (order.status === OrderStatus.Delivered &&
                        `
                        <span class="text-gray-400 italic">Delivered</span>
                        `) ||
                      ""
                    }
                    ${
                      (order.status === OrderStatus.Declined &&
                        `
                        <span class="text-gray-400 italic">Declined</span>
                        `) ||
                      ""
                    }
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      `;
      ordersContainer.innerHTML += orderItem;
    });
  };

  // Listen to orders
  onValue(ref(database, "orders"), (snapshot) => {
    orders = [];
    for (const [uid, userOrders] of Object.entries(snapshot.val())) {
      for (const [orderId, order] of Object.entries(userOrders)) {
        orders.push({ ...order, uid });
      }
    }
    renderOrders(orders);
  });
};
