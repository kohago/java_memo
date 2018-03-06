/**
 * 
 */
package kohago.java.gcp;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * to test multi Resources
 * 
 * 
 * @author shuukouha
 *
 */
@RunWith(SpringRunner.class)
public class ResourceTest {

	// @Value("gs://your-gcp-test/test1.csv")
	// Resource test1;

	@Value("csv/test-0*.csv")
	Resource[] fileResources;

	@Test
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
}
