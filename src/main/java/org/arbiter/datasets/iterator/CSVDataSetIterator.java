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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.arbiter.datasets.fetchers.CSVDataFetcher;
import org.arbiter.datasets.iterator.BaseDatasetIterator;

/**
 * CSVDataSetIterator
 * CSV reader for a dataset file
 * @author Adam Gibson
 */
public class CSVDataSetIterator extends BaseDatasetIterator {

    /**
     *
     * @param batch the mini batch size
     * @param numExamples the number of examples
     * @param is the input stream to read from
     * @param labelColumn the index (0 based) of the label
     */
    public CSVDataSetIterator(int batch, int numExamples,InputStream is,int labelColumn) {
        super(batch, numExamples, new CSVDataFetcher(is,labelColumn,0));
    }

    /**
     *
     * @param batch the mini batch size
     * @param numExamples the number of examples
     * @param f the file to read from
     * @param labelColumn the index (0 based) of the label
     * @throws IOException
     */
    public CSVDataSetIterator(int batch, int numExamples,File f,int labelColumn) throws IOException {
        super(batch, numExamples, new CSVDataFetcher(f,labelColumn,0));
    }

    /**
     *
     * @param batch the mini batch size
     * @param numExamples the number of examples
     * @param is the input stream to read from
     * @param labelColumn the index (0 based) of the label
     * @param skipLines the number of lines to skip
     */
    public CSVDataSetIterator(int batch, int numExamples,InputStream is,int labelColumn,int skipLines) {
        super(batch, numExamples, new CSVDataFetcher(is,labelColumn,skipLines));
    }

    /**
     *
     * @param batch the mini batch size
     * @param numExamples the number of examples
     * @param f the file to read from
     * @param labelColumn the index (0 based) of the label
     * @param skipLines the number of lines to skip
     * @throws IOException
     */
    public CSVDataSetIterator(int batch, int numExamples,File f,int labelColumn,int skipLines) throws IOException {
        super(batch, numExamples, new CSVDataFetcher(f,labelColumn,skipLines));
    }
}
