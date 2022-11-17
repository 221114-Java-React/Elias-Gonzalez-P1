import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        //load an example ticket
        Ticket exampleTicket = new Ticket(20, "Lunch", "Personal");
        System.out.println(exampleTicket.toString());
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        //load main menu
        while(run){
            System.out.println("Welcome to the expense ticket service \n Please select an option. \n [0] Exit \n [1] Create a new ticket \n [5] View all tickets");
            int menuOption = scanner.nextInt();

            //branch to menu options
            switch (menuOption) {
                //creates new ticket
                case 1:
                    System.out.println("Menu Option 1 selected");
                    //createTicket();
                    break;
                //exits program
                case 0:
                    run = false;
                    break;
                //invalid input
                default:
                    System.out.println("Invalid menu option...for now...");
                    break;
            }
        }
        //exits main menu and exits program
        System.out.println("Thanks for using the expense ticket service.");
        scanner.close();
        
    }
    //main menu methods
    public void createTicket() {
        Scanner innerScanner = new Scanner(System.in);
        System.out.println("Please enter the amount for the expense: \n");
        innerScanner.nextInt(); 
    }


}
