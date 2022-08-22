package ashok.ids.dataapps.sample;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ashok.ids.dataapps.common.CommonBase;

@Component
public class IDSDataAudit extends CommonBase {

	@Value("${audit.url}")
	private String auditUrl;

	@Value("${audit.auth}")
	private String authToken;

	private final String CONTENT_TYPE = "application/json";

	public IDSDataAudit() {
	}

	public void audit(String data) throws IOException {

		logger.info("Posting to activity stream: {}", data);

		HttpPost post = new HttpPost(auditUrl);
		post.setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE);
		post.setHeader(HttpHeaders.AUTHORIZATION, authToken);

		post.setEntity(new StringEntity(data));

		logger.debug("Audit post: {}", post);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			String result = EntityUtils.toString(response.getEntity());
			logger.debug("Audit result: {}", result);
			logger.info("Posting to activity stream successful");
		}
	}
}
