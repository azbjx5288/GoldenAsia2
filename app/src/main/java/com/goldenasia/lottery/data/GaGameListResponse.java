package com.goldenasia.lottery.data;

import java.util.List;

/**
 * Created by Gan on 2017/10/26.
 */
public class GaGameListResponse {


    /**
     * list : [{"ga_id":"39","user_id":"56911","username":"zdavy","top_id":"56911","belong_date":"2016-11-10","last_ga_balance":"0","ga_balance":"12010","game_id":"101","ga_buy_amount":"15700.00","ga_prize_amount":"17700.00","ga_rebate_amount":"0.00","ga_win_lose":"2000.00","frm":"1","ts":"2016-11-14 19:47:12","game_name":"游戏名称"}]
     * param : {"curPage":1,"sum":"3"}
     */

    private ParamBean param;
    private List<ListBean> list;

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ParamBean {
        /**
         * curPage : 1
         * sum : 3
         */

        private int curPage;
        private String sum;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }
    }

    public static class ListBean {
        /**
         * ga_id : 39
         * user_id : 56911
         * username : zdavy
         * top_id : 56911
         * belong_date : 2016-11-10
         * last_ga_balance : 0
         * ga_balance : 12010
         * game_id : 101
         * ga_buy_amount : 15700.00
         * ga_prize_amount : 17700.00
         * ga_rebate_amount : 0.00
         * ga_win_lose : 2000.00
         * frm : 1
         * ts : 2016-11-14 19:47:12
         * game_name : 游戏名称
         */

        private String ga_id;
        private String user_id;
        private String username;
        private String top_id;
        private String belong_date;
        private String last_ga_balance;
        private String ga_balance;
        private String game_id;
        private String ga_buy_amount;
        private String ga_prize_amount;
        private String ga_rebate_amount;
        private String ga_win_lose;
        private String frm;
        private String ts;
        private String game_name;

        public String getGa_id() {
            return ga_id;
        }

        public void setGa_id(String ga_id) {
            this.ga_id = ga_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTop_id() {
            return top_id;
        }

        public void setTop_id(String top_id) {
            this.top_id = top_id;
        }

        public String getBelong_date() {
            return belong_date;
        }

        public void setBelong_date(String belong_date) {
            this.belong_date = belong_date;
        }

        public String getLast_ga_balance() {
            return last_ga_balance;
        }

        public void setLast_ga_balance(String last_ga_balance) {
            this.last_ga_balance = last_ga_balance;
        }

        public String getGa_balance() {
            return ga_balance;
        }

        public void setGa_balance(String ga_balance) {
            this.ga_balance = ga_balance;
        }

        public String getGame_id() {
            return game_id;
        }

        public void setGame_id(String game_id) {
            this.game_id = game_id;
        }

        public String getGa_buy_amount() {
            return ga_buy_amount;
        }

        public void setGa_buy_amount(String ga_buy_amount) {
            this.ga_buy_amount = ga_buy_amount;
        }

        public String getGa_prize_amount() {
            return ga_prize_amount;
        }

        public void setGa_prize_amount(String ga_prize_amount) {
            this.ga_prize_amount = ga_prize_amount;
        }

        public String getGa_rebate_amount() {
            return ga_rebate_amount;
        }

        public void setGa_rebate_amount(String ga_rebate_amount) {
            this.ga_rebate_amount = ga_rebate_amount;
        }

        public String getGa_win_lose() {
            return ga_win_lose;
        }

        public void setGa_win_lose(String ga_win_lose) {
            this.ga_win_lose = ga_win_lose;
        }

        public String getFrm() {
            return frm;
        }

        public void setFrm(String frm) {
            this.frm = frm;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }
    }
}
