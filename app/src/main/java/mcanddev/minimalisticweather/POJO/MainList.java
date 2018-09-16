package mcanddev.minimalisticweather.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainList {

    @SerializedName("predictions")
    @Expose
    private List<Description> predictions = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Description> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Description> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
