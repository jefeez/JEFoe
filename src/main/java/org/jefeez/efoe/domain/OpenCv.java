package org.jefeez.efoe.domain;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class OpenCv {

    private Mat result = new Mat();
    private Core.MinMaxLocResult mmr;
    private Mat image;
    private Mat template;

    public boolean has(Mat image, Mat template, double threshold) {
        this.image = image;
        this.template = template;
        Imgproc.matchTemplate(image, template, this.result, Imgproc.TM_CCOEFF_NORMED);
        this.mmr = Core.minMaxLoc(this.result);
        return mmr.maxVal >= threshold;
    }

    public double maxVal() {
        return mmr.maxVal;
    }

    public Mat draw() {
        Point matchLoc = this.mmr.maxLoc;
        Imgproc.rectangle(this.image, matchLoc, new Point(matchLoc.x + this.template.cols(),
                matchLoc.y + this.template.rows()), new Scalar(0, 255, 0));
        return image;
    }


    public static Mat draw(Mat image, Mat template) {
        Mat result = new Mat();
        Imgproc.matchTemplate(image, template, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        Point matchLoc = mmr.maxLoc;
        Imgproc.rectangle(image, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(0, 255, 0));
        return image;
    }

    public static void write(String filename, Mat image) {
        Imgcodecs.imwrite(filename, image);
    }

    public static Mat read(String filename) {
        return Imgcodecs.imread(filename);
    }

    public static Mat matify(BufferedImage image) {

        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        byte[] imgPixels = null;
        Mat imgMat = null;

        int width = image.getWidth();
        int height = image.getHeight();

        if (dataBuffer instanceof DataBufferByte) {
            imgPixels = ((DataBufferByte) dataBuffer).getData();
        }

        if (dataBuffer instanceof DataBufferInt) {

            int byteSize = width * height;
            imgPixels = new byte[byteSize * 6];

            int[] imgIntegerPixels = ((DataBufferInt) dataBuffer).getData();

            for (int p = 0; p < byteSize; p++) {
                // Converte de RGB para BGR
                imgPixels[p * 3 + 0] = (byte) (imgIntegerPixels[p] & 0x000000FF); // Blue
                imgPixels[p * 3 + 1] = (byte) ((imgIntegerPixels[p] & 0x0000FF00) >> 8); // Green
                imgPixels[p * 3 + 2] = (byte) ((imgIntegerPixels[p] & 0x00FF0000) >> 16); // Red
            }
        }

        if (imgPixels != null) {
            imgMat = new Mat(height, width, CvType.CV_8UC3);
            imgMat.put(0, 0, imgPixels);
        }

        return imgMat;
    }

}