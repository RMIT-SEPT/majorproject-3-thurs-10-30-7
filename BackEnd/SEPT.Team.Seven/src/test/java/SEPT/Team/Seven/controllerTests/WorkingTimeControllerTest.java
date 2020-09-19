package SEPT.Team.Seven.controllerTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import SEPT.Team.Seven.controller.WorkingTimeController;
import SEPT.Team.Seven.model.Availability;
import SEPT.Team.Seven.model.Employee;
import SEPT.Team.Seven.model.WorkingTime;
import SEPT.Team.Seven.repo.AvailabilityRepository;
import SEPT.Team.Seven.repo.EmployeeRepository;
import SEPT.Team.Seven.repo.WorkingTimeRepository;
import SEPT.Team.Seven.security.SecurityUserDetailsService;
import SEPT.Team.Seven.service.WorkingTimeService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(WorkingTimeController.class)
public class WorkingTimeControllerTest {

	@MockBean
	WorkingTimeService workingTimeService;
	
	@Mock
	private WorkingTimeRepository workingTimeRepository;
	
	@Mock
	private AvailabilityRepository availabilityRepository;
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@MockBean
	private SecurityUserDetailsService userDetailsService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static List<WorkingTime> workingTimes;
	
	private static List<WorkingTime> emptyList;
	
	private static List<Availability> availabilities;
	
	private static Employee employee;
	
	@BeforeAll
	public static void setUp() {
		employee = new Employee("Paula", "Kurniawan", "iannguyen@hotmail.yeet", "0123456789", "4 yeet court");
		Calendar startNextMonthAvail = Calendar.getInstance();
		Calendar endNextMonthAvail = Calendar.getInstance(); 
		startNextMonthAvail.add(Calendar.DATE, 15);
		startNextMonthAvail.add(Calendar.HOUR, 1);
		endNextMonthAvail.add(Calendar.DATE, 16);
		
		Calendar startNextWeekAvail = Calendar.getInstance();
		Calendar endNextWeekAvail = Calendar.getInstance(); 
		startNextWeekAvail.add(Calendar.DATE, 7);
		startNextWeekAvail.add(Calendar.HOUR, 1);
		endNextWeekAvail.add(Calendar.DATE, 8);
		
		Calendar startNextMonthWT = Calendar.getInstance();
		Calendar endNextMonthWT = Calendar.getInstance(); 
		startNextMonthWT.add(Calendar.DATE, 15);
		startNextMonthWT.add(Calendar.HOUR, 2);
		endNextMonthWT.add(Calendar.DATE, 16);
		
		Calendar startNextWeekWT = Calendar.getInstance();
		Calendar endNextWeekWT = Calendar.getInstance(); 
		startNextWeekWT.add(Calendar.DATE, 7);
		startNextWeekWT.add(Calendar.HOUR, 2);
		endNextWeekWT.add(Calendar.DATE, 8);
		
		Calendar avail1Start = Calendar.getInstance();
		Calendar avail1End = Calendar.getInstance(); 
		avail1Start.add(Calendar.DATE, 2);
		avail1Start.add(Calendar.HOUR, 1);
		avail1End.add(Calendar.DATE, 3);
		
		Calendar avail2Start = Calendar.getInstance();
		Calendar avail2End = Calendar.getInstance(); 
		avail2Start.add(Calendar.DATE, 5);
		avail2Start.add(Calendar.HOUR, 1);
		avail2End.add(Calendar.DATE, 6);
		
		Availability avail1 = new Availability(employee, avail1Start.getTime(), avail1End.getTime());
		Availability avail2 = new Availability(employee, avail2Start.getTime(), avail2End.getTime());
		
		availabilities = new ArrayList<Availability>();
		availabilities.add(avail1);
		availabilities.add(avail2);
		availabilities.add(new Availability(employee, startNextMonthAvail.getTime(), endNextMonthAvail.getTime()));
		availabilities.add(new Availability(employee, startNextWeekAvail.getTime(), endNextWeekAvail.getTime()));
		
		workingTimes = new ArrayList<WorkingTime>();
		workingTimes.add(new WorkingTime(employee, startNextMonthWT.getTime(), endNextMonthWT.getTime()));
		workingTimes.add(new WorkingTime(employee, startNextWeekWT.getTime(), endNextWeekWT.getTime()));
		
		emptyList = new ArrayList<WorkingTime>();
	}
	
	@Test
	public void getWorkingTimesForEmployee_idOfEmployeeThatExists_ReturnsWorkingTimes() throws Exception {
		
		when(workingTimeService.getWorkingTimesForEmployee(4)).thenReturn(workingTimes);
		
		String result = this.mockMvc.perform(MockMvcRequestBuilders
			      .get("/api/workingTime/employee/5")
			      .contentType(MediaType.APPLICATION_JSON))
				  .andDo(MockMvcResultHandlers.print())
				  .andExpect(MockMvcResultMatchers.status().isOk())
				  .andReturn()
				  .getResponse()
				  .getContentAsString();
				  
		JSONArray json = new JSONArray(result);	
		assertTrue(json.length() >= 0);
		
	}
	
	@Test
	public void getWorkingTimesForEmployee_idOfEmployeeThatDoesNotExist_ReturnsEmptyList() throws Exception {
		
		when(workingTimeService.getWorkingTimesForEmployee(5)).thenReturn(emptyList);
		
		String result = this.mockMvc.perform(MockMvcRequestBuilders
			      .get("/api/workingTime/employee/5")
			      .contentType(MediaType.APPLICATION_JSON))
				  .andDo(MockMvcResultHandlers.print())
				  .andExpect(MockMvcResultMatchers.status().isOk())
				  .andReturn()
				  .getResponse()
				  .getContentAsString();
				  
		JSONArray json = new JSONArray(result);	
		assertTrue(json.length() == 0);
		
	}
	
	@Disabled
	@Test
	public void addWorkingTime_validEmployeeId_statusIsOk() throws Exception {
		
		Calendar newStart = Calendar.getInstance();
		Calendar newEnd = Calendar.getInstance(); 
		newStart.add(Calendar.DATE, 2);
		newStart.add(Calendar.HOUR, 3);
		newEnd.add(Calendar.DATE, 2);
		newEnd.add(Calendar.HOUR, 6);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
		String startStr = dateFormat.format(newStart.getTime());
		String endStr = dateFormat.format(newEnd.getTime());


		JSONObject json = new JSONObject();
		json.put("employeeId", 4);
		json.put("startTime", startStr);
		json.put("endTime", endStr);
		
		WorkingTime workingTimeBeingAdded = new WorkingTime(employee, newStart.getTime(), newEnd.getTime());
		
		when(workingTimeService.addWorkingTime(4, newStart.getTime(), newEnd.getTime())).thenReturn(Optional.of(workingTimeBeingAdded));
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/workingTimes")
				.content(json.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Disabled
	@Test
	public void addWorkingTime_invalidEmployeeId_statusIs4xxClientError() throws Exception{
		
		Calendar newStart = Calendar.getInstance();
		Calendar newEnd = Calendar.getInstance(); 
		newStart.add(Calendar.DATE, 2);
		newStart.add(Calendar.HOUR, 3);
		newEnd.add(Calendar.DATE, 2);
		newEnd.add(Calendar.HOUR, 6);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");
		String startStr = dateFormat.format(newStart.getTime());
		String endStr = dateFormat.format(newEnd.getTime());


		JSONObject json = new JSONObject();
		json.put("employeeId", 5);
		json.put("startTime", startStr);
		json.put("endTime", endStr);
		
		
		when(workingTimeRepository.save(any(WorkingTime.class))).thenReturn(null);
		when(workingTimeRepository.findAllByEmployeeId(5)).thenReturn(new ArrayList<WorkingTime>());
		when(employeeRepository.findById(5)).thenReturn(Optional.empty());
		when(availabilityRepository.findAllByEmployeeId(5)).thenReturn(new ArrayList<Availability>());
		
		when(workingTimeService.addWorkingTime(5, newStart.getTime(), newEnd.getTime())).thenReturn(Optional.empty());
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/workingTimes")
				.content(json.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}
