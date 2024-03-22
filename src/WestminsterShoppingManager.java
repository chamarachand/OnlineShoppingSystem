import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private ArrayList<Product> productList;
    private ArrayList<User> userList;
    private final int MAX_PRODUCTS = 50;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.loadProducts();
        shoppingManager.loadUsers();
        shoppingManager.execute();
    }

    /**
     * Initializing the programme by displaying the console menu
     * User is navigated to different options based on their choice
     */
    public void execute() {
        int userInput;
        boolean quit = false;

        do {
            printMainMenu();
            userInput = InputValidator.getConsoleMenuInput(0,5);

            switch (userInput) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> printProductList();
                case 4 -> saveProducts();
                case 5 -> accessControl();
                case 0 -> {
                    quit = true;
                    System.out.println("Thank you for using the program. Goodbye!");
                }
            }
        } while (!quit);
    }

    /**
     * Prints the main menu
     */
    private void printMainMenu() {
        System.out.println("""
                
                Welcome to Shopping Manager Console!
                -------------------------------------------------
                Please select an option
                1) Add a new product to the system
                2) Remove a product from the system
                3) Print a list of products
                4) Save to file
                5) Open user GUI
                0) Quit
                -------------------------------------------------
                """);
    }

    /**
     * Overrides the addProduct Method of the ShoppingManager interface
     * Allows the shopping manager to add products to the system
     */
    @Override
    public void addProduct() {
        if (productList.size() < MAX_PRODUCTS) {
            Product newProduct = null;
            
            String category = InputValidator.getStringInput("Select Category? Electronics(E)  / Clothing(C): ", "E", "C");
            boolean isElectronic = category.equals("E");
            boolean isClothing = category.equals("C");
            
            String productId = InputValidator.getStringInput("Enter product ID: ");
            String productName = InputValidator.getStringInput("Enter product name: ");
            int availableItems = InputValidator.getIntInput("Enter number of available items: ", 0);
            double price = InputValidator.getDoubleInput("Enter price: ", 0);
            
            if (isElectronic) {
                String brand = InputValidator.getStringInput("Enter product brand: ");
                int warrantyPeriod = InputValidator.getIntInput("Enter product warranty period (Weeks): ", 0);

                newProduct = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
            }

            
            if (isClothing) {
                String size = InputValidator.getStringInput("Enter cloth size: ");
                String colour = InputValidator.getStringInput("Enter cloth colour: ");

                newProduct = new Clothing(productId, productName, availableItems, price, size, colour);
            }

            productList.add(newProduct);
            System.out.println("Product added successfully!");

            Collections.sort(productList);

        } else
            System.out.println("Maximum product limit reached. Cannot add new products!");
    }

    /**
     * Overrides the removeProducts method in the ShoppingManager interface
     * Allows the shopping manager to remove products in the system by their product ID
     */
    @Override
    public void removeProduct() {
        String productId = InputValidator.getStringInput("Enter product id: ");

        for (Product product: productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                System.out.println("Product removed successfully!");
                System.out.println(product);
                System.out.println("\n" + productList.size() + " products left in the system");
                return;
            }
        }
        System.out.println("Product with the given id not found!");
    }

    /**
     * Overrides the printProductList method in the ShoppingManager interface
     * Displays a list of all the products in the system, sorted alphabetically based on the product ID
     */
    @Override
    public void printProductList() {
        if (!productList.isEmpty()) {
            for (Product product : productList)
                System.out.println(product);
        } else
            System.out.println("No products in the list!");
    }

    /**
     * Overrides the saveProducts method in the Shopping manager interface
     * Saves the products with their details into a bytecode file
     */
    @Override
    public void saveProducts() {
        try (FileOutputStream fileOut = new FileOutputStream("products.ser");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

            objOut.writeObject(productList);
            System.out.println("Saved Successfully!");

        } catch (IOException e) {
            System.out.println("Error in Saving Data! " + e);
        }
    }

    /**
     * Saves the products with their details into a bytecode file
     */
    public void saveUsers() {
        try (FileOutputStream fileOut = new FileOutputStream("users.ser");
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)) {

            objOut.writeObject(userList);

        } catch (IOException e) {
            System.out.println("Error in Saving Data! " + e);
        }
    }

    /**
     * Loads back the product details from the bytecode file to the system, when the system is initiated
     */
    public void loadProducts() {
        File file = new File("products.ser");

        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file); // yesterday changed to 'file'
                ObjectInputStream objIn = new ObjectInputStream(fileIn);

                productList  = (ArrayList<Product>) objIn.readObject();
                fileIn.close(); // Better to add close in finally or use try catch with resources
                objIn.close();

            } catch (IOException | ClassNotFoundException exception) {
                System.out.println("Could not load previous product data!");
            }
        }
    }

    /**
     * Loads back the user details from the bytecode file to the system, when the system is initiated
     */
    public void loadUsers() {
        File file = new File("users.ser");

        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objIn = new ObjectInputStream(fileIn);

                userList  = (ArrayList<User>) objIn.readObject();
                fileIn.close(); // Better to add close in finally or use try catch with resources
                objIn.close();

            } catch (IOException | ClassNotFoundException exception) {
                System.out.println("Could not load previous user data!");
            }
        }
    }

    /**
     * Displaying the sub console menu for user login and registration
     * Users can navigate to log in or sign in based on their choice
     */
    public void accessControl() {
        int userInput;
        boolean quit = false;

        do {
            printAccessControlMenu();
            userInput = InputValidator.getConsoleMenuInput(0,2);

            User user = null;

            switch (userInput) {
                case 1 -> user =  authenticateUser();
                case 2 -> user  = registerUser();
                case 0 -> quit = true;
            }

            if (user != null) {
                loadUserGUI(user);
                quit = true;
            }

        } while (!quit);
    }

    /**
     * Prints login and sign in sub menu
     */
    private void printAccessControlMenu() {
        System.out.println("""
                Please select an option
                1) Log In
                2) Sign In
                0) Exit
                """);
    }

    /**
     * Authenticating a user before login
     * @return user
     */
    private User authenticateUser() {
        String username = InputValidator.getStringInput("Username: ");
        String password = InputValidator.getStringInput("Password: ");

        for (User user: userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }

        System.out.println("Invalid username or password!\n");
        return null;
    }

    /**
     * Registering a user in the system
     * @return user
     */
    private User registerUser() {
        String username = InputValidator.getStringInput("Username: ");

        if (!userList.isEmpty()) {
            for (User user: userList) {
                if (user.getUsername().equals(username)) {
                    System.out.println("This username is already taken!\n");
                    return null;
                }
            }
        }

        String password = InputValidator.getStringInput("Password: ");
        User user = new User(username, password);
        userList.add(user);
        saveUsers();
        System.out.println("Registered Successfully!");
        return user;
    }

    /**
     * Opening and displaying the GUI main menu
     */
    private void loadUserGUI(User user) {
        WestminsterShoppingFrame frame = new WestminsterShoppingFrame(productList, userList, user);
        frame.setVisible(true);
        frame.setSize(800, 650);
    }
}
