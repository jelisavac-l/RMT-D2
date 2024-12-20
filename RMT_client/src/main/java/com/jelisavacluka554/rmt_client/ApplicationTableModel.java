package com.jelisavacluka554.rmt_client;

import com.jelisavacluka554.rmt_common.domain.Application;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author luka
 */
public class ApplicationTableModel extends AbstractTableModel {
    
    List<Application> la;
    String[] cols = {"Code", "Appl. Date", "Entry Date", "Ddn. of stay", "Transport", "Countries", "Status"};

    public ApplicationTableModel(List<Application> la) {
        this.la = la;
    }

    @Override
    public int getRowCount() {
        return la.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    
    @Override
    public String getColumnName(int index) {
        return cols[index];
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch(i1) {
            case 0: return la.get(i).getId(); 
            case 1: return la.get(i).getDateOfApplication();
            case 2: return la.get(i).getDateOfEntry();
            case 3: return la.get(i).getDuration();
            case 4: return la.get(i).getTransport().getName();
            case 5: return la.get(i).getItems();
            case 6: return la.get(i).getStatus();
        }
        return null;
    }
    
    
}
