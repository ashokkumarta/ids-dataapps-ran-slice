package ashok.ids.dataapps.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import ashok.ids.dataapps.common.CommonBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

@SpringBootApplication
@RestController
public class IDSDataAppSample extends CommonBase {

	@Value("${app.name}")
	private String appName;

	@Value("${app.type}")
	private String appType;

	@Value("${msg.help}")
	private String msgHelp;

	@Value("${msg.provider}")
	private String msgProvider;

	@Value("${msg.consumer}")
	private String msgConsumer;

	@Value("${msg.error}")
	private String msgError;

	@Value("${app.provider.name}")
	private String appProviderName;
	@Value("${app.consumer.name}")
	private String appConsumerName;
	@Value("${app.processor.name}")
	private String appProcessorName;
	

	@Value("${app.provider.policy}")
	private String appProviderPolicy;
	@Value("${app.consumer.policy}")
	private String appConsumerPolicy;
	@Value("${app.processor.policy}")
	private String appProcessorPolicy;
	
	@Value("${msg.provider.audit}")
	private Boolean msgProviderAudit;
	@Value("${msg.provider.audit.msg}")
	private String msgProviderAuditMsg;

	@Value("${user_id}")
	private String user_id;
	
	@Value("${object_id}")
	private String object_id;
	
	@Value("${activity_type}")
	private String activity_type;
	
	@Value("${action}")
	private String action;
	
	
	private static final String APP_NAME = "\\$app.name";
	private static final String APP_TYPE = "\\$app.type";

	private static final String APP_PROVIDER_NAME = "\\$app.provider.name";
	private static final String APP_PROVIDER_POLICY = "\\$app.provider.policy";

	private static final String APP_CONSUMER_NAME = "\\$app.consumer.name";
	private static final String APP_CONSUMER_POLICY = "\\$app.consumer.policy";

	private static final String APP_PROCESSOR_NAME = "\\$app.processor.name";
	private static final String APP_PROCESSOR_POLICY = "\\$app.processor.policy";

	private static final String APP_PIPELINE_PARAM = "_PROCESSED_THROUGH_";

	private static final String DATA_OPERATOR = "\\$data.operator";
	private static final String DATA_TOWER_OPERATOR = "\\$data.tower.operator";
	private static final String DATA_TOWER_CAPACITY = "\\$data.tower.capacity";
	private static final String DATA_RAN_NUMBER = "\\$data.ran.number";
	private static final String DATA_CELL_NUMBER = "\\$data.cell.number";
	private static final String DATA_CELL_CAPACITY = "\\$data.cell.capacity";
	private static final String DATA_DATA_VOLUME = "\\$data.data.volume";
	private static final String DATA_PEE_MEASUREMENT = "\\$data.pee.measurement";
	private static final String DATA_UTILIZATION = "\\$data.utilization";
	private static final String DATA_PACKET_DELAY = "\\$data.packet.delay";
	private static final String DATA_RAN_INITIATED_PAGING = "\\$data.ran.initiated.paging";
	private static final String DATA_NO_OF_CALLS_RECEIVED = "\\$data.number.of.calls.received";
	private static final String DATA_SUCCESSFUL_CALLS = "\\$data.successful.calls";
	private static final String DATA_NETWORK_SLICE_SELECTION = "\\$data.network.slice.selection";
	private static final String DATA_ENERGY_SAVING_STATE = "\\$data.energy.saving.state";
	private static final String DATA_ENERGY_SAVING_RECOMMENDATION = "\\$data.energy.saving.recommendation";
	private static final String DATA_ENERGY_SAVING_RECOMMENDATION_RESPONSE = "\\$data.energy.saving.recommendation.response";
	private static final String DATA_ENERGY_SAVING_RECOMMENDATION_INCENTIVE = "\\$data.energy.saving.recommendation.incentive";
	private static final String DATA_TIME_INTERVEL = "\\$data.time.interval";
	
	private static final String USER_ID = "\\$user_id";
	private static final String OBJECT_ID = "\\$object_id";
	private static final String ACTIVITY_TYPE = "\\$activity_type";
	private static final String ACTION = "\\$action";

	private static final String PROVIDER = "provider";
	private static final String CONSUMER = "consumer";
	private static final String PROCESSOR = "processor";
	
	private static Map<String, String> _CONSUMER_STORE = new HashMap<>();
	
	@Autowired
	private IDSDataAudit auditor;

	@Autowired
	private IDSDataGen dataGenerator;


	@GetMapping(path = { "/help" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> help() {
		return ok(parseAll(msgHelp));
	}

	@GetMapping(path = { "/**" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> provide(HttpServletRequest request) {

		if (!appType.contains(PROVIDER)) {
			return error();
		}

		String path = request.getRequestURI();
		logger.info("Processing the request for: {}", path);
		
		String msgResponse = parseAll(msgProvider);
		if(msgProviderAudit) {
			logger.info("Audit enabled");
			try {
				auditor.audit(parseAll(msgProviderAuditMsg));
			} catch (IOException e) {
				logger.warn("Unable to audit the transaction");
			}
		}

		reset();
		logger.info("Successful completion of request for: {}", path);
		return ok(msgResponse);
	}

	@PostMapping(path = { "/**" }, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> consume(HttpServletRequest request, @RequestParam Map<String, Object> params,
			@RequestBody String input, ModelMap model) {

		if (!appType.contains(CONSUMER)) {
			return error();
		}

		String path = request.getRequestURI();

		logger.debug("Received request for: {}", path);
		logger.debug("With parameters: {}", params);
		logger.debug("With message: {}", input);

		String uniqId = getUniqID();
		_CONSUMER_STORE.put(uniqId, input);
		String parsedMsg = parse(msgConsumer, UNIQ_ID, uniqId);
		parsedMsg = parse(parsedMsg, UNIQ_ID, uniqId);

		parsedMsg = parseAll(parsedMsg);

		ResponseEntity<String> response = null;

		logger.debug("Creating response");
		response = ok(parsedMsg, input);

		logger.debug("Completed processing.");
		logger.debug("Response: {}", response);
		reset();
		return response;
	}

	@PutMapping(path = { "/**" }, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> process(HttpServletRequest request, @RequestParam Map<String, Object> params,
			@RequestBody String input, ModelMap model) {

		if (!appType.contains(PROCESSOR)) {
			return error();
		}

		String path = request.getRequestURI();

		logger.debug("Received request for: {}", path);
		logger.debug("With parameters: {}", params);
		logger.debug("With message: {}", input);

		logger.debug("Processing...");
		ResponseEntity<String> response = ok(input);
		;
		logger.debug("Completed processing.");

		logger.debug("Response: {}", response);
		reset();
		return response;
	}

	private String parse(final String msgTpl, String key, String val) {
		if(null == key || null == val) {
			logger.debug("Skipping parse with key:{}, val:{}", key, val);
			return msgTpl;
		}
		return msgTpl.replaceAll(key, val);
	}

	private String parseAll(final String msgTpl) {
		
		logger.debug("ParseAll msgTpl:{}",msgTpl);
		logger.debug("ParseAll getUniqID:{}",getUniqID());
		logger.debug("ParseAll getSecret:{}",getSecret());
		logger.debug("ParseAll getTime:{}",getTime());

		String parsedMsg = parse(msgTpl, APP_NAME, appName);
		parsedMsg = parse(parsedMsg, APP_TYPE, appType);
		parsedMsg = parse(parsedMsg, UNIQ_ID, getUniqID());
		parsedMsg = parse(parsedMsg, SECRET, getSecret());
		parsedMsg = parse(parsedMsg, TIME, getTime());
		parsedMsg = parse(parsedMsg, APP_PROVIDER_NAME, appProviderName);
		parsedMsg = parse(parsedMsg, APP_PROVIDER_POLICY, appProviderPolicy);
		parsedMsg = parse(parsedMsg, APP_CONSUMER_NAME, appConsumerName);
		parsedMsg = parse(parsedMsg, APP_CONSUMER_POLICY, appConsumerPolicy);
		parsedMsg = parse(parsedMsg, APP_PROCESSOR_NAME, appProcessorName);
		parsedMsg = parse(parsedMsg, APP_PROCESSOR_POLICY, appProcessorPolicy);

		parsedMsg = parse(parsedMsg, USER_ID, user_id);
		parsedMsg = parse(parsedMsg, OBJECT_ID, object_id);
		parsedMsg = parse(parsedMsg, ACTIVITY_TYPE, activity_type);
		parsedMsg = parse(parsedMsg, ACTION, action);

		parsedMsg = parse(parsedMsg, DATA_OPERATOR, dataGenerator.getOperator());
		parsedMsg = parse(parsedMsg, DATA_TOWER_OPERATOR, dataGenerator.getTowerOperator());
		parsedMsg = parse(parsedMsg, DATA_TOWER_CAPACITY, dataGenerator.getTowerCapacity());
		
		int ranIndex = getIndex();
		parsedMsg = parse(parsedMsg, DATA_RAN_NUMBER, dataGenerator.getRanNumber(ranIndex));
		parsedMsg = parse(parsedMsg, DATA_CELL_NUMBER, dataGenerator.getCellNumber(ranIndex));
		parsedMsg = parse(parsedMsg, DATA_CELL_CAPACITY, dataGenerator.getCellCapacity(ranIndex));
		
		parsedMsg = parse(parsedMsg, DATA_DATA_VOLUME, dataGenerator.getDataVolume());
		parsedMsg = parse(parsedMsg, DATA_PEE_MEASUREMENT, dataGenerator.getPeeMeasurement());
		parsedMsg = parse(parsedMsg, DATA_UTILIZATION, dataGenerator.getUtilization());
		parsedMsg = parse(parsedMsg, DATA_PACKET_DELAY, dataGenerator.getPacketDelay());
		parsedMsg = parse(parsedMsg, DATA_RAN_INITIATED_PAGING, dataGenerator.getRanInitiatedPaging());
		
		String numberOfCallsReceived = dataGenerator.getNumberOfCallsReceived();
		
		parsedMsg = parse(parsedMsg, DATA_NO_OF_CALLS_RECEIVED, numberOfCallsReceived);
		
		parsedMsg = parse(parsedMsg, DATA_SUCCESSFUL_CALLS, dataGenerator.getSuccessfulCalls(numberOfCallsReceived));
		
		parsedMsg = parse(parsedMsg, DATA_NETWORK_SLICE_SELECTION, dataGenerator.getNetworkSliceSelection());
		parsedMsg = parse(parsedMsg, DATA_ENERGY_SAVING_STATE, dataGenerator.getEnergySavingState());
		parsedMsg = parse(parsedMsg, DATA_ENERGY_SAVING_RECOMMENDATION_RESPONSE, dataGenerator.getEnergySavingRecommendationResponse());
		parsedMsg = parse(parsedMsg, DATA_ENERGY_SAVING_RECOMMENDATION_INCENTIVE, dataGenerator.getEnergySavingRecommendationIncentive());
		parsedMsg = parse(parsedMsg, DATA_ENERGY_SAVING_RECOMMENDATION, dataGenerator.getEnergySavingRecommendation());
		parsedMsg = parse(parsedMsg, DATA_TIME_INTERVEL, dataGenerator.getTimeInterval());
		
		logger.debug("ParseAll getTime:{}",getTime());
		logger.debug("ParseAll getUniqID:{}",getUniqID());
		logger.debug("ParseAll getSecret:{}",getSecret());
		
		return parsedMsg;
	}

	
	private String addTrail(String responseMsg, String inputMsg) {

		logger.debug("Converting to json: {}", responseMsg);
		JSONObject responseJson = new JSONObject(responseMsg);

		if (null != inputMsg) {
			try {
				JSONObject inputJson = new JSONObject(inputMsg);
				responseJson.put(APP_PIPELINE_PARAM, inputJson.getJSONArray(APP_PIPELINE_PARAM));
				logger.debug("Message after adding previous trail: {}", responseJson);
			} catch (JSONException jex) {
				logger.debug("No previous trail...");
			}
		}

		logger.debug("Adding trail...");
		responseJson.append(APP_PIPELINE_PARAM, appName);
		logger.debug("Message after adding trail: {}", responseJson);
		return responseJson.toString();
	}

	private ResponseEntity<String> ok(String responseMsg, String inputMsg) {

		logger.debug("Creating response");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(addTrail(responseMsg, inputMsg));
	}

	private ResponseEntity<String> ok(String responseMsg) {

		logger.debug("Creating response");
		return ok(responseMsg, null);
	}

	private ResponseEntity<String> error(String inputMsg) {
		return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(addTrail(msgError, inputMsg));
	}

	private ResponseEntity<String> error() {

		return error(null);
	}

	public static void main(String[] args) {
		SpringApplication.run(IDSDataAppSample.class, args);
	}

}
