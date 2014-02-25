package resources;

public class Identity {
	
	public static final String CN = "CN";
	public static final String OU = "OU";

	private String mCN;
	private String mOU;
	
	public String getCN() {
		return mCN;
	}
	public void setCN(String cN) {
		mCN = cN;
	}
	public String getOU() {
		return mOU;
	}
	public void setOU(String oU) {
		mOU = oU;
	}
	
}
