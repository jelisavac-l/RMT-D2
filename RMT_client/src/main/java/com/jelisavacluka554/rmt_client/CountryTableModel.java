package com.jelisavacluka554.rmt_client;

import com.jelisavacluka554.rmt_common.domain.ApplicationItem;
import com.jelisavacluka554.rmt_common.domain.EUCountry;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author luka
 */
public class CountryTableModel extends AbstractTableModel {

    private List<EUCountry> lc;

    String[] cols = {"#", "Country"};

    @Override
    public String getColumnName(int index) {
        return cols[index];
    }

    public CountryTableModel(List<EUCountry> lai) {
        this.lc = lai;
    }

    @Override
    public int getRowCount() {
        return lc.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch (i1) {
            case 0:
                return i + 1;
            case 1:
                return lc.get(i);
        }
        return null;
    }

}
