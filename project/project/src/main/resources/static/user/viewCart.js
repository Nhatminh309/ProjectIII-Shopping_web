document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".table-row");
    const shippingSelect = document.querySelector(".form-select");
    const placeOrderButton = document.getElementById("place-order-btn");
    let shippingFee = 0; // Biến lưu phí giao hàng
    let cartItems = []; // Lưu trữ thông tin giỏ hàng

    // Vô hiệu hóa nút "Đặt hàng" ban đầu
    placeOrderButton.disabled = true;

    // Cập nhật thông tin giỏ hàng mỗi khi có sự thay đổi số lượng
    function updateCartItems() {
        cartItems = [];
        rows.forEach(row => {
            const checkbox = row.querySelector("input[type=checkbox]");
            if (checkbox.checked) {
                const cartItemId = row.getAttribute("data-id");
                const quantityInput = row.querySelector("input[name=quantity]");
                const quantity = parseInt(quantityInput.value);
                const priceElement = row.querySelector(".col-md-2 h6");
                const price = parseFloat(priceElement.innerText.replace(" VND", "").replace(/\./g, ""));
                const sizeElement = row.querySelector(".col-md-3 h6:nth-child(2)");
                const size = sizeElement ? sizeElement.innerText : "";
                cartItems.push({
                    id: cartItemId,
                    quantity: quantity,
                    price: price,
                    size: size
                });
            }
        });
    }

    // Hàm xóa sản phẩm khi số lượng bằng 0
    function confirmRemoveProduct(row) {
        const cartItemId = row.getAttribute("data-id");
        const confirmed = confirm("Số lượng bằng 0. Bạn có muốn xóa sản phẩm này khỏi giỏ hàng không?");
        if (confirmed) {
            // Gửi yêu cầu xóa sản phẩm
            fetch("/user/removeCart", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ cartItemIds: [cartItemId] })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Không thể xóa sản phẩm. Vui lòng thử lại.");
                    }
                    // Xóa dòng khỏi giao diện
                    row.remove();
                    updateTotalPrice();
                    updateSelectedItems();
                })
                .catch(error => {
                    console.error(error);
                    alert("Có lỗi xảy ra khi xóa sản phẩm.");
                });
        } else {
            // Đặt lại số lượng về 1 nếu người dùng hủy bỏ
            row.querySelector("input[name=quantity]").value = 1;
            updateTotalPrice();
        }
    }

    // Hàm tính tổng tiền và hiển thị trên giao diện
    function updateTotalPrice() {
        let totalPrice = 0;
        let hasSelectedItem = false;

        rows.forEach(row => {
            const checkbox = row.querySelector("input[type=checkbox]");
            const quantityInput = row.querySelector("input[name=quantity]");
            const priceElement = row.querySelector(".col-md-2 h6");
            const price = parseFloat(priceElement.innerText.replace(" VND", "").replace(/\./g, ""));

            if (checkbox.checked) {
                hasSelectedItem = true;
                totalPrice += price * quantityInput.value;
            }
        });

        if (hasSelectedItem) {
            totalPrice += shippingFee;
        }

        document.getElementById("total-price").innerText = totalPrice.toLocaleString("vi-VN") + " VND";
        placeOrderButton.disabled = !hasSelectedItem;
    }

    function updateSelectedItems() {
        let selectedCount = 0;

        rows.forEach(row => {
            const checkbox = row.querySelector("input[type=checkbox]");
            if (checkbox.checked) {
                selectedCount++;
            }
        });

        document.getElementById("selected-items").innerText = selectedCount;
    }

    rows.forEach(row => {
        const checkbox = row.querySelector("input[type=checkbox]");
        const quantityInput = row.querySelector("input[name=quantity]");
        const minusButton = row.querySelector(".btn-outline-secondary:nth-child(1)");
        const plusButton = row.querySelector(".btn-outline-secondary:nth-child(3)");

        checkbox.addEventListener("change", () => {
            updateTotalPrice();
            updateSelectedItems();
            updateCartItems();
        });

        quantityInput.addEventListener("input", () => {
            if (quantityInput.value === "0") {
                confirmRemoveProduct(row);
            } else {
                updateTotalPrice();
                updateCartItems();
            }
        });

        minusButton.addEventListener("click", () => {
            if (quantityInput.value > 0) {
                quantityInput.value = parseInt(quantityInput.value) - 1;
                if (Number(quantityInput.value) === 0) {
                    confirmRemoveProduct(row);
                } else {
                    updateTotalPrice();
                    updateCartItems();
                }
            }
        });

        plusButton.addEventListener("click", () => {
            quantityInput.value = parseInt(quantityInput.value) + 1;
            updateTotalPrice();
            updateCartItems();
        });
    });

    shippingSelect.addEventListener("change", () => {
        const selectedOption = shippingSelect.value;

        switch (selectedOption) {
            case "Tiết kiệm - Free":
                shippingFee = 0;
                break;
            case "Nhanh - 30.000VND":
                shippingFee = 30000;
                break;
            case "Hỏa tốc - 50.000VND":
                shippingFee = 50000;
                break;
            default:
                shippingFee = 0;
        }

        updateTotalPrice();
    });

    placeOrderButton.addEventListener("click", function () {
        if (placeOrderButton.disabled) {
            alert("Vui lòng chọn ít nhất một sản phẩm để đặt hàng.");
            return;
        }

        updateCartItems();

        const shippingMethod = shippingSelect.value;
        const paymentSelect = document.querySelectorAll(".form-select")[1];
        const paymentMethod = paymentSelect.value;

        const orderData = {
            cartItems: cartItems,
            shippingMethod: shippingMethod,
            paymentMethod: paymentMethod,
            totalPrice: parseFloat(document.getElementById("total-price").innerText.replace(" VND", "").replace(/\./g, ""))
        };

        fetch("/user/placeOrder", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(orderData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Vui lòng điền đầy đủ thông tin");
                }
                window.location.href = "/pay";
            })
            .catch(error => {
                console.error(error);
                alert("Đặt hàng thất bại. Vui lòng thử lại.");
            });
    });

    updateTotalPrice();
    updateSelectedItems();
});
