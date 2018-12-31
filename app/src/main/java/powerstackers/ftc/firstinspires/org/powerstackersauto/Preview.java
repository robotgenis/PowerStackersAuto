package powerstackers.ftc.firstinspires.org.powerstackersauto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;

import powerstackers.ftc.firstinspires.org.powerstackersauto.Paths.ConstantsLoader;
import powerstackers.ftc.firstinspires.org.powerstackersauto.Paths.PathGenerator;

public class Preview extends AppCompatActivity {

    static String data = "";
    final static double interval = 0.1;
    ImageView image;
    Bitmap updateBitmap;
    Canvas updateCanvas;
    Bitmap liveBitmap;
    Canvas liveCanvas;
    int width;
    int height;
    double[][] points;

    private int frameCount = 0;
    private CountDownTimer timer = new CountDownTimer((int)Math.round(interval*1000), 25) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            try{
                loop();
            }catch(Exception ignored){

            }
        }
    }.start();

    int[] prev;

    final int lineWidth = 10;

    //image borders
    double top = 10.0;
    double right = 10.0;
    double left = 10.0;
    double bottom = 10.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        image = findViewById(R.id.imageView);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.capture);

        width = bitmap.getWidth();
        height = bitmap.getHeight();

        points = getPoints();

        updateBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        updateCanvas = new Canvas(updateBitmap);
        updateCanvas.drawBitmap(bitmap, 0, 0, null);

        liveBitmap = Bitmap.createBitmap(updateBitmap);
        liveCanvas = new Canvas(liveBitmap);


    }

    public void loop(){
        int[] pixel = getPixel(points[frameCount][0], points[frameCount][1], width, height);

        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(lineWidth);

        updateCanvas.drawCircle(pixel[0], pixel[1], lineWidth/2, p);
        if(prev != null){
            updateCanvas.drawLine(prev[0], prev[1], pixel[0], pixel[1], p);
        }

        liveBitmap = Bitmap.createBitmap(updateBitmap);
        liveCanvas = new Canvas(liveBitmap);
        liveCanvas.save();

        liveCanvas.rotate((float) Math.toDegrees(points[frameCount][2]), pixel[0], pixel[1]);

        double robotSize = (18 * (width - (left + right))) / 144;
        int adj = (int) Math.round(robotSize / 2);

        RectF r = new RectF(pixel[0] - adj, pixel[1] - adj, pixel[0] + adj, pixel[1]+adj);
        liveCanvas.drawRect(r, p);

        liveCanvas.restore();

        image.setImageBitmap(liveBitmap);

        if(frameCount < points.length){
            frameCount++;
        }

        prev = pixel;

        timer.start();
    }

    public double[][] getPoints(){
        Trajectory trajectory = createPath();
        double[][] out = new double[(int)(trajectory.duration()/interval + 1)][4];
        for(double time = 0; time < trajectory.duration(); time += interval){
            int index = (int)Math.round(time/interval);
            Pose2d pos = trajectory.get(time);
            out[index][0] = pos.getX();
            out[index][1] = pos.getY();
            out[index][2] = pos.getHeading();
        }
        return out;
    }

    public Trajectory createPath(){
        DriveConstraints BASE_CONSTRAINTS = ConstantsLoader.getDriveConstraints();;
        int samplePos = (int) (Math.random() * 3 + 1);
        return PathGenerator.BuildPath(data, samplePos, BASE_CONSTRAINTS);
    }

    //input 0-144
    public int[] getPixel(double x, double y, double width, double height){
        double scaleWidth = width - (right + left);
        double scaleHeight = height - (top + bottom);

        double outX = (x * scaleWidth) / 144.0;
        double outY = (y * scaleHeight) / 144.0;

        outX += left;
        outY += top;

        return new int[]{(int) Math.round(outX), (int) Math.round(outY)};

    }
}
