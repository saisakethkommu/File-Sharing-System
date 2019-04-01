import java.io.*;
import java.net.*;

class Server
{
    public static BufferedReader in;
    public static BufferedWriter out;
    public static void main(String[] args)
    {
        if(args.length > 0 && args[0].equals("start"))
        {
            int SERVER_SOCKET = Integer.parseInt(args[1]);
            try
            {
                ServerSocket server = new ServerSocket(SERVER_SOCKET);
                System.out.println("Server started successfully at port number: " + SERVER_SOCKET);
                Socket client = server.accept();
                out = new BufferedWriter(new PrintWriter(client.getOutputStream(),true));
                in = new BufferedReader(new InputStreamReader(client.getInputStream())); 
                String command = in.readLine();
                if(command.equals("upload"))
                    handleUpload();
                else if(command.equals("download"))
                    handleDownload();
                in.close();
                server.close();
            }
            catch(Exception e)
            {
                e.printStackTrace(System.out);
            }
        }
        else
        {
            System.out.println("Invalid command or port number. Please make sure your all the letters are in lower case");
        }
    }

    public static void handleUpload() throws IOException
    {
        File output = new File(in.readLine());
        FileOutputStream fos = new FileOutputStream(output);      
        int c;
        while((c = in.read())!= -1)
            fos.write(((char)c));
        fos.close();
    }

    public static void handleDownload() throws IOException
    {
        File input = new File(in.readLine());
        FileInputStream fis = new FileInputStream(input);
        int c;
        while((c = fis.read()) != -1)
        {
            System.out.print((char)c);
            out.write((char)c);
        }
        out.flush();
        fis.close();
    }
}