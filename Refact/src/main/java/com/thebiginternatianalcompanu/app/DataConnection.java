package com.thebiginternatianalcompanu.app;


public interface DataConnection
{
    String[] readRecord();
    void writeRecord(String ... record);
    void close();
}
