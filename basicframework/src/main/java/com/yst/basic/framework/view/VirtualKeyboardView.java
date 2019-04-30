package com.yst.basic.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.yst.basic.framework.R;
import com.yst.basic.framework.adapter.KeyBoardAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟键盘
 *
 * @author lixiangchao
 * @date 2017/11/30
 * @version 1.0.1
 */
public class VirtualKeyboardView extends RelativeLayout {

    Context context;
    /**
     * 键盘展示的数量
     */
    public static final int KEYBOARD_COUNT = 12;

    /**
     * 用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能
     */
    private GridView gridView;

    /**
     * 因为要用Adapter中适配，用数组不能往adapter中填充
     */
    private ArrayList<Map<String, String>> valueList;

    private RelativeLayout layoutBack;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);

        valueList = new ArrayList<>();

        layoutBack = view.findViewById(R.id.layoutBack);

        gridView = view.findViewById(R.id.gv_keybord);

        initValueList();

        setupView();

        //必须要，不然不显示控件
        addView(view);
    }

    public RelativeLayout getLayoutBack() {
        return layoutBack;
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {
        // 初始化按钮上应该显示的数字
        for (int i = 0; i < KEYBOARD_COUNT; i++) {
            Map<String, String> map = new HashMap<>(16);
            if (i < 9) {
                map.put("name", String.valueOf(i));
            } else if (i == 9) {
                map.put("name", ".");
            } else if (i == 10) {
                map.put("name", String.valueOf(0));
            } else if (i == 11) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    public GridView getGridView() {
        return gridView;
    }

    private void setupView() {
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }
}
