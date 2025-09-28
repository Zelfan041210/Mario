//package com.sxt.util;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class StaticValue {
//    //background
//    public static BufferedImage bg = null;
//    public static BufferedImage bg2 = null;
//    //Mario jumps to the left
//    public static BufferedImage jump_L = null;
//    //Mario jumps to the right
//    public static BufferedImage jump_R = null;
//    //Mario stands to the left
//    public static BufferedImage stand_L = null;
//    //Mario stands to the right
//    public static BufferedImage stand_R = null;
//    //tower
//    public static BufferedImage tower = null;
//    //pole
//    public static BufferedImage pole = null;
//    //Mario runs to the left
//    public static List<BufferedImage> run_L = new ArrayList<BufferedImage>();
//    //Mario runs to the right
//    public static List<BufferedImage> run_R = new ArrayList<BufferedImage>();
//    //obstacle
//    public static List<BufferedImage> obstacle = new ArrayList<>();
//    //mushroom enemies
//    public static List<BufferedImage> mushroom = new ArrayList<>();
//    //flower enemies
//    public static List<BufferedImage> flower = new ArrayList<>();
//    //picture path
//    public static String path = System.getProperty("user.dir")+"/src/images/";
//
//    //initialization
//    public static void init(){
//        //load bg picture
//        try {
//            bg = ImageIO.read(new File(path+"bg.png"));
//            //load Mario standing to the left
//            //load Mario standing to the right
//            //load Mario jumping to the left
//            // load Mario jumping to the right
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
