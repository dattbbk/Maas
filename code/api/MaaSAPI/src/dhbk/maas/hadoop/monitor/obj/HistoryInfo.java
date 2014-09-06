package dhbk.maas.hadoop.monitor.obj;

public class HistoryInfo {

	public String startedOn;
	public String hadoopVersionBuiltOn;
	public String hadoopBuiltVersion;
	public String hadoopVersion;
	
	public HistoryInfo (String startedOn, String hadoopVersionBuiltOn, String hadoopBuiltVersion, String hadoopVersion) {
		this.startedOn = startedOn ;
		this.hadoopVersionBuiltOn = hadoopVersionBuiltOn ;
		this.hadoopBuiltVersion = hadoopBuiltVersion ;
		this.hadoopVersion = hadoopVersion ;
	}
}
