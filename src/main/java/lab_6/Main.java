package lab_6;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import lab_6.annotations.Column;
import lab_6.class_workers.ClassScanner;
import lab_6.graph.Edge;
import lab_6.graph.GraphModel;
import lab_6.graph.RelationType;
import lab_6.graph.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Class<?>> classes = new ClassScanner().get(URI.create("airlines/db"));
//        ClassAnalyzer classAnalyzer = new ClassAnalyzer(classes)
//                .file(Path.of("./src/main/resources/class_analysis.txt"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
//                .printInConsole(false);

//        classAnalyzer.analyse();
        Graph<Vertex, Edge> graph = new GraphModel(classes);
        System.out.println(graph.edgeSet());

        createPNGFile(graph);
    }

    private static void createPNGFile(Graph<Vertex, Edge> graph) throws IOException {
        File imgFile = new File("./src/main/resources/graph.png");
        imgFile.createNewFile();

        JGraphXAdapter<Vertex, Edge> graphAdapter =
                new JGraphXAdapter<>(graph);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);
    }
}
