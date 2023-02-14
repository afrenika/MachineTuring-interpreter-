import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Automat {
    private int n;
    private HashSet<Integer> q;
    private HashMap<Integer, HashMap<Character, Perehod>> map;
    private char[] symbols;

    public Automat() {

    }



    public void input(String inputFile){
        try(Scanner scanner = new Scanner(new File(inputFile))){
        map = new HashMap<>();
        n = Integer.parseInt(scanner.nextLine());
        q = new LinkedHashSet<>();
        symbols = scanner.nextLine().trim().replace("     ", "").toCharArray();
        for(int i = 1; i <= n; i++){
            String [] s = scanner.nextLine().split(" ");
            HashMap<Character, Perehod> map1 = new HashMap<>();
            q.add(i);
            for(int j = 0; j < symbols.length; j++){
                String[] t = s[j + 1].split("/");
                if (t.length != 1){
                    map1.put(symbols[j], new Perehod(t[0].charAt(0), dash(t[1].charAt(0)), Integer.parseInt(t[2])));}}
            map.put(i, map1);}}
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void output(){
        if (n != 0) {
            System.out.print(n + "\n    ");
            System.out.print(symbols[0]);
            for(int i = 1; i < symbols.length; i ++){
                System.out.print("     " + symbols[i]);}
            System.out.println();
            for(Map.Entry<Integer, HashMap<Character, Perehod>> entry: map.entrySet()){
                System.out.print(entry.getKey());
                for(char i: symbols){
                    if (entry.getValue().containsKey(i)){
                        System.out.print(" " + entry.getValue().get(i));}
                    else System.out.print(" " + "_");}
                System.out.println();
            }
        }}

    public void output(String path){
        try(Writer writer = new FileWriter(path)) {
            if (n != 0) {
                writer.append(String.valueOf(n)).append("\n    ").append(String.valueOf(symbols[0]));
                for(int i = 1; i < symbols.length; i ++){
                    writer.append("     ").append(String.valueOf(symbols[i])).append("\n");}
                for(Map.Entry<Integer, HashMap<Character, Perehod>> entry: map.entrySet()){
                    writer.append(String.valueOf(entry.getKey()));
                    for(char i: symbols){
                        if (entry.getValue().containsKey(i)){
                            writer.append(" ").append(String.valueOf(entry.getValue().get(i)));}
                        else writer.append(" _");}
                    writer.append("\n");}}}
        catch (IOException e) {e.printStackTrace();}
    }

    public String prohod(String word){
        int qn = 1;
        int position = 1;
        StringBuilder dop = new StringBuilder(word);
        boolean t = true;
        while (t){
            Perehod d = map.get(qn).get(dop.charAt(position));
            dop.setCharAt(position, d.symbol);
            qn = d.status;
            position += d.step;
            if (position < 0){
                dop.insert(0, "λ");
                position += 1;}
            else if (position > dop.length()){
                dop.append("λ");}
            if (qn == 0){
                t = false;}
        }
        return dop.toString().replace("λ", " ").trim();
    }


    private int dash(char y){
        if (y == 'R') return 1;
        else if (y == 'L') return -1;
        else return 0;}





     static class Perehod{
        char symbol;
        int step;
        int status;

        public Perehod(char symbol, int step, int status) {
            this.symbol = symbol;
            this.step = step;
            this.status = status;
        }

         @Override
         public String toString() {
             return symbol + "/" + rdash(step)  + "/" + status;}

         private char rdash(int y){
             if (y == 1) return 'R';
             else if (y == -1) return 'L';
             else return 'N';}
     }

}


