import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class CorrelationCoefficient {
  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String sXLength = "";
    String sYLength = "";
    String sXDigits = "";
    String sYDigits = "";
    String sXptDigi = "";
    String sYptDigi = "";

    try {
      System.out.print("データxの個数:");
      sXLength = br.readLine();
      System.out.print("データyの個数:");
      sYLength = br.readLine();
      System.out.print("データxの桁数:");
      sXDigits = br.readLine();
      System.out.print("データyの桁数:");
      sYDigits = br.readLine();
      System.out.print("データxの小数点以下の桁数:");
      sXptDigi = br.readLine();
      System.out.print("データyの小数点以下の桁数:");
      sYptDigi = br.readLine();
    } catch (IOException e) {
      sXLength = "";
      sYLength = "";
      sXDigits = "";
      sYDigits = "";
      sXptDigi = "";
      sYptDigi = "";
    }

    boolean check = !sXLength.equals("") &&
                    !sYLength.equals("") &&
                    !sXDigits.equals("") &&
                    !sYDigits.equals("") &&
                    !sXptDigi.equals("") &&
                    !sYptDigi.equals("");

    int xLength = 0;
    int yLength = 0;
    int xDigits = 0;
    int yDigits = 0;
    int xptDigi = 0;
    int yptDigi = 0;

    if (check) {
      try {
        xLength = Integer.parseInt(sXLength);
        yLength = Integer.parseInt(sYLength);
        xDigits = Integer.parseInt(sXDigits);
        yDigits = Integer.parseInt(sYDigits);
      } catch (NumberFormatException e) {
        System.err.println("数値ではありません");
        System.err.println("データの大きさを10, 変量の桁数を2, 変量の小数点以下の桁数を1に設定します");
        xLength = 10;
        yLength = 10;
        xDigits = 2;
        yDigits = 2;
        xptDigi = 1;
        yptDigi = 1;
      }
    }

    double[] dataX = randoms(xLength, xDigits, xptDigi);
    double[] dataY = randoms(yLength, yDigits, yptDigi);

    System.out.println("データx : ");
    System.out.println("\t" + dataToStr(dataX));
    System.out.println("データy : ");
    System.out.println("\t" + dataToStr(dataY));

    System.out.print("相関係数 = ");
    System.out.println(correCoeff(dataX, dataY) + "...");
  }

  public static double correCoeff(double[] dataX, double[] dataY) {
    //xBar yBar x-xBar y-yBar (x-xBar)(y-yBar) (x-xBar)^2 (y-yBar)^2

    final double xBar = bar(dataX);
    final double yBar = bar(dataY);

    final double[] deviationX = deviation(dataX, xBar);
    final double[] deviationY = deviation(dataY, yBar);
    final double[] mod        = mod(deviationX, deviationY);
    final double[] squareX    = square(deviationX);
    final double[] squareY    = square(deviationY);

    final double sxy = sum(mod);
    final double sx  = sum(squareX);
    final double sy  = sum(squareY);

    final double r   = sxy / (Math.sqrt(sx) * Math.sqrt(sy));

    return r;
  }

  public static double sum(double[] data) {
    double ret = 0.0;
    for (double d : data) {
      ret += d;
    }
    return ret;
  }

  public static double bar(double[] data) {
    double ret = 0.0;
    for (double d : data) {
      ret += d;
    }
    return ret / data.length;
  }

  public static double[] deviation(double[] data, double bar) {
    double[] dev = new double[data.length];
    for (int i = 0; i < data.length; i++) {
      dev[i] = data[i] - bar;
    }
    return dev;
  }

  public static double[] mod(double[] devX, double[] devY) {
    double[] mod = new double[devX.length];
    for (int i = 0; i < devX.length; i++) {
      mod[i] = devX[i] * devY[i];
    }
    return mod;
  }

  public static double[] square(double[] data) {
    double[] ret = new double[data.length];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = data[i] * data[i];
    }

    return ret;
  }

  public static double[] randoms(int length, int n, int ptDig) {
    n = (int)(Math.pow(10, n));
    double[] ret = new double[length];
    for (int i = 0; i < length; i++) {
      ret[i] = ceil(Math.random() * n, ptDig);
    }
    return ret;
  }

  public static double ceil(double d, int dig) {
    int digit = (int)Math.pow(10D, (double)dig);
    int temp  = (int)(d * digit);

    double ret = ((double)temp) / digit;

    return ret;
  }

  public static String dataToStr(double[] data) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      if (i < (data.length - 1))
        builder.append(String.valueOf(data[i])).append(", ");
      else
        builder.append(String.valueOf(data[i]));
    }
    return new String(builder);
  }
}
