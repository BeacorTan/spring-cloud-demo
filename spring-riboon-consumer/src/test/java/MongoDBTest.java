import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @author BoSongsh
 * @create 2017-10-20 16:37
 **/
public class MongoDBTest {


    public static void main(String[] args) {

        MongoClient client = new MongoClient("192.168.129.128", 27017);
        MongoDatabase db = client.getDatabase("beacor");
        System.out.println("Connect to database successfully");

        MongoCollection<Document> collection = db.getCollection("col");

        // 插入数据
        Document dInsert=new Document("name","ht");
        collection.insertOne(dInsert);

        //更新文档   将文档中likes=100的文档修改为likes=200
        Document dUpdate=new Document("likes", 200);
        dUpdate.append("name", "beacorbeacor").append("age",29);
        Document d=new Document("$set", dUpdate);
        collection.updateOne(Filters.eq("name", "beacor1"),d);


        //检索所有文档
        /**
         * 1. 获取迭代器FindIterable<Document>
         * 2. 获取游标MongoCursor<Document>
         * 3. 通过游标遍历检索出的文档集合
         * */
        System.out.println("\n\n\n\n查询所有");
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }


        System.out.println("\n\n\n\n条件查询");
        FindIterable<Document> findIterableCondition = collection.find(Filters.eq("name", "ht"));
        MongoCursor<Document> mongoCursorCondition = findIterableCondition.iterator();
        while (mongoCursorCondition.hasNext()) {
            System.out.println(mongoCursorCondition.next());
        }

    }

}
