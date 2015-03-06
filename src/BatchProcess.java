import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


public class BatchProcess {
	DatabaseAccessChromium dac = Controller.dac;
	public String getOwnerList(String s) throws Exception
	{
	
		String dirName = s;
		String ownerList=null;
		CSVReader reader = new CSVReader(new FileReader(dirName+"developer-ids.csv"), ',', '\"', 1);
		String [] nextLine;
		
		while ((nextLine = reader.readNext()) != null) 
		{
	
			if(nextLine[0].isEmpty()||nextLine[0].trim().isEmpty()||nextLine[0]==null||nextLine[0].trim().equals("none"))
			{
				return null;
			}
			else if(ownerList==null)
			{
				ownerList="\'"+nextLine[0].trim()+"\'"+",";
									
			}
			else 
			{
				ownerList=ownerList+"\'"+nextLine[0].trim()+"\'"+",";
			}
				
	}
	/*if(ownerList==null)
	{
			ownerList=dac.getOwnermail();
	}*/
	if(ownerList!=null)	
		return ownerList.substring(0, ownerList.length()-1);
	else
		return ownerList;

}
}