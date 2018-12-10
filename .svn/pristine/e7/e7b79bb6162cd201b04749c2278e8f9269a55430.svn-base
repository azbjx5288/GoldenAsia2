package com.goldenasia.lottery.component;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * Created by ACE-PC on 2018/10/1.
 */
public abstract class StickyHeaderAdapter extends BaseAdapter implements OnItemClickListener {

    private int mCount = -1;

    public abstract int sectionCounts();

    public abstract int rowCounts(int section);

    public abstract View getRowView(int section, int row, View convertView, ViewGroup parent);

    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        return null;
    }

    public abstract Object getRowItem(int section, int row);

    public boolean hasSectionHeaderView(int section) {
        return false;
    }

    public Object getSectionHeaderItem(int section) {
        return null;
    }

    public int getRowViewTypeCount() {
        return 1;
    }

    public int getSectionHeaderViewTypeCount() {
        return 1;
    }

    /**
     * 必须返回介于0和getRowViewTypeCount（）之间的值（已排除）
     */
    public int getRowItemViewType(int section, int row) {
        return 0;
    }

    /**
     * 必须返回介于0和getSectionHeaderViewTypeCount（）之间的值（排除，如果> 0）
     */
    public int getSectionHeaderItemViewType(int section) {
        return 0;
    }

    @Override
    /**
     * 调用onRowItemClick
     */
    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onRowItemClick(parent, view, getSection(position), getRowInSection(position), id);
    }

    public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {

    }

    @Override
    public final int getCount() {
        if (mCount < 0) {
            mCount = numberOfCellsBeforeSection(sectionCounts());
        }
        return mCount;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public final Object getItem(int position) {
        int section = getSection(position);
        if (isSectionHeader(position)) {
            if (hasSectionHeaderView(section)) {
                return getSectionHeaderItem(section);
            }
            return null;
        }
        return getRowItem(section, getRowInSection(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        int section = getSection(position);
        if (isSectionHeader(position)) {
            if (hasSectionHeaderView(section)) {
                return getSectionHeaderView(section, convertView, parent);
            }
            return null;
        }
        return getRowView(section, getRowInSection(position), convertView, parent);
    }

    protected int getSection(int position) {
        int section = 0;
        int cellCounter = 0;
        while (cellCounter <= position && section <= sectionCounts()) {
            cellCounter += numberOfCellsInSection(section);
            section++;
        }
        return section - 1;
    }

    /**
     * 返回指定单元格的行索引不应调用
     * 指向部分标题的位置
     */
    protected int getRowInSection(int position) {
        int section = getSection(position);
        int row = position - numberOfCellsBeforeSection(section);
        if (hasSectionHeaderView(section)) {
            return row - 1;
        } else {
            return row;
        }
    }

    /**
     * 如果此索引处的单元格是节标题，则返回true
     */
    protected boolean isSectionHeader(int position) {
        int section = getSection(position);
        return hasSectionHeaderView(section) && numberOfCellsBeforeSection(section) == position;
    }

    /**
     * 返回指示之前的单元格数（=标题+行）
     * 部分
     */
    protected int numberOfCellsBeforeSection(int section) {
        int count = 0;
        for (int i = 0; i < Math.min(sectionCounts(), section); i++) {
            count += numberOfCellsInSection(i);
        }
        return count;
    }

    private int numberOfCellsInSection(int section) {
        return rowCounts(section) + (hasSectionHeaderView(section) ? 1 : 0);
    }

    @Override
    public void notifyDataSetChanged() {
        mCount = numberOfCellsBeforeSection(sectionCounts());
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        mCount = numberOfCellsBeforeSection(sectionCounts());
        super.notifyDataSetInvalidated();
    }

    @Override
    public final int getItemViewType(int position) {
        int section = getSection(position);
        if (isSectionHeader(position)) {
            return getRowViewTypeCount() + getSectionHeaderItemViewType(section);
        } else {
            return getRowItemViewType(section, getRowInSection(position));
        }
    }

    @Override
    public final int getViewTypeCount() {
        return getRowViewTypeCount() + getSectionHeaderViewTypeCount();
    }

    @Override
    public boolean isEnabled(int position) {
        return (disableHeaders() || !isSectionHeader(position)) && isRowEnabled(getSection(position), getRowInSection(position));
    }

    public boolean disableHeaders() {
        return false;
    }

    public boolean isRowEnabled(int section, int row) {
        return true;
    }
}
