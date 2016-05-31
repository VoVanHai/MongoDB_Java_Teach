package connect.asyncm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.bson.conversions.Bson;

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
import com.mongodb.client.model.Filters;
import com.mongodb.connection.ClusterSettings;

import dataobjects.Lophoc;

public class CRUDactionsDemo {
	private static final String DATABASE_NAME = "qlsv";
	private static final String LOPHOC_COLLECTION_NAME = "lophoc";

	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document>collection;

	public CRUDactionsDemo() throws Exception{
		//Danh sách các server
		List<ServerAddress>svrs=new ArrayList<>();
		svrs.add(new ServerAddress("localhost:27017"));
		svrs.add(new ServerAddress("localhost:27020"));
		svrs.add(new ServerAddress("localhost:27030"));
		//danh sách credentials (nếu cơ sở dữ liệu securty
		List<MongoCredential> credentialList=new ArrayList<>();
		MongoCredential credential = MongoCredential.createCredential("admin", DATABASE_NAME, "admin".toCharArray());
		credentialList.add(credential);

		//xây dựng các thiết lập để kết nối
		ClusterSettings clusterSettings = ClusterSettings.builder()
				.hosts(svrs)
				.build();
		MongoClientSettings settings = MongoClientSettings.builder()
				.clusterSettings(clusterSettings)//security
				.credentialList(credentialList)
				.build();
		//kết nối
		client = MongoClients.create(settings);
		database=client.getDatabase(DATABASE_NAME);
		collection=database.getCollection(LOPHOC_COLLECTION_NAME);
	}
	public void close(){
		if(client!=null)client.close();
	}



	public List<Lophoc> getAll() throws InterruptedException{
		List<Lophoc> lstLH=new ArrayList<>();
		//tạo một dạng block-method đồng bộ việc lấy dữ liệu
		final CountDownLatch latch=new CountDownLatch(1);
		//lấy dữ liệu
		FindIterable<Document> iter = collection.find();
		Block<Document> block=new Block<Document>() {
			public void apply(Document t) {
				Lophoc l=genObjectFromDocument(t);
				lstLH.add(l);
			}
		};
		SingleResultCallback<Void> callback=new SingleResultCallback<Void>() {
			public void onResult(Void result, Throwable t) {
				System.out.println("Get all rows completed"
						+ "\n-----------------------------");
				if(t!=null)
					System.err.println(t.getMessage());
				latch.countDown();//ngưng tiến trình chờ
			}
		};
		iter.forEach(block, callback);
		latch.await();//chờ cho việc load dữ liệu 
		return lstLH;
	}
	private Lophoc genObjectFromDocument(Document doc){
		Lophoc lh=new Lophoc();
		if(doc.containsKey("malop")) lh.setMalop(doc.getString("malop"));
		if(doc.containsKey("tenlop")) lh.setTenlop(doc.getString("tenlop"));
		return lh;
	}

	public void insert(Lophoc lop)throws Exception{
		final CountDownLatch latch=new CountDownLatch(1);
		Document doc=new Document()
				.append("malop",lop.getMalop())
				.append("tenlop", lop.getTenlop());
		SingleResultCallback<Void> callback=(result,t)->{
			if(t!=null)System.err.println(t.getMessage());
			latch.countDown();
		};
		collection.insertOne(doc, callback);
		latch.await(3,TimeUnit.SECONDS);
	}
	public void update(String malop, Lophoc lopmoi)throws Exception{
		final CountDownLatch latch=new CountDownLatch(1);
		Document update=new Document()
				.append("malop",lopmoi.getMalop())
				.append("tenlop", lopmoi.getTenlop());
		Bson filter=Filters.eq("malop", malop);
		collection.findOneAndReplace(filter, update, (result,t)->
		{
			if(t!=null)System.err.println(t.getMessage());
			latch.countDown();
		});
		latch.await(3,TimeUnit.SECONDS);
	}
	
	public void delete(String malop)throws Exception{
		final CountDownLatch latch=new CountDownLatch(1);
		Bson filter=Filters.eq("malop", malop);
		collection.findOneAndDelete(filter, (result,t)->
		{
			if(t!=null)System.err.println(t.getMessage());
			latch.countDown();
		});
		latch.await(3,TimeUnit.SECONDS);
	}
	
	private Lophoc lop=null;
	public Lophoc getByMalop(String malop)throws Exception{
		final CountDownLatch latch=new CountDownLatch(1);
		//Document filter=new Document("malop",malop);//điều kiện lọc
		Bson filter = Filters.eq("malop", malop);//hoặc
		SingleResultCallback<Document> callback=(result, t) -> {
			if(t!=null)
				System.err.println("******"+t.getMessage());
			lop=genObjectFromDocument(result);
			latch.countDown();
		};
		//lấy dữ liệu
		collection.find(filter).first(callback);
		latch.await(10,TimeUnit.SECONDS);//chờ cho việc load dữ liệu hoàn tất, 
		//không có thì chờ thêm 10s
		return lop;
	}
	
	
}

//query ref: https://docs.mongodb.com/getting-started/java/query/

//dùng import static com.mongodb.client.model.Filters.*; //để có thể truy xuất các hàm eq,lt,gt
/*
 -------equal--------------
 FindIterable<Document> iterable = db.getCollection("restaurants").find(
        new Document("grades.grade", "B"));
 db.getCollection("restaurants").find(eq("grades.grade", "B"));
 
 --------------greater than---------------------------
 FindIterable<Document> iterable = db.getCollection("restaurants").find(
        new Document("grades.score", new Document("$gt", 30)));
        ===
 db.getCollection("restaurants").find(gt("grades.score", 30));

 --------------less than---------------------------
 FindIterable<Document> iterable = db.getCollection("restaurants").find(
        new Document("grades.score", new Document("$lt", 10)));
        ===
db.getCollection("restaurants").find(lt("grades.score", 10));

 ------and ------------------
 FindIterable<Document> iterable = db.getCollection("restaurants").find(
        new Document("cuisine", "Italian").append("address.zipcode", "10075"));
        ===
 db.getCollection("restaurants").find(and(eq("cuisine", "Italian"), eq("address.zipcode", "10075")));
 
 -------or------------------
 FindIterable<Document> iterable = db.getCollection("restaurants").find(
        new Document("$or", asList(new Document("cuisine", "Italian"),
                new Document("address.zipcode", "10075"))));
        ===
 db.getCollection("restaurants").find(or(eq("cuisine", "Italian"), eq("address.zipcode", "10075")));
 
 -------sort--------------------
 FindIterable<Document> iterable = db.getCollection("restaurants").find()
        .sort(new Document("borough", 1).append("address.zipcode", 1));
        ===
 db.getCollection("restaurants").find().sort(ascending("borough", "address.zipcode"));
 
 */
