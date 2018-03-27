/**
 * 
 */
package kohago.java.gcp;

import java.io.IOException;
import java.util.Arrays;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.storage.GoogleStorageProtocolResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * to test multi Resources
 * 
 * @author shuukouha
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@Import(GoogleStorageProtocolResolver.class)
public class ResourceTest {

	// change to your test gcs,or use mock
	@Value("gs://your-gcp-test/test-01.csv")
	Resource gcsResource;

	// It's Ant-style! Maybe it will be very convenient to support Ant-styple
	@Value("gs://your-gcp-test/test-*.csv")
	Resource[] gcsResources;

	@Value("csv/test-0*.csv")
	Resource[] fileResources;

	@Test
	@Ignore
	public void testFileResources() {
		if (fileResources != null) {
			Arrays.asList(fileResources).stream().forEach(fileResouce -> {
				System.out.println(fileResouce.getFilename());
			});
		}
		else {
			System.out.println("File Resouce is null");
		}
	}

	@Test
	@Ignore
	public void testGcsResource() {
		if (gcsResource != null) {
			System.out.println(gcsResource.getDescription());
			try {
				System.out.println(gcsResource.contentLength());
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("GCS Resouce is null");
		}
		System.out.println("test gcs resource finished!");
	}

	@Test
	public void testGcsResources() {
		if (gcsResources != null) {
			for (int i = 0; i < gcsResources.length; i++) {
				Resource gcsResource = gcsResources[i];
				System.out.println(gcsResource.getDescription());
				try {
					System.out.println(gcsResource.contentLength());
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("test gcs resources finished!");
	}

	@Configuration
	static class StorageApplication {
		@Bean
		public static Storage getStorage() throws Exception {
			return StorageOptions.getDefaultInstance().getService();
		}
	}
}
