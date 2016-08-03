
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestCases {
	
	    @Test
	    public void validateInputOutputofFileInputFunction() throws IOException {
	    	String fileName = "gistfile1.txt";
			List<JSONObject> jsonObjectTest = new ArrayList<JSONObject>();
	    	assertSame(jsonObjectTest.getClass(), PartyInvitationList.ReadSingleLineOfJsonFile(fileName).getClass());
    	}
	    

	    @Rule
	    public ExpectedException exception = ExpectedException.none();
	    
	    @Test
	    public void validateInputFileName() throws FileNotFoundException{
	    	String fileName = "gistfile1.txt";
	    	try {
				PartyInvitationList.ReadSingleLineOfJsonFile(fileName);
			} catch (FileNotFoundException e) {
				throw (e);
			}
	    	
	    	exception.expect(ParseException.class);
    	}
	    
	    
}
