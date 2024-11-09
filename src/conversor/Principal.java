package conversor;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        //ascii logo
        Logo.display();

        //cria array list de opções do menu
        ArrayList<String> welcomeMenuOptions = new ArrayList<>(List.of(
                "Encerrar o Programa", "Checar Cotações", "Realizar Conversão", "Histórico de Conversões"
        ));

        //inicializa o menu com as opções definidas e chama exibição
        var welcomeMenu = new PromptMenu(welcomeMenuOptions);
        welcomeMenu.setMenuMessage("O que deseja fazer? ");

        int OP = -1;
        Scanner inputReader = new Scanner(System.in);

        while (OP != 0) {
            try {
                // exibe o menu
                welcomeMenu.display();
                System.out.print("Digite uma opção: ");
                OP = inputReader.nextInt();

                // arvore de decisão
                switch (OP) {
                    case 0 -> System.out.println("Saindo...");
                    case 1 -> Exchange.rates();
                    case 2 -> Exchange.convert();
                    case 3 -> Exchange.displayHistory();
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());

                //System.err.println("Erro de entrada. Por favor, insira um número válido.");
                inputReader.nextLine(); // limpar buffer pra nao entrar em loop infinito (como eu descobri isso? como será????)
                OP = -1;
            }
        }
    }
}
