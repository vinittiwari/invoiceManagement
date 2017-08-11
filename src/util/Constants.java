package util;

public class Constants {
	Constants(String Username,int state){
		this.Username = Username;
		this.state= state;
	}
	 static String Username;
	 static int state = 28;
	public static int getState() {
		return state;
	}
	public static void setState(int state) {
		Constants.state = state;
	}
	public static void setUsername(String username) {
		Username = username;
	}
	public static String getUsername() {
		return Username;
	}
	
	 
}
