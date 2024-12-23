# Shopping Cart Application

This project is a **Shopping Cart Application** that allows users to manage their cart items, update quantities, and place orders. If a product's quantity is reduced to zero, the application automatically prompts the user to confirm whether to remove the product from the cart.

---

## Features

1. **Cart Item Management:**
    - Add, update, and remove products from the cart.
    - Update product quantities directly in the cart.

2. **Total Price Calculation:**
    - Automatically calculates the total price of selected items, including shipping fees.

3. **Shipping Options:**
    - Supports multiple shipping methods with corresponding fees:
        - Economy: Free
        - Fast: 30,000 VND
        - Express: 50,000 VND
4. **Payment Method:**
    - COD
    - VNPay

5.**Place Order:**
    - Validates cart items and sends the order data to the backend.


---

## Technology Stack

### Frontend:
- **HTML/CSS/JavaScript**
- DOM Manipulation for dynamic updates

### Backend:
- **Java Spring Boot**
    - Endpoint: `/user/login`
---

## File Structure

- **Frontend:** Contains the JavaScript code for managing the cart.
- **Backend:**
    - **Controller**
    - **Services**
    - **Repository**
    - **Entity**
    - **Config**

---



## Installation and Usage

1. Clone the repository:
   ```bash
   git clone https://github.com/Nhatminh309/ProjectIII-Shopping_web
   ```

2. Navigate to the project directory:
   ```bash
   cd ProjectIII-Shopping_web
   ```

3. Run the backend server:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Open the application in a browser and interact with the cart.



## Contact

For any inquiries, please contact:
- **Email:** nhatminh2k22k3@gmail.com
- **GitHub:** https://github.com/Nhatminh309

