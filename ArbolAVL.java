// Clase Cancion
class Cancion {
    int id;
    String nombre;
    double duracion;
    String autor;
    String album;

    public Cancion(int id, String nombre, double duracion, String autor, String album) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.autor = autor;
        this.album = album;
    }
    public int getId(){
        return this.id;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setAutor(String autor){
        this.autor = autor;
               
    }
    public void setAlbum(String album){
        this.album = album;
    }
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Duración: " + duracion + ", Autor: " + autor + ", Álbum: " + album;
    }
}

// Nodo AVL
class AVLNode {
    Cancion cancion;
    int altura;
    AVLNode izquierda, derecha;

    public AVLNode(Cancion cancion) {
        this.cancion = cancion;
        this.altura = 1;
    }
}

// Árbol AVL
class ArbolAVL {
    AVLNode raiz;

    // Altura del nodo
    int altura(AVLNode nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    // Funcion  de equilibrio
    int balance(AVLNode nodo) {
        // Segun la formula AlturaNodoIzquierda-AlturaNodoDerecho
        return (nodo == null) ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    // Rotaciones
    AVLNode rotacionDerecha(AVLNode y) {
        AVLNode x = y.izquierda;
        AVLNode T2 = x.derecha;
        x.derecha = y;
        y.izquierda = T2;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        return x;
    }

    AVLNode rotacionIzquierda(AVLNode x) {
        AVLNode y = x.derecha;
        AVLNode T2 = y.izquierda;
        y.izquierda = x;
        x.derecha = T2;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        return y;
    }

    // Inserción con balance
    public void insertar(Cancion cancion) {
        raiz = insertar(raiz, cancion);
    }

    private AVLNode insertar(AVLNode nodo, Cancion cancion) {
        if (nodo == null) return new AVLNode(cancion);
        if (cancion.id < nodo.cancion.id)
            nodo.izquierda = insertar(nodo.izquierda, cancion);
        else if (cancion.id > nodo.cancion.id)
            nodo.derecha = insertar(nodo.derecha, cancion);
        else
            return nodo;

        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));
        int balance = balance(nodo);

        if (balance > 1 && cancion.id < nodo.izquierda.cancion.id)
            return rotacionDerecha(nodo);
        if (balance < -1 && cancion.id > nodo.derecha.cancion.id)
            return rotacionIzquierda(nodo);
        if (balance > 1 && cancion.id > nodo.izquierda.cancion.id) {
            nodo.izquierda = rotacionIzquierda(nodo.izquierda);
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && cancion.id < nodo.derecha.cancion.id) {
            nodo.derecha = rotacionDerecha(nodo.derecha);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    // Búsqueda por ID
    public Cancion buscarPorID(int id) {
        return buscarPorID(raiz, id, 1);
    }

    private Cancion buscarPorID(AVLNode nodo, int id, int nivel) {
        if (nodo == null) return null;
        if (id == nodo.cancion.id) {
            System.out.println("🎵 Canción encontrada en el nivel " + nivel);
            return nodo.cancion;
        }
        if (id < nodo.cancion.id)
            return buscarPorID(nodo.izquierda, id, nivel + 1);
        else
            return buscarPorID(nodo.derecha, id, nivel + 1);
    }

    // Búsqueda por nombre
    public Cancion buscarPorNombre(String nombre) {
        return buscarPorNombre(raiz, nombre.toLowerCase(), 1);
    }

    private Cancion buscarPorNombre(AVLNode nodo, String nombre, int nivel) {
        if (nodo == null) return null;
        if (nodo.cancion.nombre.toLowerCase().equals(nombre)) {
            System.out.println("🎶 Canción encontrada en el nivel " + nivel);
            return nodo.cancion;
        }
        Cancion izquierda = buscarPorNombre(nodo.izquierda, nombre, nivel + 1);
        if (izquierda != null) return izquierda;
        return buscarPorNombre(nodo.derecha, nombre, nivel + 1);
    }

    // Inorden con nivel
    public void recorrerConNiveles() {
        System.out.println("\n🌳 Recorrido Inorden con Niveles:");
        recorrerInordenConNivel(raiz, 1);
    }

    private void recorrerInordenConNivel(AVLNode nodo, int nivel) {
        if (nodo == null) return;
        recorrerInordenConNivel(nodo.izquierda, nivel + 1);
        System.out.println("Nivel " + nivel + ": " + nodo.cancion);
        recorrerInordenConNivel(nodo.derecha, nivel + 1);
    }

    // Impresión gráfica
    public void imprimirArbol() {
        System.out.println("\n📈 Representación gráfica del árbol AVL:");
        imprimirNodo(raiz, "", true);
    }

    private void imprimirNodo(AVLNode nodo, String prefijo, boolean esUltimo) {
        if (nodo == null) return;
        System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + "ID: " + nodo.cancion.id + ", Nombre: " + nodo.cancion.nombre);
        String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
        if (nodo.izquierda != null || nodo.derecha != null) {
            if (nodo.izquierda != null)
                imprimirNodo(nodo.izquierda, nuevoPrefijo, nodo.derecha == null);
            if (nodo.derecha != null)
                imprimirNodo(nodo.derecha, nuevoPrefijo, true);
        }
    }
}

// Clase principal
public class ExamenAVL {
    public static void main(String[] args) {
        ArbolAVL arbol = new ArbolAVL();

        arbol.insertar(new Cancion(30, "Imagine", 3.1, "John Lennon", "Imagine"));
        arbol.insertar(new Cancion(20, "Hey Jude", 4.2, "The Beatles", "Hey Jude"));
        arbol.insertar(new Cancion(40, "Let it Be", 3.9, "The Beatles", "Let it Be"));
        arbol.insertar(new Cancion(10, "Yesterday", 2.3, "The Beatles", "Help!"));
        arbol.insertar(new Cancion(25, "Bohemian Rhapsody", 5.5, "Queen", "Opera"));
        arbol.insertar(new Cancion(50, "Another One Bites the Dust", 3.5, "Queen", "The Game"));
        arbol.insertar(new Cancion(5, "Smooth", 4.3, "Santana", "Supernatural"));
        arbol.insertar(new Cancion(15, "Shape of You", 3.8, "Ed Sheeran", "Divide"));
        arbol.insertar(new Cancion(27, "Hotel California", 6.3, "Eagles", "Hotel California"));

        arbol.recorrerConNiveles();
        arbol.imprimirArbol();

        Cancion buscada = arbol.buscarPorID(15);
        if (buscada != null)
            System.out.println("\n✅ Resultado búsqueda por ID: " + buscada);

        Cancion porNombre = arbol.buscarPorNombre("Hotel California");
        if (porNombre != null)
            System.out.println("✅ Resultado búsqueda por nombre: " + porNombre);
    }
}
