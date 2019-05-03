package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.models.*;

public class Similarity {

    private final static double WEIGHT_RECEIVER_TYPE = 1.0;
    private final static double WEIGHT_REQUIRED_TYPE = 1.0;
    private final static double WEIGHT_OBJECT_ORIGIN = 1.0;
    private final static double WEIGHT_SURROUNDING_EXPRESSION = 1.0;
    private final static double WEIGHT_ENCLOSING_METHOD_RETURN_TYPE = 1.0;
    private final static double WEIGHT_ENCLOSING_METHOD_PARAMETER_SIZE = 1.0;
    private final static double WEIGHT_ENCLOSING_METHOD_PARAMETER = 1.0;

    private final static double SUBWEIGHT_PARAMETER_NAME = 0.25;
    private final static double SUBWEIGHT_PARAMETER_TYPE = 0.75;

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
        calcEnclosingMethod();

        return similarityCounter / maxSimilarityValue;
    }

    private void calcReceiverType() {
        String rt1 = q1.getReceiverType();
        String rt2 = q2.getReceiverType();

        similarityCounter += WEIGHT_RECEIVER_TYPE * equalsToSimilarity(rt1, rt2);
        maxSimilarityValue += WEIGHT_RECEIVER_TYPE;
    }

    private void calcRequiredType() {
        String rt1 = q1.getRequiredType();
        String rt2 = q2.getRequiredType();

        similarityCounter += WEIGHT_REQUIRED_TYPE * equalsToSimilarity(rt1, rt2);
        maxSimilarityValue += WEIGHT_REQUIRED_TYPE;
    }

    private void calcObjectOrigin() {
        ObjectOrigin oo1 = q1.getObjectOrigin();
        ObjectOrigin oo2 = q2.getObjectOrigin();

        similarityCounter += WEIGHT_OBJECT_ORIGIN * equalsToSimilarity(oo1, oo2);
        maxSimilarityValue += WEIGHT_OBJECT_ORIGIN;
    }

    private void calcSurroundingExpression() {
        SurroundingExpression se1 = q1.getSurroundingExpression();
        SurroundingExpression se2 = q2.getSurroundingExpression();

        similarityCounter += WEIGHT_SURROUNDING_EXPRESSION * equalsToSimilarity(se1, se2);
        maxSimilarityValue += WEIGHT_SURROUNDING_EXPRESSION;
    }

    private void calcEnclosingMethod(){
        EnclosingMethodSignature ems1 = q1.getEnclosingMethodSignature();
        EnclosingMethodSignature ems2 = q2.getEnclosingMethodSignature();

        if (ems1 != null && ems2 != null) {
            calcEnclosingMethodReturnType(ems1, ems2);

            if (ems1.getParameters() != null && ems2.getParameters() != null) {
                if (ems1.getParameters().size() == ems2.getParameters().size()) {
                    similarityCounter += WEIGHT_ENCLOSING_METHOD_PARAMETER_SIZE;
                }

                calcEnclosingMethodParameters(ems1, ems2);
            }
        } else if (ems1 == null && ems2 == null) {
            //EnclosingMethodType from q1 and q2 are null so they are same
            similarityCounter += WEIGHT_ENCLOSING_METHOD_RETURN_TYPE + WEIGHT_ENCLOSING_METHOD_PARAMETER_SIZE + WEIGHT_ENCLOSING_METHOD_PARAMETER;
        }

        maxSimilarityValue += WEIGHT_ENCLOSING_METHOD_RETURN_TYPE + WEIGHT_ENCLOSING_METHOD_PARAMETER_SIZE + WEIGHT_ENCLOSING_METHOD_PARAMETER;

    }

    private void calcEnclosingMethodReturnType(EnclosingMethodSignature ems1, EnclosingMethodSignature ems2){
        String fqrt1 = ems1.getFullyQualifiedReturnType();
        String fqrt2 = ems2.getFullyQualifiedReturnType();
        if (fqrt1 == null && fqrt2 == null) {
            similarityCounter += WEIGHT_ENCLOSING_METHOD_RETURN_TYPE;
        } else if (fqrt1 != null && fqrt1.equals(fqrt2)) {
            similarityCounter += WEIGHT_ENCLOSING_METHOD_RETURN_TYPE;
        }
    }

    private void calcEnclosingMethodParameters(EnclosingMethodSignature ems1, EnclosingMethodSignature ems2){
        //increase the maxPossible similarity by at most one, thus divide similarity increase by longer parameter list times two because foreach
        // parameter name and type can be compared
        int parameterLength = Math.max(ems1.getParameters().size(), ems2.getParameters().size());
        int countSameParameterName = 0;
        int countSameParameterType = 0;
        for (MethodParameter param1 : ems1.getParameters()) {
            for (MethodParameter param2 : ems2.getParameters()) {
                if (param1.getName() == null && param2.getName() == null) {
                    countSameParameterName++;
                } else if (param1.getName() != null && param1.getName().equals(param2.getName())) {
                    countSameParameterName++;
                }

                if (param1.getType() == null && param2.getType() == null) {
                    countSameParameterType++;
                } else if (param1.getType() != null && param1.getType().equals(param2.getType())) {
                    countSameParameterType++;
                }
            }
        }

        if (parameterLength > 0) {
            double similarityParameterNames = SUBWEIGHT_PARAMETER_NAME * countSameParameterName / parameterLength;
            double similarityParameterTypes = SUBWEIGHT_PARAMETER_TYPE * countSameParameterType / parameterLength;
            double totalSubWeights = SUBWEIGHT_PARAMETER_NAME + SUBWEIGHT_PARAMETER_TYPE;
            similarityCounter += WEIGHT_ENCLOSING_METHOD_PARAMETER * (similarityParameterNames + similarityParameterTypes) / totalSubWeights;
        } else {
            similarityCounter += WEIGHT_ENCLOSING_METHOD_PARAMETER;
        }
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