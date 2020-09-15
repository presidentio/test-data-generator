package com.presidentio.testdatagenerator.stream;

import com.presidentio.testdatagenerator.AbstractGenerator;
import com.presidentio.testdatagenerator.GenerateTask;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.stream.intensivity.FixedIntervalIntensityManager;
import com.presidentio.testdatagenerator.stream.intensivity.IntensityManager;
import com.presidentio.testdatagenerator.stream.stop.InfiniteStopManager;
import com.presidentio.testdatagenerator.stream.stop.StopManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by presidentio on 24.04.15.
 */
public class StreamGenerator extends AbstractGenerator {

    private Random random = new Random();

    private List<Template> rootTemplates;

    private int weightSum;

    private StopManager stopManager;
    private IntensityManager intensityManager;

    @Override
    public void generate(Context context, Schema schema) {
        stopManager = buildStopManager();
        intensityManager = buildIntensityManager();
        rootTemplates = new ArrayList<>(schema.getRoot().size());
        for (String rootTemplateId : schema.getRoot()) {
            final Template rootTemplate = context.getTemplates().get(rootTemplateId);
            if (rootTemplate == null) {
                throw new IllegalArgumentException("Template not defined: " +  rootTemplateId);
            }
            weightSum += rootTemplate.getCount();
            rootTemplates.add(rootTemplate);
        }
        startGeneration(context);
    }

    private void startGeneration(Context context) {
        while (!stopManager.isStop(context)) {
            int randIndex = random.nextInt(weightSum);
            int curIndex = 0;
            for (Template rootTemplate : rootTemplates) {
                curIndex += rootTemplate.getCount();
                if (curIndex > randIndex) {
                    GenerateTask task = new GenerateTask(context, rootTemplate, getValueProviderFactory());
                    task.setAsync(false);
                    task.compute();
                    break;
                }
            }
            intensityManager.waitNext();
        }
    }

    private StopManager buildStopManager() {
        return new InfiniteStopManager();
    }

    private IntensityManager buildIntensityManager() {
        return new FixedIntervalIntensityManager();
    }

}
