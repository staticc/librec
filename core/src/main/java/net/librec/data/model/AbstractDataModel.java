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
package net.librec.data.model;

import net.librec.common.LibrecException;
import net.librec.conf.Configured;
import net.librec.data.DataFeature;
import net.librec.data.DataModel;
import net.librec.data.DataSplitter;
import net.librec.math.structure.DataSet;
import net.librec.util.DriverClassUtil;
import net.librec.util.ReflectionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * AbstractDataModel
 *
 * @author WangYuFeng
 */
public abstract class AbstractDataModel extends Configured implements DataModel {
    /**
     * LOG
     */
    protected final Log LOG = LogFactory.getLog(this.getClass());
    /**
     * train DataSet
     */
    protected DataSet trainDataSet;
    /**
     * test DataSet
     */
    protected DataSet testDataSet;
    /**
     * valid DataSet
     */
    protected DataSet validDataSet;
    /**
     * Data Splitter {@link net.librec.data.DataSplitter}
     */
    public DataSplitter dataSplitter;
    /**
     * Data Splitter {@link net.librec.data.DataFeature}
     */
    public DataFeature dataFeature;

    @Override
    public void buildDataModel() throws LibrecException {
        buildModel();
        buildFeature();
    }

    /**
     * build Model
     * @throws LibrecException
     */
    public abstract void buildModel() throws LibrecException;

    /**
     * build Feature Data
     */
    public void buildFeature() {
        String feature = conf.get("data.feature.format");
        if (StringUtils.isNotBlank(feature)) {
            try {
                dataFeature = (DataFeature) ReflectionUtil.newInstance(DriverClassUtil.getClass(feature), conf);
                dataFeature.setUserMappingData(getUserMappingData());
                dataFeature.setItemMappingData(getItemMappingData());
                dataFeature.processData();
            } catch (ClassNotFoundException e) {
                LOG.error(e);
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }

    public void loadDataModel() throws LibrecException {
        // TODO Auto-generated method stub

    }

    public void saveDataModel() throws LibrecException {
        // TODO Auto-generated method stub

    }

    @Override
    public DataSet getTrainDataSet() {
        return trainDataSet;
    }

    @Override
    public DataSet getTestDataSet() {
        return testDataSet;
    }

    @Override
    public DataSet getValidDataSet() {
        return validDataSet;
    }

    /**
     * @return the data Splitter
     */
    public DataSplitter getDataSplitter() {
        return dataSplitter;
    }

    /**
     * @return the dataFeature
     */
    public DataFeature getDataFeature() {
        return dataFeature;
    }
}
