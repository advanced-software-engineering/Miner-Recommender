package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.models.*;

public class Similarity {

    private Query q1;
    private Query q2;

    private int similarityCounter = 0;
    private int maxSimilarityValue = 0;

    public Similarity(Query q1, Query q2) {
        this.q1 = q1;
        this.q2 = q2;
    }

    public double calculate() {
        if (q1 == null && q2 == null) {
            return 1.0;
        } else if (q1 == null || q2 == null) {
            return 0.0;
        }

        ResultType rt1 = q1.getResultType();
        ResultType rt2 = q2.getResultType();
        if(rt1 == null || !rt1.equals(rt2)){
            return 0.0;
        }

        calcReceiverType();
        calcRequiredType();
        calcObjectOrigin();
        calcSurroundingType();

        if (q1.getEnclosingMethodSignature() != null && q2.getEnclosingMethodSignature() != null) {
            if (q1.getEnclosingMethodSignature().getFullyQualifiedReturnType() != null && q1.getEnclosingMethodSignature().getFullyQualifiedReturnType() != null) {
                if (q1.getEnclosingMethodSignature().getFullyQualifiedReturnType().equals(q2.getEnclosingMethodSignature().getFullyQualifiedReturnType()))
                    similarityCounter += 1;
            } else if (q1.getEnclosingMethodSignature().getFullyQualifiedReturnType() == null && q2.getEnclosingMethodSignature().getFullyQualifiedReturnType() == null) {
                similarityCounter += 1;
            }
            maxSimilarityValue += 1;

            if (q1.getEnclosingMethodSignature().getParameters() != null && q2.getEnclosingMethodSignature().getParameters() != null) {
                if (q1.getEnclosingMethodSignature().getParameters().size() == q2.getEnclosingMethodSignature().getParameters().size())
                    similarityCounter += 1;
                maxSimilarityValue += 1;

                //increase the maxPossible similarity by longer list of paramaters, for each parameter, name and type can be similar thus multiply maxsimilarity by 2
                if (q1.getEnclosingMethodSignature().getParameters().size() > q2.getEnclosingMethodSignature().getParameters().size()) {
                    maxSimilarityValue += q1.getEnclosingMethodSignature().getParameters().size() * 2;
                } else {
                    maxSimilarityValue += q2.getEnclosingMethodSignature().getParameters().size() * 2;
                }
                for (MethodParameter param1 : q1.getEnclosingMethodSignature().getParameters()
                ) {
                    for (MethodParameter param2 : q2.getEnclosingMethodSignature().getParameters()
                    ) {

                        if (param1.getName() != null && param2.getName() != null) {
                            if (param1.getName().equals(param2.getName()))
                                similarityCounter += 1;

                        } else if (param1.getName() == null && param2.getName() == null) {
                            similarityCounter += 1;
                        }

                        if (param1.getType() != null && param2.getType() != null) {

                            if (param1.getType().equals(param2.getType()))
                                similarityCounter += 1;

                        } else if (param1.getType() == null && param2.getType() == null) {
                            similarityCounter += 1;
                        }
                    }
                }
            }
        } else if (q1.getEnclosingMethodSignature() == null && q2.getEnclosingMethodSignature() == null) {
            similarityCounter += 1;
            maxSimilarityValue += 1;
        }

        // newvalue= (max'-min')/(max-min)*(value-max)+max' normalize between 0 and 1
        /*
        JavaScript to quickly calculate in browser
        const scale = (num, in_min, in_max, out_min, out_max) => {
              return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
            }
         */
        double similarity = (1.0 - 0.0) / (maxSimilarityValue - 0.0) * (similarityCounter - maxSimilarityValue) + 1.0;
        return Math.round((similarity * 100)) / 100.00;
    }

    private void calcReceiverType() {
        String rt1 = q1.getReceiverType();
        String rt2 = q2.getReceiverType();

        similarityCounter += equalsToSimilarity(rt1, rt2);
        maxSimilarityValue += 1;
    }

    private void calcRequiredType() {
        String rt1 = q1.getRequiredType();
        String rt2 = q2.getRequiredType();

        similarityCounter += equalsToSimilarity(rt1, rt2);
        maxSimilarityValue += 1;
    }

    private void calcObjectOrigin() {
        ObjectOrigin oo1 = q1.getObjectOrigin();
        ObjectOrigin oo2 = q2.getObjectOrigin();

        similarityCounter += equalsToSimilarity(oo1, oo2);
        maxSimilarityValue += 1;
    }

    private void calcSurroundingType() {
        SurroundingExpression se1 = q1.getSurroundingType();
        SurroundingExpression se2 = q2.getSurroundingType();

        similarityCounter += equalsToSimilarity(se1, se2);
        maxSimilarityValue += 1;
    }

    private double equalsToSimilarity(Object o1, Object o2) {
        if(o1 == null && o2 == null){
            return 1.0;
        }else if(o1 == null){
            return 0.0;
        }else{
            return o1.equals(o2) ? 1.0 : 0.0;
        }
    }
}
