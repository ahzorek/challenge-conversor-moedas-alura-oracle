package conversor;

import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {

        System.out.println("oláaaaa, digite as coisas: ");
        Scanner reader = new Scanner(System.in);

        var world = reader.nextLine();
        var times = reader.nextInt();

        var teste = new Teste(world, times);
        teste.print();
    }
}

 class Teste {
    protected String name;
    protected int times;

     public Teste(String name, int times) {
         this.name = name;
         this.times = times;
     }

     public void print(){
         for (int i = 0; i < times; i++) {
             System.out.println("hello " + name);
         }
     }
 }