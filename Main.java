import java.util.*;

// ----- Patrón Observer -----
interface Observer {
    void update(int discount);
}

class ProductDiscountNotification {
    private List<Observer> observers = new ArrayList<>();
    private int discount;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setDiscount(int discount) {
        this.discount = discount;
        notifyObservers();
    }

    public int getDiscount() {
        return discount;
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(discount);
        }
    }
}

class Subscriber implements Observer {
    private String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(int discount) {
        System.out.println(name + " ha sido notificado del descuento: " + discount + "%");
    }
}

// ----- Patrón Strategy -----
interface SortingStrategy {
    void sort();
}

class PriceSortStrategy implements SortingStrategy {
    @Override
    public void sort() {
        System.out.println("Ordenando productos por precio.");
    }
}

class PopularitySortStrategy implements SortingStrategy {
    @Override
    public void sort() {
        System.out.println("Ordenando productos por popularidad.");
    }
}

class ProductSorter {
    private SortingStrategy sortingStrategy;

    public ProductSorter(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void sortProducts() {
        sortingStrategy.sort();
    }
}

// ----- Patrón Command -----
interface Command {
    void execute();
}

class TextEditor {
    private StringBuilder text = new StringBuilder();
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    public void write(String newText) {
        undoStack.push(text.toString());
        text.append(newText);
        System.out.println("Texto actual: " + text);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(text.toString());
            text = new StringBuilder(undoStack.pop());
            System.out.println("Deshacer: " + text);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(text.toString());
            text = new StringBuilder(redoStack.pop());
            System.out.println("Rehacer: " + text);
        }
    }
}

// ----- Main ----- 
public class Main {
    public static void main(String[] args) {

        // ---------- Patrón Observer ----------
        System.out.println("\n--- Observer ---");
        ProductDiscountNotification subject = new ProductDiscountNotification();
        Subscriber customer1 = new Subscriber("Cliente1");
        Subscriber customer2 = new Subscriber("Cliente2");

        subject.addObserver(customer1);
        subject.addObserver(customer2);

        subject.setDiscount(20);

        // ---------- Patrón Strategy ----------
        System.out.println("\n--- Strategy ---");
        ProductSorter sorter = new ProductSorter(new PriceSortStrategy());
        sorter.sortProducts();

        sorter.setSortingStrategy(new PopularitySortStrategy());
        sorter.sortProducts();

        // ---------- Patrón Command ----------
        System.out.println("\n--- Command ---");
        TextEditor editor = new TextEditor();
        editor.write("Hola Mundo");
        editor.undo();
        editor.redo();
    }
}
