package connect.simple;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CRUD_Action {
	public static void main(String[] args) {
		List<ServerAddress>svrs=new ArrayList<>();
		svrs.add(new ServerAddress("localhost"));
		List<MongoCredential> cres=new ArrayList<>();
		cres.add(MongoCredential.createCredential("admin", "qlsv", "admin".toCharArray()));
		MongoClient mongoClient = new MongoClient(svrs , cres );
		MongoDatabase db = mongoClient.getDatabase("qlsv");
		MongoCollection<Document> col = db.getCollection("lophoc");
		if(col==null)
			db.createCollection("lophoc");
		//		insert(col);
		//insert2(db);
		//update(col);
		//xoa(col);
		findBymalop(col, "DHTH10A");
		
		//col.find().forEach((Document t)->{System.out.println(t.toJson());});
		//col.find().iterator().forEachRemaining((t)->{System.out.println(t);});
		mongoClient.close();
	}



	//chèn document
	static void insert(MongoCollection<Document> col) {		 
		Document lop=new Document("malop", "DHTH10A").append("tenlop", "Lớp DH KỸ Thuật PHẦN MỀM 10A");
		col.insertOne(lop);
	}
	static void insert2( MongoDatabase database) {		 
		//chèn bằng đối tượng BasicDBObject
		MongoCollection<BasicDBObject> collection = database.getCollection("lophoc", BasicDBObject.class);
		Map<String,String> map =new HashMap<>();
		map.put("malop", "CDTH9LT");
		map.put("tenlop", "Lớp cao đẳng 9 liên thông");
		BasicDBObject bo1=new BasicDBObject(map);
		collection.insertOne(bo1);
	}

	static void update(MongoCollection<Document> col) {		 
		//đối tượng dữ liệu mới cần cập nhật. Lưu ý $set
		BasicDBObject newDocument=new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("tenlop", "Lớp DH KỸ Thuật PHẦN MỀM 10A CLC___"));
		//lọc đối tượng cần cập nhật
		BasicDBObject filter=new BasicDBObject().append("malop", "DHTH10A");

		//cập nhật
		col.updateOne(filter, newDocument);
		System.out.println("OK");
	}

	static void xoa(MongoCollection<Document> col) {
		BasicDBObject filter=new BasicDBObject().append("malop", "1a");
		Document doc=col.findOneAndDelete(filter);
		System.out.println(doc);
	}
	
	static void findBymalop(MongoCollection<Document> col,String malop){
		/*BasicDBObject filter=new BasicDBObject().append("malop", malop);
		FindIterable<Document> iter = col.find(filter);
		Document doc = iter.first();
		System.out.println(doc);*/
		
		Document doc = col.find(eq("malop", malop)).first();
		System.out.println(doc);
	}

}
