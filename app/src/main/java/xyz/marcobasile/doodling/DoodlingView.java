package xyz.marcobasile.doodling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class DoodlingView extends View {

    private static final float STROKE_WIDTH = 12.f;
    private static final float CIRLE_STROKE_WIDTH = 4f;
    private static final float CIRCLE_RADIUS = 30.f;

    private OnPathStackChangeCallback onPathStackChangeCallback;
    private OnTouchDragCallback onTouchDragCallback;

    private static ArrayDeque<Path> pathStack = new ArrayDeque<>();
    private static Map<Path,Paint> paintMap = new HashMap<>();

    public int width, height;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint canvasPaint, bitmapPaint, circlePaint;
    private Path path, circlePath;

    private float x, y;
    private Float stroke = Float.valueOf(STROKE_WIDTH);
    private boolean drawPrevious = false;


    public DoodlingView(Context context) {
        super(context);
        setupView();
        setupCanvas();
    }

    public DoodlingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupView();
        setupCanvas();
    }

    public DoodlingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
        setupCanvas();
    }

    @Override
    protected void onSizeChanged(int width, int height, int prevWidth, int prevHeight) {
        super.onSizeChanged(width, height, prevWidth, prevHeight);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        if (drawPrevious) {

            drawPrevious = false;
            pathStack.push(new Path()); // e' un path dummy
            undo();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        canvas.drawPath(path, canvasPaint);
        canvas.drawPath(circlePath, circlePaint);
    }


    private void touchDown(float _x, float _y) {
        
        path.reset();
        path.moveTo(_x, _y);
        x = _x;
        y = _y;

        if (onTouchDragCallback != null) {
            onTouchDragCallback.onDragStart();
        }
    }

    private void touchDrag(float _x, float _y) {
        x = _x;
        y = _y;

        path.quadTo(x, y, (_x + x)/2, (_y + y)/2);

        circlePath.reset();
        circlePath.addCircle(x, y, CIRCLE_RADIUS, Path.Direction.CW);
    }

    private void touchUp() {

        path.lineTo(x, y);
        circlePath.reset();

        canvas.drawPath(path, canvasPaint);
        addPath(path);
        // kill this so we don't double draw
        path.reset();

        if (onTouchDragCallback != null) {
            onTouchDragCallback.onDragEnd();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float _x = event.getX();
        float _y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchDown(_x, _y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchDrag(_x, _y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

    private void addPath(Path p) {

        pathStack.push(p);
        paintMap.put(p, canvasPaint);

        canvasPaint = new Paint(canvasPaint);

        path = new Path();

        if (onPathStackChangeCallback != null) {
            onPathStackChangeCallback.onDataChange(pathStack.isEmpty());
        }
    }

    public void undo() {

        pathStack.poll();

        canvas.drawColor(Color.WHITE);
        pathStack.forEach(path -> {
            canvas.drawPath(path, paintMap.getOrDefault(path, canvasPaint));
        });
        invalidate();

        if (onPathStackChangeCallback != null) {
            onPathStackChangeCallback.onDataChange(pathStack.isEmpty());
        }
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setStrokeColor(int color) {
        canvasPaint.setColor(color);
    }

    public void setStrokeWidth(int factor) {
        stroke = STROKE_WIDTH * (float) factor;
        canvasPaint.setStrokeWidth(stroke);
    }

    public void setOnPathStackChangeCallback(OnPathStackChangeCallback cb) {
        this.onPathStackChangeCallback = cb;
    }

    public void setOnTouchDragCallback(OnTouchDragCallback cb) {
        this.onTouchDragCallback = cb;
    }

    private void setupCanvas() {

        canvasPaint = new Paint();
        canvasPaint.setAntiAlias(true);
        canvasPaint.setDither(true);
        canvasPaint.setStyle(Paint.Style.STROKE);
        canvasPaint.setStrokeJoin(Paint.Join.ROUND);
        canvasPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint.setStrokeWidth(STROKE_WIDTH);
    }

    private void setupView() {

        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        path = new Path();

        circlePath = new Path();

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(CIRLE_STROKE_WIDTH);
    }

    public interface OnPathStackChangeCallback {

        void onDataChange(boolean isEmpty);
    }

    public interface OnTouchDragCallback {

        void onDragStart();

        void onDragEnd();
    }

    public void clearData() {

        DoodlingView.pathStack.clear();
        DoodlingView.paintMap.clear();
    }

    public void setupOldDrawing() {

        drawPrevious = true;
    }
}
