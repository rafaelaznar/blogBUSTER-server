package net.ausiasmarch.bean;

import com.google.gson.annotations.Expose;


public class ResponseBean {
    @Expose
    private int status;
    @Expose
    private String message;

    public ResponseBean(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    
}
