import java.util.Scanner;

public class CustomTerminal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Welcome to Custom Terminal!");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            switch (input.toLowerCase()) {
                case "help":
                    System.out.println("Available commands:");
                    System.out.println("1. free - Display amount of free and used memory in the system");
                    System.out.println("2. ps - Display information about active processes");
                    System.out.println("3. top - Display information about CPU and memory usage");
                    System.out.println("4. vmstat - Display virtual memory statistics");
                    System.out.println("5. df - Display disk space usage");
                    System.out.println("6. du - Display disk usage of files and directories");
                    System.out.println("7. uname - Display system information");
                    System.out.println("8. uptime - Display system uptime");
                    System.out.println("9. who - Display who is logged in");
                    System.out.println("10. exit - Exit the terminal");
                    break;
                case "free":
                    executeCommand("free");
                    break;
                case "ps":
                    executeCommand("ps");
                    break;
                case "top":
                    executeCommand("top");
                    break;
                case "vmstat":
                    executeCommand("vmstat");
                    break;
                case "df":
                    executeCommand("df");
                    break;
                case "du":
                    executeCommand("du");
                    break;
                case "uname":
                    executeCommand("uname -a");
                    break;
                case "uptime":
                    executeCommand("uptime");
                    break;
                case "who":
                    executeCommand("who");
                    break;
                case "exit":
                    System.out.println("Exiting Custom Terminal. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
            }
        }
    }

    private static void executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            Scanner scanner = new Scanner(process.getInputStream());
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
