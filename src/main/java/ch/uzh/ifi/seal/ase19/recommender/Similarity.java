package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.models.*;

public class Similarity {

    private Query q1;
    private Query q2;

    private double similarityCounter = 0;
    private double maxSimilarityValue = 0;

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
        if (rt1 == null || !rt1.equals(rt2)) {
            return 0.0;
        }

        calcReceiverType();
        calcRequiredType();
        calcObjectOrigin();
        calcSurroundingExpression();

        EnclosingMethodSignature ems1 = q1.getEnclosingMethodSignature();
        EnclosingMethodSignature ems2 = q2.getEnclosingMethodSignature();

        if (ems1 != null && ems2 != null) {
            String fqrt1 = ems1.getFullyQualifiedReturnType();
            String fqrt2 = ems2.getFullyQualifiedReturnType();
            if (fqrt1 == null && fqrt2 == null) {
                similarityCounter += 1;
            } else if (fqrt1 != null) {
                if (fqrt1.equals(fqrt2))
                    similarityCounter += 1;
            }
            maxSimilarityValue += 1;

            if (ems1.getParameters() != null && ems2.getParameters() != null) {
                if (ems1.getParameters().size() == ems2.getParameters().size())
                    similarityCounter += 1;
                maxSimilarityValue += 1;

                //increase the maxPossible similarity by at most one, thus divide similarity increase by longer parameter list times two because foreach
                // parameter name and type can be compared
                // TODO are 0.5 and 1.5 weights?
                int parameterLength = Math.max(ems1.getParameters().size(), ems2.getParameters().size());
                for (MethodParameter param1 : ems1.getParameters()) {
                    for (MethodParameter param2 : ems2.getParameters()) {
                        if (param1.getName() == null && param2.getName() == null) {
                            similarityCounter += (1.0 / (parameterLength * 2)) * 0.5;
                        } else if (param1.getName() != null && param1.getName().equals(param2.getName())) {
                            similarityCounter += (1.0 / (parameterLength * 2)) * 0.5;
                        }

                        if (param1.getType() == null && param2.getType() == null) {
                            similarityCounter += (1.0 / (parameterLength * 2)) * 1.5;
                        } else if (param1.getType() != null && param1.getType().equals(param2.getType())) {
                            similarityCounter += (1.0 / (parameterLength * 2)) * 1.5;
                        }
                    }
                }
                maxSimilarityValue += 1;
            }
        } else if (ems1 == null && ems2 == null) {
            //EnclosingMethodType from q1 and q2 are null so parameterSimilarity +1, parameterLength +1, getEnclosingMethodSignatureFullyQualifiedReturnType +1
            // can be seen as the same too, account for that
            similarityCounter += 3;
            maxSimilarityValue += 3;
        } else {
            //EnclosingMethodType from either query is null
            // TODO why? one of both is null
            maxSimilarityValue += 3;  // account for missing comparing parameterSimilarity +1, parameterLength +1, getEnclosingMethodSignatureFullyQualifiedReturnType +1
        }

        return similarityCounter / maxSimilarityValue;
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

    private void calcSurroundingExpression() {
        SurroundingExpression se1 = q1.getSurroundingExpression();
        SurroundingExpression se2 = q2.getSurroundingExpression();

        similarityCounter += equalsToSimilarity(se1, se2);
        maxSimilarityValue += 1;
    }

    private double equalsToSimilarity(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return 1.0;
        } else if (o1 == null) {
            return 0.0;
        } else {
            return o1.equals(o2) ? 1.0 : 0.0;
        }
    }
}
