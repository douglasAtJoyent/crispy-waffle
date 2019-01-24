package com.joyent.jmetersampler;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * This will create a random file of garbage but specific size. It will save it to the temp directory.
 *
 * @author DouglasAnderson z
 */
public class GenerateFile extends AbstractJavaSamplerClient {

    @Override
    public void setupTest(final JavaSamplerContext context) {
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments args = new Arguments();
        args.addArgument("size", "5000");
        args.addArgument("filename", "");
        args.addArgument("variable", "FileName");
        args.addArgument("property", "FileName");
        return args;
    }

    @Override
    public SampleResult runTest(final JavaSamplerContext context) {
        // setting vars from input table.
        String filename = context.getParameter("filename");
        String varName = context.getParameter("variable");
        String propName = context.getParameter("property");
        int size = 0;
        try {
            size = Integer.parseInt(context.getParameter("size").trim());
        } catch (Exception e) {
            // Catching number format exception, if we have one we are going to fail the test.
            SampleResult result = new SampleResult();
            result.setSuccessful(false);
            result.setResponseData(String.format("Parameter incorrect %s", e.getMessage()).getBytes());
            return result;
        }
        SampleResult result = new SampleResult();
        result.sampleStart();
        filename = String.format("%s%s", System.getProperty("java.io.tmpdir"), filename);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(RandomStringUtils.randomAlphanumeric(size));
        } catch (Exception e) {
            result.setResponseData(String.format("Cannot put obj: %s", e.getMessage()).getBytes());
            result.setSuccessful(false);
            result.sampleEnd();
        }
        result.sampleEnd();
        result.setSuccessful(true);
        result.setSamplerData("Successfully wrote a file of size " + size + " to file : " + filename);
        String response = " { \"filename\" : \"" + filename + "\" }";
        result.setResponseData(response.getBytes());
        context.getJMeterVariables().put(varName, filename);
        context.getJMeterProperties().put(propName, filename);
        System.out.println(context.getJMeterVariables().get("FileName"));
        return result;
    }
}
