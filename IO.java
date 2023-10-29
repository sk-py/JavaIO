import java.net.*;
import java.io.*;
import java.util.Scanner;

public class IO {

    private void client() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean exe = true;
        while (exe == true) {
            System.out.println("Enter The Target(receiver) machine's address : ");
            String ip = sc.nextLine();
            Socket socket = new Socket(ip, 5055);
            OutputStream out = socket.getOutputStream();
            System.out.println("Enter the path of the file with fileName : example(C:/Path/to/File_Name.xyz)");
            String path = sc.nextLine();
            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int bytesReturned;
            while ((bytesReturned = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesReturned);
            }
            out.close();
            System.out.println("File Sent Successfully..!!");
            System.out.println(
                    "Do you want to send another file?\n1. Yes - Continue\n2. No - Exit\n3. Or Do You Want To Change Role - i.e from Sender to Receiver");
            int option = sc.nextInt();
            if (option == 2) {
                exe = false;
            } else if (option == 3) {
                exe = false;
                main(null);
            } else if (option == 1) {
                System.out.println("Ok");
            } else {
                socket.close();
                fis.close();
                exe = false;
                sc.close();
            }
        }
    }

    private void server() throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean exe = true;
        ServerSocket server = new ServerSocket(5055);
        System.out.println(
                "Set the path where you want to save the received file : example(C:/Path/to/) or *just press enter to save in the current directory.");
        String path = sc.nextLine();
        while (exe == true) {
            System.out.println(
                    "Give a name for the received file : example(fileName.xyz) *Be careful about the extension of file.");
            String fileName = sc.nextLine();
            System.out.println("\u001B[33m");
            System.out.println("Waiting For Sender...");
            System.out.println("\u001B[0m");
            Socket s = server.accept();
            InputStream in = s.getInputStream();
            FileOutputStream out = new FileOutputStream(path + fileName);

            byte[] buffer = new byte[1024];
            int bytesReturned;
            while ((bytesReturned = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesReturned);
            }
            System.out.println("\u001B[32m");
            System.out.println("File Saved Successfully At " + path + " As " + fileName);
            System.out.println(
                    "Do you want to recieve another file?\n1. Yes - Continue\n2. No - Exit\n3. Or Do You Want To Change Role - i.e from Receiver to Sender");
            int option = sc.nextInt();
            if (option == 2) {
                exe = false;
            } else if (option == 3) {
                exe = false;
                main(null);
            } else if (option == 1) {
                System.out.println("Ok");
            } else {
                server.close();
                out.close();
                exe = false;
                sc.close();
            }

        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose your role : \n1.Sender\n2.Receiver");
        int opt = sc.nextInt();
        IO role = new IO();
        if (opt == 1) {
            try {
                role.client();
            } catch (IOException e) {
                System.out.print("\u001B[31m");
                System.out.println(
                        e.getMessage() + " An Error Occurred While Processing Your Request, Please Try Again.");
            }
        } else if (opt == 2) {
            try {
                role.server();
            } catch (IOException e) {
                System.out.print("\u001B[31m");
                System.out.println(
                        e.getMessage() + " An Error Occurred While Processing Your Request, Please Try Again.");
            }
        } else {
            System.out.print("\u001B[31m");
            System.out.println("Choose a valid option");
        }
        sc.close();
    }

}
