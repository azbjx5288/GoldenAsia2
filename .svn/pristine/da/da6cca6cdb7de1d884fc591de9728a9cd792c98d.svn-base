package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/5/4.
 */
public class NormalRebateOptions {
    /**
     * property_id : 1
     * property_name : 时时彩
     * options : [{"rebate":"0.115","prize":1930},{"rebate":"0.110","prize":1920},{"rebate":"0.105","prize":1910},{"rebate":"0.100","prize":1900},{"rebate":"0.095","prize":1890},{"rebate":"0.090","prize":1880},{"rebate":"0.085","prize":1870},{"rebate":"0.080","prize":1860},{"rebate":"0.075","prize":1850},{"rebate":"0.070","prize":1840},{"rebate":"0.065","prize":1830},{"rebate":"0.060","prize":1820},{"rebate":"0.055","prize":1810},{"rebate":"0.050","prize":1800},{"rebate":"0.045","prize":1790},{"rebate":"0.040","prize":1780},{"rebate":"0.035","prize":1770},{"rebate":"0.030","prize":1760},{"rebate":"0.025","prize":1750},{"rebate":"0.020","prize":1740},{"rebate":"0.015","prize":1730},{"rebate":"0.010","prize":1720},{"rebate":"0.005","prize":1710},{"rebate":"0.000","prize":1700}]
     */

    @SerializedName("property_id")
    private int propertyId;
    @SerializedName("property_name")
    private String propertyName;
    @SerializedName("options")
    private List<Options> options;
    private double selected=-1;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }
    
    public double getSelected()
    {
        return selected;
    }
    
    public void setSelected(double selected)
    {
        this.selected = selected;
    }
}
