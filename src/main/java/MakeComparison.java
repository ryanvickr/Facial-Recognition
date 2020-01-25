import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class MakeComparison {

    public static CompareFacesMatch login(final String user) {
        Float similarityThreshold = 70F;
        String sourceImage = "sourceImg/" + user + "_s.jpg";//"sourceImg/shiv_s.jpg";
        String targetImage = "targetImg/" + user + "_t.jpg";;
        ByteBuffer sourceImageBytes=null;
        ByteBuffer targetImageBytes=null;

        System.out.println("Connecting to AWS...");
        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        //Load source and target images and create input parameters
        System.out.println("Uploading images...");
        try (InputStream inputStream = Main.class.getResourceAsStream("sourceImg/" + user + "_s.jpg")) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("User does not exist!");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        try (InputStream inputStream = Main.class.getResourceAsStream("targetImg/" + user + "_t.jpg")) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("User does not exist!");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        Image source=new Image()
                .withBytes(sourceImageBytes);
        Image target=new Image()
                .withBytes(targetImageBytes);

        System.out.println("Verifying image...");
        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);


        // Display results
        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();

        if (faceDetails.isEmpty()) return null;
        else return faceDetails.get(0);
    }
}
