
public class Controller {

	static DatabaseAccessChromium dac = new DatabaseAccessChromium();
	static IOFormatter io=new IOFormatter();
	static BatchProcess bp= new BatchProcess();
	
	public static void  main(String[] args) throws Exception{
		
		float startTime = 0;
		float endTime = 0;
		io.inputConString();
		System.out.println("");
		startTime = System.nanoTime();
		
		System.out.println("Connecting to Database...");
		
		boolean isChromium= dac.openConnection(io.getDBN(), io.getMysqlUserName(), io.getMysqlPass());
		if(isChromium)
		{
			System.out.println("Connected..");
			System.out.println("");
			
			if(io.getDBN().equalsIgnoreCase("chromium"))
			{
				io.batchInput();
				io.inputData();
			}	
			bp.getOwnerList(io.getDirectoryPath());
			dac.generateDRON(bp.getOwnerList(io.getDirectoryPath()), io.getStartDate(), io.getEndDate());
			io.writeFile(dac.getFileContent(), io.getDirectoryPath()+"network.net");
			endTime = System.nanoTime();
			System.out.println("Complete Execution");
			System.out.println("Total Time Elapsed: " + (((endTime - startTime)/1000000000)/60) + " minutes");
		}
		else
		{
			System.out.println("Wrong Connection String/Username/Password!");
					
		}
	}
		
	}

