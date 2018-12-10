package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alashi on 2016/1/22.
 */
public class BetDetailResponse {

    private String came;
    private float prizeMode;
    private String openCode;
    @SerializedName("package")
    private Bet bet;
    private List<Projects> projects;

    public String getCame() {
        return came;
    }

    public float getPrizeMode() {
        return prizeMode;
    }

    public String getOpenCode() {
        return openCode;
    }

    public Bet getBet() {
        return bet;
    }

    public List<Projects> getProjects() {
        return projects;
    }

    public static class Projects {
        private String cname;
        private String code;
        @SerializedName("method_id")
        private int methodId;
        @SerializedName("single_num")
        private int singleNum;
        @SerializedName("cur_rebate")
        private double curRebate;
        private List<PrizeMode> prizeMode;

        public String getCname() {
            return cname;
        }

        public String getCode() {
            return code;
        }

        public int getMethodId() {
            return methodId;
        }

        public int getSingleNum() {
            return singleNum;
        }

        public double getCurRebate() {
            return curRebate;
        }

        public List<PrizeMode> getPrizeMode() {
            return prizeMode;
        }

        public static class PrizeMode {

            /**
             * level : 1
             * prize : 194
             */

            @SerializedName("level")
            private String level;
            @SerializedName("prize")
            private String prize;
            @SerializedName("cur_rebate")
            private String curRebate;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getPrize() {
                return prize;
            }

            public void setPrize(String prize) {
                this.prize = prize;
            }
    
            public String getCurRebate()
            {
                return curRebate;
            }
    
            public void setCurRebate(String curRebate)
            {
                this.curRebate = curRebate;
            }
        }
    }
}
