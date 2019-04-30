package com.yst.onecity.view.customcalend;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.onecity.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 自定义日历
 *
 * @author chejianqi
 * @version 1.0.1
 * @date 2018/5/18
 */
public class CalendarActivity extends LinearLayout {

    NewCalenderListener newCalenderListener;
    private ImageView yearPrev;
    private ImageView yearNext;
    private ImageView monthPrev;
    private ImageView monthNext;
    private TextView yearTv;
    private TextView monthTv;
    private GridView calendarDay;
    private Calendar caData = Calendar.getInstance();
    private Calendar caData1 = Calendar.getInstance();
    private Calendar currentDate = Calendar.getInstance();
    private String startData;
    private String endData;
    private boolean flag = true;
    private boolean aFlag = false;

    public CalendarActivity(Context context) {
        super(context);
        initControl(context);
    }

    public CalendarActivity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public CalendarActivity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    public static String replaceAllMonth(String month) {
        String newStr = month.replaceAll("^(0+)", "");
        return newStr;
    }

    /**
     * 根据 年、月 获取对应的月份 的 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 日期加减。
     *
     * @param base 基础日期
     * @param days 增加天数(减天数则用负数)
     * @return 计算结果
     */
    public static Date datePlus(Date base, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public void initControl(Context context) {
        bindControl(context);
        bindContRolevent(context);
        renderCalender(getContext());

    }

    private void bindContRolevent(final Context context) {
        yearPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中的年份
                int selectYear = caData.get(Calendar.YEAR);
                //今年
                int totalYear = currentDate.get(Calendar.YEAR);
                if (selectYear > totalYear) {
                    caData.add(Calendar.YEAR, -1);
                    renderCalender(context);
                }
            }
        });
        yearNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中的年份
                int selectYear = caData.get(Calendar.YEAR);
                //今年
                int totalYear = currentDate.get(Calendar.YEAR);
                caData.add(Calendar.YEAR, 1);
                renderCalender(context);
            }
        });
        monthPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中的年份
                int selectYear = caData.get(Calendar.YEAR);
                //今年
                int totalYear = currentDate.get(Calendar.YEAR);

                //选中的月份
                int selectMonth = caData.get(Calendar.MONTH) + 1;
                //当前月份
                int totalMonth = currentDate.get(Calendar.MONTH) + 1;
                if (selectYear > totalYear) {
                    caData.add(Calendar.MONTH, -1);
                    renderCalender(context);
                } else if (selectYear == totalYear) {
                    if (selectMonth > totalMonth) {
                        caData.add(Calendar.MONTH, -1);
                        renderCalender(context);
                    }
                }
            }
        });
        monthNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //选中的年份
                int selectYear = caData.get(Calendar.YEAR);
                //今年
                int totalYear = currentDate.get(Calendar.YEAR);

                //选中的月份
                int selectMonth = caData.get(Calendar.MONTH) + 1;
                //当前月份
                int totalMonth = currentDate.get(Calendar.MONTH) + 1;
                caData.add(Calendar.MONTH, 1);
                renderCalender(context);

            }
        });
    }

    /**
     * 日历绘制
     *
     * @param context context
     */
    private void renderCalender(final Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
        monthTv.setText(replaceAllMonth(simpleDateFormat.format(caData.getTime())));
        yearTv.setText(simpleDateFormat2.format(caData.getTime()));
        final ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) caData.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int prevDays = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH - 1) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);
        int maxCellsCount = 6 * 7;
        while (cells.size() < maxCellsCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendarDay.setAdapter(new CalendarAdapter(context, cells));
    }

    private void bindControl(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_calend, this, false);
        yearPrev = (ImageView) view.findViewById(R.id.year_prev);
        yearNext = (ImageView) view.findViewById(R.id.year_next);
        monthPrev = (ImageView) view.findViewById(R.id.month_prev);
        monthNext = (ImageView) view.findViewById(R.id.month_next);
        yearTv = (TextView) view.findViewById(R.id.year_tv);
        monthTv = (TextView) view.findViewById(R.id.month_tv);
        calendarDay = (GridView) view.findViewById(R.id.calendar_day);
        addView(view);
    }

    public void setNewCalenderListener(NewCalenderListener newCalenderListener, String data, boolean flag) {
        this.newCalenderListener = newCalenderListener;
        if (flag) {
            this.startData = data;
        } else {
            String[] split = data.split("@@@");
            this.startData = split[0];
            try {
                if (!TextUtils.isEmpty(split[1])) {
                    this.endData = split[1];
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }


        }
        this.aFlag = flag;
        if (flag) {
            if (!TextUtils.isEmpty(data)) {
                String[] split = data.split("-");
                int currentMonth = currentDate.get(Calendar.MONTH) + 1;
                int currentYear = currentDate.get(Calendar.YEAR);
                if (currentYear != Integer.valueOf(split[0])) {
                    caData.add(Calendar.YEAR, Integer.valueOf(split[0]) - currentYear);
                    renderCalender(getContext());
                }
                if (currentMonth != Integer.valueOf(split[1])) {
                    caData.add(Calendar.MONTH, Integer.valueOf(split[1]) - currentMonth);
                    renderCalender(getContext());
                }
            }
        } else {
            if (!TextUtils.isEmpty(endData)) {
                String[] split = endData.split("-");
                int currentMonth = currentDate.get(Calendar.MONTH) + 1;
                int currentYear = currentDate.get(Calendar.YEAR);
                if (currentYear != Integer.valueOf(split[0])) {
                    caData.add(Calendar.YEAR, Integer.valueOf(split[0]) - currentYear);
                    renderCalender(getContext());
                }
                if (currentMonth != Integer.valueOf(split[1])) {
                    caData.add(Calendar.MONTH, Integer.valueOf(split[1]) - currentMonth);
                    renderCalender(getContext());
                }
            }else {
                String[] split = startData.split("-");
                int currentMonth = currentDate.get(Calendar.MONTH) + 1;
                int currentYear = currentDate.get(Calendar.YEAR);
                if (currentYear != Integer.valueOf(split[0])) {
                    caData.add(Calendar.YEAR, Integer.valueOf(split[0]) - currentYear);
                    renderCalender(getContext());
                }
                if (currentMonth != Integer.valueOf(split[1])) {
                    caData.add(Calendar.MONTH, Integer.valueOf(split[1]) - currentMonth);
                    renderCalender(getContext());
                }
            }
        }

    }

    /**
     * NewCalenderListener
     */
    public interface NewCalenderListener {
        /**
         * onItemListener
         *
         * @param date dta
         */
        void onItemListener(Date date);
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        LayoutInflater layoutInflater;
        private Context mContext;
        private int startIndex;
        private int endIndex;
        /**
         * 当前选中的日期(几号，只允许选中本月日历的本月日期)
         */

        private Date mCurrentSelectDate;
        /**
         * 当前日期(几号，只允许选中本月日历的本月日期)
         */
        private Date mCurrentDate;
        private ArrayList<Date> mDates = new ArrayList<>();

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> datas) {
            super(context, R.layout.calendar_item, datas);
            this.mContext = context;
            this.mDates = datas;
            layoutInflater = LayoutInflater.from(context);
            initData();
            mCurrentDate = new Date();
            mCurrentSelectDate = new Date();
        }

        /**
         * 初始化日期显示所需要数据
         */
        private void initData() {
            //取一个居中值  当前选中的月
            Date date = mDates.get(15);
            //选中月的总天数
            int daysOfMonth = getDaysByYearMonth(date.getYear() + 1900, date.getMonth() + 1);

            for (int i = 0; i < mDates.size(); i++) {
                //判断是6号6
                if (20 == mDates.get(i).getDate()) {
                    startIndex = i - 19;
                    endIndex = daysOfMonth + startIndex - 1;
                }
            }
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mViewHolder = null;
            final Date date = getItem(position);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.calendar_item, parent, false);
                mViewHolder = new ViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }

            int day = date.getDate();
            if (flag) {
                flag = false;
                if (aFlag) {
                    if (!TextUtils.isEmpty(startData)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            mCurrentSelectDate = sdf.parse(startData);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(endData)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            mCurrentSelectDate = sdf.parse(endData);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            mCurrentSelectDate = sdf.parse(startData);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
            mViewHolder.tvDate.setText(String.valueOf(day));
            mViewHolder.tvDate.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));

            if (position < startIndex) {
                mViewHolder.tvDate.setTextColor(Color.parseColor("#FF968B8B"));
            } else if (startIndex <= position && position <= endIndex) {
                mViewHolder.tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                //判断是否为选中的
                if (aFlag) {
                    if (mCurrentDate.getMonth() == date.getMonth() && mCurrentDate.getYear() == date.getYear()) {
                        // 如果在当前年，当前月，比当前日期小的话
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(mCurrentDate);
                        cal.add(Calendar.DATE, -1);
                        Date time = cal.getTime();
                        if (time.before(date)) {
                            mViewHolder.tvDate.setOnClickListener(new SelectOnClickListener(mViewHolder.tvDate, position));
                        } else {
                            mViewHolder.tvDate.setTextColor(Color.parseColor("#FF968B8B"));
                        }


                    } else {
                        mViewHolder.tvDate.setOnClickListener(new SelectOnClickListener(mViewHolder.tvDate, position));
                    }
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date d = sdf.parse(startData);
                        Date date1 = datePlus(d, 0);
                        if (date1.getMonth() == date.getMonth() && date1.getYear() == date.getYear()) {
                            // 如果在当前年，当前月，比当前日期小的话
                            if (date1.before(date)) {
                                mViewHolder.tvDate.setOnClickListener(new SelectOnClickListener(mViewHolder.tvDate, position));
                            } else {
                                mViewHolder.tvDate.setTextColor(Color.parseColor("#FF968B8B"));
                            }
                        } else {
                            mViewHolder.tvDate.setOnClickListener(new SelectOnClickListener(mViewHolder.tvDate, position));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            } else if (position > endIndex) {
                mViewHolder.tvDate.setTextColor(Color.parseColor("#FF968B8B"));
            }
            if (aFlag) {
                if (!TextUtils.isEmpty(startData)) {
                    if (mCurrentSelectDate.getDate() == date.getDate() && mCurrentSelectDate.getMonth() == date.getMonth() && mCurrentSelectDate.getYear() == date.getYear()) {
                        mViewHolder.tvDate.setBackgroundResource(R.drawable.shape_round_textview);
                        mViewHolder.tvDate.setTextColor(Color.WHITE);
                    }
                }
            } else {
                if (!TextUtils.isEmpty(endData)) {
                    if (mCurrentSelectDate.getDate() == date.getDate() && mCurrentSelectDate.getMonth() == date.getMonth() && mCurrentSelectDate.getYear() == date.getYear()) {
                        mViewHolder.tvDate.setBackgroundResource(R.drawable.shape_round_textview);
                        mViewHolder.tvDate.setTextColor(Color.WHITE);
                    }
                }else {
                    if (mCurrentSelectDate.getDate() == date.getDate() && mCurrentSelectDate.getMonth() == date.getMonth() && mCurrentSelectDate.getYear() == date.getYear()) {
                        mViewHolder.tvDate.setBackgroundResource(R.drawable.shape_round_textview);
                        mViewHolder.tvDate.setTextColor(Color.WHITE);
                    }
                }
            }

            if (mCurrentSelectDate.getDate() == date.getDate() && mCurrentSelectDate.getMonth() == date.getMonth() && mCurrentSelectDate.getYear() == date.getYear()) {
                mViewHolder.tvDate.setBackgroundResource(R.drawable.shape_round_textview);
                mViewHolder.tvDate.setTextColor(Color.WHITE);
                newCalenderListener.onItemListener(mCurrentSelectDate);
            }

            return convertView;
        }

        class SelectOnClickListener implements OnClickListener {

            public SelectOnClickListener(View view, final int position) {

                view.setOnTouchListener(new OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            mCurrentSelectDate = mDates.get(position);
                            notifyDataSetChanged();
                            if (newCalenderListener != null) {
                                newCalenderListener.onItemListener(mCurrentSelectDate);
                            }
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onClick(View v) {

            }
        }

        class ViewHolder {
            private TextView tvDate;

            public ViewHolder(View view) {
                tvDate = (TextView) view.findViewById(R.id.calendar_item_day);
            }
        }
    }

}
