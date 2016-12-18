/**
 * Copyright (C) 2016 LibRec
 * <p>
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package net.librec.recommender;

import com.google.common.collect.BiMap;
import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.data.DataModel;
import net.librec.eval.Measure.MeasureValue;
import net.librec.eval.RecommenderEvaluator;
import net.librec.math.structure.SparseTensor;
import net.librec.recommender.item.RecommendedItem;
import net.librec.recommender.item.RecommendedList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Tensor Recommender
 *
 * @author WangYuFeng
 */
public abstract class TensorRecommender implements Recommender {
    /**
     * LOG
     */
    protected final Log LOG = LogFactory.getLog(this.getClass());
    /**
     * is ranking or rating
     */
    protected boolean isRanking;
    /**
     * topN
     */
    protected int topN;
    /**
     * conf
     */
    protected Configuration conf;
    /**
     * RecommenderContext
     */
    protected RecommenderContext context;
    /**
     * train Tensor
     */
    protected SparseTensor trainTensor;
    /**
     * testTensor
     */
    protected SparseTensor testTensor;
    /**
     * validTensor
     */
    protected SparseTensor validTensor;
    /**
     * Recommended Item List
     */
    protected RecommendedList recommendedList;
    /**
     * user Mapping Data
     */
    public BiMap<String, Integer> userMappingData;
    /**
     * item Mapping Data
     */
    public BiMap<String, Integer> itemMappingData;
    /**
     * early-stop criteria
     */
    protected boolean earlyStop;

    /**
     * setup
     *
     * @throws LibrecException
     * @throws FileNotFoundException
     */
    protected void setup() throws LibrecException {
        conf = context.getConf();
        isRanking = conf.getBoolean("rec.recommender.isranking");
        if (isRanking) {
            topN = conf.getInt("rec.recommender.ranking.topn", 5);
        }

        earlyStop = conf.getBoolean("rec.recommender.earlyStop");

        trainTensor = (SparseTensor) getDataModel().getTrainDataSet();
        testTensor = (SparseTensor) getDataModel().getTestDataSet();
        validTensor = (SparseTensor) getDataModel().getValidDataSet();
        userMappingData = getDataModel().getUserMappingData();
        itemMappingData = getDataModel().getItemMappingData();
    }

    /**
     * recommend
     *
     * @param context
     * @throws LibrecException
     */
    @Override
    public void recommend(RecommenderContext context) throws LibrecException {
        this.context = context;
        setup();
        LOG.info("Job Setup completed.");
        trainModel();
        LOG.info("Job Train completed.");
        this.recommendedList = recommend();
        LOG.info("Job End.");
        cleanup();
    }

    /**
     * train Model
     *
     * @throws LibrecException
     */
    protected abstract void trainModel() throws LibrecException;

    /**
     * recommend * predict the ranking scores or ratings in the test data
     *
     * @return predictive ranking score or rating matrix
     * @throws LibrecException
     */
    protected RecommendedList recommend() throws LibrecException {
        return recommendedList;
    }

    /**
     * cleanup
     *
     * @throws LibrecException
     */
    protected void cleanup() throws LibrecException {

    }

    @Override
    public double evaluate(RecommenderEvaluator evaluator) throws LibrecException {
        return 0;
    }

    @Override
    public Map<MeasureValue, Double> evaluateMap() throws LibrecException {
        return null;
    }

    @Override
    public DataModel getDataModel() {
        return context.getDataModel();
    }

    @Override
    public void loadModel(String filePath) {

    }

    @Override
    public void saveModel(String filePath) {

    }

    @Override
    public List<RecommendedItem> getRecommendedList() {
        return null;
    }

    /**
     * @param context the context to set
     */
    @Override
    public void setContext(RecommenderContext context) {
        this.context = context;
    }

}
