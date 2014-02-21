package resources;

public class Request {
	
	public static final int GET_RECORDS = 1;
	public static final int CREATE_NEW_PATIENT = 2;
	
	private int type;
	private String data;
	
	public Request(int type, String data){
		this.type = type;
		this.data = data;
	}
	public int getType(){
		return type;
	}

}