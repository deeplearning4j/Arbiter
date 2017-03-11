/*-
 *
 *  * Copyright 2016 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */
package org.deeplearning4j.arbiter.scoring.multilayer;

import org.deeplearning4j.arbiter.optimize.api.data.DataProvider;
import org.deeplearning4j.arbiter.optimize.api.score.ScoreFunction;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.Map;

public class TestSetLossScoreFunction implements ScoreFunction<MultiLayerNetwork, DataSetIterator> {

    private final boolean average;

    public TestSetLossScoreFunction() {
        this(true);
    }

    public TestSetLossScoreFunction(boolean average) {
        this.average = average;
    }

    @Override
    public double score(MultiLayerNetwork model, DataProvider<DataSetIterator> dataProvider, Map<String, Object> dataParameters) {

        DataSetIterator testData = dataProvider.testData(dataParameters);

        //TODO: do this properly taking into account division by N, L1/L2 etc
        double sumScore = 0.0;
        int totalExamples = 0;
        while (testData.hasNext()) {
            DataSet ds = testData.next();
            int numExamples = ds.numExamples();

            sumScore += numExamples * model.score(ds);
            totalExamples += numExamples;
        }

        if (!average) return sumScore;
        return sumScore / totalExamples;
    }

    @Override
    public boolean minimize() {
        return true;
    }

    @Override
    public String toString() {
        return "TestSetLossScoreFunction()";
    }
}
