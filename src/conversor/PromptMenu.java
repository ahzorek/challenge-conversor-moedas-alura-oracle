package conversor;

import java.util.ArrayList;

public class PromptMenu {
    private ArrayList<String> options;
    private String menuMessage = "MENU ";

    public void setMenuMessage(String message){
        this.menuMessage = message;
    }

    /**
     * Constructs a new Menu with a list of options.
     *
     * @param options A list of options to be displayed in the prompt menu.
     * First option (index 0) should be an *exit* or *escape* option, as to better navigate user.
     */
    public PromptMenu(ArrayList<String> options) {
        this.options = options;
    }

    public void addMenuOption(String option){
        this.options.add(option);
    }

    public void display(){
        System.out.println(menuMessage);
        for (int i = 1; i < options.size(); i++) {
            System.out.printf("[%d] - %s%n", i, options.get(i));
        }
        System.out.printf("[%d] - %s%n", 0, options.get(0));

    }

}
