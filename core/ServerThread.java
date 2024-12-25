package core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServerThread implements  Runnable {
	private boolean isRun;
	private Thread  thread;
	private HttpServer server;
	
	public ServerThread() {
		isRun = false;
		thread = new Thread(this);
	}
	
	public void run() {
		if(isRun) {
			try {
				server = HttpServer.create(new InetSocketAddress(8080), 0);
				server.createContext("/test", new MyHandler());
				server.setExecutor(null);
				server.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean start() {
		if (!isRun) {
			isRun = true;
			thread.start();
			return true;
		}
		return false;
	}
	
	public boolean stop() throws InterruptedException {
		if (isRun) {
			isRun = false;
			server.stop(0);
			thread.join();
			return true;
		}
		return false;
	}
	
	public boolean inRun() {
		return isRun;
	}
	
	static class MyHandler implements HttpHandler {

		private int reqCount = 0;
		
		public void handle(HttpExchange t) throws IOException {
			reqCount++;
			
			String response = "This is response to " + t.getRemoteAddress() + "\nRequest count: " + reqCount + "\n";
			String[] parseUri= t.getRequestURI().toString().split(Pattern.quote("?"));
			if (parseUri.length > 1) {
				response += "It seems we have some request here:\n";
				int count = 0;
				for (var s : parseUri) {
					count ++;
					if (count == 1)
						continue;
					response += s + "\n";
				}
			}
			
			for (var s : parseUri) {
				System.out.println(s);
			}
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}

