import java.util.ArrayList;

// Clase que representa un nodo del Árbol B
class BTreeNode {
    int t;  // Grado mínimo del árbol (define la cantidad mínima de claves)
    ArrayList<Integer> keys; // Lista de claves almacenadas en el nodo
    ArrayList<BTreeNode> children; // Lista de hijos del nodo
    boolean isLeaf; // Indica si el nodo es una hoja o no

    public BTreeNode(int t, boolean isLeaf) {
        this.t = t;
        this.isLeaf = isLeaf;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // Método para recorrer el árbol e imprimir sus claves en orden
    public void traverse() {
        for (int i = 0; i < keys.size(); i++) {
            if (!isLeaf) children.get(i).traverse(); // Recorre los hijos antes de imprimir la clave
            System.out.print(keys.get(i) + " ");
        }
        if (!isLeaf) children.get(keys.size()).traverse(); // Recorre el último hijo
    }

    // Método para buscar una clave en el árbol B
    public BTreeNode search(int key) {
        int i = 0;
        while (i < keys.size() && key > keys.get(i)) i++; // Encuentra la posición de la clave o la ubicación donde debería estar
        if (i < keys.size() && keys.get(i) == key) return this; // Si encuentra la clave, devuelve el nodo
        if (isLeaf) return null; // Si es una hoja y no encontró la clave, retorna null
        return children.get(i).search(key); // Continúa la búsqueda en el hijo correspondiente
    }

    // Método para insertar una clave en un nodo hoja
    public void insertNonFull(int key) {
        int i = keys.size() - 1;
        if (isLeaf) {
            keys.add(0);
            while (i >= 0 && keys.get(i) > key) {
                keys.set(i + 1, keys.get(i));
                i--;
            }
            keys.set(i + 1, key);
        } else {
            while (i >= 0 && keys.get(i) > key) i--;
            i++;
            if (children.get(i).keys.size() == 2 * t - 1) {
                splitChild(i);
                if (keys.get(i) < key) i++;
            }
            children.get(i).insertNonFull(key);
        }
    }

    // Método para dividir un hijo lleno
    public void splitChild(int i) {
        BTreeNode child = children.get(i);
        BTreeNode newNode = new BTreeNode(t, child.isLeaf);
        for (int j = 0; j < t - 1; j++) newNode.keys.add(child.keys.remove(t));
        if (!child.isLeaf) for (int j = 0; j < t; j++) newNode.children.add(child.children.remove(t));
        children.add(i + 1, newNode);
        keys.add(i, child.keys.remove(t - 1));
    }
}

// Clase que representa el Árbol B
class BTree {
    BTreeNode root; // Nodo raíz del árbol
    int t; // Grado mínimo

    public BTree(int t) {
        this.t = t;
        this.root = new BTreeNode(t, true); // Crea la raíz como una hoja vacía
    }

    // Método para recorrer e imprimir todas las claves del árbol
    public void traverse() {
        if (root != null) root.traverse();
        System.out.println();
    }

    // Método para buscar una clave en el árbol B
    public BTreeNode search(int key) {
        return root == null ? null : root.search(key);
    }

    // Método para insertar una clave en el árbol
    public void insert(int key) {
        if (root.keys.size() == 2 * t - 1) {
            BTreeNode newRoot = new BTreeNode(t, false);
            newRoot.children.add(root);
            newRoot.splitChild(0);
            root = newRoot;
        }
        root.insertNonFull(key);
    }
}

// Clase principal con un ejemplo de uso del Árbol B
public class BTreeExample {
    public static void main(String[] args) {
        BTree tree = new BTree(3); // Crea un Árbol B de orden 3
        
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(6);
        tree.insert(12);
        tree.insert(30);
        tree.insert(7);
        tree.insert(17);
        
        System.out.println("Recorrido del árbol B:");
        tree.traverse();
        
        // Evaluación del tiempo de búsqueda
        int searchKey = 12;
        long startTime = System.nanoTime();
        BTreeNode result = tree.search(searchKey);
        long endTime = System.nanoTime();
        
        if (result != null) {
            System.out.println("Clave " + searchKey + " encontrada en el árbol.");
        } else {
            System.out.println("Clave " + searchKey + " no encontrada en el árbol.");
        }
        
        System.out.println("Tiempo de búsqueda: " + (endTime - startTime) + " nanosegundos");
    }
}
