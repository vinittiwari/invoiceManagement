package util;

public class Constants {
	Constants(String Username,String state){
		this.Username = Username;
		this.state= state;
	}
	 static String Username;
	 static String state;
	public static String getState() {
		return state;
	}
	public static void setState(String state) {
		Constants.state = state;
	}
	public static void setUsername(String username) {
		Username = username;
	}
	public static String getUsername() {
		return Username;
	}
	
	 
}
