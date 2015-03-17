/*
 * Copyright 2015 Skymind,Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.arbiter.nn.params;

import org.canova.api.conf.Configuration;
import org.arbiter.nn.weights.WeightInitUtil;
import org.arbiter.nn.api.ParamInitializer;
import org.arbiter.nn.conf.NeuralNetConfiguration;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Map;

/**
 * Static weight initializer with just a weight matrix and a bias
 * @author Adam Gibson
 */
public class DefaultParamInitializer implements ParamInitializer {

    public final static String WEIGHT_KEY = "W";
    public final static String BIAS_KEY = "b";

    @Override
    public void init(Map<String, INDArray> params, NeuralNetConfiguration conf) {
        params.put(WEIGHT_KEY,createWeightMatrix(conf));
        params.put(BIAS_KEY,createBias(conf));
        conf.addVariable(WEIGHT_KEY);
        conf.addVariable(BIAS_KEY);

    }

    @Override
    public void init(Map<String, INDArray> params, NeuralNetConfiguration conf, Configuration extraConf) {
        init(params,conf);

    }


    protected INDArray createBias(NeuralNetConfiguration conf) {
        return Nd4j.zeros(conf.getnOut());
    }


    protected INDArray createWeightMatrix(NeuralNetConfiguration conf) {
        return WeightInitUtil.initWeights(
                conf.getnIn(),
                conf.getnOut(),
                conf.getWeightInit(),
                conf.getDist());
    }



}
