package com.sxt.util;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //background
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;
    //Mario jumps to the left
    public static BufferedImage jump_L = null;
    //Mario jumps to the right
    public static BufferedImage jump_R = null;
    //Mario stands to the left
    public static BufferedImage stand_L = null;
    //Mario stands to the right
    public static BufferedImage stand_R = null;
    //tower
    public static BufferedImage tower = null;
    //pole
    public static BufferedImage pole = null;
    //Mario runs to the left
    public static List<BufferedImage> run_L = new ArrayList<BufferedImage>();
    //Mario runs to the right
    public static List<BufferedImage> run_R = new ArrayList<BufferedImage>();
}
