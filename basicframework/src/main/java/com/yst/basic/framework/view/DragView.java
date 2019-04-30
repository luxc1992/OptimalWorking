package com.yst.basic.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yst.basic.framework.Const;
import com.yst.basic.framework.R;
import com.yst.basic.framework.utils.MyLog;

/**
 * 可以拖动排序的列表
 *
 * @author lixiangchao
 * @date 2017/12/5
 * @version 1.0.1
 */
public class DragView extends ListView {

    /**
     * 被拖动的影像
     */
    private ImageView dragImageView;

    /**
     * 手指在touch事件触摸时候的原始位置
     */
    private int dragSrcPosition;

    /**
     * 手指拖动列表项时候的位置
     */
    private int dragPosition;

    /**
     * 手指点击位置在当前数据项item中的位置,只有Y坐标
     */
    private int dragPoint;

    /**
     * 当前视图listview在屏幕中的位置，只有Y坐标
     */
    private int dragOffset;

    private int itemHeight;

    /**
     * 窗口管理类
     */
    private WindowManager windowManager = null;

    /**
     * 窗口参数类
     */
    private WindowManager.LayoutParams layoutParams = null;

    /**
     * 判断拖动的距离
     */
    private int scaledTouchSlop;

    /**
     * 向上滑动的边界
     */
    private int upScrollBounce;

    /**
     * 拖动的时候向下滑动的边界
     */
    private int downScrollBounce;

    /**
     * 重写于absListView
     */
    private ViewGroup itemView;
    private ImageView imageViewForDragAni;
    private Bitmap bm;

    private int mMoveY;
    private int mDownY;

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //触发移动事件的最小距离
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //获取的该touch事件的x坐标和y坐标，该坐标是相对于组件的左上角的位置
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            mDownY = y;
            //赋值手指点击时候的开始坐标item
            dragSrcPosition = dragPosition = this.pointToPosition(x, y);
            //如果点击在列表之外，也就是不允许的位置
            if (dragPosition == AdapterView.INVALID_POSITION) {
                //直接执行父类，不做任何操作
                return super.onInterceptTouchEvent(ev);
            }

            /***
             * 锁定手指touch的列表item,
             * 参数为屏幕的touch坐标减去listview左上角的坐标
             * 这里的getChildAt方法参数为相对于组件左上角坐标为00的情况
             * 故有下面的这种参数算法
             */
            itemView = (ViewGroup) this.getChildAt(dragPosition - this.getFirstVisiblePosition());
            /****
             * 说明：getX Y为touch点相对于组件左上角的距离
             * getRawX 、Y 为touch点相对于屏幕左上角的距离
             * 参考http://blog.csdn.net/love_world_/article/details/8164293
             */
            itemHeight = itemView.getHeight();

            MyLog.i("DragView", "itemHeight == " + itemHeight);
            MyLog.i("DragView", "dragPosition == " + dragPosition);
            MyLog.i("DragView", "getFirstVisiblePosition == " + this.getFirstVisiblePosition());

            //touch点的view相对于该childitem的top坐标的距离
            dragPoint = y - itemView.getTop();
            //为距离屏幕左上角的Y减去距离组件左上角的Y，其实就是
            //组件上方的view+标题栏+状态栏的Y
            dragOffset = (int) (ev.getRawY() - y);

            //拿到拖动的imageview对象
            View drager = itemView.findViewById(R.id.goods_classify_move_img);

            //判断条件为拖动touch图片是否为null和touch的位置，是否符合
            if (drager != null && x > drager.getLeft() - Const.DRAG_LEFT_MARGIN && drager.isShown()) {
                MyLog.i("DragView", "startDrag == ");
                //判断得出向上滑动和向下滑动的值
                upScrollBounce = Math.min(y - scaledTouchSlop, getHeight() / 3);
                downScrollBounce = Math.max(y + scaledTouchSlop, getHeight() * 2 / 3);
                //启用绘图缓存
                itemView.setDrawingCacheEnabled(true);
                //根据图像缓存拿到对应位图
                bm = Bitmap.createBitmap(itemView.getDrawingCache());
                startDrag(bm, y);
                //使用完cache后关闭掉
                itemView.setDrawingCacheEnabled(false);
            }
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (dragImageView != null && dragPosition != INVALID_POSITION) {
            int currentAction = ev.getAction();

            switch (currentAction) {
                case MotionEvent.ACTION_UP:
                    int upY = (int) ev.getY();
                    MyLog.i("DragView", "upY == " + upY);
                    //还有一些操作
                    stopDrag();
                    if (mMoveY != 0 && Math.abs(mMoveY - mDownY) > itemHeight / Const.DRAG_ITEM_DENOMINATOR * Const.DRAG_ITEM_GEOMETRY) {
                        MyLog.i("DragView", "onDrop");
                        onDrop(upY);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) ev.getY();
                    mMoveY = moveY;
                    MyLog.i("DragView", "moveY == " + moveY);
                    onDrag(moveY);
                    break;
                default:
                    break;
            }
            return true;
        }
        //决定了选中的效果
        return super.onTouchEvent(ev);
    }

    /**
     * 准备拖动，初始化拖动时的影像，和一些window参数
     *
     * @param bm 拖动缓存位图
     * @param y  拖动之前touch的位置
     */
    public void startDrag(Bitmap bm, int y) {
        stopDrag();

        layoutParams = new WindowManager.LayoutParams();
        //设置重力
        layoutParams.gravity = Gravity.TOP;
        //横轴坐标不变
        layoutParams.x = 0;
        /**
         *
         * y轴坐标为   视图相对于自身左上角的Y-touch点在列表项中的y
         * +视图相对于屏幕左上角的Y，=
         * 该view相对于屏幕左上角的位置
         */
        layoutParams.y = y - dragPoint + dragOffset;
        /****
         * 宽度和高度都为wrapContent
         */
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        /****
         * 设置该layout参数的一些flags参数
         */
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        //设置该window项是半透明的格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //设置没有动画
        layoutParams.windowAnimations = 0;

        //配置一个影像ImageView
        imageViewForDragAni = new ImageView(getContext());
        imageViewForDragAni.setImageBitmap(bm);
        //配置该windowManager
        windowManager = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(imageViewForDragAni, layoutParams);
        dragImageView = imageViewForDragAni;
    }

    /**
     * 停止拖动，去掉拖动时候的影像
     */
    public void stopDrag() {
        if (dragImageView != null) {
            windowManager.removeView(dragImageView);
            dragImageView = null;
        }
    }


    /**
     * 拖动方法
     *
     * @param y
     */
    public void onDrag(int y) {
        if (dragImageView != null) {
            //透明度
            layoutParams.alpha = 0.8f;
            layoutParams.y = y - this.dragPoint + this.dragOffset;
            windowManager.updateViewLayout(dragImageView, layoutParams);
        }

        //避免拖动到分割线返回-1
        int tempPosition = pointToPosition(0, y);
        if (tempPosition != INVALID_POSITION) {
            this.dragPosition = tempPosition;
        }

        //滚动,暂时不理解
        int scrollHeight = 0;
        if (y < upScrollBounce) {
            //定义向上滚动8个像素，如果可以向上滚动的话
            scrollHeight = 8;
        } else if (y > downScrollBounce) {
            //定义向下滚动8个像素，，如果可以向上滚动的话
            scrollHeight = -8;
        }

        if (scrollHeight != 0) {
            //真正滚动的方法setSelectionFromTop()
            setSelectionFromTop(dragPosition, getChildAt(dragPosition - getFirstVisiblePosition()).getTop() + scrollHeight);
        }
    }


    /***
     * 拖动放下的时候
     * param : y
     */
    public void onDrop(int y) {
        int tempPosition = this.pointToPosition(0, y);
        MyLog.i("DragView", "onDrop == tempPosition" + tempPosition);
        if (tempPosition != INVALID_POSITION) {
            this.dragPosition = tempPosition;
        }

        //超出边界处理
        if (y < getChildAt(0).getTop()) {
            //超出上边界
            dragPosition = 0;
        } else if (y > getChildAt(getChildCount() - 1).getTop()) {
            //超出下边界
            dragPosition = getAdapter().getCount() - 1;
            //
        }

        //数据交换
        if (dragPosition >= 0 && dragPosition < getAdapter().getCount()) {
            if (exchangeDataListener != null) {
                exchangeDataListener.setExchangeData(dragSrcPosition, dragPosition);
            }
            mMoveY = 0;
        }
    }

    /**
     * 交换数据的监听
     */
    private ExchangeDataListener exchangeDataListener = null;

    /**
     * 交换数据的监听接口
     */
    public interface ExchangeDataListener {
        /**
         * 交互数据
         *
         * @param from 起始位置
         * @param to 结束位置
         */
        void setExchangeData(int from, int to);
    }

    /**
     * 初始化交换数据的监听
     * @param exchangeDataListener
     */
    public void setExchangeDataListener(
            ExchangeDataListener exchangeDataListener) {
        this.exchangeDataListener = exchangeDataListener;
    }
}