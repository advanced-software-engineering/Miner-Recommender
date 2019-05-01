package ch.uzh.ifi.seal.ase19.recommender;

import ch.uzh.ifi.seal.ase19.core.models.MethodParameter;
import ch.uzh.ifi.seal.ase19.core.models.Query;

import java.util.Optional;

import static java.util.Optional.of;

public class Similarity {

    public static double calculateSimilarity(Query q1, Query q2) {
        double similarityCounter = 0;
        int maxSimilarityValue = 0;

        if (q1 == null && q2 == null) {
            return 1.0;
        }
        if (q1 != null && q2 == null) {
            return 0.0;
        }
        if (q1 == null && q2 != null) {
            return 0.0;
        }


        if (q1.getResultType() != null && q1.getResultType() != null) {
            if (q1.getResultType().equals(q2.getResultType()))
                similarityCounter += 1;
            else   // if ResultType is unequal similarity is automatically 0 (don't recommend unequal types!)
                return 0.0;
        } else if (q1.getResultType() == null && q2.getResultType() == null) {
            similarityCounter += 1;
        }
        maxSimilarityValue += 1;

        if (q1.getReceiverType() != null && q2.getReceiverType() != null) {
            if (q1.getReceiverType().equals(q2.getReceiverType()))
                similarityCounter += 1;

        } else if (q1.getReceiverType() == null && q2.getReceiverType() == null) {
            similarityCounter += 1;
        }
        maxSimilarityValue += 1;

        if (q1.getRequiredType() != null && q2.getRequiredType() != null) {
            if (q1.getRequiredType().equals(q2.getRequiredType()))
                similarityCounter += 1;

        } else if (q1.getRequiredType() == null && q2.getRequiredType() == null) {
            similarityCounter += 1;
        }
        maxSimilarityValue += 1;

        if (q1.getObjectOrigin() != null && q2.getObjectOrigin() != null) {
            if (q1.getObjectOrigin().equals(q2.getObjectOrigin()))
                similarityCounter += 1;

        } else if (q1.getObjectOrigin() == null && q2.getObjectOrigin() == null) {
            similarityCounter += 1;
        }
        maxSimilarityValue += 1;


        if (q1.getSurroundingType() != null && q2.getSurroundingType() != null) {
            if (q1.getSurroundingType().equals(q2.getSurroundingType()))
                similarityCounter += 1;

        } else if (q1.getSurroundingType() == null && q2.getSurroundingType() == null) {
            similarityCounter += 1;
        }
        maxSimilarityValue += 1;

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

                //increase the maxPossible similarity by at most one, thus divide similarity increase by longer parameter list times two because foreach
                // parameter name and type can be compared )
                int parameterLength;
                if (q1.getEnclosingMethodSignature().getParameters().size() > q2.getEnclosingMethodSignature().getParameters().size()) {
                    parameterLength = q1.getEnclosingMethodSignature().getParameters().size();
                } else {
                    parameterLength = q2.getEnclosingMethodSignature().getParameters().size();
                }
                for (MethodParameter param1 : q1.getEnclosingMethodSignature().getParameters()
                        ) {
                    for (MethodParameter param2 : q2.getEnclosingMethodSignature().getParameters()
                            ) {

                        if (param1.getName() != null && param2.getName() != null) {
                            if (param1.getName().equals(param2.getName()))
                                similarityCounter += (1.0/(parameterLength*2))*0.5;

                        } else if (param1.getName() == null && param2.getName() == null) {
                            similarityCounter += (1.0/(parameterLength*2))*0.5;
                        }

                        if (param1.getType() != null && param2.getType() != null) {

                            if (param1.getType().equals(param2.getType()))
                                similarityCounter += (1.0/(parameterLength*2))*1.5;

                        } else if (param1.getType() == null && param2.getType() == null) {
                            similarityCounter += (1.0/(parameterLength*2))*1.5;
                        }
                    }

                }
                maxSimilarityValue +=1;

            }


        } else if (q1.getEnclosingMethodSignature() == null && q2.getEnclosingMethodSignature() == null) {
            //EnclosingMethodType from q1 and q2 are null so parameterSimilarity +1, parameterLength +1, getEnclosingMethodSignatureFullyQualifiedReturnType +1
            // can be seen as the same too, account for that
            similarityCounter += 3;
            maxSimilarityValue += 3;
        } else {
            //EnclosingMethodType from either query is null
            maxSimilarityValue +=3;  // account for missing comparing parameterSimilarity +1, parameterLength +1, getEnclosingMethodSignatureFullyQualifiedReturnType +1
        }


        // newvalue= (max'-min')/(max-min)*(value-max)+max' normalize between 0 and 1
        /*
        JavaScript to quickly calculate in browser
        const scale = (num, in_min, in_max, out_min, out_max) => {
              return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
            }
         */
        double similarity = (1.0 - 0.0) / (maxSimilarityValue - 0.0) * (similarityCounter - maxSimilarityValue) + 1.0;
        double rounded = (double) Math.round((similarity * 100)) / 100.00;


        return rounded;
    }
}
