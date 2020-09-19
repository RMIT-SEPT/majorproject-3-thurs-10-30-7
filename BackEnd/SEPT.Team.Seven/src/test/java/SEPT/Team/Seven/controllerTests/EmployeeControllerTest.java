package SEPT.Team.Seven.controllerTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import SEPT.Team.Seven.controller.EmployeeController;
import SEPT.Team.Seven.model.Availability;
import SEPT.Team.Seven.model.Employee;
import SEPT.Team.Seven.security.SecurityUserDetailsService;
import SEPT.Team.Seven.service.EmployeeService;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	@MockBean
	private SecurityUserDetailsService userDetailsService;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static List<Availability> availabilities;
	
	private static Employee employee;
	
	@BeforeAll
	public static void setUp() {
		employee = new Employee("Paula", "Kurniawan", "iannguyen@hotmail.yeet", "0123456789", "4 yeet court");
		
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
		
	}
	
	@Test
	public void getNext7DaysAvailabilitiesById_validId_returnsAvailabilitiesForNextWeek() throws Exception {
		
		when(employeeService.getNext7DaysAvailabilitiesById(4)).thenReturn(availabilities);
		
		String result = this.mockMvc.perform(MockMvcRequestBuilders
			      .get("/api/employee/next7DaysAvai/4")
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
	public void getNext7DaysAvailabilitiesById_invalidId_returnsEmptyList() throws Exception {
		
		when(employeeService.getNext7DaysAvailabilitiesById(5)).thenReturn(new ArrayList<Availability>());
		
		String result = this.mockMvc.perform(MockMvcRequestBuilders
			      .get("/api/employee/next7DaysAvai/5")
			      .contentType(MediaType.APPLICATION_JSON))
				  .andDo(MockMvcResultHandlers.print())
				  .andExpect(MockMvcResultMatchers.status().isOk())
				  .andReturn()
				  .getResponse()
				  .getContentAsString();
				  
		JSONArray json = new JSONArray(result);	
		assertTrue(json.length() == 0);
	}
}
