/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Lucaso
 */
public class Maze
{

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    private static int[][] mazeMap;
    private static String mapFileName;
    private static BufferedImage bi;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        mapFileName = "big2.png";
        bi = ImageIO.read(new FileInputStream(mapFileName));
        ArrayList<Point> starts = new ArrayList<>();
        mazeMap = new int[bi.getWidth()][bi.getHeight()];
        System.out.println("map size: " + mazeMap.length + "x" + mazeMap[0].length);
        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                mazeMap[x][y] = ((bi.getRGB(x, y) == -1) ? 0 : 1);
                if ((x == 0 || y == 0 || x == mazeMap.length - 1 || y == mazeMap[0].length - 1) && mazeMap[x][y] == 0) {
                 starts.add(new Point(x, y));
                 }
            }
        }
        //starts.add(new Point(437, 453)); //for big8
        for (Point start : starts) {
            System.out.println("start: " + start.x + "," + start.y);
        }

        findPath(starts.get(0).x, starts.get(0).y);
        //printMap(starts.get(0));
        printMapToFile(mapFileName);
    }

    private static int findPath(int x, int y) {
        if (isInMap(x, y)) {
            if (mazeMap[x][y] == 1) //zed
            {
                return -1; //zed
            }
            if (mazeMap[x][y] == 2)//pouzito
            {
                return -2;
            }
            if (mazeMap[x][y] == 3)//spatna cesta
            {
                return -3;
            }
            if (mazeMap[x][y] == 7)//uz pouzita cesta
            {
                return -7;
            }
            if (mazeMap[x][y] == 0) { //volna
                mazeMap[x][y] = 2; //pouzita
                boolean goodWay = false;
                if (findPath(x - 1, y) >= 0)//doleva  
                {
                    goodWay = true;
                }
                if (findPath(x + 1, y) >= 0) //doprava
                {
                    goodWay = true;
                }
                if (findPath(x, y - 1) >= 0) //nahoru
                {
                    goodWay = true;
                }
                if (findPath(x, y + 1) >= 0) //dolu
                {
                    goodWay = true;
                }
                if (goodWay) {
                    mazeMap[x][y] = 7; //dobra cesta
                    return 7;
                } else {
                    mazeMap[x][y] = 3;
                    return -3; //spatna cesta
                }
            }
        }
        return 0; //mimo pole / cil
    }

    static void printMap(Point start) {
        for (int y = 0; y < mazeMap[0].length; y++) {
            for (int x = 0; x < mazeMap.length; x++) {
                /*if (x == start.x && y == start.y) {
                 System.out.print(mazeMap[x][y]);
                 } else*/ {
                    System.out.print(mazeMap[x][y]);
                }
            }
            System.out.println();
        }
    }

    static void printMapToFile(String fileName) throws IOException {
        BufferedImage biw = new BufferedImage(mazeMap.length, mazeMap[0].length, BufferedImage.TYPE_INT_RGB);
        biw.getGraphics().drawImage(bi, 0, 0, null);
        for (int y = 0; y < mazeMap[0].length; y++) {
            for (int x = 0; x < mazeMap.length; x++) {
                if (mazeMap[x][y] == 7) {
                    biw.setRGB(x, y, 0xff0000);
                }
            }
        }
        ImageIO.write(biw, "png", new File("finish " + fileName));
    }

    static boolean isInMap(int x, int y) {
        return x < mazeMap.length && y < mazeMap[0].length && x >= 0 && y >= 0;
    }
}
