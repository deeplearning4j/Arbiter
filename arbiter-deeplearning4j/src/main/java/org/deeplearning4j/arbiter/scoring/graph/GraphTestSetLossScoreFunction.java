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
package org.deeplearning4j.arbiter.scoring.graph;

import org.deeplearning4j.arbiter.optimize.api.data.DataProvider;
import org.deeplearning4j.arbiter.optimize.api.score.ScoreFunction;
import org.deeplearning4j.arbiter.scoring.util.ScoreUtil;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

import java.util.Map;

public class GraphTestSetLossScoreFunction implements ScoreFunction<ComputationGraph, Object> {

    private final boolean average;

    public GraphTestSetLossScoreFunction() {
        this(false);
    }

    public GraphTestSetLossScoreFunction(boolean average) {
        this.average = average;
    }

    @Override
    public double score(ComputationGraph model, DataProvider<Object> dataProvider, Map<String, Object> dataParameters) {
        MultiDataSetIterator testData = ScoreUtil.getMultiIterator(dataProvider.testData(dataParameters));
        return ScoreUtil.score(model,testData,average);
    }

    @Override
    public boolean minimize() {
        return true;
    }

    @Override
    public String toString() {
        return "GraphTestSetLossScoreFunctionDataSet()";
    }
}
