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

package org.deeplearning4j.arbiter.optimize.config;

import lombok.*;
import org.deeplearning4j.arbiter.optimize.api.CandidateGenerator;
import org.deeplearning4j.arbiter.optimize.api.data.DataProvider;
import org.deeplearning4j.arbiter.optimize.api.saving.ResultSaver;
import org.deeplearning4j.arbiter.optimize.api.score.ScoreFunction;
import org.deeplearning4j.arbiter.optimize.api.termination.TerminationCondition;
import org.deeplearning4j.arbiter.optimize.serde.jackson.JsonMapper;
import org.nd4j.shade.jackson.annotation.JsonTypeInfo;
import org.nd4j.shade.jackson.core.JsonProcessingException;
import org.nd4j.shade.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * OptimizationConfiguration ties together all of the various
 * components (such as data, score functions, result saving etc)
 * required to execute hyperparameter optimization.
 *
 * @author Alex Black
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"dataProvider", "terminationConditions", "candidateGenerator", "resultSaver"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class OptimizationConfiguration {
    @JsonSerialize
    private DataProvider dataProvider;
    @JsonSerialize
    private CandidateGenerator candidateGenerator;
    @JsonSerialize
    private ResultSaver resultSaver;
    @JsonSerialize
    private ScoreFunction scoreFunction;
    @JsonSerialize
    private List<TerminationCondition> terminationConditions;
    @JsonSerialize
    private Long rngSeed;

    @Getter
    @Setter
    private long executionStartTime;


    private OptimizationConfiguration(Builder builder) {
        this.dataProvider = builder.dataProvider;
        this.candidateGenerator = builder.candidateGenerator;
        this.resultSaver = builder.resultSaver;
        this.scoreFunction = builder.scoreFunction;
        this.terminationConditions = builder.terminationConditions;
        this.rngSeed = builder.rngSeed;

        if (rngSeed != null)
            candidateGenerator.setRngSeed(rngSeed);

        //Validate the configuration: data types, score types, etc
        //TODO
    }

    public static class Builder {

        private DataProvider dataProvider;
        private CandidateGenerator candidateGenerator;
        private ResultSaver resultSaver;
        private ScoreFunction scoreFunction;
        private List<TerminationCondition> terminationConditions;
        private Long rngSeed;

        public Builder dataProvider(DataProvider dataProvider) {
            this.dataProvider = dataProvider;
            return this;
        }

        public Builder candidateGenerator(CandidateGenerator candidateGenerator) {
            this.candidateGenerator = candidateGenerator;
            return this;
        }

        public Builder modelSaver(ResultSaver resultSaver) {
            this.resultSaver = resultSaver;
            return this;
        }

        public Builder scoreFunction(ScoreFunction scoreFunction) {
            this.scoreFunction = scoreFunction;
            return this;
        }

        /**
         * Termination conditions to use
         * @param conditions
         * @return
         */
        public Builder terminationConditions(TerminationCondition... conditions) {
            terminationConditions = Arrays.asList(conditions);
            return this;
        }

        public Builder terminationConditions(List<TerminationCondition> terminationConditions) {
            this.terminationConditions = terminationConditions;
            return this;
        }

        public Builder rngSeed(long rngSeed) {
            this.rngSeed = rngSeed;
            return this;
        }

        public OptimizationConfiguration build() {
            return new OptimizationConfiguration(this);
        }
    }


    /**
     * Create an optimization configuration from the json
     * @param json the json to create the config from
     *  For type definitions
     *  @see OptimizationConfiguration
     */
    public static OptimizationConfiguration fromYaml(String json) {
        try {
            return JsonMapper.getYamlMapper().readValue(json, OptimizationConfiguration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an optimization configuration from the json
     * @param json the json to create the config from
     *  @see OptimizationConfiguration
     */
    public static OptimizationConfiguration fromJson(String json) {
        try {
            return JsonMapper.getMapper().readValue(json, OptimizationConfiguration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return a json configuration of this optimization configuration
     *
     * @return
     */
    public String toJson() {
        try {
            return JsonMapper.getMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Return a yaml configuration of this optimization configuration
     *
     * @return
     */
    public String toYaml() {
        try {
            return JsonMapper.getYamlMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
