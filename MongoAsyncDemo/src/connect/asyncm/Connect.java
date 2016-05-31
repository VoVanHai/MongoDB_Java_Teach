package connect.asyncm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;

public class Connect {
	public static void main(String[] args) throws Exception{
		List<ServerAddress>svrs=new ArrayList<>();
		svrs.add(new ServerAddress("localhost:27017"));
		svrs.add(new ServerAddress("localhost:27020"));
		svrs.add(new ServerAddress("localhost:27030"));

		List<MongoCredential> credentialList=new ArrayList<>();
		MongoCredential credential = MongoCredential.createCredential("admin", "mondial", "admin".toCharArray());
		credentialList.add(credential);

		ClusterSettings clusterSettings = ClusterSettings.builder()
				.hosts(svrs)
				.description("Local Server")
				.build();
		MongoClientSettings settings = MongoClientSettings.builder()
				.clusterSettings(clusterSettings)
				.credentialList(credentialList)
				.build();
		//phải chờ vì không có block method
		final CountDownLatch latch = new CountDownLatch(1);

		MongoClient client = MongoClients.create(settings);

		MongoDatabase db = client.getDatabase("mondial");
		MongoCollection<Document> coll = db.getCollection("countries");
		/*FindIterable<Document> iter = coll.find();

		iter.forEach(new Block<Document>() {
			public void apply(Document doc) {
				System.out.println(doc);
			}
		}, new SingleResultCallback<Void>() {
			@Override
			public void onResult(Void v, Throwable t) {
				System.out.println("finish");
				if (t != null) {
					System.out.println("listDatabaseNames() errored: " + t.getMessage());
				}
				latch.countDown();//ngừng tiến trình đợi khi load xong
			}
		});*/
		
		

		latch.await();//đợi cho công việc hoàn tất
		client.close();
	}

	void insert(MongoCollection<Document> collection){
		// insert a document
		Document document = new Document("x", 1).append("?", "?");
		collection.insertOne(document, new SingleResultCallback<Void>() {
			@Override
			public void onResult(final Void result, final Throwable t) {
				System.out.println("Inserted!");
			}
		});
	}
	void findAll(MongoCollection<Document> collection)throws Exception{
		collection.find()
		.forEach(new Block<Document>() {
			public void apply(Document arg0) {
				
			}
		}, new SingleResultCallback<Void>() {
			@Override
			public void onResult(Void v, Throwable t) {
				System.out.println("Query completed");
				if(t!=null)
					System.err.println(t.getMessage());
			}
		});
	}


	/*public static String getDocuments(MongoCollection<Document> collection)throws Exception{
		CompletableFuture<String> result = new CompletableFuture<>(); // <-- create an empty, uncompleted Future

		collection.find().map(Document::toJson)
		.into(new HashSet<String>(), new SingleResultCallback<HashSet<String>>() {
			@Override
			public void onResult(HashSet<String> strings, Throwable throwable) {
				result.complete(strings.toString()); // <--resolves the future
			}
		});

		return result.get(); // <-- blocks until result.complete is called
	}*/
}
