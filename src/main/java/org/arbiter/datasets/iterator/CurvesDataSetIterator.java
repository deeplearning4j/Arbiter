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

package org.arbiter.datasets.iterator;

import java.io.IOException;
import org.arbiter.datasets.fetchers.CurvesDataFetcher;
import org.arbiter.datasets.iterator.BaseDatasetIterator;

/**
 * Curves data applyTransformToDestination iterator
 *
 * @author Adam Gibson
 */
public class CurvesDataSetIterator extends BaseDatasetIterator {
    public CurvesDataSetIterator(int batch, int numExamples) throws IOException {
        super(batch, numExamples, new CurvesDataFetcher());
    }
}
