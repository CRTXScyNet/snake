package org.example.Player;

import org.example.Enemy.Enemy;
import org.example.Painter.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Player {

    private static final double innerPlace = 0.9;
    private static final double exteriorBorder = (1 - innerPlace) / 2;
    static final double[] playGround = new double[]{innerPlace, exteriorBorder};

    public void moveXy(double[] direct) {
        for(int i = 0;i< xy.size();i++){
            xy.set(i,new double[]{xy.get(i)[0]-direct[0],xy.get(i)[1]-direct[1]});
        }
        for(int i = 0;i< phantomXY.size();i++){
            phantomXY.set(i,new double[]{phantomXY.get(i)[0]-direct[0],phantomXY.get(i)[1]-direct[1]});
        }
    }

    private ArrayList<double[]> xy = new ArrayList<>();
    private ArrayList<double[]> directionOfPhantomXY = new ArrayList<>();
    private ArrayList<double[]> phantomXY = new ArrayList<>();
    static double timerStat = 1000;
    static double timer = timerStat;


    private void setPhantomXY() {
        phantomXY.clear();
        for (double[] p : xy) {
            phantomXY.add(new double[]{p[0], p[1]});
        }
    }



    private Color color = new Color(Color.white.getRGB());

    boolean perviyNah = false;

    private double chance = Math.random() * 0.8 + 0.2;

    static double size = 5;
    static double stepOfSize = 2;

    public static void setStep() {
        Player.step = (int) (Player.getSize() * stepOfSize);
    }

    public static int step = (int) (Player.getSize() * stepOfSize);
    static double delayStat = 6;
    private double delayDouble = delayStat;
    private int delay = (int) delayDouble;
    private int delayCount = 0;





    public void setDelay() {
        if (delay < 100) {
            delay+= 1;
//            this.delay = (int) delayDouble;
        }
    }
    double[] tailless = new double[2];
    public double[] taillessCopy = new double[2];
    public double[] taillessPhantomCopy = new double[2];
    private int reverseCount = 100;
    private int reverse = reverseCount;
    private BufferedImage image;
    private boolean isMove = false;
    public static int snakeLength = 10;
    DecimalFormat myFormat = new DecimalFormat("#.###");

    public double[] getDirection(){
        if (directionOfPhantomXY.size()>0){
            return directionOfPhantomXY.get(0);
        }else {
            return null;
        }

    }

    public static ArrayList<Player> players = new ArrayList<>();
   static int width;
   static int height;
  public Player(BufferedImage image) {

//        xy.add(new int[]{(int)(Math.random()*imahe.getWidth()*playGround[0])+(int)(imahe.getWidth()*playGround[1]),(int)(Math.random()*imahe.getHeight()*playGround[0])+(int)(imahe.getHeight()*playGround[1])});
        this.image = image;
        Point co = getRandomPoint();
//        xy.add(new double[]{co.x,co.y});
      width = Picture.image.getWidth();
      height = Picture.image.getHeight();
      xy.add(new double[]{width/2,image.getHeight()/2});
        setPhantomXY();

        players.add(this);


        color = new Color((int) (Math.random() * 254 + 1), (int) (Math.random() * 254 + 1), (int) (Math.random() * 254 + 1) /*Color.cyan.getRGB()*/);
        for (int i = 0; i < snakeLength + 2; i++) {
            addCircle();
        }
    }

    public ArrayList<double[]> getXy() {
        return xy;
    }

    public ArrayList<double[]> getPhantomXY() {
        return phantomXY;
    }

    public void addCircle() {
        if (xy.size() < maxSize) {
            xy.add(new double[]{xy.get(xy.size() - 1)[0], xy.get(xy.size() - 1)[1]});
        }
    }

    public void grow() {
        for (int i = 0; i < 1; i++) {
            addCircle();
        }
    }

    public void reset() {

        delayDouble = delayStat;
        delay = (int) delayStat;
        delayCount = 0;
        xy.clear();
        xy.add(new double[]{width/2,height/2});
//        move(width/2,height/2);
        for (int i = 0; i < snakeLength; i++) {
            addCircle();
        }

    }

    public Point getRandomPoint() {
        int x = (int) (Math.random() * (width * playGround[0])) + (int) (width * playGround[1]);
        int y = (int) (Math.random() * (image.getHeight() * playGround[0]))+ (int) (image.getHeight() * Player.playGround[1]);
        return new Point(x, y);
    }

    public Color getColor() {
        return color;
    }

    public static double getSize() {
        return size;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isReset() {
        return reset;
    }

    private boolean reset = false;
    private double tMouse = 1;
    static double stepRad = 0.1;
    private double[] pointWatch = new double[]{width / 2, Picture.image.getHeight() / 2};

    void setRadian(Point Target) {

//        stepRad = (1 - Math.sqrt(Math.pow(Math.abs(Target.x - xy.get(0)[0]), 2) + Math.pow(Math.abs(Target.y - xy.get(0)[1]), 2)) / Math.sqrt(Math.pow(width, 2) + Math.pow(image.getHeight(), 2)))/2;
        double yTarget = Target.y - xy.get(0)[1];
        double xTarget = Target.x - xy.get(0)[0];
        double TargetRadian = 0;
        // 1143 372 900 600
        double a = Math.pow((yTarget), 2) + Math.pow((xTarget), 2);
        if (xTarget > 0) {
            TargetRadian = Math.acos((yTarget) / Math.sqrt(a));
//            System.out.println(1);
        } else if (xTarget < 0 && yTarget < 0) {
            TargetRadian = (Math.acos((xTarget) / Math.sqrt(a))) + 1.57;
//            System.out.println(2);
        } else {
            TargetRadian = (Math.asin((xTarget) / Math.sqrt(a))) + 6.28;
//            System.out.println(3);
        }

        double halfNear = (tMouse + 3.14) % 6.28;

        pointWatch[0] = (step * Math.sin(tMouse) + xy.get(0)[0]);
        pointWatch[1] = (step * Math.cos(tMouse) + xy.get(0)[1]);

//        System.out.println("pointWatch is " + pointWatch);
//        System.out.println("halfNear is " + halfNear);
//        System.out.println("tMouse is " + tMouse);
//
//
//        System.out.println("Enemy is " + Enemy + ", rad: " + EnemyRadian);

        tMouse = (tMouse + 6.28) % 6.28;
        double dif = Math.abs((TargetRadian- tMouse)%6.28);
        if(dif<3.14){

            stepRad = dif/2;
        }else {
            dif = Math.abs((Math.max(TargetRadian,tMouse)-3.14)-(Math.min(TargetRadian,tMouse)+3.14));

            stepRad = dif/2;
        }


        if (TargetRadian > tMouse && TargetRadian < halfNear) {                                // увеличение

            tMouse += stepRad;
//            System.out.print("+");
        } else if (TargetRadian > tMouse && TargetRadian > halfNear && halfNear >= 3.14) {          // уменьшение

            tMouse -= stepRad;
//System.out.println("-");
        } else if (TargetRadian > tMouse && TargetRadian > halfNear && halfNear < 3.14) {          // увеличение

            tMouse += stepRad;
//            System.out.print("+");
        } else if (TargetRadian < tMouse && TargetRadian < halfNear && halfNear >= 3.14) {                          // уменьшение

            tMouse -= stepRad;
//            System.out.print("-");
        } else if (TargetRadian < tMouse && TargetRadian > halfNear) {                          // уменьшение

            tMouse -= stepRad;
//            System.out.print("-");
        } else if (TargetRadian < tMouse && TargetRadian < halfNear) {                          // увеличение

            tMouse += stepRad;
//            System.out.print("+");
        }
//        System.out.println();
        if (tMouse > 6.28) {
            tMouse -= 6.28;
//            System.out.println("tMouse > 6.28");
        } else if (tMouse < 0) {
//            System.out.println("tMouse<0");
            tMouse += 6.28;
        }


    }
static boolean isGrow = false;
    public void moveCheck(int targetX, int targetY, boolean mouseControl, boolean targetChanged) {
       try {

//        SearchRegion once = SearchRegion.getRegion(xy.get(0).x, xy.get(0).y);
//        try {
//            if (!once.equals(region)) {
//                once.addOrRemoveSnake(this);
//                region.addOrRemoveSnake(this);
////region = once;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//            region.addOrRemoveSnake(this);
//            for(SearchRegion region1 : region.getNearbyRegions()){
//                region1.addOrRemoveSnake(this);
//            }
//        if(Math.abs(xy.get(0).x-Picture.width/2)>Picture.width*2 || Math.abs(xy.get(0).y-Picture.image.getHeight()/2)>Picture.image.getHeight()*2){
//            Point co = getRandomPoint();
//            move(co.x, co.y);
//        }


            timer -= 1;

            if (timer <= 0) {
                grow();
                if (delay > 1) {
                    delay -= 1;
                }
                timer = timerStat;

            }
            if ((int) timer == timerStat/2) {
                grow();

            }
            try {
                if (reset) {
                    reset();
                    reset = false;
                }
                perviyNah = false;
                if (delayCount < delay) {
                    delayCount++;
                    movePhantom();
                    return;
                }
                delayCount = 0;
                int xTarget;
                int yTarget;
                if (Picture.mouseControl) {
                    xTarget = Picture.xMouse;
                    yTarget = Picture.yMouse;
                } else {
                    xTarget = width / 2;
                    yTarget = image.getHeight() / 2;
                }

                double x = getXy().get(0)[0];
                double y = getXy().get(0)[1];
                setRadian(new Point(xTarget, yTarget));
//            selfStep = Math.random()*(step+ Math.sqrt(Math.pow(Math.abs(xTarget - x), 2) + Math.pow(Math.abs(yTarget - y), 2)) / 50);
                try {
                    pointWatch[0] = (step * Math.sin(tMouse) + xy.get(0)[0]);
                    pointWatch[1] = (step * Math.cos(tMouse) + xy.get(0)[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                move(pointWatch[0], pointWatch[1]);


            } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(e);
            }
        }catch (Exception e){

       }
    }




    public void movePhantom() {
        try {
//            tailless = new int[]{phantomXY.get(phantomXY.size() - 1).x, phantomXY.get(phantomXY.size() - 1).y};
            for (int i = 0; i < phantomXY.size(); i++) {
                try {
                    phantomXY.get(i)[0] += directionOfPhantomXY.get(i)[0];
                    phantomXY.get(i)[1] += directionOfPhantomXY.get(i)[1];
                } catch (IndexOutOfBoundsException e) {

                }
            }
//            if (directionOfPhantomXY.size() > 1) {
//                try {
//                    taillessCopy[0] += directionOfPhantomXY.get(directionOfPhantomXY.size() - 2)[0];
//                    taillessCopy[1] += directionOfPhantomXY.get(directionOfPhantomXY.size() - 2)[1];
//                } catch (Exception e) {
//                    taillessCopy[0] += directionOfPhantomXY.get(directionOfPhantomXY.size() - 1)[0];
//                    taillessCopy[1] += directionOfPhantomXY.get(directionOfPhantomXY.size() - 1)[1];
//                }
//            }
//            if (directionOfPhantomXY.size() > 1) {
//                taillessPhantomCopy[0] += directionOfPhantomXY.get(directionOfPhantomXY.size() - 1)[0];
//                taillessPhantomCopy[1] += directionOfPhantomXY.get(directionOfPhantomXY.size() - 1)[1];
//            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
//xy.add(0,new int[]{x,y});
//xy.remove(xy.size()-1);
    }

    public void move(double x, double y) {
        directionOfPhantomXY.clear();
        setPhantomXY();
        try {

//
//            if (tailless[0] != 0 && xy.size()<2) {
//            if (Math.abs((xy.get(xy.size() - 1)[0]) - tailless[0]) > step || Math.abs((xy.get(xy.size() - 1)[1] - tailless[1])) > step) {
//
//                directionOfPhantomXY.add(new double[]{0, 0});
//
//            } else {
//
//                directionOfPhantomXY.add(new double[]{((xy.get(xy.size() - 1)[0]) - tailless[0]) / (delay + 1), (xy.get(xy.size() - 1)[1] - tailless[1]) / (delay + 1)});
//            }
//            taillessPhantomCopy = new double[]{tailless[0], tailless[1]};
//            }

//            tailless = new double[]{(xy.get(xy.size() - 1)[0]), xy.get(xy.size() - 1)[1]};
            for (int i = xy.size() - 1; i >= 0; i--) {
                try {
                    if (i == xy.size() - 1) {
//                        if (Math.abs(((double) xy.get(i).x - phantomXY.get(i)[0])) > step || Math.abs(((double) xy.get(i).y - phantomXY.get(i)[1])) > step) {
////                            directionOfPhantomXY.add(0, new double[]{0, 0});
////                            phantomXY.get(i)[0] = xy.get(i).x;
////                            phantomXY.get(i)[1] = xy.get(i).y;
//                        } else {

                        phantomXY.get(i)[0] = xy.get(i)[0];
                        phantomXY.get(i)[1] = xy.get(i)[1];
//                        }
                    }
                    xy.get(i)[0] = xy.get(i - 1)[0];
                    xy.get(i)[1] = xy.get(i - 1)[1];
                    directionOfPhantomXY.add(0, new double[]{((double) xy.get(i)[0] - phantomXY.get(i)[0]) / (delay + 1), ((double) xy.get(i)[1] - phantomXY.get(i)[1]) / (delay + 1)});


                } catch (IndexOutOfBoundsException e) {

                }
            }
            phantomXY.get(0)[0] = xy.get(0)[0];
            phantomXY.get(0)[1] = xy.get(0)[1];
            xy.get(0)[0] = x;
            xy.get(0)[1] = y;
//            if (Math.abs(((double) xy.get(0).x - phantomXY.get(0)[0])) > step || Math.abs(((double) xy.get(0).y - phantomXY.get(0)[1])) > step) {
//                directionOfPhantomXY.add(0, new double[]{0, 0});
//
////                tailless[0] = phantomXY.get(phantomXY.size() - 1)[0];
////                tailless[1] = phantomXY.get(phantomXY.size() - 1)[1];
////                directionOfPhantomXY.add(new double[]{0,directionOfPhantomXY.get(directionOfPhantomXY.size()-1)[1]});
//            } else {
            directionOfPhantomXY.add(0, new double[]{((double) xy.get(0)[0] - phantomXY.get(0)[0]) / (delay + 1), ((double) xy.get(0)[1] - phantomXY.get(0)[1]) / (delay + 1)});

//                taillessCopy[0] = phantomXY.get(phantomXY.size() - 1)[0] - xy.get(xy.size() - 1).x+phantomXY.get(phantomXY.size() - 1)[0];
//                taillessCopy[1] = phantomXY.get(phantomXY.size() - 1)[1] - xy.get(xy.size() - 1).y+ phantomXY.get(phantomXY.size() - 1)[1];
//                directionOfPhantomXY.add(new double[]{directionOfPhantomXY.get(directionOfPhantomXY.size()-1)[0],directionOfPhantomXY.get(directionOfPhantomXY.size()-1)[1]});

//            }
//            if(xy.size()>1){
//
//            taillessCopy = new double[]{phantomXY.get(phantomXY.size() - 1)[0], phantomXY.get(phantomXY.size() - 1)[1]};
//            }


        } catch (Exception e) {
            e.printStackTrace();

        }
//xy.add(0,new int[]{x,y});
//xy.remove(xy.size()-1);
    }



    static int maxSize = 150;

}
