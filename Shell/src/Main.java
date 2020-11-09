import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        String commandLine = "";
        File wd;


        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        TestProcessBuilder tpb = new TestProcessBuilder(commandLine);


        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");
        // we break out with ‘END’

        while (true) {

            // show the Java shell prompt and read what command they entered
            System.out.print("enter>");

            commandLine = console.readLine();

            // if user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;

            } if (commandLine.toLowerCase().equals("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                System.exit(0);

            } if (commandLine.toLowerCase().equals("showerrlog")) {
                System.out.println(tpb.getShowerrlog());

            } else {

                tpb.command = commandLine;
                new Thread(tpb).start();
            }
        }

    }



    public static class TestProcessBuilder implements Runnable {

        public String command;
        private String showerrlog;


        public TestProcessBuilder(String command) {
            this.command = command;
        }


        @Override
        public void run() {

            List<String> input = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(command);

            while (tokenizer.hasMoreTokens()) {
                input.add(tokenizer.nextToken());
            }

            ProcessBuilder pb = new ProcessBuilder(input);

            //pb.directory(new File(System.getProperty("user.ahmedmohamud")));

            // ProcessBuilder creates a process corresponding to the input command
            // now start the process
            BufferedReader br = null;

            try {
                Process proc = pb.start();
                // obtain the input and output streams
                InputStream is = proc.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                // read what the process returned
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

                br.close();
            } catch (java.io.IOException ioe) {

                System.err.println("Error");
                System.err.println(ioe);
                showerrlog +="\n" + ioe;

            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public String getShowerrlog() {
            return showerrlog;
        }

        /*public void createProcess(String command) throws java.io.IOException {
            Thread rc = new Thread(new TestProcessBuilder(command));
            rc.start();


        }*/

    }

}
