import java.io.*;
import java.net.*;
import java.util.*;

class Client
{
    public static BufferedReader br;
    public static BufferedWriter pw;
    public static Socket s;
    public static void main(String[] args)
    {
        try
        {
            InetAddress address = InetAddress.getLocalHost();
            s = new Socket(address,12000);
            pw = new BufferedWriter(new PrintWriter(s.getOutputStream(), true));
            if(args[0].equals("upload"))
            {
                String filename = args[1];
                String output_directory = args[2];
                File upload_file = new File(filename);
                upload(upload_file, output_directory);
            }
            else if(args[0].equals("download"))
            {
                String server_directory = args[1];
                String filename = args[2];
                File download_file = new File(filename);
                download(download_file, server_directory);
            }
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    public static void upload(File file, String output_directory) throws IOException
    {
        System.out.println("Initiating Upload");
        pw.write("upload" + "\n");
        pw.write(output_directory + "\n");
        FileInputStream fis = new FileInputStream(file);
        int c;
        while((c = fis.read()) != -1)
            pw.write((char)c);
        fis.close();
        System.out.println("File upload completed succesfully");
        pw.close();
    }

    public static void download(File download_file, String server_directory) throws IOException, InterruptedException
    {
        System.out.println("Initiating Download");
        pw.write("download" + "\n");
        pw.write(server_directory + "\n");
        pw.flush();
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        FileOutputStream fos = new FileOutputStream(download_file);
        int c;
        while((c = br.read()) != -1)
            fos.write((char)c);
        fos.close();
        br.close();
        System.out.println("File Download Completed Succesfully");       
    }
}