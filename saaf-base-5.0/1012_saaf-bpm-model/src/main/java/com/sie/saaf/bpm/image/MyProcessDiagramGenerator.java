package com.sie.saaf.bpm.image;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class MyProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
    
    private DefaultProcessDiagramCanvas processDiagramCanvas;
    private static int minX_ = 0;
    private static int minY_ = 0;
    
    public MyProcessDiagramGenerator() {
    }
    
    public InputStream generateDiagramMy(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, 
            List<String> lowLightedActivities, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor) {
        processDiagramCanvas = generateProcessDiagram(bpmnModel, imageType, highLightedActivities, Collections.<String>emptyList(), 
            activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor);
        BufferedImage buff = processDiagramCanvas.generateBufferedImage(imageType);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();  
        if(lowLightedActivities != null && !lowLightedActivities.isEmpty()) {
            Graphics2D g = buff.createGraphics();
            try {
                for(String lowLightedActivity: lowLightedActivities) {
                    drawLowLight(bpmnModel, lowLightedActivity, g);
                }
                ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
                ImageIO.write(buff, imageType, imOut);
            } catch (IOException e) {
                
            } finally {
                g.dispose();
            }
        }
        return new ByteArrayInputStream(bs.toByteArray());
    }
    

    public void drawLowLight(BpmnModel bpmnModel,String activityId, Graphics2D g) {
        GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(activityId);
        drawHighLight(g, (int) graphicInfo.getX()-minX_ + 5, (int) graphicInfo.getY()-minY_ + 5, (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());
    }
    
    public void drawHighLight(Graphics2D g, int x, int y, int width, int height) {
        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();
        g.setPaint(Color.GRAY);
        g.setStroke(new BasicStroke(3.0f));
        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.draw(rect);
        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }
    
    
    protected static DefaultProcessDiagramCanvas initProcessDiagramCanvas(BpmnModel bpmnModel, String imageType,
            String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader){
        double minX = Double.MAX_VALUE;
        double maxX = 0;
        double minY = Double.MAX_VALUE;
        double maxY = 0;
        for (Pool pool : bpmnModel.getPools()){
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            minX = graphicInfo.getX();
            maxX = graphicInfo.getX() + graphicInfo.getWidth();
            minY = graphicInfo.getY();
            maxY = graphicInfo.getY() + graphicInfo.getHeight();
        }
        List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
        for (FlowNode flowNode : flowNodes){
            GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
            // width
            if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX){
                maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
            }
            if (flowNodeGraphicInfo.getX() < minX){
                minX = flowNodeGraphicInfo.getX();
            }
            // height
            if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY){
                maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
            }
            if (flowNodeGraphicInfo.getY() < minY){
                minY = flowNodeGraphicInfo.getY();
            }
            for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()){
                List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
                if (graphicInfoList != null){
                    for (GraphicInfo graphicInfo : graphicInfoList){
                        // width
                        if (graphicInfo.getX() > maxX){
                            maxX = graphicInfo.getX();
                        }
                        if (graphicInfo.getX() < minX){
                            minX = graphicInfo.getX();
                        }
                        // height
                        if (graphicInfo.getY() > maxY){
                            maxY = graphicInfo.getY();
                        }
                        if (graphicInfo.getY() < minY){
                            minY = graphicInfo.getY();
                        }
                    }
                }
            }
        }

        List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
        for (Artifact artifact : artifacts){
            GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact.getId());
            if (artifactGraphicInfo != null){
                // width
                if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX){
                    maxX = artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth();
                }
                if (artifactGraphicInfo.getX() < minX){
                    minX = artifactGraphicInfo.getX();
                }
                // height
                if (artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight() > maxY){
                    maxY = artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight();
                }
                if (artifactGraphicInfo.getY() < minY){
                    minY = artifactGraphicInfo.getY();
                }
            }

            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
            if (graphicInfoList != null){
                for (GraphicInfo graphicInfo : graphicInfoList){
                    // width
                    if (graphicInfo.getX() > maxX){
                        maxX = graphicInfo.getX();
                    }
                    if (graphicInfo.getX() < minX){
                        minX = graphicInfo.getX();
                    }
                    // height
                    if (graphicInfo.getY() > maxY){
                        maxY = graphicInfo.getY();
                    }
                    if (graphicInfo.getY() < minY){
                        minY = graphicInfo.getY();
                    }
                }
            }
        }

        int nrOfLanes = 0;
        for (Process process : bpmnModel.getProcesses()){
            for (Lane l : process.getLanes()){
                nrOfLanes++;
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
                // // width
                if (graphicInfo.getX() + graphicInfo.getWidth() > maxX){
                    maxX = graphicInfo.getX() + graphicInfo.getWidth();
                }
                if (graphicInfo.getX() < minX){
                    minX = graphicInfo.getX();
                }
                // height
                if (graphicInfo.getY() + graphicInfo.getHeight() > maxY){
                    maxY = graphicInfo.getY() + graphicInfo.getHeight();
                }
                if (graphicInfo.getY() < minY){
                    minY = graphicInfo.getY();
                }
            }
        }
        // Special case, see https://activiti.atlassian.net/browse/ACT-1431
        if (flowNodes.isEmpty() && bpmnModel.getPools().isEmpty() && nrOfLanes == 0){
            // Nothing to show
            minX = 0;
            minY = 0;
        }
        minX_ = (int) minX;
        minY_ = (int) minY;
        return new DefaultProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY,
                                               imageType, activityFontName, labelFontName,annotationFontName, customClassLoader);
    }
    
    protected DefaultProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType,
            List<String> highLightedActivities, List<String> highLightedFlows,
            String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor){
        
        prepareBpmnModel(bpmnModel);
        DefaultProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
        // Draw pool shape, if process is participant in collaboration
        for (Pool pool : bpmnModel.getPools()){
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
        }
        // Draw lanes
        for (Process process : bpmnModel.getProcesses()){
            for (Lane lane : process.getLanes()){
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo);
            }
        }
        // Draw activities and their sequence-flows
        for (FlowNode flowNode : bpmnModel.getProcesses().get(0).findFlowElementsOfType(FlowNode.class)){
            drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows, scaleFactor);
        }
        // Draw artifacts
        for (Process process : bpmnModel.getProcesses()){
            for (Artifact artifact : process.getArtifacts()){
                drawArtifact(processDiagramCanvas, bpmnModel, artifact);
            }
        }
        return processDiagramCanvas;
    }
    

}
