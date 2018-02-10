package org.deeplearning4j.arbiter.scoring.impl;

import lombok.*;
import org.deeplearning4j.datasets.iterator.MultiDataSetWrapperIterator;
import org.deeplearning4j.datasets.iterator.impl.MultiDataSetIteratorAdapter;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.eval.ROC;
import org.deeplearning4j.eval.ROCBinary;
import org.deeplearning4j.eval.ROCMultiClass;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

/**
 * Score function that calculates AUC (area under ROC curve) or AUPRC (area under precision/recall curve) on a test set
 * for a {@link MultiLayerNetwork} or {@link ComputationGraph}
 *
 * @author Alex Black
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //JSON
public class ROCScoreFunction extends BaseNetScoreFunction {

    public enum ROCType {ROC, BINARY, MULTICLASS}
    public enum Metric {AUC, AUPRC};

    protected ROCType type;
    protected Metric metric;

    /**
     * @param metric Evaluation metric to calculate
     */
    public ROCScoreFunction(@NonNull ROCType type, @NonNull Metric metric) {
        this.type = type;
        this.metric = metric;
    }

    @Override
    public String toString() {
        return "ROCScoreFunction(type=" + type + ",metric=" + metric + ")";
    }

    @Override
    public double score(MultiLayerNetwork net, DataSetIterator iterator) {
        switch (type){
            case ROC:
                ROC r = net.evaluateROC(iterator);
                return metric == Metric.AUC ? r.calculateAUC() : r.calculateAUCPR();
            case BINARY:
                ROCBinary r2 = net.doEvaluation(iterator, new ROCBinary())[0];
                return metric == Metric.AUC ? r2.calculateAverageAuc() : r2.calculateAverageAUCPR();
            case MULTICLASS:
                ROCMultiClass r3 = net.evaluateROCMultiClass(iterator);
                return metric == Metric.AUC ? r3.calculateAverageAUC() : r3.calculateAverageAUCPR();
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    @Override
    public double score(MultiLayerNetwork net, MultiDataSetIterator iterator) {
        return score(net, new MultiDataSetWrapperIterator(iterator));
    }

    @Override
    public double score(ComputationGraph graph, DataSetIterator iterator) {
        return score(graph, new MultiDataSetIteratorAdapter(iterator));
    }

    @Override
    public double score(ComputationGraph net, MultiDataSetIterator iterator) {
        switch (type){
            case ROC:
                ROC r = net.evaluateROC(iterator);
                return metric == Metric.AUC ? r.calculateAUC() : r.calculateAUCPR();
            case BINARY:
                ROCBinary r2 = net.doEvaluation(iterator, new ROCBinary())[0];
                return metric == Metric.AUC ? r2.calculateAverageAuc() : r2.calculateAverageAUCPR();
            case MULTICLASS:
                ROCMultiClass r3 = net.evaluateROCMultiClass(iterator, 0);
                return metric == Metric.AUC ? r3.calculateAverageAUC() : r3.calculateAverageAUCPR();
            default:
                throw new RuntimeException("Unknown type: " + type);
        }
    }

    @Override
    public boolean minimize() {
        return false;   //Want to maximize all evaluation metrics: Accuracy, F1, precision, recall, g-measure, mcc
    }
}