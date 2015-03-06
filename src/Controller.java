
public class Controller {

	static DatabaseAccessChromium dac = new DatabaseAccessChromium();
	static IOFormatter io=new IOFormatter();
	static BatchProcess bp= new BatchProcess();
	
	public static void  main(String[] args) throws Exception{
		
		io.inputConString();
		System.out.println("");
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
			System.out.println("Complete Execution");
		}
		else
		{
			System.out.println("Wrong Connection String/Username/Password!");
					
		}
	}
		
	}

