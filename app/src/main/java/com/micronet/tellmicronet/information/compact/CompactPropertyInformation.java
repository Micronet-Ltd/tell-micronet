package com.micronet.tellmicronet.information.compact;

/**
 * Created by austin.oneil on 9/26/2018.
 */

public class CompactPropertyInformation extends CompactInformation {
    String property;
    public CompactPropertyInformation(String property) {
        this.property = property;
    }

    @Override
    public String retrieveInfo() {
        return System.getProperty(property);
    }
}
