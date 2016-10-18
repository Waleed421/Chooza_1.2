package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class Program_University {
    private int Program_Uni_ID;
    private int Program_ID;
    private int University_ID;
    private int Fee_ID;
    private String HSSC_Criteria;
    private String SSC_Criteria;
    private String Reference;

    public int getProgram_Uni_ID() {
        return Program_Uni_ID;
    }

    public void setProgram_Uni_ID(int program_Uni_ID) {
        Program_Uni_ID = program_Uni_ID;
    }

    public int getProgram_ID() {
        return Program_ID;
    }

    public void setProgram_ID(int program_ID) {
        Program_ID = program_ID;
    }

    public int getUniversity_ID() {
        return University_ID;
    }

    public void setUniversity_ID(int university_ID) {
        University_ID = university_ID;
    }

    public int getFee_ID() {
        return Fee_ID;
    }

    public void setFee_ID(int fee_ID) {
        Fee_ID = fee_ID;
    }

    public String getHSSC_Criteria() {
        return HSSC_Criteria;
    }

    public void setHSSC_Criteria(String HSSC_Criteria) {
        this.HSSC_Criteria = HSSC_Criteria;
    }

    public String getSSC_Criteria() {
        return SSC_Criteria;
    }

    public void setSSC_Criteria(String SSC_Criteria) {
        this.SSC_Criteria = SSC_Criteria;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }
}
