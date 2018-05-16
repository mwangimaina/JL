package appdata;

public class Survey {


    private String event_Id;
    private String event_date;
    private String start_time;
    private String stop_time;
    private String s_county;
    private String sub_county;
    private String s_village;
    private String area;
    private String s_activity;
    private String s_output;
    private int s_cost;
    private String facility_name;
    private String officer_name;
    private String facility_type ;
    private byte[] imageBytes1;
    private byte[] imageBytes2;

    public Survey(String event_Id, String event_date, String start_time, String stop_time, String s_county, String sub_county, String s_village, String area, String s_activity, String s_output, int s_cost, String facility_name, String officer_name, String facility_type, byte[] imageBytes1, byte[] imageBytes2) {
        this.event_Id = event_Id;
        this.event_date = event_date;
        this.start_time = start_time;
        this.stop_time = stop_time;
        this.s_county = s_county;
        this.sub_county = sub_county;
        this.s_village = s_village;
        this.area = area;
        this.s_activity = s_activity;
        this.s_output = s_output;
        this.s_cost = s_cost;
        this.facility_name = facility_name;
        this.officer_name = officer_name;
        this.facility_type = facility_type;
        this.imageBytes1 = imageBytes1;
        this.imageBytes2 = imageBytes2;
    }

    public String getEvent_Id() {
        return event_Id;
    }

    public void setEvent_Id(String event_Id) {
        this.event_Id = event_Id;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getS_county() {
        return s_county;
    }

    public void setS_county(String s_county) {
        this.s_county = s_county;
    }

    public String getSub_county() {
        return sub_county;
    }

    public void setSub_county(String sub_county) {
        this.sub_county = sub_county;
    }

    public String getS_village() {
        return s_village;
    }

    public void setS_village(String s_village) {
        this.s_village = s_village;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getS_activity() {
        return s_activity;
    }

    public void setS_activity(String s_activity) {
        this.s_activity = s_activity;
    }

    public String getS_output() {
        return s_output;
    }

    public void setS_output(String s_output) {
        this.s_output = s_output;
    }

    public int getS_cost() {
        return s_cost;
    }

    public void setS_cost(int s_cost) {
        this.s_cost = s_cost;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public String getFacility_type() {
        return facility_type;
    }

    public void setFacility_type(String facility_type) {
        this.facility_type = facility_type;
    }

    public byte[] getImageBytes1() {
        return imageBytes1;
    }

    public void setImageBytes1(byte[] imageBytes1) {
        this.imageBytes1 = imageBytes1;
    }

    public byte[] getImageBytes2() {
        return imageBytes2;
    }

    public void setImageBytes2(byte[] imageBytes2) {
        this.imageBytes2 = imageBytes2;
    }
}
