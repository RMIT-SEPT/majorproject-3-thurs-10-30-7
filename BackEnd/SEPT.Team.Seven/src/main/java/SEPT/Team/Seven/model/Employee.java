package SEPT.Team.Seven.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="employee")
public class Employee extends Account {
	
	@OneToMany(mappedBy="employee")
	private List<WorkingTime> workingTimes;
	
	@OneToMany(mappedBy="employee")
	private List<Availability> availabilities;
	
	@OneToMany(mappedBy="employee")
	private List<Booking> bookings;
	
	@ManyToMany()
	@JoinTable(
			name = "employee_service",
			joinColumns = @JoinColumn(name = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "service_id")
	)
	private List<Service> services;
	
	public Employee() {};
	
	public Employee(String firstName, String lastName, String email, String phoneNo, String address) {
		super(firstName,lastName,email,phoneNo, address);
	}
	
	public void addToServices(Service service) {
		services.add(service);
	}
	
	public void addToWorkingTime(WorkingTime time) {
		workingTimes.add(time);
	}
	
	public void addToAvailability(Availability time) {
		availabilities.add(time);
	}
	
	public void addToBookings(Booking booking) {
		bookings.add(booking);
	}
	
}
