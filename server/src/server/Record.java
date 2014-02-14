package server;

public class Record {

	private int id;
	private String patient;
	private String nurse;
	private String doctor;
	private String division;
	private String data;
	
	public Record(int id, String patient, String nurse, String doctor, String division, String data){
		this.id = id;
		this.patient = patient;
		this.nurse = nurse;
		this.doctor = doctor;
		this.division = division;
		this.data = data;
	}
	
	public int getId() {
		return id;
	}

	public String getPatient() {
		return patient;
	}

	public String getNurse() {
		return nurse;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getDivision() {
		return division;
	}

	public String getData() {
		return data;
	}

}
