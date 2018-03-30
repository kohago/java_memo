package kohago.java.gcp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.cloud.gcp.storage.GoogleStorageResourceObject;
import org.springframework.core.io.Resource;

public class DataStorageTest {

	private static final String TEST_CONTENT = "My preferred option in this circumstance is to use org.apache.commons.codec.binary.Hex which has useful APIs for converting between Stringy hex and binary. For example:\n"
			+ "Hex.decodeHex(char[] data) which throws a DecoderException if there are non-hex characters in the array, or if there are an odd number of characters.\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n"
			+ "Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].Hex.encodeHex(byte[] data) is the counterpart to the decode method above, and spits out the char[].\n";

	@Test
	public void test1() throws Exception {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		String inputPath = String.format("gs://%s/backup/%s", "your-gcp-test", "masterTravelForm-*.csv");
		Resource resource = new GoogleStorageResourceObject(storage, inputPath);
		System.out.println(resource.getFilename());
	}

	@Test
	@Ignore
	public void test2() throws Exception {
		int testThreadNum = 7;
		int fileSize = 100;
		int fileNumberPerThread = 1;

		Storage storage = StorageOptions.getDefaultInstance().getService();
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(testThreadNum);

		IntStream.rangeClosed(1, testThreadNum).forEach(i -> {
			Action action = new Action(storage, "ActionThread" + i, start, finish, fileNumberPerThread, fileSize);
			action.start();
		});

		start.countDown();
		finish.await();
		System.out.println("finished!");
	}

	public static class Action extends Thread {
		private Storage storage;

		private String name;

		private CountDownLatch start;

		private CountDownLatch finish;

		private int fileNumber;

		private int fileSize;

		public Action(Storage storage, String name, CountDownLatch start, CountDownLatch finish, int fileNumber,
				int fileSize) {
			this.storage = storage;
			this.name = name;
			this.start = start;
			this.finish = finish;
			this.fileNumber = fileNumber;
			this.fileSize = fileSize;
		}

		@Override
		public void run() {
			try {
				start.await();
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IntStream.rangeClosed(1, fileNumber).forEach(i -> {
				BlobInfo blobInfo = BlobInfo
						.newBuilder(BlobId.of("syuu-test", "test/" + name + "_" + i)).build();
				ByteArrayOutputStream out = new ByteArrayOutputStream();

				while (out.size() / (1024 * 1024) < fileSize) {
					System.out.println("size:" + out.size() / (1024 * 1024) + "MB");
					try {
						out.write(TEST_CONTENT.getBytes());
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				storage.create(blobInfo, out.toByteArray());
				System.out.println(name + "create file! " + i);
			});
			finish.countDown();
		}
	}
}
