package ashok.ids.dataapps.sample;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ashok.ids.dataapps.common.CommonBase;

@Component
public class IDSDataGen extends CommonBase {

	@Value("${data.operator}")
	private String operator;

	@Value("${data.tower.operator}")
	private String towerOperator;

	@Value("${data.tower.capacity}")
	private String towerCapacity;

	@Value("${data.ran.number}")
	private List<String> ranNumber;

	@Value("${data.cell.number}")
	private List<String> cellNumber;

	@Value("${data.cell.capacity}")
	private List<String> cellCapacity;
	
	@Value("${data.data.volume}")
	private String dataVolume;

	@Value("${data.pee.measurement}")
	private String peeMeasurement;
	
	@Value("${data.utilization}")
	private String utilization;

	@Value("${data.packet.delay}")
	private String packetDelay;

	@Value("${data.ran.initiated.paging}")
	private String ranInitiatedPaging;

	@Value("${data.number.of.calls.received}")
	private String numberOfCallsReceived;

	@Value("${data.successful.calls}")
	private String successfulCalls;

	@Value("${data.network.slice.selection}")
	private List<String> networkSliceSelection;

	@Value("${data.energy.saving.state}")
	private List<String> energySavingState;

	@Value("${data.time.interval}")
	private int timeInterval;

	@Value("${data.energy.saving.recommendation}")
	private List<String> energySavingRecommendation;

	@Value("${data.energy.saving.recommendation.response}")
	private List<String> energySavingRecommendationResponse;

	@Value("${data.energy.saving.recommendation.incentive}")
	private String energySavingRecommendationIncentive;
	
	public String getOperator() {
		return operator;
	}
	
	public String getTowerOperator() {
		return towerOperator;
	}

	
	public String getTowerCapacity() {
		return towerCapacity;
	}

	public String getRanNumber(int index) {
		return ranNumber.get(index % ranNumber.size());
	}

	public String getCellNumber(int index) {
		return cellNumber.get(index % ranNumber.size());
	}
	
	public String getCellCapacity(int index) {
		return cellCapacity.get(index % ranNumber.size());
	}
	
	public String getDataVolume() {
		return getIndexInRange(dataVolume);
	}
	
	public String getPeeMeasurement() {
		return getIndexInRange(peeMeasurement);
	}

	public String getUtilization() {
		return getIndexInRange(utilization);
	}

	public String getPacketDelay() {
		return getIndexInRange(packetDelay);
	}

	public String getRanInitiatedPaging() {
		return getIndexInRange(ranInitiatedPaging);
	}

	public String getNumberOfCallsReceived() {
		return getIndexInRange(numberOfCallsReceived);
	}

	public String getSuccessfulCalls(String numberOfCallsReceived) {
		int i_numberOfCallsReceived = Integer.parseInt(numberOfCallsReceived);
		int i_successfulCallsPercentage = Integer.parseInt(getIndexInRange(successfulCalls));
		int i_successfulCalls = i_numberOfCallsReceived * i_successfulCallsPercentage / 100; 
		return String.valueOf(i_successfulCalls);
	}
	
	public String getNetworkSliceSelection() {
		return networkSliceSelection.get(getIndex() % networkSliceSelection.size());
	}

	public String getEnergySavingState() {
		return energySavingState.get(getIndex() % energySavingState.size());
	}
	
	public String getTimeInterval() {
		Calendar now = Calendar.getInstance();
		int currHour = now.get(Calendar.HOUR_OF_DAY);
		now.add(Calendar.HOUR_OF_DAY, -timeInterval);
		int startHour = now.get(Calendar.HOUR_OF_DAY);
		return startHour + "-" + currHour;

	}
	
	public String getEnergySavingRecommendation() {
		return energySavingRecommendation.get(getIndex() % energySavingRecommendation.size());
	}

	public String getEnergySavingRecommendationResponse() {
		return energySavingRecommendationResponse.get(getIndex() % energySavingRecommendationResponse.size());
	}
	
	public String getEnergySavingRecommendationIncentive() {
		return getIndexInRange(energySavingRecommendationIncentive);
	}
	
}
