package com.elielamora.randomwalker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by eliel on 12/14/16.
 */
public class Main {

    public static final long MAX_ITERATIONS = (long) Math.pow(2, 20);
    public static final int ATTEMPTS = 128;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static int[][] field = new int[WIDTH][HEIGHT];

    /**
     * Main Method
     */
    public static void main(String ... args) {
        long startTime = System.currentTimeMillis();
        String basePath = "/Users/eliel/Desktop/test/";
        int tries = ATTEMPTS;
        while (tries-- > 0){
            BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            field = new int[WIDTH][HEIGHT];
            double x = WIDTH / 2;
            double y = HEIGHT / 2;
            int max = 1; // will usually be greater than 1 and 1 is a hack to avoid (/0) error later
            int count = 0;
            double factor = 1;
            // 1. do the 'random walk'
            while (count++ < MAX_ITERATIONS) {
                // fill in dot in field (to be drawn later)
                max = Math.max(max, ++field[(int) x][(int) y]); // increases field at the same time
                //debug("x = " + (int) x + ", y = " + (int) y + ", val = " + field[(int) x][(int) y]);
                // move to next location randomly (hence the random walk)
                //double D = D(x, y, WIDTH, HEIGHT);
                double newX = x + (Math.random() - 0.5) * factor;// * D;
                double newY = y + (Math.random() - 0.5) * factor;// * D;
                // check it is within boundaries
                if (0 < newX && newX < WIDTH)
                    x = newX;
                if (0 < newY && newY < HEIGHT)
                    y = newY;
            }
            // draw image to buffered image
            for (int i = 0; i < bi.getWidth(); i++) { // real
                for (int j = 0; j < bi.getHeight(); j++) { // imaginary
                    int lum = (int) (255 * ((double) field[i][j] / max));
                    //debug("lum = " + lum);
                    if (lum > 0){//if (lum > 255) {
                        lum = 255;
                    }
                    if (lum < 0) {
                        lum = 0;
                    }        // should always be asserted
                    bi.setRGB(i, j, (int)
                            lum + (lum << 8) + (lum << 16)
                    );
                    //System.out.print(data[i][j] + "\t");
                }
                //System.out.print("\n");
            }
            long endTime = System.currentTimeMillis();

            System.out.println("seconds elapsed: " + ((endTime - startTime) / 1000.0));
            try {
                ImageIO.write(bi, "PNG", new File(basePath + "test-" + tries+ ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debug(String s){
        System.out.print(s + "\n");
    }
    public static void debug(int i){
        debug(String.valueOf(i));
    }
    public static void debug(double d){
        debug(String.valueOf(d));
    }
    public static double D (double x, double y, int w, int h){
        //return x * 0.001 + y * 0.001 + 1;
        return x*x/(w*w) + y*y/(h*h) + 1;
    }
}
