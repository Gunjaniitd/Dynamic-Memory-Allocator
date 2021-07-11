import java.util.Scanner;

public class Driver {
    public static void main(String args[]) {
        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size, 3);
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument;
                int result = -5;
                switch (command) {
                case "Allocate":
                    argument = sc.nextInt();
                    result = obj.Allocate(argument);
                    break;
                case "Free":
                    argument = sc.nextInt();
                    result = obj.Free(argument);
                    break;
                case "Defragment":
                    obj.Defragment();
                    result = -10;
                    break;
                default:
                    break;
                }
                if (!obj.freeBlk.sanity()) {
                    System.out.println("Insane");
                    break;
                }
                if (!obj.allocBlk.sanity()) {
                    System.out.println("Insane");
                    break;
                }
                if (result != -10)
                    System.out.println(result);
                else
                    System.out.println("Defragmented");
            }

        }
    }
}

