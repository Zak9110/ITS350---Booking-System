//ITS350 - Dr.Chnoor M. Rahman - Assignment 2 – Booking System
//Zaid Khudhur Salih (21-00184) - Sec 1
//Kamiran Sulaiman Ilyas (20-0096) - Sec 2
//Date: 1/5/2024


public class BookingSystem {
    Node root; // Root node of the binary search tree
    int count; // Total number of booked seats

    // Inner class representing a node in the binary search tree
    class Node {
        int seatNum; // Seat number
        String ownerName; // Name of the seat owner
        Node leftChild; // Reference to the left child node
        Node rightChild; // Reference to the right child node

        // Constructor
        public Node(int seatNum, String ownerName) {
            this.seatNum = seatNum;
            this.ownerName = ownerName;
        }
    }

    // Method to book a seat
    public void bookSeat(int seatNum, String ownerName) {
        // Validate seat number
        if (seatNum < 1 || seatNum > 155) {
            System.out.println("Invalid seat number!");
            return;
        }

        // Check if the seat is already booked
        if (isSeatBooked(seatNum)) {
            System.out.println("Seat " + String.format("%03d", seatNum) + " is already booked!");
            return;
        }

        // Insert the node into the binary search tree
        insert(seatNum, ownerName);
        count++;

        // Print booking success message
        System.out.println("Seat " + String.format("%03d", seatNum) + " booked successfully by " + ownerName + ".");
    }

    // Method to check if a seat is already booked
    public boolean isSeatBooked(int seatNum) {
        Node current = root;
        while (current != null) {
            if (current.seatNum == seatNum)
                return true;
            else if (seatNum < current.seatNum)
                current = current.leftChild;
            else
                current = current.rightChild;
        }
        return false;
    }

    // Method to delete a booked seat
    public void deleteSeat(int seatNum) {
        // Validate seat number
        if (seatNum < 1 || seatNum > 155) {
            System.out.println("Invalid seat number!");
            return;
        }

        // Check if the seat is booked
        if (!isSeatBooked(seatNum)) {
            System.out.println("Seat " + String.format("%03d", seatNum) + " is not booked to be deleted!");
            return;
        }

        // Delete the seat
        boolean deleted = delete(seatNum);
        if (deleted) {
            count--;
            System.out.println("Seat " + String.format("%03d", seatNum) + " deleted successfully.");
        } else {
            System.out.println("Error deleting seat " + String.format("%03d", seatNum) + ".");
        }
    }

    // Method to insert a node into the binary search tree
    void insert(int seatNum, String ownerName) {
        Node node = new Node(seatNum, ownerName);
        if (root == null)
            root = node;
        else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (seatNum < current.seatNum) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = node;
                        return;
                    }
                } else {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = node;
                        return;
                    }
                }
            }
        }
    }

    // Method to delete a node from the binary search tree
    public boolean delete(int key) {
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;

        // Find node to delete
        while (current.seatNum != key) {
            parent = current;
            if (key < current.seatNum) {
                current = current.leftChild;
                isLeftChild = true;
            } else {
                current = current.rightChild;
                isLeftChild = false;
            }
            if (current == null)
                return false;
        }

        // Case 1: Node to be deleted has no children
        if (current.leftChild == null && current.rightChild == null) {
            if (current == root)
                root = null;
            else if (isLeftChild)
                parent.leftChild = null;
            else
                parent.rightChild = null;
        }
        // Case 2: Node to be deleted has only one child
        else if (current.rightChild == null) {
            if (current == root)
                root = current.leftChild;
            else if (isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;
        } else if (current.leftChild == null) {
            if (current == root)
                root = current.rightChild;
            else if (isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;
        }
        // Case 3: Node to be deleted has two children
        else {
            Node successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;
            successor.leftChild = current.leftChild;
        }
        return true;
    }

    // Method to find the successor of a node
    public Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild) {
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    // Method to count the number of available seats
    public int countAvailableSeats() {
        return 155 - count;
    }

    // Method to show the names of people who booked the seats
    public void showBookedSeats() {
        System.out.println("Booked seats:");
        inOrder(root);
    }

    // In-order traversal of the binary search tree
    public void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.println("Seat " + String.format("%03d", localRoot.seatNum) + " - Booked by: " + localRoot.ownerName);
            inOrder(localRoot.rightChild);
        }
    }
 // Method to show the booked seats after a specific seat
    public void showBookedSeatsAfter(int seatNum) {
        System.out.println("Booked seats after seat " + String.format("%03d", seatNum) + ":");
        // Find the node with the given seat number
        Node node = findNode(root, seatNum);
        // If the node exists, traverse its right subtree
        if (node != null) {
            inOrder(node.rightChild);
        }else
        	System.out.println("Seat " + seatNum + " is not booked");
    }

    // Method to find a node with the given seat number
    public Node findNode(Node localRoot, int seatNum) {
        if (localRoot == null || localRoot.seatNum == seatNum) {
            return localRoot;
        }
        if (seatNum < localRoot.seatNum) {
            return findNode(localRoot.leftChild, seatNum);
        }
        return findNode(localRoot.rightChild, seatNum);
    }

    public static void main(String[] args) {
        // Creating a BookingSystem instance
        BookingSystem bookingSystem = new BookingSystem();

        // Booking some seats for testing purposes
        bookingSystem.bookSeat(133, "Zaid");
        bookingSystem.bookSeat(150, "Elias");
        bookingSystem.bookSeat(120, "Roz");
        bookingSystem.bookSeat(90, "Kamiran");
        bookingSystem.bookSeat(144, "Azad");
        bookingSystem.bookSeat(151, "Murad");
        bookingSystem.bookSeat(149, "Layla");
        bookingSystem.bookSeat(140, "Nazli");
        
        // Deleting a booked seat
        bookingSystem.deleteSeat(144);
        bookingSystem.deleteSeat(140);


        // Attempting to book already booked and invalid seats
        bookingSystem.bookSeat(150, "Zaid");
        bookingSystem.bookSeat(-1, "Eva");
        bookingSystem.bookSeat(330, "Eva");
        // Attempting to delete an unbooked seat
        bookingSystem.deleteSeat(1);

        
        // Counting available seats
        System.out.println("Available seats: " + bookingSystem.countAvailableSeats());

        // Showing booked seats
        bookingSystem.showBookedSeats();

        // Showing booked seats after a specific seat
        bookingSystem.showBookedSeatsAfter(133);
        
        //this is used to try to show seats after a seat that is not booked 
        bookingSystem.showBookedSeatsAfter(140);

        
        
    }
}
