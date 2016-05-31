package connect.simple;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Connect {
	public static void main(String[] args) {
		List<ServerAddress>svrs=new ArrayList<>();
		svrs.add(new ServerAddress("localhost:27017"));
		svrs.add(new ServerAddress("localhost:27020"));
		svrs.add(new ServerAddress("localhost:27030"));
		List<MongoCredential> cres=new ArrayList<>();
		cres.add(MongoCredential.createCredential("admin", "mondial", "admin".toCharArray()));
		//MongoClient mongoClient = new MongoClient("localhost" , 27017 );
		MongoClient mongoClient = new MongoClient(svrs , cres );
		MongoDatabase db = mongoClient.getDatabase("mondial");
		MongoCollection<Document> col = db.getCollection("countries");
		FindIterable<Document> iter = col.find();
		/*MongoCursor<Document> itor = iter.iterator();
		while(itor.hasNext()){
			Document doc = itor.next();
			String name = doc.get("Name").toString();
			String code= doc.get("Code").toString();
			System.out.println(name+"\t"+code);
			System.out.println(doc);
		}*/
		/*iter.forEach(new Block<Document>() {
			public void apply(Document t) {
				System.out.println(t);
			}
		});
		iter.forEach(new Consumer<Document>(){
			@Override
			public void accept(Document t) {
				System.out.println(t);
			}
		});*/
		iter.iterator().forEachRemaining(t->{
			if(t.get("Name").toString().equalsIgnoreCase("Vietnam"))	
				System.out.println(t.toString());
		});
		mongoClient.close();
	}
	
}
