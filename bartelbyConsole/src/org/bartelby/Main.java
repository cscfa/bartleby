package org.bartelby;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;
import org.newsclub.net.unix.AFUNIXSocketException;

public class Main {

	public static void main(String[] args) throws IOException {
		ConsoleArgument console = new ConsoleArgument(args);


        final File socketFile = new File(new File(System
                .getProperty("java.io.tmpdir")), "bartelby-unixsocket.sock");

        AFUNIXSocket sock = AFUNIXSocket.newInstance();
        try {
            sock.connect(new AFUNIXSocketAddress(socketFile));
        } catch (AFUNIXSocketException e) {
            System.out.println("Cannot connect to server. Have you started it?");
            System.exit(0);
        }
        System.out.println("Connected");

        DataInputStream is = new DataInputStream(sock.getInputStream());
        DataOutputStream os = new DataOutputStream(sock.getOutputStream());
        
        Boolean closed = false;
        
        try{
	        while(true){
	            byte[] buf = new byte[1024];
	            int read;
	            String request;
	        	
	        	read = is.read(buf);
	        	request = new String(buf, 0, read);
	        	
	        	if(request.equals("output")){
	                os.write("ready".getBytes());
	                os.flush();
	
	            	read = is.read(buf);
	            	request = new String(buf, 0, read);
	                System.out.print(request);

	                os.write("--- ---".getBytes());
	                os.flush();
	        	}else if(request.equals("close")){
	        		closed = true;
	                os.close();
	                is.close();
	                sock.close();
	                break;
	        	}else if(request.equals("input")){
	                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	                String s = br.readLine();
	                os.write(s.getBytes());
	                os.flush();
	        	}
	        }
        }catch(Exception e){
        	if(closed)
        		System.out.println("Connection closed by server.");
        }


/*
        int read = is.read(buf);
        System.out.println(new String(buf, 0, read));
        int read = is.read(buf);
        System.out.println(new String(buf, 0, read));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        os.write(s.getBytes());
        os.flush();

        read = is.read(buf);
        System.out.println(new String(buf, 0, read));
        br = new BufferedReader(new InputStreamReader(System.in));
        s = br.readLine();
        os.write(s.getBytes());
        os.flush();
        
        System.out.println("Replying to server...");
        os.write("Hello Server".getBytes());
        os.flush();

        os.close();
        is.close();

        sock.close();*/

	}
	/*
	public String receive(OutputStream os){
		
	}*/

}
