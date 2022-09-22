package com.rapipay.qrsicici.sequencegeneration;

public class Sequence {
    private String sequenceName;
    private String aucounter;
    private String icicicounter;

    public Sequence() {
    }

    public Sequence(String sequenceName, String aucounter, String icicicounter) {
        this.sequenceName = sequenceName;
        this.aucounter = aucounter;
        this.icicicounter = icicicounter;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getAucounter() {
        return aucounter;
    }

    public void setAucounter(String aucounter) {
        this.aucounter = aucounter;
    }

    public String getIcicicounter() {
        return icicicounter;
    }

    public void setIcicicounter(String icicicounter) {
        this.icicicounter = icicicounter;
    }

    @Override
    public String toString() {
        return "Sequence{" +
                "sequenceName='" + sequenceName + '\'' +
                ", aucounter='" + aucounter + '\'' +
                ", icicicounter='" + icicicounter + '\'' +
                '}';
    }
}
