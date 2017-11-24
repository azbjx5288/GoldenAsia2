package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/5/4.
 */
public class RegChildRebate {
    /**
     * normal_rebate_options : [{"property_id":"1","property_name":"时时彩","options":[{"rebate":"0.120","prize":"1940"},{"rebate":"0.115","prize":"1930"}]},{"property_id":"2","property_name":"十一选五","options":[{"rebate":"0.085","prize":"1851.3"},{"rebate":"0.080","prize":"1841.4"}]}]
     * lhc_rebate_options : [{"method_id":583,"method_name":"特码直选","options":[{"rebate":"0.000","prize":"90"}]},{"method_id":584,"method_name":"特码生肖","options":[{"rebate":"0.000","prize":"22.4"}]}]
     */

    @SerializedName("normal_rebate_options")
    private List<NormalRebateOptions> normalRebateOptions;
    @SerializedName("lhc_rebate_options")
    private List<LhcRebateOptions> lhcRebateOptions;

    public void setNormalRebateOptions(List<NormalRebateOptions> normalRebateOptions) {
        this.normalRebateOptions = normalRebateOptions;
    }

    public void setLhcRebateOptions(List<LhcRebateOptions> lhcRebateOptions) {
        this.lhcRebateOptions = lhcRebateOptions;
    }

    public List<NormalRebateOptions> getNormalRebateOptions() {
        return normalRebateOptions;
    }

    public List<LhcRebateOptions> getLhcRebateOptions() {
        return lhcRebateOptions;
    }
}
