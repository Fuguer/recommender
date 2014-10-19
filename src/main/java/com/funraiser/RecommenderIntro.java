package com.funraiser;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

public class RecommenderIntro {

	public static void recommend() throws Exception {

		Map<Long, PointOfInterest> idToPoi = new HashMap<Long, PointOfInterest>();

		ObjectMapper mapper = new ObjectMapper();

		JsonFactory f = new JsonFactory();
		JsonParser jp = f.createJsonParser(new File(
				"resources/events/dineshopplay-1.json"));

		Long poiId = 100l;
		jp.nextToken();
		while (jp.nextToken() == JsonToken.START_OBJECT) {
			PointOfInterest poi = mapper.readValue(jp, PointOfInterest.class);
			idToPoi.put(poiId, poi);
			poiId++;
		}

		DataModel model = new FileDataModel(new File("resources/intro.csv"));

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
				similarity, model);

		Recommender recommender = new GenericUserBasedRecommender(model,
				neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(1, 1);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
			System.out.println("You should go to "
					+ idToPoi.get(recommendation.getItemID()).getPlace() + "!");
		}

	}
}
