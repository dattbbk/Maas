package dhbk.maas.api.hadoop.monitor.obj;

public class HistoryJob {

	public String submitTime;
	public String state;
	public String user;
	public String reducestotal;
	public String mapscompleted;
	public String starttime;
	public String id;
	public String name;
	public String reducescompleted;
	public String mapstotal;
	public String queue;
	public String finishtime;
	
	public HistoryJob (String submitTime, String state, String user, String reducesToal, String mapsCompleted, 
			String startTime, String id, String name, String reducesCompleted, String mapsTotal, 
			String queue, String finish) {
		this.submitTime = submitTime;
		this.finishtime = finish;
		this.id = id;
		this.mapscompleted = mapsCompleted;
		this.mapstotal = mapsTotal;
		this.name = name ;
		this.queue = queue;
		this.reducescompleted = reducesCompleted;
		this.reducestotal = reducesToal;
		this.starttime = startTime;
		this.state = state;
		this.user = user ;
	}
}
