package ashok.ids.dataapps.ranslice;

import java.util.Calendar;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ashok.ids.dataapps.common.CommonBase;

@Component
public class IDSDataGen extends CommonBase {

	@Value("${data.CustomerId}")
	private List<String> CustomerId;

	@Value("${data.AccountNumber}")
	private String AccountNumber;

	@Value("${data.SubscriberId}")
	private String SubscriberId;

	@Value("${data.CustomerType}")
	private List<String> CustomerType;

	@Value("${data.UserEquipmentIdPrefix}")
	private String UserEquipmentIdPrefix;

	@Value("${data.UserEquipmentId}")
	private String UserEquipmentId;

	@Value("${data.UserEquipmentType}")
	private List<String> UserEquipmentType;
	
	@Value("${data.TenentType}")
	private List<String> TenentType;

	@Value("${data.RequiredSliceType}")
	private List<String> RequiredSliceType;

	@Value("${data.RequiredStartInDays}")
	private String RequiredStartInDays;

	@Value("${data.RequiredDurationType}")
	private List<String> RequiredDurationType;

	@Value("${data.RequiredDuration}")
	private String RequiredDuration;

	@Value("${data.RequiredStartTimeHrs}")
	private String RequiredStartTimeHrs;

	@Value("${data.RequiredStartTimeMins}")
	private String RequiredStartTimeMins;

	@Value("${data.RequiredEndTimeHrs}")
	private String RequiredEndTimeHrs;

	@Value("${data.RequiredEndTimeMins}")
	private String RequiredEndTimeMins;

	@Value("${data.RequiredPerformanceLevel}")
	private List<String> RequiredPerformanceLevel;

	@Value("${data.RequiredReliabilityLevel}")
	private List<String> RequiredReliabilityLevel;

	@Value("${data.RequiredBandwidth}")
	private String RequiredBandwidth;

	@Value("${data.AllotmentConfirmation}")
	private List<String> AllotmentConfirmation;

	@Value("${data.TrackingAreaId}")
	private List<String> TrackingAreaId;

	@Value("${data.CellNumber}")
	private List<String> CellNumber;

	@Value("${data.RRMPolicyType}")
	private String RRMPolicyType;

	@Value("${data.Cost}")
	private String Cost;

	@Value("${data.SliceAllocationStatus}")
	private List<String> SliceAllocationStatus;

	@Value("${data.SliceAllocationId}")
	private String SliceAllocationId;

	public String getCustomerId() {		
		return CustomerId.get(getIndex() % CustomerId.size());
	}
	
	public String getAccountNumber() {
		return getIndexInRange(AccountNumber);
	}

	public String getSubscriberId() {
		return getIndexInRange(SubscriberId);
	}

	public String getCustomerType() {
		return CustomerType.get(getIndex() % CustomerType.size());
	}

	public String getUserEquipmentId() {
		return getIndexInRange(UserEquipmentIdPrefix) + getIndexInRange(UserEquipmentId);
	}
	
	public String getUserEquipmentType() {
		return UserEquipmentType.get(getIndex() % UserEquipmentType.size());
	}
	
	public String getTenentType() {
		return TenentType.get(getIndex() % TenentType.size());
	}

	public String getRequiredSliceType() {
		return RequiredSliceType.get(getIndex() % RequiredSliceType.size());
	}

	private Long getRequiredStartInDays() {
		return Long.parseLong(getIndexInRange(RequiredStartInDays));
	}

	private String getRequiredDurationType() {
		return RequiredDurationType.get(getIndex() % RequiredDurationType.size());
	}

	private String getRequiredDuration() {
		return getIndexInRange(RequiredDuration);
	}

	private String timeFormat(String data) {
		int idata = Integer.parseInt(data);
		if (idata < 10) {
			return "0"+data;
		}
		return data;
	} 

	private String getRequiredStartTimeHrs() {
		return timeFormat(getIndexInRange(RequiredStartTimeHrs));
	}

	private String getRequiredStartTimeMins() {
		return timeFormat(getIndexInRange(RequiredStartTimeMins));
	}

	private String getRequiredEndTimeHrs() {
		return timeFormat(getIndexInRange(RequiredEndTimeHrs));
	}

	private String getRequiredEndTimeMins() {
		return timeFormat(getIndexInRange(RequiredEndTimeMins));
	}

	public LocalDateTime getRequiredStartDate() {
		return LocalDateTime.now().plusDays(getRequiredStartInDays());
	}

	public LocalDateTime getRequiredEndDate(LocalDateTime startDate) {

		LocalDateTime endDate = startDate.plusDays(1);
		String durationType = getRequiredDurationType();
		String duration = getRequiredDuration();
		logger.debug("durationType: {}", durationType);
		logger.debug("duration: {}", duration);
		if("hours".equals(durationType)) {
			endDate = endDate.plusHours(Long.parseLong(getRequiredDuration()));
		} else {
			endDate = endDate.plusDays(Long.parseLong(getRequiredDuration()));
		}
		return endDate;
	}

	public String getRequiredStartTime() {
		return getRequiredStartTimeHrs()+":"+getRequiredStartTimeMins();
	}

	public String getRequiredEndTime() {
		return getRequiredEndTimeHrs()+":"+getRequiredEndTimeHrs();
	}

	public String getRequiredPerformanceLevel() {
		return RequiredPerformanceLevel.get(getIndex() % RequiredPerformanceLevel.size());
	}
	
	public String getRequiredReliabilityLevel() {
		return RequiredReliabilityLevel.get(getIndex() % RequiredReliabilityLevel.size());
	}

	public String getRequiredBandwidth() {
		return getIndexInRange(RequiredBandwidth);
	}

	public String getAllotmentConfirmation() {
		return AllotmentConfirmation.get(getIndex() % AllotmentConfirmation.size());
	}

	public String getTrackingAreaId() {
		return TrackingAreaId.get(getIndex() % TrackingAreaId.size());
	}

	public String getCellNumber() {
		return CellNumber.get(getIndex() % CellNumber.size());
	}

	public String getRRMPolicyType() {
		return "P" + getIndexInRange(RRMPolicyType);
	}

	public String getCost() {
		return "$" + getIndexInRange(Cost);
	}

	public String getSliceAllocationStatus() {
		return SliceAllocationStatus.get(getIndex() % SliceAllocationStatus.size());
	}

	public String getSliceAllocationId() {
		return "SLICE#" + getIndexInRange(SliceAllocationId);
	}
}
