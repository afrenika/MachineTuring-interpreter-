

public class Main {
    public static void main(String[] args) {
        Automat automat = new Automat();
        automat.input("input.txt");
        automat.output();
        System.out.println(automat.prohod("abbab"));
    }
}
