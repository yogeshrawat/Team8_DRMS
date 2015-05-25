import java.util.ArrayList;
import java.util.List;

public class Student 
{
	private String firstName; 
	private String lastName;
	private String emailAddress;
	private String phoneNumber; 
	private String userName; 
	private String password; 
	private String institute;
	
	//ArrayList<Borrow> borrowList = new ArrayList<Borrow>();
			
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInst() {
		return institute;
	}

	public void setInst(String inst) {
		this.institute = inst;
	}
	
	public Student(String userName, String password, String inst)
	{
		this.userName = userName;
		this.password = password;
		this.institute	  = inst;
	}
}