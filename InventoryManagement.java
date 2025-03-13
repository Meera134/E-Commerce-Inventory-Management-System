//Product Class 
class Product {
    String name;
    double price;
    int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        return name + " - $" + price + ", Quantity: " + quantity;
    }
}

//  Linked List Node
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

    public ProductLinkedList() {
        this.head = this.tail = null;
    }

    // Insert Product at End
    public void insertProduct(Product product) {
        ProductNode newNode = new ProductNode(product);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        System.out.println(" Product added: " + product.name);
    }

    // Delete Product by Name
    public void deleteProduct(String name) {
        ProductNode temp = head, prev = null;
        while (temp != null) {
            if (temp.product.name.equals(name)) {
                if (prev != null) {
                    prev.next = temp.next;
                } else {
                    head = temp.next;
                }
                if (temp == tail) {
                    tail = prev;
                }
                System.out.println(" Product '" + name + "' deleted.");
                return;
            }
            prev = temp;
            temp = temp.next;
        }
        System.out.println("Product '" + name + "' not found.");
    }

    // Bubble Sort Directly on Linked List
    public void bubbleSort(String attribute) {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            ProductNode current = head;

            while (current.next != null) {
                boolean shouldSwap = false;
                if (attribute.equals("name") && current.product.name.compareTo(current.next.product.name) > 0) {
                    shouldSwap = true;
                } else if (attribute.equals("price") && current.product.price > current.next.product.price) {
                    shouldSwap = true;
                }

                if (shouldSwap) {
                    Product temp = current.product;
                    current.product = current.next.product;
                    current.next.product = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);

        System.out.println(" Products sorted by " + attribute);
    }

    // Filter Products by Price Range
    public void filterByPrice(double minPrice, double maxPrice) {
        ProductNode temp = head;
        boolean found = false;
        System.out.println("\n Products in price range $" + minPrice + " - $" + maxPrice + ":");

        while (temp != null) {
            if (temp.product.price >= minPrice && temp.product.price <= maxPrice) {
                System.out.println("  - " + temp.product);
                found = true;
            }
            temp = temp.next;
        }

        if (!found) {
            System.out.println(" No products found in this price range.");
        }
    }

    //  Display Products in Linked List
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

//  BST Node (Each Node Represents a Category)
class BSTNode {
    String category;
    ProductLinkedList products;
    BSTNode left, right;

    public BSTNode(String category) {
        this.category = category;
        this.products = new ProductLinkedList();
        this.left = this.right = null;
    }
}

// BST Class (Manages Product Categories)
class ProductBST {
    BSTNode root;

    public ProductBST() {
        this.root = null;
    }

    //  Insert a Category into BST
    private BSTNode insertCategory(BSTNode root, String category) {
        if (root == null) return new BSTNode(category);
        if (category.compareTo(root.category) < 0) {
            root.left = insertCategory(root.left, category);
        } else if (category.compareTo(root.category) > 0) {
            root.right = insertCategory(root.right, category);
        }
        return root;
    }

    public void addProduct(String category, Product product) {
        root = insertCategory(root, category);
        BSTNode node = findCategory(root, category);
        if (node != null) {
            node.products.insertProduct(product);
        }
    }

    //  Find Category Node
    private BSTNode findCategory(BSTNode root, String category) {
        if (root == null || root.category.equals(category)) return root;
        if (category.compareTo(root.category) < 0) return findCategory(root.left, category);
        return findCategory(root.right, category);
    }

    public BSTNode findCategory(String category) {
        return findCategory(root, category);
    }

    //  Sort Products within a Category
    public void sortProducts(String category, String attribute) {
        BSTNode node = findCategory(category);
        if (node != null) {
            node.products.bubbleSort(attribute);
        }
    }

    // Filter Products by Price Range in a Category
    public void filterByPrice(String category, double minPrice, double maxPrice) {
        BSTNode node = findCategory(category);
        if (node != null) {
            node.products.filterByPrice(minPrice, maxPrice);
        }
    }

    // Display Entire Inventory
    public void display() {
        inorderTraversal(root);
    }

    private void inorderTraversal(BSTNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.println("\n Category: " + root.category);
            root.products.display();
            inorderTraversal(root.right);
        }
    }
}

//  Main Class
public class InventoryManagement {
    public static void main(String[] args) {
        ProductBST bst = new ProductBST();

        //  Insert Categories & Add Products
        bst.addProduct("Smartphone", new Product("iPhone 14", 999, 10));
        bst.addProduct("Smartphone", new Product("Galaxy S22", 799, 8));
        bst.addProduct("Laptop", new Product("MacBook Pro", 1999, 5));
        bst.addProduct("Tablet", new Product("iPad Air", 599, 7));

        // Display Inventory Before Sorting
        System.out.println("\n Inventory Before Sorting:");
        bst.display();

        //  Bubble Sort Products by Price
        System.out.println("\n Sorting Smartphones by Price:");
        bst.sortProducts("Smartphone", "price");

        //  Display Inventory After Sorting
        System.out.println("\n Inventory After Sorting:");
        bst.display();

        // Filter Products by Price Range
        System.out.println("\n Filtering Smartphones priced between $500 and $1000:");
        bst.filterByPrice("Smartphone", 500, 1000);
    }
}


