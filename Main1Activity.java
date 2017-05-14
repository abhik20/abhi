package com.example.mahe.numberplate1;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;


public class Main1Activity extends AppCompatActivity {

    public static final int REQUEST_CAPTURE = 1;
    ImageView result_photo;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        result_photo = (ImageView) findViewById(R.id.imageView);


        Button click = (Button) findViewById(R.id.Bcapture);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CAPTURE);

            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            if (OpenCVLoader.initDebug()) {
                Log.i(TAG, "Opencv loaded successfully");
            } else {
                Log.i(TAG, "Opencv not loaded");
            }

            //Mat img = new Mat();
           // Utils.bitmapToMat(photo, img);
            Mat img = new Mat(photo.getWidth(), photo.getHeight(), CvType.CV_8UC1);
            Utils.bitmapToMat(photo, img);



            Mat img1 = new Mat();

            img.copyTo(img1);





           //Imgproc.GaussianBlur(tmp1, tmp, new Size(1,1), 3);

           /* Imgproc.cvtColor(tmp1, tmp, Imgproc.COLOR_BGR2GRAY);//converting to grayscale
            Mat eq = new Mat();

            Imgproc.equalizeHist(tmp,eq);//enhancing grayscale

            Mat blur1 = new Mat();
            Mat blur2 = new Mat();

            Imgproc.GaussianBlur(eq, blur1, new Size(15,15), 0);
            Imgproc.GaussianBlur(eq, blur2, new Size(25,25), 0);

            Mat DoG = new Mat();
            Core.absdiff(blur1,blur2,DoG);//performing DOG

            Core.multiply(DoG, new Scalar(100), DoG);

            Imgproc.threshold(DoG, DoG, 127, 255, Imgproc.THRESH_BINARY_INV);//thresholding


           // Imgproc.Canny(tmp, tmp, 10, 100);

           // Imgproc.threshold(tmp,tmp,127,255,0);

           // Imgproc.adaptiveThreshold(tmp, tmp, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 1);

            //Core.bitwise_not(tmp, tmp);

            //Imgproc.dilate(tmp, tmp, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1)));

            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(DoG, contours, hierarchy, Imgproc.RETR_EXTERNAL, 1);

            double max=0;
            int c=0;

            for(int i=0; i<contours.size(); i++)
            {
              double carea = Imgproc.contourArea(contours.get(i));
              if (carea>max)
              {
                 max=carea;
                 c=i;

              }

           }
            tmp1.copyTo(DoG);
           Imgproc.drawContours(tmp1, contours, c, new Scalar(0,255,0), 3);//drawing contour using maxArea*/




            Mat Gray = new Mat();
           // Mat Canny = new Mat();
            Mat Gauss = new Mat();
            Mat Sobel = new Mat();
            Mat Threshold = new Mat();
            Mat Morphex = new Mat();
            Mat mask = new Mat(img.cols(), img.rows(), CvType.CV_8UC1, new Scalar(255,255,255) );
            Scalar zeos = new Scalar(0, 0, 0);
            Imgproc.cvtColor(img, Gray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.GaussianBlur(Gray, Gauss, new Size(5,5), 0);
            Imgproc.Sobel(Gauss, Sobel, -1, 1, 0);
            Imgproc.threshold(Sobel, Threshold, 0, 255, Imgproc.THRESH_OTSU);
            Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(16, 4));
            Imgproc.morphologyEx(Threshold, Morphex, Imgproc.MORPH_CLOSE, element);
            ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
            Mat hierarchy = new Mat();
            Mat crop = new Mat();
            Mat output = new Mat();


            Imgproc.findContours(Morphex, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

            for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {

                Rect brect = Imgproc.boundingRect(contours.get(contourIdx));



                //Imgproc.drawContours(img1, contours, contourIdx, new Scalar(0,255,0), 0);

               // double ratio = brect.width/brect.height;

                double area = brect.width*brect.height;
                //double ratio = brect.width/brect.height;

                if ( area <= 20500 && area >= 2500 ){
                    Rect ROI = new Rect(brect.tl(), brect.br());
                    crop = new Mat(img1, ROI);
                    crop.copyTo(output);
                }


                //Core.rectangle(img1, brect.tl(), brect.br(), new Scalar(255, 0, 0, 255), 3, 8, 0);
               // Core.rectangle(img1,  new Point(brect.x, brect.y), new Point(brect.x + brect.width, brect.y + brect.height), new Scalar(255, 0, 0, 255), 3, 8, 0);



            }
            /*Mat gray = new Mat();
            Imgproc.cvtColor(output, gray, Imgproc.COLOR_BGR2GRAY);

            Imgproc.threshold(gray, gray, 0, 255, Imgproc.THRESH_OTSU);*/

            Mat gr = new Mat();
            Imgproc.cvtColor(output, gr, Imgproc.COLOR_BGR2GRAY);

            Mat th = new Mat();
            Imgproc.threshold(gr, th, 1, 255, Imgproc.THRESH_BINARY);

            ArrayList<MatOfPoint> cont = new ArrayList<MatOfPoint>();
            Mat hier = new Mat();

            Mat cr = new Mat();

            Mat output1 = new Mat();

            Imgproc.findContours(th, cont, hier, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            for (int i = 0; i < cont.size(); i++) {

            Rect brect1 = Imgproc.boundingRect(cont.get(i));

                Rect r = new Rect(brect1.tl(), brect1.br());
                cr = new Mat(output, r);
                cr.copyTo(output1);

            }
            Mat gray = new Mat();
            Imgproc.cvtColor(output1, gray, Imgproc.COLOR_BGR2GRAY);

            Imgproc.threshold(gray, gray, 0, 255, Imgproc.THRESH_OTSU);












           /* Core.bitwise_not(img1,img1);
            Mat output = new Mat(mask.cols(), mask.rows(), CvType.CV_8UC1);
            Core.bitwise_and(img1, img1, output, mask);*/
           // Core.bitwise_not(output,output);

           /* Mat grey = new Mat();
            Imgproc.cvtColor(output, grey, Imgproc.COLOR_RGB2GRAY);
            Mat Canny = new Mat();
            Imgproc.Canny(grey, Canny, 100, 200);
            Mat lines = new Mat();
            int threshold=50;

            Imgproc.HoughLinesP(Canny, lines, 1, Math.PI/180, threshold, 60, 10 );
            boolean	[]	include=new	boolean[lines.cols()];
            double	maxTop=Canny.rows();
            double	maxBottom=0;
            double	maxRight=0;
            double	maxLeft=Canny.cols();
            int	leftLine=0; int	rightLine=0;
            int	topLine=0; int	bottomLine=0;
            ArrayList<org.opencv.core.Point> points= new ArrayList<org.opencv.core.Point>();
            for	(int i = 0;	i <	lines.cols(); i++)
            {
                double[] line =	lines.get(0,i);
                double	xStart = line[0], xEnd = line[2];
                if(xStart<maxLeft && !include[i])
                {
                    maxLeft=xStart;
                    leftLine=i;
            }		if(xEnd<maxLeft && !include[i])
                    {
                         maxLeft=xEnd;
                        leftLine=i;
                    }
            }
            include[leftLine]=true;
            double[] line =	lines.get(0, leftLine);
            double	xStartleftLine = line[0], yStartleftLine = line[1], xEndleftLine = line[2], yEndleftLine = line[3];
            org.opencv.core.Point lineStartleftLine	= new org.opencv.core.Point(xStartleftLine,	yStartleftLine);
            org.opencv.core.Point lineEndleftLine =	new	org.opencv.core.Point(xEndleftLine,	yEndleftLine);
            points.add(lineStartleftLine);
            points.add(lineEndleftLine);
            for	(int i = 0; i <	lines.cols(); i++)
            {
                line = lines.get(0,i);
                double	xStart = line[0], xEnd = line[2];
                if(xStart>maxRight && !include[i])
                {
                    maxRight=xStart;
                    rightLine=i;
                }
                if(xEnd>maxRight && !include[i])
                 {
                     maxRight=xEnd;
                     rightLine=i;
                 }
            }
            include[rightLine]=true;
            line = lines.get(0,rightLine);
            double	xStartRightLine	= line[0], yStartRightLine = line[1], xEndRightLine	= line[2], yEndRightLine = line[3];						org.opencv.core.Point	lineStartRightLine	=	new	org.opencv.core.Point(xStartRightLine,	yStartRightLine); org.opencv.core.Point	lineEndRightLine	=	new	org.opencv.core.Point(xEndRightLine,	yEndRightLine);
            points.add(lineStartRightLine);
            points.add(lineEndRightLine);
            for	(int i = 0;	i < lines.cols(); i++)
            {
                line = lines.get(0,i);
                double	yStart = line[1],yEnd =	line[3];
                if(yStart<maxTop	&&	!include[i])
                {
                    maxTop=yStart;
                    topLine=i;
                }
                if(yEnd<maxTop	&&	!include[i])
                {
                    maxTop=yEnd;
                    topLine=i;
                }
            }
            include[topLine]=true;
            line = lines.get(0,topLine);
            double	xStartTopLine = line[0], yStartTopLine = line[1], xEndTopLine = line[2], yEndTopLine = line[3];
            org.opencv.core.Point lineStartTopLine = new org.opencv.core.Point(xStartTopLine, yStartTopLine);
            org.opencv.core.Point lineEndTopLine = new org.opencv.core.Point(xEndTopLine, yEndTopLine);
            points.add(lineStartTopLine);
            points.add(lineEndTopLine);
            for	(int i = 0; i <	lines.cols(); i++)
            {
                line = lines.get(0,i);
                double	yStart	= line[1],yEnd = line[3];
                if(yStart>maxBottom	&& !include[i])
                {
                    maxBottom=yStart;
                    bottomLine=i;
                }
                if(yEnd>maxBottom && !include[i])
                {
                    maxBottom=yEnd;
                    bottomLine=i;
                }
            }
            include[bottomLine]=true;
            line = lines.get(0, bottomLine);
            double	xStartBottomLine = line[0], yStartBottomLine = line[1], xEndBottomLine = line[2], yEndBottomLine = line[3];
            org.opencv.core.Point lineStartBottomLine =	new	org.opencv.core.Point(xStartBottomLine,	yStartBottomLine);
            org.opencv.core.Point lineEndBottomLine	= new org.opencv.core.Point(xEndBottomLine,	yEndBottomLine);
            points.add(lineStartBottomLine);
            points.add(lineEndBottomLine);

            MatOfPoint2f mat=new MatOfPoint2f();
            mat.fromList(points);

            RotatedRect rect= Imgproc.minAreaRect(mat);

            org.opencv.core.Point rect_points[]=new	org.opencv.core.Point[4];
            rect.points(rect_points);

            Mat	correctedImage = new Mat(output.rows(),output.cols(),output.type());

            Mat	srcPoints = Converters.vector_Point2f_to_Mat(Arrays.asList(rect_points));
            Mat	destPoints=Converters.vector_Point2f_to_Mat(Arrays.asList(new org.opencv.core.Point[]{ new	org.opencv.core.Point(0, correctedImage.rows()), new	org.opencv.core.Point(0,0), new	org.opencv.core.Point(correctedImage.cols(), 0), new org.opencv.core.Point(correctedImage.cols(), correctedImage.rows()) }));

            Mat	transformation=Imgproc.getPerspectiveTransform(srcPoints, destPoints);


            Imgproc.warpPerspective(output, correctedImage, transformation, correctedImage.size());*/


                Bitmap bmp = Bitmap.createBitmap(gray.cols(), gray.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(gray , bmp);
                Intent intent = new Intent(Main1Activity.this, Second1Activity.class);
                intent.putExtra("BitmapImage", bmp);
                startActivity(intent);

        }
    }
}

