package project_demo;

// Product Class
class Product {
    String name;
    double price;
    int quantity;
    int productId;

    public Product(String name, double price, int quantity, int productId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ID: " + productId + ", " + name + " - $" + price + ", Quantity: " + quantity;
    }
}

// Linked List Node
class ProductNode {
    Product product;
    ProductNode next;

    public ProductNode(Product product) {
        this.product = product;
        this.next = null;
    }
}

// Linked List Class
class ProductLinkedList {
    ProductNode head, tail;

    public void insertProduct(Product product) {
        ProductNode temp = head;
        while (temp != null) {
            if (temp.product.name.equals(product.name)) {
                temp.product.quantity += product.quantity;
                System.out.println(" Restocked '" + product.name + "'. New quantity: " + temp.product.quantity);
                return;
            }
            temp = temp.next;
        }
        ProductNode newNode = new ProductNode(product);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void deleteProduct(String name, int quantity) {
        ProductNode temp = head, prev = null;
        while (temp != null) {
            if (temp.product.name.equals(name)) {
                if (quantity <= 0) {
                    System.out.println(" Invalid quantity specified. Must be greater than 0.");
                    return;
                }

                if (temp.product.quantity == 0) {
                    System.out.println(" Product '" + name + "' is out of stock.");
                    return;
                }

                if (quantity > temp.product.quantity) {
                    throw new IllegalArgumentException(" Cannot delete " + quantity + " items. Only " + temp.product.quantity + " in stock for '" + name + "'.");
                }

                temp.product.quantity -= quantity;
                if (temp.product.quantity == 0) {
                    if (prev != null) {
                        prev.next = temp.next;
                    } else {
                        head = temp.next;
                    }
                    if (temp == tail) tail = prev;
                    System.out.println(" Product '" + name + "' removed as quantity reached 0.");
                } else {
                    System.out.println(" Decreased quantity of '" + name + "' by " + quantity + ". New quantity: " + temp.product.quantity);
                }
                return;
            }
            prev = temp;
            temp = temp.next;
        }
        System.out.println(" Product '" + name + "' not found.");
    }

    public void bubbleSort(String attribute) {
        if (head == null || head.next == null) return;
        boolean swapped;
        do {
            swapped = false;
            ProductNode current = head;
            while (current.next != null) {
                boolean condition = false;
                if (attribute.equals("name")) {
                    condition = current.product.name.compareTo(current.next.product.name) > 0;
                } else if (attribute.equals("price")) {
                    condition = current.product.price > current.next.product.price;
                }
                if (condition) {
                    Product temp = current.product;
                    current.product = current.next.product;
                    current.next.product = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
        System.out.println(" Products sorted by " + attribute);
        display();
    }

    public void filterByPrice(double min, double max) {
        ProductNode temp = head;
        boolean found = false;
        System.out.println("\n Products in price range $" + min + " - $" + max + ":");
        while (temp != null) {
            if (temp.product.price >= min && temp.product.price <= max) {
                System.out.println("  - " + temp.product);
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println(" No products found in this price range.");
    }

    public Product searchProduct(String name) {
        ProductNode temp = head;
        while (temp != null) {
            if (temp.product.name.equals(name)) {
                System.out.println(" Product found: " + temp.product);
                return temp.product;
            }
            temp = temp.next;
        }
        System.out.println(" Product '" + name + "' not found.");
        return null;
    }

    public Product searchById(int productId) {
        ProductNode temp = head;
        while (temp != null) {
            if (temp.product.productId == productId) {
                System.out.println(" Product found: " + temp.product);
                return temp.product;
            }
            temp = temp.next;
        }
        System.out.println(" Product with ID '" + productId + "' not found.");
        return null;
    }

    public void removeMostExpensive() {
        if (head == null) {
            System.out.println(" No products to remove.");
            return;
        }
        double max = -1;
        String name = null;
        ProductNode temp = head;
        while (temp != null) {
            if (temp.product.price > max) {
                max = temp.product.price;
                name = temp.product.name;
            }
            temp = temp.next;
        }
        if (name != null) {
            System.out.println(" Removing most expensive product: " + name);
            deleteProduct(name, Integer.MAX_VALUE);
        }
    }

    public void display() {
        if (head == null) {
            System.out.println(" No products in this category.");
            return;
        }
        ProductNode temp = head;
        while (temp != null) {
            System.out.println("  - " + temp.product);
            temp = temp.next;
        }
    }
}

// BST Node
class BSTNode {
    int categoryId;
    String categoryName;
    ProductLinkedList products;
    BSTNode left, right;

    public BSTNode(int id, String name) {
        categoryId = id;
        categoryName = name;
        products = new ProductLinkedList();
    }
}

// BST Class
class ProductBST {
    BSTNode root;
    java.util.Map<Integer, Integer> productCount = new java.util.HashMap<>();

    public void insertCategory(int id, String name) {
        if (findCategory(id) == null) {
            root = insertRecursive(root, id, name);
        }
    }

    private BSTNode insertRecursive(BSTNode node, int id, String name) {
        if (node == null) return new BSTNode(id, name);
        if (id < node.categoryId) node.left = insertRecursive(node.left, id, name);
        else if (id > node.categoryId) node.right = insertRecursive(node.right, id, name);
        return node;
    }

    public void deleteCategory(int id) {
        root = deleteRecursive(root, id);
    }

    private BSTNode deleteRecursive(BSTNode root, int id) {
        if (root == null) return null;
        if (id < root.categoryId) root.left = deleteRecursive(root.left, id);
        else if (id > root.categoryId) root.right = deleteRecursive(root.right, id);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            BSTNode min = findMin(root.right);
            root.categoryId = min.categoryId;
            root.categoryName = min.categoryName;
            root.products = min.products;
            root.right = deleteRecursive(root.right, min.categoryId);
        }
        return root;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public BSTNode findCategory(int id) {
        return findRecursive(root, id);
    }

    private BSTNode findRecursive(BSTNode node, int id) {
        if (node == null || node.categoryId == id) return node;
        return id < node.categoryId ? findRecursive(node.left, id) : findRecursive(node.right, id);
    }

    public void addProduct(int categoryId, String categoryName, String name, double price, int quantity) {
        productCount.putIfAbsent(categoryId, 0);
        productCount.put(categoryId, productCount.get(categoryId) + 1);
        int productId = categoryId * 100 + productCount.get(categoryId);
        insertCategory(categoryId, categoryName);
        BSTNode node = findCategory(categoryId);
        node.products.insertProduct(new Product(name, price, quantity, productId));
    }

    public void deleteProduct(int categoryId, String name, int quantity) {
        BSTNode node = findCategory(categoryId);
        if (node != null) node.products.deleteProduct(name, quantity);
    }

    public Product searchProduct(int categoryId, String name) {
        BSTNode node = findCategory(categoryId);
        if (node != null) return node.products.searchProduct(name);
        System.out.println(" Category ID '" + categoryId + "' not found.");
        return null;
    }

    public Product searchByProductId(int productId) {
        return searchByProductIdRecursive(root, productId);
    }

    private Product searchByProductIdRecursive(BSTNode node, int productId) {
        if (node == null) return null;
        Product result = node.products.searchById(productId);
        if (result != null) return result;
        Product leftResult = searchByProductIdRecursive(node.left, productId);
        if (leftResult != null) return leftResult;
        return searchByProductIdRecursive(node.right, productId);
    }

    public void sortProducts(int categoryId, String attr) {
        BSTNode node = findCategory(categoryId);
        if (node != null) node.products.bubbleSort(attr);
    }

    public void filterByPrice(int categoryId, double min, double max) {
        BSTNode node = findCategory(categoryId);
        if (node != null) node.products.filterByPrice(min, max);
    }

    public void display() {
        inOrder(root);
    }

    private void inOrder(BSTNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println("\n Category ID: " + node.categoryId + ", Name: " + node.categoryName);
            node.products.display();
            inOrder(node.right);
        }
    }
}

// Main Class
public class InventoryManagement {
    public static void main(String[] args) {
        ProductBST bst = new ProductBST();

        // Smartphones (101)
        bst.addProduct(101, "Smartphone", "iPhone 14", 999, 5);
        bst.addProduct(101, "Smartphone", "Galaxy S22", 799, 3);
        bst.addProduct(101, "Smartphone", "Pixel 7", 699, 4);

        // Laptops (102)
        bst.addProduct(102, "Laptop", "MacBook Pro", 1999, 2);
        bst.addProduct(102, "Laptop", "Dell XPS", 1499, 3);
        bst.addProduct(102, "Laptop", "HP Spectre", 1299, 4);

        // Tablets (103)
        bst.addProduct(103, "Tablet", "iPad Air", 599, 6);
        bst.addProduct(103, "Tablet", "Galaxy Tab", 499, 5);
        bst.addProduct(103, "Tablet", "Surface Go", 649, 2);

        // Headphones (104)
        bst.addProduct(104, "Headphones", "Sony WH-1000XM4", 349, 5);
        bst.addProduct(104, "Headphones", "Bose QC45", 329, 4);
        bst.addProduct(104, "Headphones", "AirPods Max", 549, 3);

        // Cameras (105)
        bst.addProduct(105, "Camera", "Canon EOS R10", 1099, 2);
        bst.addProduct(105, "Camera", "Nikon Z50", 1199, 2);
        bst.addProduct(105, "Camera", "Sony Alpha a6400", 1249, 1);

        // Smartwatches (106)
        bst.addProduct(106, "Smartwatch", "Apple Watch Series 8", 499, 4);
        bst.addProduct(106, "Smartwatch", "Samsung Galaxy Watch 5", 399, 5);
        bst.addProduct(106, "Smartwatch", "Fitbit Sense 2", 329, 6);

        // Monitors (107)
        bst.addProduct(107, "Monitor", "Dell Ultrasharp", 529, 2);
        bst.addProduct(107, "Monitor", "LG UltraFine", 599, 3);
        bst.addProduct(107, "Monitor", "ASUS ProArt", 649, 1);
        
        bst.deleteProduct(103, "Galaxy Tab", 2);

        bst.searchProduct(101, "iPhone 14");

        bst.sortProducts(102, "price");

        bst.filterByPrice(106, 300, 500);

        // Search by ID test
        Product p = bst.searchByProductId(10101);
        if (p == null) System.out.println(" Product with ID 10101 not found.");
        
        System.out.println("\n Final Inventory with Category and Product IDs:");
        bst.display();

        
    }
}