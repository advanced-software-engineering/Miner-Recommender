package ch.uzh.ifi.seal.ase19.recommender;

public class SimilarityDto {
    public final Double similarity;
    public final double similarityReceiverType;
    public final double similarityRequiredType;
    public final double similarityObjectOrigin;
    public final double similaritySurroundingExpression;
    public final double similarityEnclosingMethodReturnType;
    public final double similarityEnclosingMethodParameterSize;
    public final double similarityEnclosingMethodParameters;
    public final double similarityEnclosingMethodSuper;

    public SimilarityDto(double similarity, double similarityReceiverType, double similarityRequiredType, double similarityObjectOrigin, double similaritySurroundingExpression, double similarityEnclosingMethodReturnType, double similarityEnclosingMethodParameterSize, double similarityEnclosingMethodParameters, double similarityEnclosingMethodSuper) {
        this.similarity = similarity;
        this.similarityReceiverType = similarityReceiverType;
        this.similarityRequiredType = similarityRequiredType;
        this.similarityObjectOrigin = similarityObjectOrigin;
        this.similaritySurroundingExpression = similaritySurroundingExpression;
        this.similarityEnclosingMethodReturnType = similarityEnclosingMethodReturnType;
        this.similarityEnclosingMethodParameterSize = similarityEnclosingMethodParameterSize;
        this.similarityEnclosingMethodParameters = similarityEnclosingMethodParameters;
        this.similarityEnclosingMethodSuper = similarityEnclosingMethodSuper;
    }
}
