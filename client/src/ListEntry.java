
public class ListEntry {
	
	private String name, header, records;

	public ListEntry(String name, String header, String records) {
		this.name = name;
		this.header = header;
		this.records = records;
	}
	
	
	public String toString() {
		return name;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getRecords() {
		return records;
	}
}
