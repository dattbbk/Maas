package dhbk.maas.api.hadoop.monitor.obj;

public class ListADirectory {

	public String accessTime ;
	public String blockSize ;
	public String group ;
	public String length ;
	public String modificationTime ;
	public String owner ;
	public String pathSuffix ;
	public String permission ;
	public String replication ;
	public String type ;
	
	public ListADirectory (String accessTime, String blockSize, String group, String length, String modificationTime, 
								String owner, String pathSuffix, String permission, String replication, String type) {
		this.accessTime = accessTime ;
		this.blockSize = blockSize ;
		this.group = group;
		this.length = length ;
		this.modificationTime = modificationTime ;
		this.owner = owner ;
		this.pathSuffix = pathSuffix ;
		this.permission = permission ;
		this.replication = replication;
		this.type = type ; 
				
	}
}
