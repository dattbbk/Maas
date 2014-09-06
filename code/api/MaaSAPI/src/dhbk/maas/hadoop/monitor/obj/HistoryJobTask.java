package dhbk.maas.hadoop.monitor.obj;

public class HistoryJobTask {

	public String progress;
	public String elapsedTime;
	public String state;
	public String startTime;
	public String id;
	public String type;
	public String successfulAttempt;
	public String finishTime;
	
	public HistoryJobTask (String progress, String elapsedTime, String state, String startTime, 
								String id, String type, String successfulAttempt, String finishTime) {
		this.progress = progress;
		this.elapsedTime = elapsedTime ;
		this.state = state ;
		this.startTime = startTime ;
		this.id = id;
		this.type = type ;
		this.successfulAttempt = successfulAttempt ;
		this.finishTime = finishTime ;
	}
}
