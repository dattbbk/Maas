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
package dhbk.maas.api.hadoop.monitor.obj;

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
