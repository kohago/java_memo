package kohago.java.gcp;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import org.junit.Test;

public class DataStoreTest {

	@Test
	public void test1() throws Exception {

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		String gqlQuery = "select __key__ from Test where __key__ = Key(Test, '001', Test, 11)";
		Query<?> query = Query.newGqlQueryBuilder(gqlQuery).setAllowLiteral(true).build();
		QueryResults<?> results = datastore.run(query);
		if (!results.hasNext())
			System.out.println("no value in datastore");
	}

	@Test
	public void test2() throws Exception {
		int testThreadNum = 10;

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(testThreadNum);

		IntStream.rangeClosed(1, testThreadNum).forEach(i -> {
			Action action = new Action(datastore, "ActionThread" + i, start, finish);
			action.start();
		});

		start.countDown();
		finish.await();
		System.out.println("finished!");
	}

	public static class Action extends Thread {
		private Datastore datastore;

		private String name;

		private CountDownLatch start;

		private CountDownLatch finish;

		public Action(Datastore datastore, String name, CountDownLatch start, CountDownLatch finish) {
			this.datastore = datastore;
			this.name = name;
			this.start = start;
			this.finish = finish;
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

			IntStream.rangeClosed(1, 1000).forEach(i -> {
				// String gqlQuery = "select __key__ from MasterTravelForm where __key__
				// =Key(MasterTravelFormList, '0000030001', MasterTravelForm, "
				// + i + ")";
				String gqlQuery = "select __key__ from Test where __key__ =Key(Test, '001', Test, 11)";
				Query<?> query = Query.newGqlQueryBuilder(gqlQuery).setAllowLiteral(true).build();
				QueryResults<?> results = datastore.run(query);
				System.out.println("name:" + name + ";  i:" + i);
				if (!results.hasNext())
					System.out.println("no value in datastore");

			});
			finish.countDown();
		}
	}
}
