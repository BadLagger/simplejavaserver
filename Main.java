import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import core.ServerThread;

public class Main {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ServerThread server = new ServerThread();
		File runFile = new File("run.state");
		File exitFile = new File("exit.state");
		
		System.out.println("SimpleServer!");
		InetAddress lAddr = InetAddress.getLocalHost();
		System.out.println("Address: " + lAddr);
		
		if(server.start()) {
			runFile.createNewFile();
		}
		
		if (exitFile.exists()) { 
			exitFile.delete();
		}
		
		while(server.inRun()) {
			
			if (exitFile.exists()) {
				server.stop();
				exitFile.delete();
			}
		}
		
		runFile.delete();
		
		System.out.println("DONE!!!");
	}
}
