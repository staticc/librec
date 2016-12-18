/**
 * Copyright (C) 2016 LibRec
 *
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package net.librec.recommender.cf;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.librec.BaseTestCase;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.conf.Configuration.Resource;
import net.librec.data.DataModel;
import net.librec.data.model.TextDataModel;
import net.librec.filter.GenericRecommendedFilter;
import net.librec.filter.RecommendedFilter;
import net.librec.job.RecommenderJob;
import net.librec.recommender.Recommender;
import net.librec.recommender.RecommenderContext;
import net.librec.recommender.item.RecommendedItem;
import net.librec.similarity.PCCSimilarity;
import net.librec.similarity.RecommenderSimilarity;
import net.librec.util.DriverClassUtil;

/**
 * UserKnn Test Case corresponds to UserKNNRecommender
 * {@link net.librec.recommender.cf.UserKNNRecommender}
 *
 * @author liuxz
 */
public class UserKNNTestCase extends BaseTestCase {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * test the whole rating process of UserKNN recommendation
	 *
	 * @throws ClassNotFoundException
	 * @throws LibrecException
	 * @throws IOException
	 */
//	@Ignore
	@Test
	public void testRecommenderRating() throws ClassNotFoundException, LibrecException, IOException {
		Resource resource = new Resource("rec/cf/userknn-test.properties");
		conf.addResource(resource);
		RecommenderJob job = new RecommenderJob(conf);
		job.runJob();
	}

	/**
	 * test the whole ranking process of UserKNN recommendation
	 *
	 * @throws ClassNotFoundException
	 * @throws LibrecException
	 * @throws IOException
	 */
//	@Ignore
	@Test
	public void testRecommenderRanking() throws ClassNotFoundException, LibrecException, IOException {
		Resource resource = new Resource("rec/cf/userknn-testranking.properties");
		conf.addResource(resource);
		RecommenderJob job = new RecommenderJob(conf);
		job.runJob();
	}

	/**
	 * test the whole process of UserKNN recommendation
	 *
	 * @throws ClassNotFoundException
	 * @throws LibrecException
	 * @throws IOException
	 */
	@Ignore
	@Test
	public void test1SaveModel() throws ClassNotFoundException, LibrecException, IOException {
		Configuration conf = new Configuration();
		Configuration.Resource resource = new Resource("rec/cf/userknn-test.properties");
		conf.addResource(resource);
		DataModel dataModel = new TextDataModel(conf);
		dataModel.buildDataModel();
		RecommenderContext context = new RecommenderContext(conf, dataModel);
		RecommenderSimilarity similarity = new PCCSimilarity();
		similarity.setConf(conf);
		similarity.buildSimilarityMatrix(dataModel, true);
		context.setSimilarity(similarity);
		Recommender recommender = new UserKNNRecommender();
		recommender.recommend(context);
		String filePath = conf.get("dfs.result.dir")+"/model-"+DriverClassUtil.getDriverName(UserKNNRecommender.class);
		recommender.saveModel(filePath);
	}
	/**
	 * test the whole process of UserKNN recommendation
	 *
	 * @throws ClassNotFoundException
	 * @throws LibrecException
	 * @throws IOException
	 */
	@Ignore
	@Test
	public void test2LoadModel() throws ClassNotFoundException, LibrecException, IOException {
		Configuration conf = new Configuration();
		Configuration.Resource resource = new Resource("rec/cf/userknn-test.properties");
		conf.addResource(resource);
//		DataModel dataModel = new TextDataModel(conf);
//		dataModel.buildDataModel();
		RecommenderContext context = new RecommenderContext(conf);
//		RecommenderSimilarity similarity = new PCCSimilarity();
//		similarity.setConf(conf);
//		similarity.buildSimilarityMatrix(dataModel, true);
//		context.setSimilarity(similarity);
		Recommender recommender = new UserKNNRecommender();
		recommender.setContext(context);
		String filePath = conf.get("dfs.result.dir")+"/model-"+DriverClassUtil.getDriverName(UserKNNRecommender.class);
		recommender.loadModel(filePath);
		recommender.recommend(context);
		List<RecommendedItem> recommendedItemList = recommender.getRecommendedList();
		RecommendedFilter filter = new GenericRecommendedFilter();
		recommendedItemList = filter.filter(recommendedItemList);
	}

}
