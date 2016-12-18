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

import net.librec.common.LibrecException;
import net.librec.data.DataModel;
import net.librec.eval.Measure.MeasureValue;
import net.librec.eval.RecommenderEvaluator;
import net.librec.recommender.item.RecommendedItem;

import java.util.List;
import java.util.Map;

/**
 * General recommenders
 *
 * @author WangYuFeng
 */
public interface Recommender {
    /**
     * recommend
     *
     * @return
     * @throws LibrecException
     */
    void recommend(RecommenderContext context) throws LibrecException;

    /**
     * evaluate
     *
     * @return
     * @throws LibrecException
     */
    double evaluate(RecommenderEvaluator evaluator) throws LibrecException;

    /**
     * evaluate Map
     *
     * @return
     * @throws LibrecException
     */
    Map<MeasureValue, Double> evaluateMap() throws LibrecException;

    /**
     * get DataModel
     *
     * @return
     */
    DataModel getDataModel();

    /**
     * load Model
     */
    void loadModel(String filePath);

    /**
     * save Model
     */
    void saveModel(String filePath);

    /**
     * get Recommended List
     *
     * @return
     */
    List<RecommendedItem> getRecommendedList();


    /**
     * set Context
     *
     * @param context
     */
    void setContext(RecommenderContext context);
}
