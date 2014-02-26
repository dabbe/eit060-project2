package resources;

public class Record {

	private int id;
	private String patient;
	private String nurse;
	private String doctor;
	private String division;
	private String data;

	public Record(int id, String patient, String nurse, String doctor, String division, String data) {
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

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return data;
	}

	public String toLongString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + id);
		sb.append("\nPatient: " + patient);
		sb.append("\nNurse: " + nurse);
		sb.append("\nDoctor: " + doctor);
		sb.append("\nDivision: " + division);
		sb.append("\nData: " + data);

		return sb.toString();
	}

	public void setData(String text) {
		data = text;
	}

}
