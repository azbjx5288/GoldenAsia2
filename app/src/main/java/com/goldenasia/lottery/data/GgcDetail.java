package com.goldenasia.lottery.data;

/**
 * Created by Sakura on 2016/10/10.
 */

public class GgcDetail
{
    private String sc_id;
    private String user_id;
    private String scb_id;
    private String scrape_type;
    private String amount;
    private String prize;
    private String create_time;
    private String sale_time;
    private String open_time;
    private String status;
    private String ts;
    private String serial_num;

    private GgcDetailNumber numbers;

    public GgcDetailNumber getNumbers()
    {
        return numbers;
    }

    public void setNumbers(GgcDetailNumber numbers)
    {
        this.numbers = numbers;
    }

    public String getSc_id() { return sc_id;}

    public void setSc_id(String sc_id) { this.sc_id = sc_id;}

    public String getUser_id() { return user_id;}

    public void setUser_id(String user_id) { this.user_id = user_id;}

    public String getScb_id() { return scb_id;}

    public void setScb_id(String scb_id) { this.scb_id = scb_id;}

    public String getScrape_type() { return scrape_type;}

    public void setScrape_type(String scrape_type) { this.scrape_type = scrape_type;}

    public String getAmount() { return amount;}

    public void setAmount(String amount) { this.amount = amount;}

    public String getPrize() { return prize;}

    public void setPrize(String prize) { this.prize = prize;}

    public String getCreate_time() { return create_time;}

    public void setCreate_time(String create_time) { this.create_time = create_time;}

    public String getSale_time() { return sale_time;}

    public void setSale_time(String sale_time) { this.sale_time = sale_time;}

    public String getOpen_time() { return open_time;}

    public void setOpen_time(String open_time) { this.open_time = open_time;}

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    public String getTs() { return ts;}

    public void setTs(String ts) { this.ts = ts;}

    public String getSerial_num() { return serial_num;}

    public void setSerial_num(String serial_num) { this.serial_num = serial_num;}
}
