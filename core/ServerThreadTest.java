package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ServerThreadTest {

	private static ServerThread st = null;
	private static File log = new File("log.txt");
	
	private static void writeToLog(String str) throws IOException {
		Files.write(Paths.get(log.getName()), 
				str.getBytes(),
				StandardOpenOption.APPEND);
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		st = new ServerThread();
		
		if (log.exists())
			log.delete();
		log.createNewFile();
		writeToLog("ServerThread starts...\n");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		writeToLog("ServerThread done...\n");
	}

	@Test
	void testInRun() throws IOException {
		Assertions.assertEquals(false, st.inRun());
		writeToLog("ServerThread inRun test done\n");
	}

}
