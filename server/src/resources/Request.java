package resources;

public class Request {
	
	public static final int GET_RECORDS = 1;
	public static final int CREATE_RECORD = 2;
	public static final int UPDATE_RECORD = 3;
	public static final int DELETE_RECORD = 4;
	private int type;
	private String data;
	
	public Request(int type, String data){
		this.type = type;
		this.data = data;
	}
	public int getType(){
		return type;
	}
	
	public String getData(){
		return data;
	}

}
