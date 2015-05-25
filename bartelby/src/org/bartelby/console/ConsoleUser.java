package org.bartelby.console;

public class ConsoleUser {
	
	private String role;
	private String password;
	private String passwordType;
	private String name;

	/**
	 * @param role
	 * @param password
	 * @param passwordType
	 * @param name
	 */
	public ConsoleUser(String role, String password, String passwordType,
			String name) {
		super();
		this.role = role;
		this.password = password;
		this.passwordType = passwordType;
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordType() {
		return passwordType;
	}

	public String getName() {
		return name;
	}

	public ConsoleUser() {
		// TODO Auto-generated constructor stub
	}

}
