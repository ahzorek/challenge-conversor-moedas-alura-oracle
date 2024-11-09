package conversor;

public class Logo {
    private static final String LOGO = String.join("\n",
            "                                                       _      _ __   __",
            "                                                      (_)    | |\\ \\ / /",
            "                 _ __   _   _  _ __   __ _  _ __ ___   _   __| | \\ V /",
            "                | '_ \\ | | | || '__| / _` || '_ ` _ \\ | | / _` | /   \\",
            "                | |_) || |_| || |   | (_| || | | | | || || (_| |/ /^\\ \\",
            "                | .__/  \\__, ||_|    \\__,_||_| |_| |_||_| \\__,_|\\/   \\/",
            "                | |      __/ |",
            "                |_|     |___/"
    );

    public static void display() {
        System.out.println(LOGO);
        System.out.printf("::: Bem-vindo a pyramidX Exchange ::: %n%n");
    }
}
