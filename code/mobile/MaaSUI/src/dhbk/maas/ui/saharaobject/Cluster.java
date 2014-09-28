/************************************************************************
 * Copyright 2014	Le Dai Cat, Tran Sy Dat
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

***************************************************************************/
package dhbk.maas.ui.saharaobject;

public class Cluster {

	private String status;
	private String name ;
	
	public Cluster (String status, String name) {
		this.status = status;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getStatus() {
		return status;
	}
	
	public boolean isActive () {
		return "Active".equalsIgnoreCase(status) ;
	}
}
