package ch.uzh.ifi.seal.ase19.recommender;

public class SimilarityDto {
    private final double similarity;
    private final double similarityReceiverType;
    private final double similarityRequiredType;
    private final double similarityObjectOrigin;
    private final double similaritySurroundingExpression;
    private final double similarityEnclosingMethodReturnType;
    private final double similarityEnclosingMethodParameterSize;
    private final double similarityEnclosingMethodParameters;

    public SimilarityDto(double similarity, double similarityReceiverType, double similarityRequiredType, double similarityObjectOrigin, double similaritySurroundingExpression, double similarityEnclosingMethodReturnType, double similarityEnclosingMethodParameterSize, double similarityEnclosingMethodParameters) {
        this.similarity = similarity;
        this.similarityReceiverType = similarityReceiverType;
        this.similarityRequiredType = similarityRequiredType;
        this.similarityObjectOrigin = similarityObjectOrigin;
        this.similaritySurroundingExpression = similaritySurroundingExpression;
        this.similarityEnclosingMethodReturnType = similarityEnclosingMethodReturnType;
        this.similarityEnclosingMethodParameterSize = similarityEnclosingMethodParameterSize;
        this.similarityEnclosingMethodParameters = similarityEnclosingMethodParameters;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public double getSimilarityReceiverType() {
        return similarityReceiverType;
    }

    public double getSimilarityRequiredType() {
        return similarityRequiredType;
    }

    public double getSimilarityObjectOrigin() {
        return similarityObjectOrigin;
    }

    public double getSimilaritySurroundingExpression() {
        return similaritySurroundingExpression;
    }

    public double getSimilarityEnclosingMethodReturnType() {
        return similarityEnclosingMethodReturnType;
    }

    public double getSimilarityEnclosingMethodParameterSize() {
        return similarityEnclosingMethodParameterSize;
    }

    public double getSimilarityEnclosingMethodParameters() {
        return similarityEnclosingMethodParameters;
    }
}
