package com.docker.PersonTimeTracker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonTimeTrackerController {

	@Autowired
	PersonTimeTrackerService personTimeTrackerService;
	
    @GetMapping
    public String getForm()
    {
        return "PersonDetails";
    }

    @RequestMapping("/personTimeTracker")
    public String personTimeTracker()
    {
        return "PersonDetails";
    }

    @RequestMapping("/")
    public String welcome()
    {
        return "PersonDetails";
    }
    
    @RequestMapping(value="/processForm",params="saveDetails",method=RequestMethod.POST)
    public String saveDetails(@RequestParam("personEmail")String personEmail,
    		@RequestParam("startTime")String startTs, @RequestParam("endTime")String endTs,
                              ModelMap aModelMap){
    	String myResponse = personTimeTrackerService.insertPersonDetails(personEmail,startTs,endTs);
    	aModelMap.put("personEmail", personEmail);
    	aModelMap.put("insertResponse", myResponse);
        return "SaveDetails";
    }
    
    /*
     * fetches the time details based on email address provided
     */
    @RequestMapping(value="/processForm",params="fetchDetails",method=RequestMethod.GET)
    public String fetchDetails(@RequestParam("personEmail")String personEmail,
    		@RequestParam("numRecordFetch")String numRecordFetch,
            ModelMap aModelMap){
	
    	List<String> myResponse = personTimeTrackerService.fetchPersonDetails(personEmail,numRecordFetch);
		aModelMap.put("personEmail", personEmail);
		aModelMap.put("personDetails", myResponse);
		
		return "FetchDetails";
	}

}
