package ashok.ids.dataapps.ranslice;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ashok.ids.dataapps.common.CommonBase;

@Component
public class IDSDataGen extends CommonBase {

	@Value("${data.CustomerId}")
	private String CustomerId;

	@Value("${data.AccountNumber}")
	private String AccountNumber;

	@Value("${data.SubscriberId}")
	private String SubscriberId;

	@Value("${data.CustomerType}")
	private String CustomerType;

	@Value("${data.UserEquipmentIdPrefix}")
	private String UserEquipmentIdPrefix;

	@Value("${data.UserEquipmentId}")
	private String UserEquipmentId;

	@Value("${data.UserEquipmentType}")
	private String UserEquipmentType;
	
	@Value("${data.TenentType}")
	private String TenentType;

	@Value("${data.RequiredSliceType}")
	private List<String> RequiredSliceType;
	
	@Value("${data.RequiredStartDate}")
	private String RequiredStartDate;

	@Value("${data.RequiredEndDate}")
	private String RequiredEndDate;

	@Value("${data.RequiredStartTime}")
	private String RequiredStartTime;

	@Value("${data.RequiredEndTime}")
	private String RequiredEndTime;

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

	public String getCustomerId() {
		return CustomerId;
	}
	
	public String getAccountNumber() {
		return getIndexInRange(AccountNumber);
	}

	public String getSubscriberId() {
		return getIndexInRange(SubscriberId);
	}

	public String getCustomerType() {
		return CustomerType;
	}

	public String getUserEquipmentId() {
		return getIndexInRange(UserEquipmentIdPrefix) + getIndexInRange(UserEquipmentId);
	}
	
	public String getUserEquipmentType() {
		return UserEquipmentType;
	}
	
	public String getTenentType() {
		return TenentType;
	}

	public String getRequiredSliceType() {
		return RequiredSliceType.get(getIndex() % RequiredSliceType.size());
	}

	public String getRequiredStartDate() {
		return RequiredStartDate;
	}

	public String getRequiredEndDate() {
		return RequiredEndDate;
	}

	public String getRequiredStartTime() {
		return RequiredStartTime;
	}

	public String getRequiredEndTime() {
		return RequiredEndTime;
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

}
