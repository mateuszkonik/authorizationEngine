package future.database;

import com.datastax.driver.core.*;
import com.datastax.driver.extras.codecs.enums.EnumNameCodec;
import com.datastax.driver.extras.codecs.joda.DateTimeCodec;
import com.datastax.driver.mapping.MappingManager;
import future.models.data.Config;

public class Database {

	static Cluster cluster;
	static Session session;
	static MappingManager mapper;


	public static void init() {
		PoolingOptions poolingOptions = new PoolingOptions();

		cluster = Cluster.builder()
				.addContactPoint("193.70.31.66")
				.addContactPoint("193.70.31.67")
				.withPoolingOptions(poolingOptions)
				.build();


		///System.out.println("version " + cluster.getConfiguration().getProtocolOptions().getProtocolVersion());

		cluster.getConfiguration().getCodecRegistry().register(new EnumNameCodec<Config.LoginVia>(future.models.data.Config.LoginVia.class));
		cluster.getConfiguration().getCodecRegistry().register(new EnumNameCodec<future.models.data.Language>(future.models.data.Language.class));
		TupleType tupleType = cluster.getMetadata()
				.newTupleType(DataType.timestamp(), DataType.varchar());
		cluster.getConfiguration().getCodecRegistry()
				.register(new DateTimeCodec(tupleType));

//        session = cluster.connect("auth_engine");

		session = cluster.connect("auth_engine");
		mapper = new MappingManager(getSession());


	}

	public static Session getSession() {
		return session;
	}

	public static MappingManager getMapper() {
		return mapper;
	}

}
