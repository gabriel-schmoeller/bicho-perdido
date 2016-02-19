package bichoperdido.business.cor;

import bichoperdido.business.cor.domain.CieLab;
import bichoperdido.business.cor.domain.Rgb;
import org.springframework.stereotype.Component;

/**
 * @author Gabriel.
 */
@Component
public class CorUtils {

    public double deltaE(CieLab a, CieLab b) {
        return Math.sqrt(
                Math.pow(a.getL() - b.getL(), 2)
                + Math.pow(a.getA() - b.getA(), 2)
                + Math.pow(a.getB() - b.getB(), 2));
    }

    public CieLab rgb2lab(int R, int G, int B) {
        double var_R = ( (double) R / 255.0 );
        double var_G = ( (double) G / 255.0 );
        double var_B = ( (double) B / 255.0 );

        if ( var_R > 0.04045 ) var_R = Math.pow(( ( var_R + 0.055 ) / 1.055 ), 2.4);
        else var_R = var_R / 12.92;
        if ( var_G > 0.04045 ) var_G = Math.pow(( ( var_G + 0.055 ) / 1.055 ), 2.4);
        else var_G = var_G / 12.92;
        if ( var_B > 0.04045 ) var_B = Math.pow(( ( var_B + 0.055 ) / 1.055 ), 2.4);
        else var_B = var_B / 12.92;

        var_R = var_R * 100;
        var_G = var_G * 100;
        var_B = var_B * 100;

        double X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805;
        double Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722;
        double Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505;

        double var_X = X / 95.047;
        double var_Y = Y / 100.000;
        double var_Z = Z / 108.883;

        if ( var_X > 0.008856 ) var_X = Math.pow(var_X, ( 1.0/3.0 ));
        else var_X = ( 7.787 * var_X ) + ( 16.0 / 116.0 );
        if ( var_Y > 0.008856 ) var_Y = Math.pow(var_Y, ( 1.0/3.0 ));
        else var_Y = ( 7.787 * var_Y ) + ( 16.0 / 116.0 );
        if ( var_Z > 0.008856 ) var_Z = Math.pow(var_Z, ( 1.0/3.0 ));
        else var_Z = ( 7.787 * var_Z ) + ( 16.0 / 116.0 );

        double cieL = ( 116 * var_Y ) - 16;
        double cieA = 500 * ( var_X - var_Y );
        double cieB = 200 * ( var_Y - var_Z );

        return new CieLab(cieL, cieA, cieB);
    }

    public Rgb lab2rgb(double l, double a, double b) {
        double var_Y = ( l + 16 ) / 116;
        double var_X = a / 500 + var_Y;
        double var_Z = var_Y - b / 200;

        if ( Math.pow(var_Y, 3) > 0.008856 ) var_Y = Math.pow(var_Y, 3);
        else var_Y = ( var_Y - 16 / 116 ) / 7.787;
        if ( Math.pow(var_X, 3) > 0.008856 ) var_X = Math.pow(var_X, 3);
        else var_X = ( var_X - 16 / 116 ) / 7.787;
        if ( Math.pow(var_Z, 3) > 0.008856 ) var_Z = Math.pow(var_Z, 3);
        else var_Z = ( var_Z - 16 / 116 ) / 7.787;

        double X = 95.047 * var_X;
        double Y = 100.000 * var_Y;
        double Z = 108.883 * var_Z;

        var_X = X / 100;
        var_Y = Y / 100;
        var_Z = Z / 100;

        double var_R = var_X *  3.2406 + var_Y * -1.5372 + var_Z * -0.4986;
        double var_G = var_X * -0.9689 + var_Y *  1.8758 + var_Z *  0.0415;
        double var_B = var_X *  0.0557 + var_Y * -0.2040 + var_Z *  1.0570;

        if ( var_R > 0.0031308 ) var_R = 1.055 * ( Math.pow(var_R, ( 1 / 2.4 )) ) - 0.055;
        else var_R = 12.92 * var_R;
        if ( var_G > 0.0031308 ) var_G = 1.055 * ( Math.pow(var_G, ( 1 / 2.4 )) ) - 0.055;
        else var_G = 12.92 * var_G;
        if ( var_B > 0.0031308 ) var_B = 1.055 * ( Math.pow(var_B, ( 1 / 2.4 )) ) - 0.055;
        else var_B = 12.92 * var_B;

        int R = (int) Math.round(var_R * 255);
        int G = (int) Math.round(var_G * 255);
        int B = (int) Math.round(var_B * 255);

        return new Rgb(R, G, B);
    }


    public String rgb2Hex(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
